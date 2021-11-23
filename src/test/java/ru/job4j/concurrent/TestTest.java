package ru.job4j.concurrent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;

public class TestTest {

    @Test
    public void testCount() {
        TestExample testExample = new TestExample();
        int result = testExample.count(10);
        assertEquals(result, 20);
    }
}