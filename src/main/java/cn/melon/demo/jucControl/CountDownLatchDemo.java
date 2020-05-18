package cn.melon.demo.jucControl;

import java.util.concurrent.CountDownLatch;

/**
 * 等待指定线程数的线程执行忘，统计执行时间
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        long start = System.currentTimeMillis();

        LatchThread t1 = new LatchThread(latch);
        for (int i = 0; i < 10; i++) {
            new Thread(t1).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗时为：" + (end - start) + "ms");
    }
}

class LatchThread implements Runnable {
    private CountDownLatch latch;
    public LatchThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName() + "i");
        }
        latch.countDown();
    }
}
