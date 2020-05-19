package cn.melon.demo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolTest1 {
    public static void main(String[] args) throws InterruptedException {

        BigTask task = new BigTask(1,30000);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }

}



class BigTask extends RecursiveAction {
    private static int THRESHOLD = 50;
    private int start;
    private int end;
    public BigTask(int start, int end) {
        super();
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值：" + i);
            }
        } else {
            int middle = (start + end) / 2;
            BigTask left = new BigTask(start, middle);
            BigTask right = new BigTask(middle, end);

            left.fork();
            right.fork();
        }

    }
}
