package cn.melon.demo.threadPool;

import java.util.concurrent.*;

public class Demo1 {
    public static void main(String[] args) {
        int corePoolSize = 5;
        int maximumPoolSize =10;
        long keepActiveTime = 200;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit, workQueue);

        for (int i = 0; i < 15; i++) {
            MyTask task = new MyTask(i);

            pool.execute(task);

            System.out.println("线程池中线程数目" + pool.getPoolSize() +
                                "正在等待执行的任务数目" + pool.getQueue() +
                                "已执行完的任务数量" + pool.getCompletedTaskCount());
        }

        pool.shutdown();
    }
}

class MyTask implements Runnable {
    private int num;

    public MyTask(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task ：" + num);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("task" + num + "执行完毕");
    }
}
