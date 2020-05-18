package cn.melon.demo.volatileDemo;

public class VolatileTest1 {
    public static void main(String[] args) {
        VolatileThread t1 = new VolatileThread();

        new Thread(t1).start();

        // 会进入死循环
        while (true) {
            if (t1.isFlag()) {
                System.out.println("主线程的程序读到flag为true");
                break;
            }
//            这里有语句循环最终会结束，即主线程能够读到flag为true
//            System.out.println("hhd");
        }
    }
}

class VolatileThread implements Runnable {
//    private boolean flag = false;
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag:" + flag);
    }

    public boolean isFlag() {
        return flag;
    }
}
