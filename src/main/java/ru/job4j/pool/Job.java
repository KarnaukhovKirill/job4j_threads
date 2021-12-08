package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

public class Job extends Thread {
    SimpleBlockingQueue<Runnable> sbq;

    public Job(SimpleBlockingQueue<Runnable> sbq) {
        this.sbq = sbq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                sbq.poll().run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
