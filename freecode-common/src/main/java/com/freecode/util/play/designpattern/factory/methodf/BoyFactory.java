package com.freecode.util.play.designpattern.factory.methodf;

import com.freecode.util.play.designpattern.factory.Boy;
import com.freecode.util.play.designpattern.factory.Person;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 10:37
 */
public class BoyFactory implements IPersonFactory{
    @Override
    public Person createPerson() {
        return new Boy();
    }
}
