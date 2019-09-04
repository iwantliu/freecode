package com.freecode.api;

import java.text.DecimalFormat;

/**
 * <p>Description:测试类</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-09-04 14:25
 */
public class Main {
    public static void main(String[] args) {
        DecimalFormat d = new DecimalFormat("#####.##");
        System.out.println(d.format(2.0d));
    }
}
