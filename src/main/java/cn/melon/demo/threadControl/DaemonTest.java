package cn.melon.demo.threadControl;

public class DaemonTest {
    public static void main(String[] args) {
        DaemonThread t1 = new DaemonThread("后台进程");
        t1.setDaemon(true);

        t1.start();

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() +":"+ i);
        }
    }
}
class DaemonThread extends Thread {
    public DaemonThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}

