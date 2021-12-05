package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        var ref =  count.get();
        int expected;
        do {
            expected = ref + 1;
        }
        while (!count.compareAndSet(ref, expected));
    }

    public int get() {
        return count.get();
    }
}
