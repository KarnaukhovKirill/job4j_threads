package ru.job4j.pools;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class ParallelFinderIndexTest {
    private final ParallelFinderIndex finder = new ParallelFinderIndex();

    @Test
    public void whenEvenArrayLastNumber() {
        Integer[] integers = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        var rsl = finder.find(integers, 11);
        assertThat(rsl, is(11));
    }

    @Test
    public void whenNotEvenArrayLastNumber() {
        Integer[] integers = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        var rsl = finder.find(integers, 12);
        assertThat(rsl, is(12));
    }

    @Test
    public void whenNumberLessThan10() {
        Integer[] integers = new Integer[] {0, 1, 2};
        var rsl = finder.find(integers, 2);
        assertThat(rsl, is(2));
    }

    @Test
    public void whenGotSameNumbersInArray() {
        Integer[] integers = new Integer[] {0, 1, 2, 1, 0};
        var rsl = finder.find(integers, 1);
        assertThat(rsl, is(1));
    }

    @Test
    public void whenGotSameNumbersInBigArray() {
        Integer[] integers = new Integer[] {0, 2, 2, 1, 0, 1, 1, 1, 7, 1, 12, 1};
        var rsl = finder.find(integers, 1);
        assertThat(rsl, is(3));
    }

    @Test
    public void whenNotFound() {
        Integer[] integers = new Integer[] {0, 1, 2, 1, 0};
        var rsl = finder.find(integers, 999);
        assertThat(rsl, is(-1));
    }
}