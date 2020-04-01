package com.app.alg;

import java.util.Arrays;
import java.util.Map;

/**
 * @author:wuqi
 * @date:2020/3/18
 * @description:com.app.alg
 * @version:1.0
 */
public class QuickSort {

    public void quickSort(int[] array, int low, int high) {
        if (high > low) {
            int index = this.partition(array, low, high);
            quickSort(array, low, index);
            quickSort(array, index+1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int value = array[low];
        while (low < high && array[high] >= value) {
            --high;
        }
        array[low] = array[high];
        while (low < high && array[low] <= value) {
            ++low;
        }
        array[high] = array[low];
        array[low] = value;
        return low;
    }

    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 1, 7, 33, 22};
        new QuickSort().quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
