package cn.melon.demo.volatileDemo;

public class VolatileTest2 {
    public static void main(String[] args) {
        AtomicThread t1 = new AtomicThread();

        for (int i = 0; i < 10; i++) {
            new Thread(t1).start();
        }
    }
}

class AtomicThread implements Runnable {
    private volatile int num = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num++);
    }

}
