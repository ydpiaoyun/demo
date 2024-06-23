package com.yun.demo.cal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author: zhangxiaoyun
 * @description: 贪心算法
 * @date: 2023/12/4
 */
public class GreedySolution {
    // 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
    // 对每个孩子都有一个胃口值 g[i]；每块饼干都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i。目标是尽可能满足越多数量的孩子，并输出这个最大数值。
    // 使用贪心策略，先将饼干数组和小孩数组排序,优先考虑饼干，小饼干先喂饱小胃口
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int start = 0;
        int count = 0;
        for (int i = 0; i < s.length && start < g.length; i++) {
            if (s[i] >= g[start]) {
                start++;
                count++;
            }
        }
        return count;
    }

    /**
     @description: 给定一个数组，它的第  i 个元素是一支给定股票第 i 天的价格。
     设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）
     想法：选一个低的买入，再选个高的卖，再选一个低的买入.....循环反复。
     其实我们需要收集每天的正利润就可以，收集正利润的区间，就是股票买卖的区间，而我们只需要关注最终利润，不需要记录区间。
    */
    public int maxProfit(int[] prices) {
        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            result += Math.max(prices[i] - prices[i - 1], 0);
        }
        return result;
    }
    /** 
     @description: 跳跃游戏：给定一个非负整数数组，你最初位于数组的第一个位置。
     数组中的每个元素代表你在该位置可以跳跃的最大长度。判断你是否能够到达最后一个位置。
     输入: [3,2,1,0,4]    输出: false
     解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置
     思路：这个问题就转化为跳跃覆盖范围究竟可不可以覆盖到终点
     贪心算法局部最优解：每次取最大跳跃步数（取最大覆盖范围），整体最优解：最后得到整体最大覆盖范围，看是否能到终点。
    */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        //覆盖范围, 初始覆盖范围应该是0，因为下面的迭代是从下标0开始的
        int coverRange = 0;
        //在覆盖范围内更新最大的覆盖范围
        for (int i = 0; i <= coverRange; i++) {
            coverRange = Math.max(coverRange, i + nums[i]);
            if (coverRange >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    /** 
     @description: 跳跃游戏升级：目标是使用最少的跳跃次数到达数组的最后一个位置。
     思路：从覆盖范围出发，不管怎么跳，覆盖范围内一定是可以跳到的，以最小的步数增加覆盖范围，覆盖范围一旦覆盖了终点，得到的就是最少步数

     */

    public int jump(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return 0;
        }
        //记录跳跃的次数
        int count=0;
        //当前的覆盖最大区域
        int curDistance = 0;
        //最大的覆盖区域
        int maxDistance = 0;
        for (int i = 0; i < nums.length; i++) {
            //在可覆盖区域内更新最大的覆盖区域
            maxDistance = Math.max(maxDistance,i+nums[i]);
            //说明当前一步，再跳一步就到达了末尾
            if (maxDistance>=nums.length-1){
                count++;
                break;
            }
            //走到当前覆盖的最大区域时，更新下一步可达的最大区域
            if (i==curDistance){
                curDistance = maxDistance;
                count++;
            }
        }
        return count;
    }

    @Test
    public void testJump() {
        int[] nums1 = {3, 1, 2, 1, 4};
        assertEquals(2, jump(nums1));
    }
    
    /** 
     @description: 分发糖果：有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。按照以下要求，给这些孩子分发糖果：
     每个孩子至少分配到 1 个糖果。相邻的孩子中，评分高的孩子必须获得更多的糖果。
     那么这样下来，老师至少需要准备多少颗糖果呢？
     思路：一定是要确定一边之后，再确定另一边，例如比较每一个孩子的左边，然后再比较右边，如果两边一起考虑一定会顾此失彼。
     分两个阶段
     1、先确定右边评分大于左边的情况，起点下标1 从左往右，只要 右边 比 左边 大，右边的糖果=左边 + 1
     2、再确定左孩子大于右孩子的情况（从后向前遍历）。起点下标 ratings.length - 2 从右往左， 只要左边 比 右边 大，此时 左边的糖果应该 取本身的糖果数（符合比它左边大） 和 右边糖果数 + 1 二者的最大值，这样才符合 它比它左边的大，也比它右边大
    */
    public int candy(int[] ratings) {
        int len = ratings.length;
        int[] candyVec = new int[len];
        candyVec[0] = 1;
        for (int i = 1; i < len; i++) {
            candyVec[i] = (ratings[i] > ratings[i - 1]) ? candyVec[i - 1] + 1 : 1;
        }

        for (int i = len - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candyVec[i] = Math.max(candyVec[i], candyVec[i + 1] + 1);
            }
        }

        int ans = 0;
        for (int num : candyVec) {
            ans += num;
        }
        return ans;
    }
    
    /** 
     @description: 柠檬水找零：每一杯柠檬水的售价为 5 美元。每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。
     必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。注意，一开始你手头没有任何零钱。
     如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     思路：只需要维护三种金额的数量，5，10和20。有如下三种情况：
         情况一：账单是5，直接收下。
         情况二：账单是10，消耗一个5，增加一个10
         情况三：账单是20，优先消耗一个10和一个5（美元 10 只能给20找零，而美元5可以给10和20找零），如果不够，再消耗三个5
    */
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;

        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five < 1) {
                    return false;
                }
                five--;
                ten++;
            } else if (bill == 20) {
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five > 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 用最少数量的箭引爆气球
     */
    public int findMinArrowShots(int[][] points) {
        // 根据气球直径的开始坐标从小到大排序
        // 使用Integer内置比较方法，不会溢出
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));

        int count = 1;  // points 不为空至少需要一支箭
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > points[i - 1][1]) {  // 气球i和气球i-1不挨着，注意这里不是>=
                count++; // 需要一支箭
            } else {  // 气球i和气球i-1挨着
                points[i][1] = Math.min(points[i][1], points[i - 1][1]); // 更新重叠气球最小右边界
            }
        }
        return count;
    }

    @Test
    public void testFindMinArrowShots() {
        int[][] points = {{1, 5}, {2, 4}, {3, 3}, {8, 2}};
        System.out.println(findMinArrowShots(points));
    }

    @Test
    public void test() {
        int[][] points = {{10, 16}, {2, 4}, {1, 6}, {8, 2}};
        System.out.println(Arrays.deepToString(points));
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println(Arrays.deepToString(points));
    }

}
