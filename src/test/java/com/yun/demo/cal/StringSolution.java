package com.yun.demo.cal;

import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/9/29
 */
public class StringSolution {
    
    /** 
     @description: 实现一个函数，把字符串 s 中的每个空格替换成"%20"。
     @author zhangxiaoyun
     思路：扩充数组到每个空格替换成"%20"之后的大小。
     然后从后向前替换空格，也就是双指针法，right指向新长度的末尾，left指向旧长度的末尾
     @date: 2023/10/9
    */ 
    public String replaceSpace(String s) {
        if(s == null || s.length() == 0){
            return s;
        }
        //扩充空间，空格数量2倍
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' '){
                str.append("  ");
            }
        }
        //若是没有空格直接返回
        if(str.length() == 0){
            return s;
        }
        //有空格情况 定义两个指针
        int left = s.length() - 1;//左指针：指向原始字符串最后一个位置
        s += str.toString();
        int right = s.length()-1;//右指针：指向扩展字符串的最后一个位置
        char[] chars = s.toCharArray();
        while(left>=0){
            if(chars[left] == ' '){
                chars[right--] = '0';
                chars[right--] = '2';
                chars[right] = '%';
            }else{
                chars[right] = chars[left];
            }
            left--;
            right--;
        }
        return new String(chars);
    }

    @Test
    public void replaceSpaceTest(){
        String s = "we are happy";
        System.out.println(replaceSpace(s));
    }

    /**
     @description: 计算 字符串的 next
    */
    public static int[] kmpnext(String dest){
        int[] next = new int[dest.length()];
        next[0] = 0;

        for(int i = 1,j = 0; i < dest.length(); i++){
            while(j > 0 && dest.charAt(j) != dest.charAt(i)){
                j = next[j - 1];
            }
            if(dest.charAt(i) == dest.charAt(j)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static int kmp(String str, String dest){
        // 首先计算出部分匹配表
        int[] next = kmpnext(dest);
        // j为模式串a下标，i为主串b下标。循环主串，查找匹配位置。
        for(int i = 0, j = 0; i < str.length(); i++){
            while(j > 0 && str.charAt(i) != dest.charAt(j)){
                j = next[j-1];
            }
            if(str.charAt(i) == dest.charAt(j)){
                j++;
            }
            if(j == dest.length()){
                return i-j+1;
            }
        }
        return -1;
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String s : tokens) {
            if ("+".equals(s)) {
                // 注意 - 和/ 需要特殊处理
                stack.push(stack.pop() + stack.pop());
            } else if ("-".equals(s)) {
                stack.push(-stack.pop() + stack.pop());
            } else if ("*".equals(s)) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(s)) {
                int temp1 = stack.pop();
                int temp2 = stack.pop();
                stack.push(temp2 / temp1);
            } else {
                stack.push(Integer.valueOf(s));
            }
        }
        return stack.pop();
    }
}
