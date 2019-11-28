package com.freecode.util.play.designpattern.proxy.dynamic;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:19
 */
public class RealSubject implements Subject {
    @Override
    public String invoke(String msg) {
        System.out.println("RealSubject.invoke " + msg);
        return "invoke done";
    }
}
