package com.hyr.lean.base.sort;

import java.util.Arrays;

/**
 * <b><code>CardinalitySort</code></b>
 * <p/>
 * Description 基数排序
 * <p/>
 * <b>Creation Time:</b> 2019/6/6 17:55.
 *
 * @author Hu-Weihui
 * @since hui-base-java ${PROJECT_VERSION}
 */
public class CardinalitySort {
    public static void main(String[] args) {
        int arry[] = {54, 23, 96, 75, 1, 7438, 9};

        cardinalitySort(arry);

        System.out.println(Arrays.toString(arry));
    }

    /**
     * 基数排序�?
     * MSD（Most significant digital�? 从最左侧高位�?始进行排序�?�先按k1排序分组, 同一组中记录, 关键码k1相等, 再对各组按k2排序分成子组, 之后, 对后面的关键码继续这样的排序分组, 直到按最次位关键码kd对各子组排序�?. 再将各组连接起来, 便得到一个有序序列�?�MSD方式适用于位数多的序列�??
     * <p>
     * LSD （Least significant digital）从�?右侧低位�?始进行排序�?�先从kd�?始排序，再对kd-1进行排序，依次重复，直到对k1排序后便得到�?个有序序列�?�LSD方式适用于位数少的序列�??
     * LSD例子�?
     * 得数组中的最大数，并取得位数�?
     * arr为原始数组，从最低位�?始取每个位组成radix数组�?
     * 对radix进行计数排序（利用计数排序�?�用于小范围数的特点）；
     *
     * @param arry
     */
    private static void cardinalitySort(int[] arry) {
        if (arry.length <= 1) {
            return;
        }

        //取得数组中的�?大数，并取得位数
        int max = 0;
        for (int i = 0; i < arry.length; i++) {
            if (max < arry[i]) {
                max = arry[i];
            }
        }
        int maxDigit = 1;
        while (max / 10 > 0) {
            maxDigit++;
            max = max / 10;
        }
        //申请�?个桶空间
        int[][] buckets = new int[10][arry.length];
        int base = 10;

        //从低位到高位，对每一位遍历，将所有元素分配到桶中
        for (int i = 0; i < maxDigit; i++) {
            //存储各个桶中存储元素的数�?
            int[] bktLen = new int[10];

            //分配：将�?有元素分配到桶中
            for (int j = 0; j < arry.length; j++) {
                int whichBucket = (arry[j] % base) / (base / 10);
                buckets[whichBucket][bktLen[whichBucket]] = arry[j];
                bktLen[whichBucket]++;
            }

            //收集：将不同桶里数据挨个捞出�?,为下�?轮高位排序做准备,由于靠近桶底的元素排名靠�?,因此从桶底先�?
            int k = 0;
            for (int b = 0; b < buckets.length; b++) {
                for (int p = 0; p < bktLen[b]; p++) {
                    arry[k++] = buckets[b][p];
                }
            }
            System.out.println("Sorting: " + Arrays.toString(arry));
            base *= 10;
        }
    }


}
