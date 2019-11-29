package com.freecode.util.play.designpattern.factory.methodf;

import com.freecode.util.play.designpattern.factory.Girl;
import com.freecode.util.play.designpattern.factory.Person;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 10:38
 */
public class GirlFactory implements IPersonFactory {
    @Override
    public Person createPerson() {
        return new Girl();
    }
}
