package happyangel.learnjava.Concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by happyangel  on 17-1-13.
 *
 * example from <java concurrent in practice> to demonstrate how to extend ThreadPoolExecutor
 */
public class TimingThreadPool extends ThreadPoolExecutor{
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.printf("Thread %s: start %s\n", t, r);
        startTime.set(System.nanoTime());
    }

    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            System.out.printf("Thread %s: end %s, time=%dns\n", t,r,taskTime);
        } finally {
            super.afterExecute(r, t);
        }
    }

    protected void terminated() {
        try {
            System.out.printf("Terminated: avg time = %dns\n", totalTime.get()/numTasks.get());
        } finally {
            super.terminated();
        }
    }


    public static void main(String[] args) {
        ExecutorService service = new TimingThreadPool(2,2,0,TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>());

        try {
            service.submit(() -> {
                System.out.println("Task 1 executing");
            });

            service.submit(() -> {
                System.out.println("Task 2 executing");
            });

            service.submit(() -> {
                System.out.println("Task 3 executing");
            });

        } finally {
            service.shutdown();
        }
    }
}
