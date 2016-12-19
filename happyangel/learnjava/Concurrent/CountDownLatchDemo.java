package happyangel.learnjava.Concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lei on 16-12-19.
 *
 * from Java Concurrency in Practice
 */
public class CountDownLatchDemo {
    public long timeTasks(int nThreads, final Runnable task)
        throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i=0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {}
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }

    public static void main(String[] args) {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        long timeElapsed = 0;
        try {
            timeElapsed = demo.timeTasks(10, new Task());
        } catch (InterruptedException ex) {}

        System.out.println("Time elapsed: " + timeElapsed/1000000000);
    }

    static class Task implements Runnable {
        @Override
        public void run(){
            System.out.println("start to sleep for 1s");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {

            }
        }
    }
}

