package com.freecode.util.play.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-11-29 15:23
 */
public class A {
    private static Lock lock = new ReentrantLock(true);

    /**
     * 三个线程交替输出ABC
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    System.out.println("A");
                    Thread.sleep(1000);
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    System.out.println("B");
                    Thread.sleep(1000);
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    System.out.println("C");
                    Thread.sleep(1000);
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
