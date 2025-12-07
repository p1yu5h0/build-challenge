package com.piyush.assignment1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWaitNotify {
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static void main(String[] args) throws InterruptedException {
        demo();
    }

    private final List<Integer> queue;
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public ProducerConsumerWaitNotify(int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayList<>(capacity);
    }

    public void produce(Integer item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                log("Queue FULL - producer WAITING...");
                notFull.await();
            }
            queue.add(item);
            log("Produced " + item + " (queue=" + queue.size() + "/" + capacity + ")");
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Integer consume() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                log("Queue EMPTY - consumer WAITING...");
                notEmpty.await();
            }
            Integer item = queue.remove(0);
            log("✓ Consumed " + item + " (queue=" + queue.size() + "/" + capacity + ")");
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }

    static void log(String message) {
        String time = LocalTime.now().format(TIME_FORMATTER);
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + time + "][" + threadName + "] " + message);
    }

    public static void demo() throws InterruptedException {
        System.out.println("\n WAIT/NOTIFY PRODUCER-CONSUMER DEMO \n");
        ProducerConsumerWaitNotify buffer = new ProducerConsumerWaitNotify(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "WAIT-PRODUCER");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "WAIT-CONSUMER");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Wait/Notify demo complete!");
    }
}