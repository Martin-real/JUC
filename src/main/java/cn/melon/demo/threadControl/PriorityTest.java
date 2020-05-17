package cn.melon.demo.threadControl;

public class PriorityTest {
    public static void main(String[] args) {

        Thread.currentThread().setPriority(6);

        for (int i = 0; i < 30; i++) {
            if (i == 10) {
                PriorityThread low = new PriorityThread("low");
                low.start();
                System.out.println("初始优先级：" + low.getPriority());
                low.setPriority(Thread.MIN_PRIORITY);
            }
            if (i == 20) {
                PriorityThread high = new PriorityThread("high");
                high.start();
                System.out.println("初始优先级：" + high.getPriority());
                high.setPriority(Thread.MAX_PRIORITY);
            }
        }
    }
}


class PriorityThread extends Thread {
    public PriorityThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(this.getName() + "优先级：" + this.getPriority() + "循环" + i);
        }
    }
}