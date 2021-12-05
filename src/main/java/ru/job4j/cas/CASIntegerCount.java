package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Вариант решения, где используется встроенный класс {@link AtomicInteger}
 */
@ThreadSafe
public class CASIntegerCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }
}
