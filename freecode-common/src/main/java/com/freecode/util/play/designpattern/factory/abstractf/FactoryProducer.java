package com.freecode.util.play.designpattern.factory.abstractf;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 16:02
 */
public class FactoryProducer {

    private FactoryProducer() {
    }

    public static AbstractFactory getFactory(String type) {
        if ("person".equals(type)) {
            return new PersonFactory();
        }
        if ("animal".equals(type)) {
            return new AnimalFactory();
        }
        throw new RuntimeException("type unknown :" + type);
    }
}
