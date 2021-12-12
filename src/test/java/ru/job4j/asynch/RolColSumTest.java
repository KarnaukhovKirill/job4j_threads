package ru.job4j.asynch;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class RolColSumTest {

    @Test
    public void testAsyncSum() throws Exception {
        int[][] matrix = new int[][] {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        var sums = RolColSum.asyncSum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[2].getColSum(), is(9));
    }

    @Test
    public void testSimpleSum() {
        int[][] matrix = new int[][] {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        var sums = RolColSum.sum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[2].getColSum(), is(9));
    }
}