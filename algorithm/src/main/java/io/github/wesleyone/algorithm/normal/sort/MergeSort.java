package io.github.wesleyone.algorithm.normal.sort;

import java.util.function.Consumer;

/**
 * 归并排序
 * 方法论：拆分->排序归并
 * @author http://wesleyone.github.io/
 */
public class MergeSort implements Consumer<int[]> {
    @Override
    public void accept(int[] source) {
        mergeSort(source, 0 , source.length-1);
    }

    public static void main(String[] args) {
        Checker.check(new MergeSort(),10);
    }

    private void mergeSort(int[] source, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = (left + right)/2;
        mergeSort(source,left,middle);
        mergeSort(source,middle+1,right);
        merge(source, left, middle, right);
    }

    private void merge(int[] source, int left, int middle, int right) {
        int[] tmp = new int[right - left + 1];
        int leftIndex = left;
        int rightIndex = middle + 1;
        int tmpIndex = 0;

        while (leftIndex <= middle && rightIndex <= right) {
            if (source[leftIndex] <= source[rightIndex]) {
                tmp[tmpIndex++] = source[leftIndex++];
            } else {
                tmp[tmpIndex++] = source[rightIndex++];
            }
        }

        while (leftIndex <= middle) {
            tmp[tmpIndex++] = source[leftIndex++];
        }

        while (rightIndex <= right) {
            tmp[tmpIndex++] = source[rightIndex++];
        }

        // 覆盖source
        for (int i=0;i<tmp.length;i++) {
            source[i+left] = tmp[i];
        }

        System.out.println(Checker.array2str(source));
    }

}
