package ru.job4j.store;

public interface Store<T> {

    boolean add(T item);
    boolean update(T item);
    boolean delete(T item);
}
