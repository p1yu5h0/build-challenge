package com.piyush.assignment1;  // Use your actual package

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProducerConsumerAppTest {

    @Test
    @Timeout(5)
    void allItemsTransferredInOrder() throws InterruptedException {
        List<Integer> source = Arrays.asList(10, 20, 30, 40, 50);
        List<Integer> destination = new ArrayList<>();
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

        ProducerConsumerApp.Producer producer = new ProducerConsumerApp.Producer(queue, source);
        ProducerConsumerApp.Consumer consumer = new ProducerConsumerApp.Consumer(queue, destination);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        assertEquals(source.size(), destination.size(),
                "All items must transfer from source to destination");
        assertIterableEquals(source, destination,
                "Items must maintain original order");
        // REMOVED: queue.isEmpty() check - poison pill stays in queue by design
    }

    @Test
    @Timeout(3)
    void consumerStopsOnPoisonPill() throws InterruptedException {
        List<Integer> source = Arrays.asList(1, 2, 3);
        List<Integer> destination = new ArrayList<>();
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

        ProducerConsumerApp.Producer producer = new ProducerConsumerApp.Producer(queue, source);
        ProducerConsumerApp.Consumer consumer = new ProducerConsumerApp.Consumer(queue, destination);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        assertIterableEquals(source, destination,
                "Consumer must process all items before poison pill");
    }

    @Test
    @Timeout(5)
    void poisonPillRemainsInQueueForOtherConsumers() throws InterruptedException {
        List<Integer> source = Arrays.asList(100);
        List<Integer> destination = new ArrayList<>();
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

        ProducerConsumerApp.Producer producer = new ProducerConsumerApp.Producer(queue, source);
        ProducerConsumerApp.Consumer consumer = new ProducerConsumerApp.Consumer(queue, destination);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        assertIterableEquals(source, destination);
        assertEquals(1, queue.size(), "Poison pill should remain in queue");
        assertEquals(ProducerConsumerApp.Producer.POISON_PILL, queue.peek(),
                "Queue should contain poison pill after single consumer");
    }
}
