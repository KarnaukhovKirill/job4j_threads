package ru.job4j.thread;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CountTest {

    private class ThreadCount extends Thread {
        private final Count count;

        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void testIncrement() {
        final Count count = new Count();
        ThreadCount[] threads = new ThreadCount[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new ThreadCount(count);
        }
            Stream.of(threads).forEach(Thread::start);
            Stream.of(threads).forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        assertThat(count.get(), is(100));
    }
}