package cn.melon.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PCDemo2 {
    public static void main(String[] args) {
       ReentrantLock lock = new ReentrantLock();
       Condition condition = lock.newCondition();

       P2 p = new P2(lock,condition);
       C2 c = new C2(lock, condition);

       p.start();
       c.start();


    }
}

/**
 * 模拟队列
 */
class Value2 {
    public static String value = "";
}

/**
 * 生产者为1的情况
 */
class P2 extends Thread {
    private ReentrantLock lock;
    private Condition condition;

    public P2(ReentrantLock lock, Condition condition) {
        super();
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            // 如果队列不为空
            if (!Value2.value.equals("")) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String value = System.currentTimeMillis() + "_" + System.nanoTime();
            System.out.println("设置的值为：" + value);
            Value2.value = value;
            condition.signal();
            lock.unlock();
        }
    }
}

/**
 * 消费者也为1的情况
 */
class C2 extends Thread {
    private ReentrantLock lock;
    private Condition condition;

    public C2(ReentrantLock lock, Condition condition) {
        super();
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (Value2.value.equals("")) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("消费的值为：" + Value2.value);
            Value2.value = "";
            condition.signal();
            lock.unlock();
        }
    }
}

