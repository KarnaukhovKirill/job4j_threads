package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFinderIndex extends RecursiveTask<Integer> {
    private final Integer[] array;
    private final int from;
    private final int to;
    private final int number;

    public ParallelFinderIndex() {
        this.array = null;
        this.from = 0;
        this.to = 0;
        this.number = 0;
    }

    public ParallelFinderIndex(Integer[] array, int from, int to, int number) {
        this.array = array;
        this.number = number;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return indexOf(from, to);
        }
        int mid = (from + to) / 2;
        ParallelFinderIndex leftFinder = new ParallelFinderIndex(array, from, mid, number);
        ParallelFinderIndex rightFinder = new ParallelFinderIndex(array, mid + 1, to, number);
        leftFinder.fork();
        rightFinder.fork();
        var left = leftFinder.join();
        var right = rightFinder.join();
        if (Objects.equals(left, right)) {
            return left;
        } else if (left == -1) {
            return right;
        } else {
            return left;
        }
    }

    private int indexOf(int from, int to) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i] == number) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public Integer find(Integer[] array, int number) {
        ForkJoinPool fjp = new ForkJoinPool();
        return fjp.invoke(new ParallelFinderIndex(array, 0, array.length - 1, number));
    }
}
