package ru.job4j.store;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import java.util.stream.Stream;

public class UserStoreTest {
    private UserStore store;

    @Before
    public void init() {
        store = new UserStore();
    }

    @Test
    public void testAdd() {
        User firstUser = new User(1, 100);
        User secondUser = new User(2, 200);
        store.add(firstUser);
        assertThat(store.add(secondUser), is(true));
    }

    @Test
    public void whenAddSomeIds() {
        User firstUser = new User(1, 100);
        store.add(firstUser);
        var rsl = store.add(new User(1, 10000000));
        assertThat(rsl, is(false));
    }

    @Test
    public void testUpdate() {
        User firstUser = new User(1, 100);
        User secondUser = new User(1, 200);
        store.add(firstUser);
        var rsl = store.update(secondUser);
        assertThat(rsl, is(true));
    }

    @Test
    public void testMultithreadingUpdate() {
        User firstUser = new User(1, 0);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> store.update(new User(1, 100)));
        }
        store.add(firstUser);
        Stream.of(threads).forEach(Thread::start);
        Stream.of(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        var updatedUser = store.findAll().get(0);
        assertThat(updatedUser.getAmount(), is(100));
    }

    @Test
    public void testDelete() {
        User firstUser = new User(1, 100);
        assertThat(store.add(firstUser), is(true));
        assertThat(store.delete(firstUser), is(true));
        assertTrue(store.findAll().isEmpty());
    }

    @Test
    public void whenTransferIsRight() {
        User first = new User(1, 100);
        User second = new User(2, 200);
        store.add(first);
        store.add(second);
        assertTrue(store.transfer(1, 2, 50));
    }

    @Test
    public void whenMultithreadingTransfer() {
        User first = new User(1, 101);
        User second = new User(2, 0);
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> store.transfer(1, 2, 1));
        }
        store.add(first);
        store.add(second);
        Stream.of(threads).forEach(Thread::start);
        Stream.of(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertTrue(store.delete(first));
        assertThat(first.getAmount(), is(1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenPayerDontExists() {
        User first = new User(1, 101);
        User second = new User(2, 0);
        store.add(first);
        store.add(second);
        store.transfer(1, 10000, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenPaymentRecieverDontExists() {
        User first = new User(1, 101);
        User second = new User(2, 0);
        store.add(first);
        store.add(second);
        store.transfer(10000, 2, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNotEnoughMoney() {
        User first = new User(1, 101);
        User second = new User(2, 0);
        store.add(first);
        store.add(second);
        store.transfer(1, 2, 1000);
    }
}