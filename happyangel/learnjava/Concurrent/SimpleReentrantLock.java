package happyangel.learnjava.Concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by xionglei on 16-11-15.
 *
 * example from the art of multiprocessor programming
 *
 * to demonstrate lock & condition
 */
public class SimpleReentrantLock implements Lock {
    Lock lock;
    Condition condition;
    int owner, holdCount;

    public SimpleReentrantLock() {
        lock = new SimpleLock();
        condition = lock.newCondition();
        owner = 0;
        holdCount = 0;
    }

    public void lock() {
        int me = ThreadID.get();
        lock.lock();
        try {
            if (owner == me) {
                holdCount++;
                return;
            }

            while (holdCount != 0) {
                condition.await();
            }
        } catch (InterruptedException ex) {

        } finally {
            lock.unlock();
        }

        owner = me;
        holdCount = 1;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        lock.lock();
        try {
            if (holdCount == 0 || owner != ThreadID.get()) {
                throw new IllegalMonitorStateException();
            }
            holdCount--;
            if (holdCount == 0) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
