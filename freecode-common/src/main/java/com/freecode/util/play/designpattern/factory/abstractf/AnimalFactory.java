package com.freecode.util.play.designpattern.factory.abstractf;

import com.freecode.util.play.designpattern.factory.Animal;
import com.freecode.util.play.designpattern.factory.Cat;
import com.freecode.util.play.designpattern.factory.Dog;
import com.freecode.util.play.designpattern.factory.Person;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 15:54
 */
public class AnimalFactory extends AbstractFactory {
    @Override
    public Person createPerson(String type) {
        throw new RuntimeException("AnimalFactory cant create person class");
    }

    @Override
    public Animal createAnimal(String type) {
        if ("dog".equals(type)) {
            return new Dog();
        }
        if ("cat".equals(type)) {
            return new Cat();
        }
        throw new RuntimeException("type unknown :" + type);
    }
}
