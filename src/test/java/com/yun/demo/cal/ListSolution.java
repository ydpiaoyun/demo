package com.yun.demo.cal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author: zhangxiaoyun
 * @description: 链表：增加虚拟头结点会方便操作。
 * 链表操作当前节点必须要找前一个节点才能操作
 * @date: 2023/9/26
 */

@Slf4j
public class ListSolution {
    static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    void printList(ListNode head,String desc) {
        System.out.printf("desc: %15s\t", desc);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    ListNode initData() {
        Random random = new Random();
        ListNode head = new ListNode(random.nextInt(100), null);
        ListNode one = new ListNode(random.nextInt(100), null);
        ListNode two = new ListNode(random.nextInt(100), null);
        ListNode three = new ListNode(random.nextInt(100), null);
        ListNode four = new ListNode(random.nextInt(100), null);
        head.next = one;
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = null;
        printList(head,"initData");
        return head;
    }

    ListNode removeElements(ListNode head, int val) {
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    @Test
    void removeElementsTest() {
        ListNode data = initData();
        printList(removeElements(data, 2),"removeElements");
    }

    ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode tmp = null;
        while (cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    @Test
    void revertseListTest() {
        ListNode data = initData();
        printList(reverseList(data),"reverseList");
    }

    /** 
     @description: 相邻的节点两两交换，设置虚拟头结点方便操作
     @date: 2023/9/28
    */ 
    ListNode swapPairs(ListNode head) {
        ListNode dumyhead = new ListNode(-1); // 设置一个虚拟头结点
        dumyhead.next = head; // 将虚拟头结点指向head，这样方面后面做删除操作
        ListNode cur = dumyhead;
        ListNode temp; // 临时节点，保存两个节点后面的节点
        ListNode firstnode; // 临时节点，保存两个节点之中的第一个节点
        ListNode secondnode; // 临时节点，保存两个节点之中的第二个节点
        while (cur.next != null && cur.next.next != null) {
            temp = cur.next.next.next;
            firstnode = cur.next;
            secondnode = cur.next.next;
            cur.next = secondnode;      
            secondnode.next = firstnode; 
            firstnode.next = temp;     
            cur = firstnode; // cur移动，准备下一轮交换
        }
        return dumyhead.next;
    }

    @Test
    void swapPairsTest(){
        ListNode data = initData();
        printList(swapPairs(data),"swapPairs");
    }

    /**
     @description: 删除链表的倒数第 N 个节点，使用一趟扫描实现
     双指针经典应用：一个指针先走 n 步，然后两个指针同时走，当第一个指针走到链表末尾时，第二个指针指向需要删除的节点的上一个节点（方便做删除下个节点操作）
     @author zhangxiaoyun
     @date: 2023/9/28
    */
    ListNode removeNthFromEnd(ListNode head, int n){
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        ListNode fastIndex = dummyNode;
        ListNode slowIndex = dummyNode;

        for (int i = 0; i < n  ; i++){
            fastIndex = fastIndex.next;
        }

        while (fastIndex.next != null){
            fastIndex = fastIndex.next;
            slowIndex = slowIndex.next;
        }

        //此时 slowIndex 的位置就是待删除元素的前一个位置。
        //具体情况可自己画一个链表长度为 3 的图来模拟代码来理解
        slowIndex.next = slowIndex.next.next;
        return dummyNode.next;
    }

    @Test
    void removeNthFromEndTestTest(){
        ListNode data = initData();
        printList(removeNthFromEnd(data,2),"removeNthFromEnd");
    }

    /**
     @description: 获取两个列表相交的子列表
     @author zhangxiaoyun
     @date: 2023/9/28
    */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode curA = headA;
        ListNode curB = headB;
        int lenA = 0, lenB = 0;
        while (curA != null) { // 求链表A的长度
            lenA++;
            curA = curA.next;
        }
        while (curB != null) { // 求链表B的长度
            lenB++;
            curB = curB.next;
        }
        curA = headA;
        curB = headB;
        // 让curA为最长链表的头，lenA为其长度
        if (lenB > lenA) {
            //1. swap (lenA, lenB);
            int tmpLen = lenA;
            lenA = lenB;
            lenB = tmpLen;
            //2. swap (curA, curB);
            ListNode tmpNode = curA;
            curA = curB;
            curB = tmpNode;
        }
        // 求长度差
        int gap = lenA - lenB;
        // 让curA和curB在同一起点上（末尾位置对齐）
        while (gap-- > 0) {
            curA = curA.next;
        }
        // 遍历curA 和 curB，遇到相同则直接返回
        while (curA != null) {
            if (curA == curB) {
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }
        return null;
    }

    @Test
    void getIntersectionNodeTest(){
        ListNode dataA = initData();
        ListNode dataB = new ListNode(100);
        dataB.next = dataA;
        ListNode intersectionNode = getIntersectionNode(dataA, dataB);
        printList(intersectionNode,"intersectionNode");
    }

    /** 
     @description: 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     @author zhangxiaoyun
     @date: 2023/9/28
    */ 
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {// 有环
                ListNode index1 = fast;
                ListNode index2 = head;
                // 两个指针，从头结点和相遇结点，各走一步，直到相遇，相遇点即为环入口
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }
        return null;
    }
}
