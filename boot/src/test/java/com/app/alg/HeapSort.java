package com.app.alg;

import java.util.Arrays;

/**
 * @author:wuqi
 * @date:2020/3/20
 * @description:com.app.alg
 * @version:1.0
 */
public class HeapSort {

    public void buildMaxHeap(int[] array, int length) {
        for (int i = length / 2; i > 0; i--) {
            adjustDown(array, i, length);
        }
    }

    public void adjustDown(int a[], int i, int length) {
        a[0] = a[i];
        for (int j = 2 * i; j <= length; j *= 2){
            if (j < length && a[j] < a[j+1]){
                j++;
            }
            if (a[0] >= a[j]) {
                break;
            }else {
                a[i] = a[j];
                i = j;
            }

        }
        a[i] = a[0];
    }

    public void heapSort(int array[]){
        buildMaxHeap(array,array.length - 1);
        System.out.println(Arrays.toString(array));
        for (int i = array.length - 1; i > 1; --i){
            int temp = array[1];
            array[1] = array[i];
            array[i] = temp;
            adjustDown(array,1,i-1);
        }
    }

    public static void main(String[] args) {
        int[] array = {0,1,5,9,11,5,53,39,77};
        new HeapSort().heapSort(array);
        System.out.println(Arrays.toString(array));
    }
}
