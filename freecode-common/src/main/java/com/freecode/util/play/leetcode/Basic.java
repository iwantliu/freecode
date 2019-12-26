package com.freecode.util.play.leetcode;

import java.util.HashMap;

/**
 *
 */
public class Basic {

    public static void main(String[] args) {
//        sum();
        lengthOfLongestSubstring();
    }

    /**
     * 2数之和等于目标值
     * hash存储补数,和当前数的索引
     * 注意多对符合结果的情况 本代码暂不考虑多对情况
     */
    private static int[] sum() {
        int[] indexs = new int[2];
        //入参
        int[] nums = new int[]{1, 3, 5, 4, 7, 8, 9};
        int target = 12;

        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                indexs[0] = hash.get(nums[i]);
                indexs[1] = i;
                System.out.println(indexs[0] + "," + indexs[1]);
                return indexs;
            }
            hash.put(target - nums[i], i);
        }
        return indexs;
    }

    /**
     * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
     * <p>
     * 示例：
     * <p>
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public ListNode mergeTwoLists(ListNode heada, ListNode headb) {
        return null;
    }

    /**
     * leetcode 3 https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     * 思路： 从左往右依次匹配，记录最大子串长度，外循选取子串，内循环比对有无相同值
     * 若有则选择子串去掉第一个值从下一个继续比对，注意最后重复字符串到字符串结束无重复需和之前的maxLength比对的到最终值
     */
    private static int lengthOfLongestSubstring() {
        String s = "abcadbcxyzhk";
        int maxLength = 0;
        char[] chars = s.toCharArray();
        //用leftIndex控制
        int leftIndex = 0;
        //0;1
        for (int j = 0; j < chars.length; j++) {
            for (int innerIndex = leftIndex; innerIndex < j; innerIndex++) {
                if (chars[innerIndex] == chars[j]) {
                    maxLength = Math.max(maxLength, j - leftIndex);
                    leftIndex = innerIndex + 1;
                    break;
                }
            }
        }
        int max = Math.max(chars.length - leftIndex, maxLength);
        System.out.println(max);
        return max;
    }

    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     * 你可以假设 nums1 和 nums2 不会同时为空。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * 示例 1:
     * <p>
     * nums1 = [1, 3]
     * nums2 = [2]
     * <p>
     * 则中位数是 2.0
     * 示例 2:
     * <p>
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * <p>
     * 则中位数是 (2 + 3)/2 = 2.5
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return 0d;
    }
}
