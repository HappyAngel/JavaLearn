package happyangel.learnjava.Concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xionglei on 16-11-14.
 *
 * from <the art of  Multiprocessor programming>
 *
 * A simple implementation of Readers-Writers Lock
 *
 * Drawbacks:
 *
 * If readers are much more frequent than writers, as is usually the case,
 then writers could be locked out for a long time by a continual stream of readers
 */
public class SimpleReadWriteLock implements ReadWriteLock{
    int readers;
    boolean writer;
    Lock lock;
    Condition condition;
    Lock readLock, writeLock;

    public SimpleReadWriteLock() {
        writer = false;
        readers = 0;
        lock = new ReentrantLock();
        readLock = new ReadLock();
        writeLock = new WriteLock();
        condition = lock.newCondition();
    }

    @Override
    public Lock readLock() {
        return readLock;
    }

    @Override
    public Lock writeLock() {
        return writeLock;
    }

    class ReadLock implements Lock {

        @Override
        public void lock() {
            lock.lock();
            try {
                while (writer) {
                    condition.await();
                }
                readers++;
            } catch (InterruptedException ex) {
            } finally {
                lock.unlock();
            }
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

        @Override
        public void unlock() {
            lock.lock();
            try {
                readers--;
                if (readers == 0) {
                    condition.signalAll();
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

    class WriteLock implements Lock {

        @Override
        public void lock() {
            lock.lock();

            try {
                while (readers > 0 | writer) {
                    condition.await();
                }
                writer = true;
            } catch (InterruptedException ex) {

            } finally {
                lock.unlock();
            }
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

        @Override
        public void unlock() {
            writer = false;
            condition.signalAll();
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }
}
