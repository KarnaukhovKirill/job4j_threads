package ru.job4j.pool;

/**
 * Демонстрация работы класса ThreadPool.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Runnable runnable = () -> {
            try {
                var threadName = Thread.currentThread().getName();
                System.out.println(" выполняет " + threadName);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        };
        for (int i = 0; i < 100; i++) {
            System.out.print("задачу №" + i);
            threadPool.work(runnable);
        }
        threadPool.shutdown();
    }
}
