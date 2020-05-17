package cn.melon.demo.threadSynchronized;

import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedTest3 {
    public static void main(String[] args) {
        ReentrantLockThread thread = new ReentrantLockThread();

        for (int i = 0; i < 4; i++) {
            new Thread(thread).start();
        }
    }
}

class ReentrantLockThread implements Runnable {
    private int tickets = 100;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + tickets--);
            }
            lock.unlock();
        }
    }
}