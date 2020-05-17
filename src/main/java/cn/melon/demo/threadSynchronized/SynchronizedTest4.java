package cn.melon.demo.threadSynchronized;

public class SynchronizedTest4 {
    public static void main(String[] args) {
        ThreadLocalDemo thread = new ThreadLocalDemo();

        for (int i = 0; i < 4; i++) {
            new Thread(thread).start();
        }
    }
}

class ThreadLocalDemo implements Runnable {
    private ThreadLocal<Integer> tickets = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 100;
        }
    };

    @Override
    public void run() {
        while (true) {
            if (tickets.get() > 0) {
                System.out.println(Thread.currentThread().getName() + ":" + tickets.get());
                tickets.set(tickets.get() - 1);
            } else {
                break;
            }
        }
    }
}