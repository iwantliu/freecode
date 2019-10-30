package com.freecode.api;

import com.freecode.api.order.A;
import com.freecode.api.order.B;
import com.freecode.api.order.T;

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

        A a = new A();
        T t = new T();
        t.setO(a);
        System.out.println(t.getO() instanceof A);

        B b = new B();
        T t2 = new T();
        t2.setO(b);
        System.out.println(t2.getO() instanceof A);
        System.out.println(t2.getO() instanceof B);

    }
}
