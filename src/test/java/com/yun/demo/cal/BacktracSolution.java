package com.yun.demo.cal;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/12/1
 */
public class BacktracSolution {

    // 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
    //示例: 输入: n = 4, k = 2 输出: [ [2,4], [3,4], [2,3], [1,2], [1,3], [1,4], ]
    List<List<Integer>> result= new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combine(int n, int k) {
        backtracking(n,k,1);
        return result;
    }
    public void backtracking(int n,int k,int startIndex){
        // 终止条件
        if (path.size() == k){
            result.add(new ArrayList<>(path));
            return;
        }
//        for (int i =startIndex;i<=n;i++){
        // 剪枝优化
        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++){
            path.add(i);
            backtracking(n,k,i+1);
            path.removeLast();
        }
    }

    List<List<String>> lists = new ArrayList<>();
    Deque<String> deque = new LinkedList<>();

    // 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
    //返回 s 所有可能的分割方案
    public List<List<String>> partition(String s) {
        backTracking(s, 0);
        return lists;
    }

    private void backTracking(String s, int startIndex) {
        //如果起始位置大于s的大小，说明找到了一组分割方案
        if (startIndex >= s.length()) {
            lists.add(new ArrayList(deque));
            return;
        }
        for (int i = startIndex; i < s.length(); i++) {
            //如果是回文子串，则记录
            if (isPalindrome(s, startIndex, i)) {
                String str = s.substring(startIndex, i + 1);
                deque.addLast(str);
            } else {
                continue;
            }
            //起始位置后移，保证不重复
            backTracking(s, i + 1);
            deque.removeLast();
        }
    }
    //判断是否是回文串
    private boolean isPalindrome(String s, int startIndex, int end) {
        for (int i = startIndex, j = end; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testPartition() {
        String s = "abca";
        List<List<String>> result = partition(s);
        result.forEach(System.out::println);
    }

    // 解数独
    public void solveSudoku(char[][] board) {
        solveSudokuHelper(board);
    }

    private boolean solveSudokuHelper(char[][] board){
        //「一个for循环遍历棋盘的行，一个for循环遍历棋盘的列，
        // 一行一列确定下来之后，递归遍历这个位置放9个数字的可能性！」
        for (int i = 0; i < 9; i++){ // 遍历行
            for (int j = 0; j < 9; j++){ // 遍历列
                if (board[i][j] != '.'){ // 跳过原始数字
                    continue;
                }
                for (char k = '1'; k <= '9'; k++){ // (i, j) 这个位置放k是否合适
                    if (isValidSudoku(i, j, k, board)){
                        board[i][j] = k;
                        if (solveSudokuHelper(board)){ // 如果找到合适一组立刻返回
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }
                // 9个数都试完了，都不行，那么就返回false
                return false;
                // 因为如果一行一列确定下来了，这里尝试了9个数都不行，说明这个棋盘找不到解决数独问题的解！
                // 那么会直接返回， 「这也就是为什么没有终止条件也不会永远填不满棋盘而无限递归下去！」
            }
        }
        // 遍历完没有返回false，说明找到了合适棋盘位置了
        return true;
    }

    private boolean isValidSudoku(int row, int col, char val, char[][] board){
        // 同行是否重复
        for (int i = 0; i < 9; i++){
            if (board[row][i] == val){
                return false;
            }
        }
        // 同列是否重复
        for (int j = 0; j < 9; j++){
            if (board[j][col] == val){
                return false;
            }
        }
        // 9宫格里是否重复
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++){
            for (int j = startCol; j < startCol + 3; j++){
                if (board[i][j] == val){
                    return false;
                }
            }
        }
        return true;
    }


}
