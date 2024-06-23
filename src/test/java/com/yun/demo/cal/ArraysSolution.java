package com.yun.demo.cal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
public class ArraysSolution {

    void printArray(int[] arr){
        System.out.println(Arrays.toString(arr));
    }


    void printArray(int[][] arr){
        System.out.println(Arrays.deepToString(arr));
    }

    // 二分查找
    public int binarySearch(int[] arr, int target) {
        if (target < arr[0] || target > arr[arr.length - 1]) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int guess = arr[mid];

            if (guess == target) {
                return mid;
            } else if (guess < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    @Test
    void binarySearchTest(){
        int[] sortedNums = {-1,0,3,5,9,12};
        int target = 12;
        int index = binarySearch(sortedNums, target);
        log.info("{}", index);
    }

    public int removeElement(int[] nums, int val) {
        // 快慢指针
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
            if (nums[fastIndex] != val) {
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
        }
        // slowIndex 即为需要删除元素后的数组长度
        return slowIndex;
    }

    @Test
    void removeElementTest(){
        int[] arrays = {-1,0,3,5,3,12};
        int target = 3;
        int index = removeElement(arrays, target);
        log.info("{}", index);
    }

    public int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int[] result = new int[nums.length];
        for (int i = result.length; i > 0 ; i--) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];
            if(leftSquare>rightSquare){
                result[i-1] = leftSquare;
                left++;
            }else {
                result[i-1] = rightSquare;
                right--;
            }
        }
        return result;
    }

    @Test
    void sortedSquaresTest(){
        int[] arrays = {-4,-1,0,3};
        int[] result = sortedSquares(arrays);
        printArray(result);
    }

    public int minSubArrayLen(int[] nums,int target) {
        int result = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0;
        // 滑动窗口
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            // 注意这里使用while，每次更新 start（起始位置），并不断比较子序列是否符合条件
            while (sum >= target){
                result = Math.min(right - left + 1, result);
                sum -= nums[left++];
            }
        }
        return result==Integer.MAX_VALUE ? 0:result;
    }

    @Test
    void minSubArrayLenTest(){
        int[] arrays = {2,3,1,2,4,3};
        int target = 7;
        int result = minSubArrayLen(arrays,target);
        log.info("{}", result);
    }

    public int[][] generateMatrix(int n) {
        int[][] arrays = new int[n][n];
        int x = 0,y = 0;
        int loop=1;
        int loopNum = n / 2; // 每个圈循环几次，例如n为奇数3，那么loop = 1 只是循环一圈，矩阵中间的值需要单独处理
        int mid = n / 2; // 矩阵中间的位置，例如：n为3， 中间的位置就是(1，1)，n为5，中间位置为(2, 2)
        int number = 1;
        while (loop<=loopNum){
            for (;y < n-loop; y++) {
                arrays[x][y] = number++;
            }
            for(;x<n-loop;x++){
                arrays[x][y] = number++;
            }
            for (;y > loop-1; y--) {
                arrays[x][y] = number++;
            }
            for(;x>loop-1;x--){
                arrays[x][y] = number++;
            }
            loop++;
            x++;
            y++;
        }
        if(n%2==1){
            arrays[mid][mid] = number;
        }
        return arrays;
    }

    @Test
    void generateMatrixTest(){
        int n =4;
        int[][] arrays = generateMatrix(n);
        printArray(arrays);
    }

    /**
     @description: 三数之和为零，采用双指针方法
     @date: 2023/9/29
    */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 首先要对数组进行排序
        Arrays.sort(nums);
        // 找出a + b + c = 0
        // a = nums[i], b = nums[left], c = nums[right]
        for (int i = 0; i < nums.length; i++) {
            // 排序之后如果第一个元素已经大于零，那么无论如何组合都不可能凑成三元组，直接返回结果就可以了
            // 上面的逻辑只对正数有效，如果是负数，两个负数想加会更小就不符合上面的逻辑
            if (nums[i] > 0) {
                return result;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {  // 去重a
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (right > left) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去重逻辑应该放在找到一个三元组之后，对b 和 c去重
                    while (right > left && nums[right] == nums[right - 1]) right--;
                    while (right > left && nums[left] == nums[left + 1]) left++;
                    right--;
                    left++;
                }
            }
        }
        return result;
    }


    /**
     @description: 求前 k 个高频元素
     @author zhangxiaoyun
     @date: 2023/10/31
    */
    public int[] topKFrequent(int[] nums, int k) {
        // 优先级队列，为了避免复杂 api 操作，pq 存储数组
        // lambda 表达式设置优先级队列从大到小存储 o1 - o2 为从大到小，o2 - o1 反之
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        int[] res = new int[k]; // 答案数组为 k 个元素
        Map<Integer, Integer> map = new HashMap<>(); // 记录元素出现次数
        for(int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for(Map.Entry<Integer,Integer> x : map.entrySet()) {
            // 将 kv 转化成数组
            int[] tmp = new int[2];
            tmp[0] = x.getKey();
            tmp[1] = x.getValue();
            pq.offer(tmp);
            if(pq.size() > k) {
                pq.poll();
            }
        }
        for(int i = 0; i < k; i ++) {
            res[i] = pq.poll()[0]; // 获取优先队列里的元素
        }
        return res;
    }

    @Test
    void topKFrequentTest(){
        int[] arrays= {1,1,1,2,2,3};
        int k = 2;
        printArray(topKFrequent(arrays, k));
    }
}