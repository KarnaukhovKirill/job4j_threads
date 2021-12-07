package ru.job4j.cache;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CacheTest {
    public Cache cache;

    @Before
    public void initCache() {
        cache = new Cache();
    }

    @Test
    public void testAdd() {
        Base user1 = new Base(0, 0);
        Base user2 = new Base(1, 0);
        Base user3 = new Base(2, 0);
        assertTrue(cache.add(user1));
        assertTrue(cache.add(user2));
        assertTrue(cache.add(user3));
        assertFalse(cache.add(user1));
        assertThat(cache.size(), is(3));
    }

    @Test
    public void testUpdate() {
        Base user1 = new Base(0, 0);
        cache.add(user1);
        Base fromDBUser1 = new Base(0, 0);
        assertTrue(cache.update(fromDBUser1));
    }

    /**
     * Метод computeIfPresent() должен return false, так как нет записи в кеш под id = 1;
     */
    @Test
    public void whenUpdateIsFalse() {
        Base user1 = new Base(0, 0);
        cache.add(user1);
        Base fromDBUser1 = new Base(1, 1);
        assertFalse(cache.update(fromDBUser1));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateThrowException() {
        Base user1 = new Base(0, 0);
        cache.add(user1);
        Base fromDBUser1 = new Base(0, 1);
        cache.update(fromDBUser1);
    }

    @Test
    public void whenDelete() {
        Base user1 = new Base(0, 0);
        cache.add(user1);
        cache.delete(user1);
        assertThat(cache.size(), is(0));
    }
}