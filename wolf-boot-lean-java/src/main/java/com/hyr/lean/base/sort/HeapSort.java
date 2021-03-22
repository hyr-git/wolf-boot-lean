package com.hyr.lean.base.sort;

import java.util.Arrays;

/**
 * <b><code>HeapSort</code></b>
 * <p/>
 * Description 堆排�?
 * <p/>
 * <b>Creation Time:</b> 2019/6/6 17:56.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class HeapSort {

    public static void main(String[] args) {
        int arry[] = {54,23,96,75,1,7438,9};

        heatSort(arry);

        System.out.println(Arrays.toString(arry));
    }

    /**
     * 先将初始序列K[1..n]建成�?个大顶堆, 那么此时第一个元素K1�?�?, 此堆为初始的无序�?.
     * 再将关键字最大的记录K1 (即堆�?, 第一个元�?)和无序区的最后一个记�? Kn 交换, 由此得到新的无序区K[1..n�?1]和有序区K[n], 且满足K[1..n�?1].keys⩽K[n].key
     * 交换K1 �? Kn �?, 堆顶可能违反堆�?�质, 因此�?将K[1..n�?1]调整为堆. 然后重复步骤2, 直到无序区只有一个元素时停止�?
     */
    private static void heatSort(int arry[]){
        for (int i = arry.length - 1; i > 0; i--) {
            max_heapify(arry, i);

            //堆顶元素(第一个元�?)与Kn交换
            int temp = arry[0];
            arry[0] = arry[i];
            arry[i] = temp;
        }
    }

    /***
     *
     *  将数组堆�?
     *  i = 第一个非叶子节点�?
     *  从第�?个非叶子节点�?始即可�?�无�?从最后一个叶子节点开始�??
     *  叶子节点可以看作已符合堆要求的节点，根节点就是它自己且自己以下�?�为�?大�??
     *
     * @param a
     * @param n
     */
    public static void max_heapify(int[] a, int n) {
        int child;
        for (int i = (n - 1) / 2; i >= 0; i--) {
            //左子节点位置
            child = 2 * i + 1;
            //右子节点存在且大于左子节点，child变成右子节点
            if (child != n && a[child] < a[child + 1]) {
                child++;
            }
            //交换父节点与左右子节点中的最大�??
            if (a[i] < a[child]) {
                int temp = a[i];
                a[i] = a[child];
                a[child] = temp;
            }
        }
    }
}
