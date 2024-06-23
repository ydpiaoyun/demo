package com.yun.demo.cal;

import org.junit.jupiter.api.Test;

import java.util.*;

public class TreeSolution {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    List<Integer> result = new ArrayList<>();

    /**
     * @description: 前序遍历顺序：中-左-右
     */
    public void preorder(TreeNode root) {
        if (root == null) {
            return;
        }
        result.add(root.val);
        preorder(root.left);
        preorder(root.right);
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        // 前序遍历顺序：中-左-右，入栈顺序：中-右-左
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    /**
     * @description: 中序遍历：左中右
     */
    public void inorder(TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        result.add(root.val);
        inorder(root.right);
    }

    /**
     * @description: 后序遍历：左右中
     */
    public void postorder(TreeNode root) {
        if (root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        result.add(root.val);
    }

    List<List<Integer>> resList = new ArrayList<List<Integer>>();

    /**
     * @description: 二叉树的层序遍历
     * @author zhangxiaoyun
     * @param: @param null
     * @date: 2023/11/19
     */

    public List<List<Integer>> levelOrder(TreeNode root) {
        //checkFun01(root,0);
        checkFun02(root);

        return resList;
    }

    //DFS--递归方式
    public void checkFun01(TreeNode node, Integer deep) {
        if (node == null) return;
        deep++;

        if (resList.size() < deep) {
            //当层级增加时，list的Item也增加，利用list的索引值进行层级界定
            List<Integer> item = new ArrayList<Integer>();
            resList.add(item);
        }
        resList.get(deep - 1).add(node.val);

        checkFun01(node.left, deep);
        checkFun01(node.right, deep);
    }

    //BFS--迭代方式--借助队列
    public void checkFun02(TreeNode node) {
        if (node == null) return;
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(node);

        while (!que.isEmpty()) {
            List<Integer> itemList = new ArrayList<>();
            int size = que.size();
            while (size-- > 0) {
                TreeNode tmpNode = que.poll();
                itemList.add(tmpNode.val);

                if (tmpNode.left != null) que.offer(tmpNode.left);
                if (tmpNode.right != null) que.offer(tmpNode.right);
            }
            resList.add(itemList);
        }
    }

    /**
     * 翻转二叉树，前后序遍历都可以。下面为后续遍历翻转代码
     * 中序不行，因为先左孩子交换孩子，再根交换孩子（做完后，右孩子已经变成了原来的左孩子），再右孩子交换孩子（此时其实是对原来的左孩子做交换）
     */
    public TreeNode invertTreeDFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        invertTreeDFS(root.left);
        invertTreeDFS(root.right);
        swapNode(root);
        return root;
    }

    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            while (size-- > 0) {
                TreeNode node = deque.poll();
                swapNode(node);
                if (node.left != null) deque.offer(node.left);
                if (node.right != null) deque.offer(node.right);
            }
        }
        return root;
    }

    private void swapNode(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }

    /**
     * 对称二叉树，给定一个二叉树判断是否镜像对称
     * 1、递归法
     */
    public boolean isSymmetric1(TreeNode root) {
        return compare(root.left, root.right);
    }

    private boolean compare(TreeNode left, TreeNode right) {
        if (left == null && right != null) {
            return false;
        }
        if (left != null && right == null) {
            return false;
        }
        if (left == null && right == null) {
            return true;
        }
        if (left.val != right.val) {
            return false;
        }
        // 比较外侧
        boolean compareOutside = compare(left.left, right.right);
        // 比较内侧
        boolean compareInside = compare(left.right, right.left);
        return compareOutside && compareInside;
    }

    /**
     * 迭代法
     * 使用双端队列，相当于两个栈
     */
    public boolean isSymmetric2(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offerFirst(root.left);
        deque.offerLast(root.right);
        while (!deque.isEmpty()) {
            TreeNode leftNode = deque.pollFirst();
            TreeNode rightNode = deque.pollLast();
            if (leftNode == null && rightNode == null) {
                continue;
            }
            if (leftNode == null || rightNode == null || leftNode.val != rightNode.val) {
                return false;
            }
            deque.offerFirst(leftNode.left);
            deque.offerFirst(leftNode.right);
            deque.offerLast(rightNode.right);
            deque.offerLast(rightNode.left);
        }
        return true;
    }

    /**
     * 求二叉树的最大深度，1、递归法
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return 1 + Math.max(leftDepth, rightDepth);
    }

    /**
     * 求二叉树最小深度（从根节点到最近 叶子节点 的最短路径上的节点数量）
     * 递归法，注意与 MaxDepth 的区别
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        if (root.left == null) {
            return rightDepth + 1;
        }
        if (root.right == null) {
            return leftDepth + 1;
        }
        return 1 + Math.min(leftDepth, rightDepth);
    }

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 高度平衡二叉树：一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1
     */
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight(root.right);
        if (rightHeight == -1) {
            return -1;
        }
        // 左右子树高度差大于1，return -1表示已经不是平衡树了
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
     * 递归法
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();// 存最终的结果
        if (root == null) {
            return res;
        }
        List<Integer> paths = new ArrayList<>();// 作为结果中的路径
        traversal(root, paths, res);
        return res;
    }

    private void traversal(TreeNode root, List<Integer> paths, List<String> res) {
        paths.add(root.val);// 前序遍历，中
        // 遇到叶子结点
        if (root.left == null && root.right == null) {
            // 输出
            StringBuilder sb = new StringBuilder();// StringBuilder用来拼接字符串，速度更快
            for (int i = 0; i < paths.size() - 1; i++) {
                sb.append(paths.get(i)).append("->");
            }
            sb.append(paths.get(paths.size() - 1));// 记录最后一个节点
            res.add(sb.toString());// 收集一个路径
            return;
        }
        // 递归和回溯是同时进行，所以要放在同一个花括号里
        if (root.left != null) { // 左
            traversal(root.left, paths, res);
            paths.remove(paths.size() - 1);// 回溯
        }
        if (root.right != null) { // 右
            traversal(root.right, paths, res);
            paths.remove(paths.size() - 1);// 回溯
        }
    }

    /**
     * 迭代法
     */
    public List<String> treePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null)
            return result;
        Stack<Object> stack = new Stack<>();
        // 节点和路径同时入栈
        stack.push(root);
        stack.push(root.val + "");
        while (!stack.isEmpty()) {
            // 节点和路径同时出栈
            String path = (String) stack.pop();
            TreeNode node = (TreeNode) stack.pop();
            // 若找到叶子节点
            if (node.left == null && node.right == null) {
                result.add(path);
            }
            //右子节点不为空
            if (node.right != null) {
                stack.push(node.right);
                stack.push(path + "->" + node.right.val);
            }
            //左子节点不为空
            if (node.left != null) {
                stack.push(node.left);
                stack.push(path + "->" + node.left.val);
            }
        }
        return result;
    }

    /**
     * @description: 左叶子之和
     * @author zhangxiaoyun
     * @param: @param root
     * @date: 2023/11/23
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        int leftValue = sumOfLeftLeaves(root.left);    // 左
        int rightValue = sumOfLeftLeaves(root.right);  // 右

        int value = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {
            value = root.left.val;
        }
        int sum = value + leftValue + rightValue;  // 中
        return sum;
    }

    /**
     * @description: 判断树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * @date: 2023/11/23
     */
    public boolean haspathsum(TreeNode root, int targetsum) {
        if (root == null) return false; // 为空退出

        // 叶子节点判断是否符合
        if (root.left == null && root.right == null) return root.val == targetsum;

        // 求两侧分支的路径和
        return haspathsum(root.left, targetsum - root.val) || haspathsum(root.right, targetsum - root.val);
    }

    @Test
    public void testPaths() {
        TreeNode root = initData();
//        List<String> paths = binaryTreePaths(root);
        List<String> paths = treePaths(root);
        paths.forEach(System.out::println);
    }

    private TreeNode initData() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        return root;
    }

    /**
     * @description: 合并二叉树，规则：如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;

        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    // 二叉搜索树（BST，Binary Search Tree），也称二叉排序树或二叉查找树。左子树的所有键值小于其根结点的键值,右子树的所有键值大于其根结点的键值,左、右子树都是二叉搜索树。
    // 给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 NULL
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        return root.val > val? searchBST(root.left, val) : searchBST(root.right, val);
    }

    // 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
    // 递归
    TreeNode max;
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean left = isValidBST(root.left);
        if (!left) {
            return false;
        }
        // 中
        if (max != null && root.val <= max.val) {
            return false;
        }
        max = root;
        // 右
        boolean right = isValidBST(root.right);
        return right;
    }

}