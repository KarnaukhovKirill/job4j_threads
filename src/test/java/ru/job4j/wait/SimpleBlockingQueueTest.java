package ru.job4j.wait;

import org.junit.Test;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenQueueIsFull() throws InterruptedException {
        int maxSize = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(maxSize);
        Thread producer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    sbq.offer(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        Thread.sleep(100);
        producer.interrupt();
        producer.join();
        assertThat(sbq.size(), is(maxSize));
    }

    @Test
    public void job4jTest() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5)
                        .forEach(
                                i -> {
                            try {
                                queue.offer(i);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        })
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void arrayOfferPollTest() throws InterruptedException {
        String[] words = new String[] {"Java", "is", "so", "hard", ",", "but", "I", "love", "it"};
        SimpleBlockingQueue<String> sbq = new SimpleBlockingQueue<>(3);
        String[] buffer = new String[words.length];
        Thread producer = new Thread(
                () -> Arrays.stream(words).forEach(
                        i -> {
                    try {
                        sbq.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!sbq.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        for (int i = 0; i < buffer.length; i++) {
                            try {
                                buffer[i] = sbq.poll();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(words, is(buffer));
    }
}