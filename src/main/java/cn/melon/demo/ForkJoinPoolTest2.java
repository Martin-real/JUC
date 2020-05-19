package cn.melon.demo;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = new int[300];
        Random random = new Random();
        int total = 0;

        // 使用正常for循环计算arr数组的和
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            int temp = random.nextInt(20);
            total += (arr[i] = temp);
        }
        System.out.println("初始数组总和:" + total);
        long end = System.currentTimeMillis();
        System.out.println("for循环用时" + (end - start) );


        start = System.currentTimeMillis();
        SumTask task = new SumTask(arr, 0, arr.length);
        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> future = pool.submit(task);
        System.out.println("多线程执行结果："+future.get());
        pool.shutdown();
        end = System.currentTimeMillis();
        System.out.println("for循环用时" + (end - start) );

    }
}

class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 20;
    private int arry[];
    private int start;
    private int end;

    public SumTask(int[] arry, int start, int end) {
        super();
        this.arry = arry;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum =0;
        if(end - start <THRESHOLD){
            for(int i= start;i<end;i++){
                sum += arry[i];
            }
            return sum;
        }else {
            int middle = (start+ end)/2;
            SumTask left = new SumTask(arry, start, middle);
            SumTask right = new SumTask(arry, middle, end);

            left.fork();
            right.fork();

            return left.join()+right.join();
        }
    }
}
