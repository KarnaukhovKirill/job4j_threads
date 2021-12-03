package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList() {
        this.list = new ArrayList<>();
    }

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public void add(T value) {
        synchronized (this) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (this) {
            return list.get(index);
        }
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (this) {
            return copy(this.list).iterator();
        }
    }

    private List<T> copy(List<T> list) {
        synchronized (this) {
            return new ArrayList<>(list);
        }
    }
}
