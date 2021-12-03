package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс подсчитывает кол-во раз, когда сосед начинает сверлить стену вне положенного времени.
 * После определённого порога, когда count становиться больше total,
 * вступает в силу метод {@link CountBarrier#killNeighbor()}.
 */
@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    /**
     * Подсчитываем кол-во и будим все нити монитора monitor
     */
    public void count() {
        synchronized (monitor) {
            System.out.printf("Сосед сверху сверлит стену %d-й раз\n", count++);
            monitor.notifyAll();
        }
    }

    /**
     * Пришло время расплаты. Подставляем свой метод борьбы с таким соседом, включаем фантазию.
     */
    public void killNeighbor() {
        synchronized (monitor) {
            var nameMethod = new Throwable()
                    .getStackTrace()[0]
                    .getMethodName();
            System.out.println(nameMethod);
        }
    }

    /**
     * Метод сравнивает count с пороговым значением. Если count < total, терпим до следующего раза.
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Метод демонстрирует работу {@link CountBarrier}.
     * @param args входящие параметры
     */
    public static void main(String[] args) {
        CountBarrier cb = new CountBarrier(5);
        Thread counterThread = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        cb.count();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        Thread waitingThread = new Thread(
                () -> {
                    cb.await();
                    cb.killNeighbor();
                }
        );
        counterThread.start();
        waitingThread.start();
    }
}
