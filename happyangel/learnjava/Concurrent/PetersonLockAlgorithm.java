package happyangel.learnjava.Concurrent;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by xionglei on 16-10-19.
 *
 * code to demonstrate Peterson Lock Algorithm
 */
public class PetersonLockAlgorithm {
    private static PetersonLock petersonLock;

    public static void main(String[] args) throws Exception {
        Run r = new Run();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);

        petersonLock = new PetersonLock(thread1.getId(), thread2.getId());

        thread1.start();
        thread2.start();

        TimeUnit.MINUTES.sleep(1);
    }

    static class Run implements Runnable {
        private volatile int testNum = 0;

        @Override
        public void run() {
            while(true) {
                petersonLock.lock();
                testNum++;
                testNum++;
                testNum++;
                testNum++;

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    System.out.println("thread got interrupted. " + ex.getMessage());
                }
                System.out.println(Thread.currentThread().getId() + ": testNum = " + testNum);
                petersonLock.unlock();
            }
        }
    }

}

class PetersonLock implements Lock {
    private volatile Map<Long, Boolean> flag = new HashMap<>(); // if i'm interested
    private volatile long victim; // you go first
    private final long threadIndex1;
    private final long threadIndex2;

    public PetersonLock(long threadIndex1, long threadIndex2) {
        flag.put(threadIndex1, false);
        flag.put(threadIndex2, false);
        this.threadIndex1 = threadIndex1;
        this.threadIndex2 = threadIndex2;
    }

    @Override
    public void lock() {
        long id = Thread.currentThread().getId();
        long theOtherId;

        if (id == threadIndex1) {
            theOtherId = threadIndex2;
        } else {
            theOtherId = threadIndex1;
        }

        flag.put(id, true);
        victim = id;

        while(flag.get(theOtherId) && victim==id);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        throw new NotImplementedException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new NotImplementedException();
    }

    @Override
    public void unlock() {
        flag.put(Thread.currentThread().getId(), false);
    }

    @Override
    public Condition newCondition() {
        throw new NotImplementedException();
    }
}
