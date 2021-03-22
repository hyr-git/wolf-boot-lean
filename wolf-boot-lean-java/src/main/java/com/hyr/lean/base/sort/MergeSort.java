package com.hyr.lean.base.sort;

import java.util.Arrays;

/**
 * <b><code>MergeSort</code></b>
 * <p/>
 * Description 归并排序
 * <p/>
 * <b>Creation Time:</b> 2019/6/6 17:05.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class MergeSort {
    public static void main(String[] args) {

        int arry[] = {54,23,96,75,1,7438,9};

        mergeSort(arry);

        System.out.println(Arrays.toString(arry));
    }

    //归并�?�?的辅助数�?
    private static int[] aux;

    public static void mergeSort(int[] a) {
        //�?次�?�分配空�?
        aux = new int[a.length];
        mergeSort(a, 0, a.length - 1);
    }


    /**
     * 该方法先将所有元素复制到aux[]中，然后在归并会a[]中�?�方法咋归并�?(第二个for循环)
     * 进行�?4个条件判断：
     * - 左半边用�?(取右半边的元�?)
     * - 右半边用�?(取左半边的元�?)
     * - 右半边的当前元素小于左半边的当前元素(取右半边的元�?)
     * - 右半边的当前元素大于等于左半边的当前元素(取左半边的元�?)
     * @param a
     * @param low
     * @param mid
     * @param high
     */
    public static void merge(int[] a, int low, int mid, int high) {
        //将a[low..mid]和a[mid+1..high]归并
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    /**
     * 将序列每相邻两个数字进行归并操作，形�? floor(n/2)个序列，排序后每个序列包含两个元素；
     * 将上述序列再次归并，形成 floor(n/4)个序列，每个序列包含四个元素�?
     * 重复步骤2，直到所有元素排序完毕�??
     * @param arry
     * @param low
     * @param high
     */
    public static void mergeSort(int arry[],int low,int high){
        if (low >= high) {
            return;
        }
        int mid = (low + high) / 2;
        //将左半边排序
        mergeSort(arry, low, mid);
        //将右半边排序
        mergeSort(arry, mid + 1, high);
        merge(arry, low, mid, high);
    }

}
