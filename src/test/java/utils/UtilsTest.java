package test.java.utils;

import main.java.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class UtilsTest {
    @Test
    public void testDeepCopy2dIntArray() {
        int[][] matrix = new int[][] {
                {0, 6, 0, 1, 0, 0, 0},
                {6, 0, 5, 2, 2, 0, 0},
                {0, 5, 0, 0, 5, 6, 0},
                {1, 2, 0, 0, 1, 0, 0},
                {0, 2, 5, 1, 0, 0, 4},
                {0, 0, 6, 0, 0, 0, 7},
                {0, 0, 0, 0, 4, 7, 0}
        };

        Assert.assertTrue(Arrays.deepEquals(matrix, Utils.deepCopy2dIntArray(matrix)));
    }
}
