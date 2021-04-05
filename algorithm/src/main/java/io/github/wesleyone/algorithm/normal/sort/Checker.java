package io.github.wesleyone.algorithm.normal.sort;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author http://wesleyone.github.io/
 */
public class Checker {

    static int[] arrayCopy(int[] source) {
        int[] target = new int[source.length];
        System.arraycopy(source,0,target,0,source.length);
        return target;
    }

    static int[] generateArray(int num) {
        Random r = new Random();
        int[] arr = new int[num];
        for (int i=0;i<arr.length;i++) {
            arr[i] = r.nextInt(10);
        }
        return arr;
    }

    static boolean check(int[] source, int[] result) {
        if (source.length != result.length) {
            return false;
        }
        Arrays.sort(source);
        boolean isSame = true;
        for (int i=0; i<result.length; i++) {
            isSame = isSame && (source[i] == result[i]);
        }
        return isSame;
    }

    static void check(Consumer<int[]> consumer) {
        check(consumer, 10);
    }

    static void check(Consumer<int[]> consumer, int generateNum) {
        for (int num=0;num<10;num ++) {
            int[] array = Checker.generateArray(generateNum);
            int[] source = Checker.arrayCopy(array);
            int[] sourceForReal = Checker.arrayCopy(array);
            long startTime = System.currentTimeMillis();
            consumer.accept(array);
            long costTime = System.currentTimeMillis() - startTime;
            boolean check = Checker.check(sourceForReal, array);
            if (check) {
                System.out.println(check + " | " + costTime + " | " + array2str(source) + " -> " + array2str(array));
            } else {
                System.err.println(check + " | " + costTime + " | " + array2str(source) + " -> " + array2str(array));
            }
        }
    }

    static String array2str(int[] arr) {
        StringBuilder sbr = new StringBuilder();
        for (int i=0;i<arr.length;i++) {
            sbr.append(arr[i]).append(",");
        }
        return sbr.toString();
    }
}
