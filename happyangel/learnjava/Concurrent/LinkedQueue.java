package happyangel.learnjava.Concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lei on 2016/11/13.
 *
 * examples from the art of multiprocessor programming
 *
 * to demonstrate Condition
 */
public class LinkedQueue<T> {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    final T[] items;
    int tail, head, count;

    public LinkedQueue(int capacity) {
        items = (T[])new Object[capacity];
    }

    public void enq(T x) {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } catch (InterruptedException ex) {
            // other thread interrupt
        } finally {
            lock.unlock();
        }
    }

    public T deq() {
        lock.lock();
        try {
            // should keep check, even if get signalled
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } catch (InterruptedException ex) {

        } finally {
            lock.unlock();
        }
        return null;
    }
}
