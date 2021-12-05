package ru.job4j.cas;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CASCountTest {

    /**
     * Создаём 100 потоков, которые инкрементируют count по 10 раз. expected = 100 * 10 = 1000
     */
    @Test
    public void testIncrement() {
        CASCount count = new CASCount();
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
        assertThat(count.get(), is(1000));
    }
}