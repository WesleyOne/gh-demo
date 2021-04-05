package io.github.wesleyone.algorithm.normal.sort;

import java.util.function.Consumer;

/**
 * 插入排序
 * 样本小基本有序时效率比较高
 * @author http://wesleyone.github.io/
 */
public class InsertionSort implements Consumer<int[]> {
    @Override
    public void accept(int[] source) {
        insertion(source);
    }

    public static void main(String[] args) {
        Checker.check(new InsertionSort());
    }

    public void insertion(int[] source) {
        for (int i=1;i<source.length;i++) {
            for (int j=i;j>0;j--) {
                if (source[j] < source[j-1]) {
                    swap(source, j, j-1);
                } else {
                    break;
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
