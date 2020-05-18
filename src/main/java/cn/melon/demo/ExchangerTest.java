package cn.melon.demo;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {
    private static String aa = null;
    private static String bb = null;

    public void a(Exchanger<String> exchanger) {
        aa = "aa";

        System.out.println("aa = " + aa);
        try {
            aa = exchanger.exchange(aa);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("aa = " + aa);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void b(Exchanger<String> exchanger) {
        bb = "bb";

        System.out.println("bb = " + bb);

        try {
            exchanger.exchange(bb);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("bb = " + bb);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();
        final ExchangerTest exchangerTest = new ExchangerTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                exchangerTest.a(exchanger);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                exchangerTest.b(exchanger);
            }
        }).start();


    }
}

