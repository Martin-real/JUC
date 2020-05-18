package cn.melon.demo.threadSynchronized;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedTest5 {
    public static void main(String[] args) {
        TryLockThread t1 = new TryLockThread();
        new Thread(t1, "线程A").start();
        new Thread(t1, "线程B").start();
    }
}

class TryLockThread implements Runnable {
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
//        if (lock.tryLock()) {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName()+ "获取到锁");
            } else {
                System.out.println(Thread.currentThread().getName() + "没有获取到锁");
                return;
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
