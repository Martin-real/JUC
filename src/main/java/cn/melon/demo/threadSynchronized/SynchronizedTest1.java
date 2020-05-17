package cn.melon.demo.threadSynchronized;

public class SynchronizedTest1 {
    public static void main(String[] args) {
        SynchronizedThread thread = new SynchronizedThread();

        for (int i = 0; i < 4; i++) {
            new Thread(thread).start();
        }
    }
}

class SynchronizedThread implements Runnable {
    private Object obj = new Object();
    private int tickets = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (obj) {
                if (tickets > 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + tickets--);
                }
            }
        }
    }
}

