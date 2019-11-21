package com.freecode.util.play;

/**
 * <p>Description:矩阵</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-21 16:52
 */
public class Matrix {

    public static void main(String[] args) {
        printMatrix();
    }

    //    已知二维数组数据如下
    //           1, 2, 3, 4,  5
    //           6, 7, 8, 9, 10
    //           11,12,13,14,15
    //           16,17,18,19,20
    //           21,22,23,24,25
    //    写程序输出
    //           1,2,6,3,7,11,4,8,12,16,5,9,13,17,21,10,14,18,22,15,19,23,20,24,25

    private static void printMatrix() {

        int[][] aa = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        int x = 0;
        int y = 0;
        int xMax = aa.length;
        int yMax = aa[0].length;
        //00,01,10,02,11,20,
    }
}
