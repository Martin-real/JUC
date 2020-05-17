package cn.melon.demo.threadControl;

public class YieldTest {
    public static void main(String[] args) {
        YieldThread t1 = new YieldThread("yield");
        t1.start();

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ":" +i);
        }

        Thread.yield();

    }
}

class YieldThread extends Thread {
    public YieldThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}