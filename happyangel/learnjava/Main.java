package happyangel.learnjava;

import happyangel.learnjava.Concurrent.OneShotLatch;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final OneShotLatch oneShotLatch = new OneShotLatch();

        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 running...");
            try {
                oneShotLatch.await();
            } catch (InterruptedException ex) {}

            System.out.println("Thread 1 finished...");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("Thread 2 running...");
            try {
                oneShotLatch.await();
            } catch (InterruptedException ex) {}

            System.out.println("Thread 2 finished...");
        });

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Now allowing to proceed...");

        oneShotLatch.signal();
    }
}

