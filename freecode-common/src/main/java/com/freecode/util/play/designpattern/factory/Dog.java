package com.freecode.util.play.designpattern.factory;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 15:51
 */
public class Dog implements Animal {
    @Override
    public void say() {
        System.out.println("Dog.say wang wang wang !!!");
    }
}
