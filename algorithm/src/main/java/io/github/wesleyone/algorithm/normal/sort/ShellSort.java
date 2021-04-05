package io.github.wesleyone.algorithm.normal.sort;

import java.util.function.Consumer;

/**
 * @author http://wesleyone.github.io/
 */
public class ShellSort implements Consumer<int[]> {
    @Override
    public void accept(int[] source) {
        shell(source);
    }

    public static void main(String[] args) {
        Checker.check(new ShellSort(), 20);
    }

    private void shell(int[] source) {
        int h = 1;
        while (h <= source.length/3) {
            h = h*3 + 1;
        }
        System.out.println("h:"+h);
        for (int gap = h; gap > 0; gap /= 2) {
            for (int i=gap;i<source.length;i++) {
                for (int j=i;j>gap-1;j-=gap) {
                    if (source[j] < source[j-gap]) {
                        swap(source, j, j-gap);
                    } else {
                        break;
                    }
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
