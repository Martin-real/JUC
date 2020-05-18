package cn.melon.demo.jucControl;

import java.util.concurrent.Semaphore;

/**
 * 信号量demo
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(5);
        SemaphoreThread semaphoreThread = new SemaphoreThread(s);
        for (int i = 0; i < 8; i++) {
            new Thread(semaphoreThread).start();
        }
    }
}
class SemaphoreThread implements Runnable {
    private Semaphore s;
    public SemaphoreThread(Semaphore s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            s.acquire();
            System.out.println("占用一个机器");
            Thread.sleep(2000);
            System.out.println("释放一个机器");
            s.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
