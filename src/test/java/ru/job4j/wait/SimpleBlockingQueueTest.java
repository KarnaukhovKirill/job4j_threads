package ru.job4j.wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenQueueIsFull() throws InterruptedException {
        int maxSize = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(maxSize);
        Thread producer = new Thread(() -> {
            while (true) {
                sbq.offer(10);
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
            sbq.offer(1);
            sbq.offer(2);
            sbq.offer(3);
        });
        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                buffer.add(sbq.poll());
            }
        });
        producer.start();
        Thread.sleep(100);
        producer.interrupt();
        consumer.start();
        Thread.sleep(1000);
        assertThat(buffer, is(List.of(1, 2, 3)));
    }
}