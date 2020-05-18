package cn.melon.demo.jucControl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 大巴组织旅游，三个景点，使用CyclicBarrier控制统一出发
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        BarrierThread t1 = new BarrierThread(cyclicBarrier);

        for (int i = 0; i < 3; i++) {
            new Thread(t1).start();
        }
    }

}

class BarrierThread implements Runnable {
    private CyclicBarrier cyclicBarrier;
    public BarrierThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random()*10000));
            System.out.print(Thread.currentThread().getName() + "到达集合地点1   ");
            System.out.print((cyclicBarrier.getNumberWaiting() + 1) + "个已经到达   ");
            System.out.println((cyclicBarrier.getNumberWaiting() == 2) ? "都到齐了" : "继续等待");

            cyclicBarrier.await();

            Thread.sleep((long) (Math.random()*10000));
            System.out.print(Thread.currentThread().getName() + "到达集合地点2   ");
            System.out.print((cyclicBarrier.getNumberWaiting() + 1) + "个已经到达   ");
            System.out.println((cyclicBarrier.getNumberWaiting() == 2) ? "都到齐了" : "继续等待");

            cyclicBarrier.await();


            Thread.sleep((long) (Math.random()*10000));
            System.out.print(Thread.currentThread().getName() + "到达集合地点3   ");
            System.out.print((cyclicBarrier.getNumberWaiting() + 1) + "个已经到达   ");
            System.out.println((cyclicBarrier.getNumberWaiting() == 2) ? "都到齐了" : "继续等待");

            cyclicBarrier.await();



        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
