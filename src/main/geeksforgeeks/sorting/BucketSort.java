package main.geeksforgeeks.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BucketSort {
    public void maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return;
        }

        int minVal = Arrays.stream(nums).min().getAsInt();
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int d = Math.max(1, (maxVal - minVal) / (n - 1));
        int bucketSize = (maxVal - minVal) / d + 1;

        ArrayList<Integer>[] bucket = new ArrayList[bucketSize];
        for (int i = 0; i < bucketSize; ++i) {
            bucket[i] = new ArrayList<Integer>();
        }
        for (int num : nums) {
            int idx = (num - minVal) / d;
            bucket[idx].add(num);
        }
        for (int i = 0; i < bucketSize; ++i) {
            Collections.sort(bucket[i]);
        }
        int index = 0;
        for (int i = 0; i < bucketSize; ++i) {
            for (int j = 0; j < bucket[i].size(); j++) {
                nums[index++] = bucket[i].get(j);
            }
        }
    }
}
