package ru.job4j.concurrent;

import java.util.Random;

public class ThreadState {
    public static void main(String[] args) {
        Random random = new Random();
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                && second.getState() != Thread.State.TERMINATED) {
            System.out.println(random.nextInt(100));
        }
        System.out.println("Работа завершена");
    }
}
