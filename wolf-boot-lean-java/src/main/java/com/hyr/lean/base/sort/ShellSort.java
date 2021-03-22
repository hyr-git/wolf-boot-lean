package com.hyr.lean.base.sort;

import java.util.Arrays;

/**
 * <b><code>ShellSort</code></b>
 * <p/>
 * Description 希尔排序
 * <p/>
 * <b>Creation Time:</b> 2019/6/6 16:52.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class ShellSort {
    public static void main(String[] args) {
        int arry[] = {54,23,96,75,1,7438,9};

        shellSort(arry);

        System.out.println(Arrays.toString(arry));
    }

    /**
     * 选择�?个增量序�? t1，t2，�?��?�，tk，其�? ti > tj, tk = 1�?
     * 按增量序列个�? k，对序列进行 k 趟排序；
     * 每趟排序，根据对应的增量 ti，将待排序列分割成若干长度为 m 的子序列，分别对各子表进行直接插入排序�?�仅增量因子�? 1 时，整个序列作为�?个表来处理，表长度即为整个序列的长度�?
     */
    public static void shellSort(int arry[]) {
        int length = arry.length;
        int h = 1;
        while (h < length / 3){
            h = 3 * h + 1;
        }
        for (; h >= 1; h /= 3) {
            for (int i = 0; i < arry.length - h; i += h) {
                for (int j = i + h; j > 0; j -= h) {
                    if (arry[j] < arry[j - h]) {
                        int temp = arry[j];
                        arry[j] = arry[j - h];
                        arry[j - h] = temp;
                    }
                }
            }
        }
    }
}
