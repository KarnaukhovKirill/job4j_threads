package ru.job4j.asynch;

import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];

            }
            sums[i] = new Sums(rowSum, colSum);
            rowSum = 0;
            colSum = 0;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws Exception {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getTask(matrix, i).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[start][i];
                colSum += matrix[i][start];
            }
            return new Sums(rowSum, colSum);
        });
    }
}
