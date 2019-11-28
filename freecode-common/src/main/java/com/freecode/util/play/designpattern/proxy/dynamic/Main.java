package com.freecode.util.play.designpattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:30
 */
public class Main {

    public static void main(String[] args) {
        Subject s = new RealSubject();
        InvocationHandler proxy = new ProxySubjectJava(s);
        Class<? extends Subject> realClass = s.getClass();
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(realClass.getClassLoader(), realClass.getInterfaces(), proxy);
        proxyInstance.invoke("im dynamic proxy java");
        System.out.println("==============================>>>>>>>");
        ProxySubjectCglib proxyCglib = new ProxySubjectCglib();
        Subject o = (Subject) proxyCglib.creatProxyObj(realClass);
        o.invoke("im dynamic proxy cglib");
    }
}
