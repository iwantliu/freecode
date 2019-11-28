package com.freecode.util.play.designpattern.proxy.dynamic;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:46
 */
public class ProxySubjectCglib implements MethodInterceptor {


    // 根据一个类型产生代理类，此方法不要求一定放在MethodInterceptor中
    public Object createProxyObj(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        after();
        Object invokeSuper = methodProxy.invokeSuper(o, objects);
        before();
        return invokeSuper;
    }

    private void before() {
        System.out.println("ProxySubjectCglib.before");
    }

    private void after() {
        System.out.println("ProxySubjectCglib.after");
    }
}
