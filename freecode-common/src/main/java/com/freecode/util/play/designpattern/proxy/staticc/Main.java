package com.freecode.util.play.designpattern.proxy.staticc;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:13
 */
public class Main {

    public static void main(String[] args) {
        Subject s = new RealSubject();
        Subject proxy = new ProxySubject(s);
        proxy.doSome();
    }
}
