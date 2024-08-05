package main.java.utils;

import java.util.Arrays;

public class Utils {

    /**
     * Creates a deep copy of a 2D integer array.
     */
    public static int[][] deepCopy2dIntArray(int[][] arr) {
        int[][] myInt = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            int[] aMatrix = arr[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }

        return myInt;
    }

    /**
     * Calculates the determinant of a square matrix.
     */
    public static int determinant(int[][] matrix) {
        int n = matrix.length;
        if (matrix[0].length != n) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        if (n == 1) return matrix[0][0];
        if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        int det = 0;
        for (int j = 0; j < n; j++) {
            int[][] subMatrix = removeRowAndColumn(matrix, 0, j);
            det += (j % 2 == 0 ? 1 : -1) * matrix[0][j] * determinant(subMatrix);
        }
        return det;
    }

    /**
     * Prints a 2D integer array to the console.
     */
    public static void print2dArray(int[][] arr) {
        for (int[] ints : arr) {
            String arrString = Arrays.toString(ints);
            System.out.println(arrString.substring(1, arrString.length() - 1));
        }
    }

    /**
     * Creates a sub-matrix by removing a specific row and column from the input matrix.
     */
    public static int[][] removeRowAndColumn(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] subMatrix = new int[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                subMatrix[r][c++] = matrix[i][j];
            }
            r++;
        }
        return subMatrix;
    }
}