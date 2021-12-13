package ru.job4j.asynch;

import java.util.concurrent.CompletableFuture;

/**
 * Класс содержит две реализации для подсчёта суммы числе в ряду и вертикали.
 */
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

    /**
     * Метод подсчитывает сумму чисел в ряду и вертикали последовательно
     * @param matrix матрица
     * @return массив, содержащий сумму числе в i ряду и вертикали
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = count(matrix, i);
        }
        return sums;
    }

    /**
     * Метод подсчитывает сумму чисел в ряду и вертикали с использованием асинхронного подсчёта
     * @param matrix матрица
     * @return массив, содержащий сумму числе в i ряду и вертикали
     * @throws Exception exception
     */
    public static Sums[] asyncSum(int[][] matrix) throws Exception {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getTask(matrix, i).get();
        }
        return sums;
    }

    /**
     * Метод запускает асинхронную задачу
     * @param matrix матрица
     * @param start с какого ряда и вертикали начать подсчёт суммы
     * @return объект CompletableFuture
     */
    public static CompletableFuture<Sums> getTask(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> count(matrix, start));
    }

    /**
     * Алгоритм подсчёта суммы в матрице
     * @param matrix матрица
     * @param start с какого ряда и вертикали начать подсчёт суммы
     * @return модель данных Sums, в котором храниться сумма числе в ряду и вертикали
     */
    private static Sums count(int[][] matrix, int start) {
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            rowSum += matrix[start][i];
            colSum += matrix[i][start];
        }
        return new Sums(rowSum, colSum);
    }
}
