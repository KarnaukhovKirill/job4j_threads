package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ExampleTest {

    @Test
    public void testCount() {
        Example example = new Example();
        var result = example.count(10);
        assertThat(result, is(20));
    }
}