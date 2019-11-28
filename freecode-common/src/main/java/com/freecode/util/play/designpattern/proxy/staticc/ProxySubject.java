package com.freecode.util.play.designpattern.proxy.staticc;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-27 20:11
 */
public class ProxySubject implements Subject {

    private Subject subject;

    public ProxySubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String doSome() {
        before();
        String s = subject.doSome();
        after();
        return s;
    }


    private void before() {
        System.out.println("ProxySubject.before");
    }

    private void after() {
        System.out.println("ProxySubject.after");
    }
}
