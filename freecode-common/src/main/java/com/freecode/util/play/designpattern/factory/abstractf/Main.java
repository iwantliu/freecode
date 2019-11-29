package com.freecode.util.play.designpattern.factory.abstractf;

import com.freecode.util.play.designpattern.factory.Animal;
import com.freecode.util.play.designpattern.factory.Person;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 16:07
 */
public class Main {
    /**
     * 抽象工厂
     * 场景 系统的产品有多于一个的产品族，而系统只消费其中某一族的产品
     * 这里是 Person 和 Animal
     */
    public static void main(String[] args) {
        AbstractFactory personFactory = FactoryProducer.getFactory("person");
        AbstractFactory animalFactory = FactoryProducer.getFactory("animal");
        Person boy = personFactory.createPerson("boy");
        Animal dog = animalFactory.createAnimal("dog");
        boy.info();
        dog.say();
    }
}
