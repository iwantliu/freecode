package com.freecode.util.play.designpattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:20
 */
public class ProxySubjectJava implements InvocationHandler {


    private Object object;

    public ProxySubjectJava(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(object, args);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("ProxySubjectJava.before");
    }

    private void after() {
        System.out.println("ProxySubjectJava.after");
    }
}
