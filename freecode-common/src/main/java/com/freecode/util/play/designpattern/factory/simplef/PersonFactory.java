package com.freecode.util.play.designpattern.factory.simplef;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-28 20:19
 */
public class PersonFactory {

    private static Person createPerson(String type) {
        if ("boy".equals(type)) {
            return new Boy();
        }
        if ("girl".equals(type)) {
            return new Girl();
        }
        throw new RuntimeException("unknown type:" + type);
    }

    /**
     * 简单工厂
     */
    public static void main(String[] args) {
        PersonFactory.createPerson("boy").info();
        PersonFactory.createPerson("girl").info();
    }
}
