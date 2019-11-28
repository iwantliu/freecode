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
        /**
         * 动态代理
         * 1 jdk动态代理
         *   参与成员
         *      接口、接口实现类(被代理的类)、代理类
         *   要点
         *      被代理类必须实现接口 调用Proxy (实例化代理的时候需要传递接口参数)
         *      代理类实现 InvocationHandler 接口重写 invoke 方法
         *      代理类需持有被代理类对象引用
         * 2 cglib动态代理
         *    参与成员
         *      被代理类，代理类
         *   要点
         *      代理类实现 cglib的MethodInterceptor接口 重写  intercept 方法
         *      用cglib 的Enhancer类获取代理类
         * */
        Subject s = new RealSubject();
        InvocationHandler proxy = new ProxySubjectJava(s);
        Class<? extends Subject> realClass = s.getClass();
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(realClass.getClassLoader(), realClass.getInterfaces(), proxy);
        proxyInstance.invoke("im dynamic proxy java");
        System.out.println("==============================>>>>>>>");
        ProxySubjectCglib proxyCglib = new ProxySubjectCglib();
        Subject o = (Subject) proxyCglib.createProxyObj(realClass);
        o.invoke("im dynamic proxy cglib");
    }
}
