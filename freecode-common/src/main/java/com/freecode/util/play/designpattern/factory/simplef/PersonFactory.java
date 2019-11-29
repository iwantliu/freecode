package com.freecode.util.play.designpattern.factory.simplef;


import com.freecode.util.play.designpattern.factory.Boy;
import com.freecode.util.play.designpattern.factory.Girl;
import com.freecode.util.play.designpattern.factory.Person;

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
     * 简单工厂模式它又称为静态工厂方法，所有类型的创建逻辑都在工厂一个工厂中实现，
     * 拓展是需修改工厂类，增加类型
     * （违背开闭原则）
     */
    public static void main(String[] args) {
        PersonFactory.createPerson("boy").info();
        PersonFactory.createPerson("girl").info();
    }
}
