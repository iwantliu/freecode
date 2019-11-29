package com.freecode.util.play.designpattern.factory.methodf;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 10:41
 */
public class Main {
    /**
     * 工厂方法 不同的类型根据不同的工厂创建
     * 拓展类型时候拓展工厂类来实现
     */
    public static void main(String[] args) {
        IPersonFactory boyFactory = new BoyFactory();
        IPersonFactory girlFactory = new GirlFactory();
        boyFactory.createPerson().info();
        girlFactory.createPerson().info();
    }
}
