package io.github.wesleyone.algorithm.normal.sort;

import java.util.function.Consumer;

/**
 * 选择算法
 * 方法论：每次循环寻找最小数，与当次循环第一个数替换位置
 * 不稳：相同大小的值会因为交换导致位置变更
 * @author http://wesleyone.github.io/
 */
public class SelectionSort implements Consumer<int[]> {

    @Override
    public void accept(int[] source) {
        selection(source);
    }

    public static void main(String[] args) {
        Checker.check(new SelectionSort());
    }

    private static void selection(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i+1; j < array.length; j++) {
                if (array[minIndex] > array[j]) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
            // 输出结果
            for (int z = 0; z < array.length; z++) {
                System.out.print(array[z]+",");
            }
            System.out.println();
        }
    }

    private static void swap(int[] array, int i, int minPosition) {
        int tmp = array[i];
        array[i] = array[minPosition];
        array[minPosition] = tmp;
    }

}
