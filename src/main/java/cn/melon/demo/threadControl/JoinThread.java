package cn.melon.demo.threadControl;

public class JoinThread {
    public static void main(String[] args) {
        ThreadJoinTest t1 = new ThreadJoinTest("thread1");
        ThreadJoinTest t2 = new ThreadJoinTest("thread2");
        ThreadJoinTest t3 = new ThreadJoinTest("thread3");
        t1.start();
        t2.start();
        // 等t1线程结束，再进行其他线程
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t3.start();

    }
}

class ThreadJoinTest extends Thread {

    public ThreadJoinTest(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}


