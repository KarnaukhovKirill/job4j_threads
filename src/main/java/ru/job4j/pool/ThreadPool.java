package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int coreCount;
    private final List<Thread> threads;
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        coreCount = Runtime.getRuntime().availableProcessors();
        threads = new LinkedList<>();
        tasks = new SimpleBlockingQueue<>();
        init();
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    private void init() {
        for (int i = 0; i != coreCount; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        tasks.poll().run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            thread.start();
            threads.add(thread);
        }
    }
}
