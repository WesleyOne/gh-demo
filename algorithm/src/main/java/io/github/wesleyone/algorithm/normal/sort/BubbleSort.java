package io.github.wesleyone.algorithm.normal.sort;

import java.util.function.Consumer;

/**
 * 冒泡排序
 * 方法论：相邻数比较，最大数向上冒泡
 * @author http://wesleyone.github.io/
 */
public class BubbleSort implements Consumer<int[]> {
    @Override
    public void accept(int[] source) {
        bubble(source);
    }

    public static void main(String[] args) {
        Checker.check(new BubbleSort());
    }

    private void bubble(int[] source) {
        for (int i=source.length-1;i>0;i--) {
            for (int j=0;j<i;j++) {
                if (source[j] > source[j+1]) {
                    swap(source,j,j+1);
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
