package com.freecode.util.play.designpattern.factory.abstractf;

import com.freecode.util.play.designpattern.factory.*;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 15:59
 */
public class PersonFactory extends AbstractFactory {
    @Override
    public Person createPerson(String type) {
        if ("girl".equals(type)) {
            return new Girl();
        }
        if ("boy".equals(type)) {
            return new Boy();
        }
        throw new RuntimeException("type unknown :" + type);
    }

    @Override
    public Animal createAnimal(String type) {
        throw new RuntimeException("AnimalFactory cant create person class");
    }
}
