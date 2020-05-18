package cn.melon.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABC {
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA() {
        lock.lock();
        try {
            if (num != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName());
            num = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    public void loopB() {
        lock.lock();
        try {
            if (num != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName());
            num = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
    public void loopC() {
        lock.lock();
        try {
            if (num != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName());
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lock.unlock();
    }



    public static void main(String[] args) {
        final ABC abc = new ABC();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    abc.loopA();
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    abc.loopB();
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    abc.loopC();
                }
            }
        }, "C").start();
    }
}