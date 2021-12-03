package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final Object monitor = this;
    @GuardedBy("monitor")
    private final Queue<T> queue = new LinkedList<>();
    private final int count;

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            while (count == queue.size()) {
                    monitor.wait();
            }
            queue.add(value);
            monitor.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() == 0) {
                    monitor.wait();
            }
            monitor.notifyAll();
            return queue.poll();
        }
    }

    public int size() {
        synchronized (monitor) {
            return queue.size();
        }

    }
}
