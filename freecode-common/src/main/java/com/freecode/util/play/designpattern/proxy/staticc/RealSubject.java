package com.freecode.util.play.designpattern.proxy.staticc;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:06
 */
public class RealSubject implements Subject {

    @Override
    public String doSome() {
        System.out.println("RealSubject.doSome");
        return "done";
    }
}
