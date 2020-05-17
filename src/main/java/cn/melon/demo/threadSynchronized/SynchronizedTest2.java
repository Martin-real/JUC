package cn.melon.demo.threadSynchronized;

public class SynchronizedTest2 {
    public static void main(String[] args) {
        SynchronizedT2 thread = new SynchronizedT2();

        for (int i = 0; i < 4; i++) {
            new Thread(thread).start();
        }
    }
}

class SynchronizedT2 implements Runnable {
    private int tickets = 100;
    @Override
    public void run() {
        save();
    }

    public synchronized void save() {
        while (true) {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName() + tickets--);
            }
        }
    }
}