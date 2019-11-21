package com.freecode.util.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-21 14:35
 */
public class KV2RelationLine {

    public static void main(String[] args) {
        findRelation();
    }


    //   已知数据关系
    //            1->2
    //            1->3
    //            2->5
    //            2->6
    //            3->7
    //            3->8
    //            5->4
    //            6->4
    //            7->4
    //            8->4
    //    写程序输出
    //            1 2 5 4
    //            1 2 6 4
    //            1 3 7 4
    //            1 3 8 4
    private static void findRelation() {
        Map<Integer, List<Integer>> hashMap = new HashMap<>();
        initData(hashMap);

        List<List<Integer>> all = new ArrayList<>();
        int root = 1;
        List<Integer> result = new ArrayList<>();
        findNext(hashMap, root, result, all);
        System.out.println(all);
    }


    private static void findNext(Map<Integer, List<Integer>> map, Integer key, List<Integer> result, List<List<Integer>> all) {
        result.add(key);
        List<Integer> list = map.get(key);
        if (null == list) {
            List<Integer> partResult = new ArrayList<>();
            partResult.addAll(result);
            all.add(partResult);
            result.clear();
        } else {
            for (Integer newK : list) {
                List<Integer> partResult = new ArrayList<>();
                partResult.addAll(result);
                findNext(map, newK, partResult, all);
            }
        }
    }

    private static void initData(Map<Integer, List<Integer>> hashMap) {
        List<Integer> a = new ArrayList<>();
        a.add(2);
        a.add(3);
        hashMap.put(1, a);
        a = new ArrayList<>();
        a.add(5);
        a.add(6);
        hashMap.put(2, a);
        a = new ArrayList<>();
        a.add(7);
        a.add(8);
        hashMap.put(3, a);
        a = new ArrayList<>();
        a.add(4);
        hashMap.put(5, a);
        hashMap.put(6, a);
        hashMap.put(7, a);
        hashMap.put(8, a);
    }


}
