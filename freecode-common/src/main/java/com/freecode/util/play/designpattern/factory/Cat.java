package com.freecode.util.play.designpattern.factory;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 15:50
 */
public class Cat implements Animal{
    @Override
    public void say() {
        System.out.println("Cat.say miao miao miao ~~");
    }
}
