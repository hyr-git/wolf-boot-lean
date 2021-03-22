package com.hyr.lean.base.sort;

import java.util.Arrays;

/**
 * <b><code>BubbleSort</code></b>
 * <p/>
 * Description: 冒泡排序
 * <p/>
 * <b>Creation Time:</b> 2019/6/5 21:19.
 *
 * @author HuWeihui
 */
public class BubbleSort {

    public static void main(String[] args) {

        int arry[] = {54,23,96,75,1,7438,9};

        bubbbleSort(arry);

        System.out.println(Arrays.toString(arry));
    }

    /**
     * 在要排序的一组数中，对当前还未排好序的范围内的全部数�?
     * 自上而下对相邻的两个数依次进行比较和调整�?
     * 让较大的数往下沉，较小的�?上冒
     * 时间复杂�? O(n^2)
     * 空间复杂度O(1)
     *
     * @param arry
     */
    private static void bubbbleSort(int[] arry) {
        for (int i = 0; i < arry.length-1; i++) {
            for (int j = 0; j < arry.length-i-1; j++) {
                if (arry[j] > arry[j+1]){
                    int tmp = arry[j+1];
                    arry[j+1] = arry[j];
                    arry[j] = tmp;
                }
            }
        }
    }
}
