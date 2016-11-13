package happyangel.learnjava.Concurrent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * Created by Lei on 2016/10/30.
 *
 * spin lock with random waiting
 *
 *
 * two issues for back-off lock:
 - Cache-coherence Traffic: All threads spin on the same shared location causing  cache-coherence traffic on every successful lock access;
 - Critical Section Underutilization: Threads delay longer than necessary, causing  the the critical section to be underutilized.

 * from <The art of multiprocessor programming>
 */
public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 10;
    private static final int MAX_DELAY = 30;

    @Override
    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while(true) {
            // optimize here to use local cache instead of lock
            while (state.get()) {}
            if (!state.getAndSet(true)) {
                return;
            } else {
                try {
                    backoff.backoff();
                } catch (InterruptedException ex) {

                }
            }
        }
    }

    @Override
    public void unlock() {
        state.set(false);
    }

    class Backoff {
        final int minDelay, maxDelay;
        int limit;
        final Random random;

        public Backoff(int min, int max) {
            minDelay = min;
            maxDelay = max;
            limit = minDelay;
            random = new Random();
        }

        // exponential back off time
        public void backoff() throws InterruptedException {
            int delay = random.nextInt(limit);
            limit = Math.min(maxDelay, 2*limit);
            Thread.sleep(delay);
        }
    }
}
