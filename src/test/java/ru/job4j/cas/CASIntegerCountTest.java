package ru.job4j.cas;

import org.hamcrest.Matchers;
import org.junit.Test;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.junit.Assert.*;

public class CASIntegerCountTest {

    /**
     * Создаём 100 потоков, которые инкрементируют count по 10 раз. expected = 100 * 10 = 1000
     */
    @Test
    public void testIncrement() {
        CASIntegerCount count = new CASIntegerCount();
        Thread[] threads = new Thread[100];
        IntStream.range(0, 100).forEach(i -> {
            threads[i] = new Thread(
                    () -> {
                        for (int j = 0; j != 10; j++) {
                            count.increment();
                        }

                    }
            );
        });
        Stream.of(threads).forEach(thread -> {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        assertThat(count.get(), Matchers.is(1000));
    }
}