package com.freecode.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TrieTree {

    private static final Logger logger = LoggerFactory.getLogger(TrieTree.class);
    /**
     * 树的根节点
     */
    private TrieNode root;

    public TrieNode getRoot() {
        return root;
    }

    public TrieTree() {
    }

    public TrieTree(TrieNode root) {
        this.root = root;
    }

    /**
     * 向前缀树中插入一个词
     *
     * @param str 词
     */
    public void insert(String str, int suggestID) {
        if (str == null || str.length() <= 1) {// 不能只插入一个汉字
            return;
        }
        char[] chars = str.toCharArray();
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 1; i < chars.length; i++) {
            deque.addLast(chars[i]);
        }
        if (root == null) {
            root = new TrieNode(chars[0]);
        }
        if (root.key != chars[0]) {
            logger.error("前缀树的根节点'" + root.key + "'与词的首字符'" + chars[0] + "'不相同，不能插入 suggestID:" + suggestID);
            return;
        }
        insert(deque, root, suggestID);
    }

    /**
     * 在子树上插入一个词的后缀
     *
     * @param deque  词的后缀
     * @param parent 子树的根节点
     */
    private void insert(Deque<Character> deque, TrieNode parent, int suggestID) {
        if (deque == null || deque.size() == 0) {
            parent.bound = true;
            parent.suggestion.add(suggestID);
            return;
        }
        char ch = deque.pollFirst();
        TrieNode child = parent.children.get(ch);
        if (child == null) {
            child = new TrieNode(ch);
            parent.children.put(ch, child);
        }
        insert(deque, child, suggestID);
    }

    /**
     * 给定词的前缀，获取子树上所有的词
     *
     * @param prefix 词的前缀
     * @return
     */
    public List<Integer> getAllSuggestions(String prefix) {
        List<Integer> result = new ArrayList<>();
        travel(prefix, root, "", result);
        return result;
    }

    /**
     * 给定词的前缀，获取子树上所有的词
     *
     * @param node      子树的根节点
     * @param prefix    词的前缀
     * @param outResult 输出参数，用于存储遍历得到的词
     */
    private void travel(String str, TrieNode node, String prefix, List<Integer> outResult) {
        String newPrefix = prefix + node.key;
        if (node.bound) {
            if (isSubSeq(str, newPrefix)) {
                outResult.addAll(node.suggestion);
            }
        }
        for (Map.Entry<Character, TrieNode> child : node.children.entrySet()) {
            travel(str, child.getValue(), newPrefix, outResult);
        }
    }

    /**
     * 判断str1是str2的子序列
     *
     * @param str1
     * @param str2
     * @return
     */
    private boolean isSubSeq(String str1, String str2) {
        // 注意拼音和五笔的情况，如果是纯字母，则不能是“子序列”，而要求是“子串”
        if (str1.matches("[a-z0-9]+")) {
            if (str2.contains(str1)) {
                return true;
            } else {
                return false;
            }
        }
        int prePos = 0;
        for (int i = 0; i < str1.length(); i++) {
            int pos = str2.indexOf(str1.charAt(i), prePos);
            if (pos >= 0) {
                prePos = pos + 1;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
        tree.insert("市场策划", 1);
        tree.insert("策划", 2);
        tree.insert("市场", 3);
        tree.insert("市场销售", 4);
        tree.insert("市场总监", 5);
        tree.insert("市场总管", 6);
        tree.insert("市赢率", 7);
        tree.insert("市场策", 8);
        tree.insert("市场策", 9);
        List<Integer> list = tree.getAllSuggestions("市总");
        for (int ele : list) {
            System.out.print(ele + "\t");
        }
        System.out.println();
        list = tree.getAllSuggestions("市总监");
        for (int ele : list) {
            System.out.print(ele + "\t");
        }
        System.out.println();
        list = tree.getAllSuggestions("市监");
        for (int ele : list) {
            System.out.print(ele + "\t");
        }
        System.out.println();
        list = tree.getAllSuggestions("市场");
        for (int ele : list) {
            System.out.print(ele + "\t");
        }
        System.out.println();
        String positionBrigntPoint = "a,b,c；d，e    f";
        String[] split = positionBrigntPoint.split("[\\s+,，；;]");
        System.out.println(split);
    }
}