package cn.melon.demo.volatileDemo;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest3 {
    public static void main(String[] args) {
        JUCAtomic t1 = new JUCAtomic();

        for (int i = 0; i < 10; i++) {
            new Thread(t1).start();
        }
    }
}


class JUCAtomic implements Runnable {
    private AtomicInteger num = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num.getAndIncrement());
    }

}
