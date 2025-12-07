package com.piyush.assignment1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class ProducerConsumerApp {

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static void main(String[] args) {
        // Source and destination "containers"
        List<Integer> sourceData = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> destinationData = new ArrayList<>();
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3); //// Shared blocking queue with fixed capacity

        log("Source container      : " + sourceData);
        log("Initial queue size    : " + queue.size());

        Producer producer = new Producer(queue, sourceData); // create producer
        Consumer consumer = new Consumer(queue, destinationData); //create consumer
        Thread producerThread = new Thread(producer, "producer-thread");
        Thread consumerThread = new Thread(consumer, "consumer-thread");

        log("Starting threads");
        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted");
        }

        System.out.println("Source container : " + sourceData);
        System.out.println("Destination container : " + destinationData);
        System.out.println("All items transferred successfully");
    }

    static void log(String message) {
        String time = LocalTime.now().format(TIME_FORMATTER);
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + time + "][" + threadName + "] " + message);
    }

    public static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final List<Integer> source;
        public static final int POISON_PILL = Integer.MIN_VALUE;
        public Producer(BlockingQueue<Integer> queue, List<Integer> source) {
            this.queue = queue;
            this.source = source;
        }

        @Override
        public void run() {
            try {
                for (Integer item : source) {
                    produce(item);
                }
            } finally {
                try {
                    queue.put(POISON_PILL);
                    log("Producer sent POISON_PILL, signaling no more items");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log("Producer interrupted while sending POISON_PILL");
                }
            }
        }

        private void produce(Integer item){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
                log("About to put item " + item + " into queue (current size=" + queue.size() + ")");
                queue.put(item);
                log("Put item " + item + " into queue (new size=" + queue.size() + ")");
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Producer interrupted while producing item " + item);
            }
        }
    }

    public static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final List<Integer> destination;
        public Consumer(BlockingQueue<Integer> queue, List<Integer> destination) {
            this.queue = queue;
            this.destination = destination;

        }
        @Override
        public void run() {
            while (true) {
                try {
                    log("Waiting to take item from queue (current size=" + queue.size() + ")");
                    Integer item = queue.take();
                    log("Took item " + item + " from queue (new size=" + queue.size() + ")");

                    if (item.equals(Producer.POISON_PILL)) {
                        log("Received POISON_PILL, consumer will stop");
                        queue.put(item); // keep pill for any other consumers
                        break;
                    }

                    consume(item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log("Consumer interrupted");
                    break;
                }
            }
        }

        private void consume(Integer item){
            try {
                log("Processing item " + item);
                TimeUnit.MILLISECONDS.sleep(70);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Consumer interrupted while processing item " + item);
            }
            destination.add(item);
            log("Finished processing item " + item + ", destination=" + destination);
        }

        public List<Integer> getDestination(){
            return destination;
        }
    }
}
