package cn.melon.demo;

public class PCDemo1 {
    public static void main(String[] args) {
        String lock = new String("");
        P p = new P(lock);
        C c = new C(lock);

        p.start();
        c.start();
    }
}

/**
 * 模拟队列
 */
class Value {
    public static String value = "";
}

/**
 * 生产者为1的情况
 */
class P extends Thread {
    private String lock;

    public P(String lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                // 如果队列不为空
                if (!Value.value.equals("")) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String value = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("设置的值为：" + value);
                Value.value = value;
                lock.notify();
            }
        }
    }
}

/**
 * 消费者也为1的情况
 */
class C extends Thread {
    private String lock;

    public C(String lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (Value.value.equals("")) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("消费的值为：" + Value.value);
                Value.value = "";
                lock.notify();
            }

        }
    }
}
