package com.freecode.util.play.designpattern.factory.abstractf;

import com.freecode.util.play.designpattern.factory.Animal;
import com.freecode.util.play.designpattern.factory.Person;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 15:52
 */
public abstract class AbstractFactory {

    public abstract Person createPerson(String type);

    public abstract Animal createAnimal(String type);
}
