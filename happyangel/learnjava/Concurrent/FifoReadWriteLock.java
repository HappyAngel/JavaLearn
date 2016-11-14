package happyangel.learnjava.Concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xionglei on 16-11-14.
 *
 * from <the art of multiprocessor programming>
 *
 * to demonstrate the fair read-write lock
 *
 * This class ensures that once a writer calls the write lock’s lock()
 method, then no more readers will be able to acquire the read lock until the writer
 has acquired and released the write lock. Eventually, the readers holding the read
 lock will drain out without letting any more readers in, and the writer will acquire
 the write lock.
 */
public class FifoReadWriteLock implements ReadWriteLock{
    int readAcquires, readReleases;
    boolean writer;
    Lock lock;
    Condition condition;
    Lock readLock, writeLock;

    public FifoReadWriteLock() {
        readAcquires = readReleases = 0;
        writer = false;
        lock = new ReentrantLock();
        condition = lock.newCondition();
        readLock = new ReadLock();
        writeLock = new WriteLock();
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
                readAcquires++;
            } catch (InterruptedException ex){} finally {
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
                readReleases++;
                if (readAcquires == readReleases) {
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
                // exclusive with other writers
                while (writer) {
                    condition.await();
                }
                // ensure no more readers
                writer = true;
                while (readAcquires != readReleases) {
                    condition.await();
                }
            } catch (InterruptedException ex) {

            }finally {
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