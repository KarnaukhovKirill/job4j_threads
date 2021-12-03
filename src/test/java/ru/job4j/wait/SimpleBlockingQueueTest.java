package ru.job4j.wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenQueueIsFull() throws InterruptedException {
        int maxSize = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(maxSize);
        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    sbq.offer(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        Thread.sleep(100);
        assertThat(sbq.size(), is(maxSize));
    }

    @Test
    public void whenPoll() throws InterruptedException {
        int maxSize = 3;
        List<Integer> buffer = new ArrayList<>();
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(maxSize);
        Thread producer = new Thread(() -> {
            try {
                sbq.offer(1);
                sbq.offer(2);
                sbq.offer(3);
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add(sbq.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        Thread.sleep(1000);
        consumer.start();
        Thread.sleep(1000);
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(List.of(1, 2, 3)));
    }
}