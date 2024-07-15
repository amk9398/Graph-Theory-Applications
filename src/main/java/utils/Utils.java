package main.java.utils;

public class Utils {
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
}
