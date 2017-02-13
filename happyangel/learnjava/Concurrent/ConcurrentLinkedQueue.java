package happyangel.learnjava.Concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Lei on 17-2-13.
 *
 * from <java concurrency in practice>
 *
 * insertion using Michael-Scott Non-blocking Queue Algorithm
 */
public class ConcurrentLinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);

        while(true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();

            // tail could get changed just after the read
            if(curTail == tail.get()) {
                if (tailNext != null) {
                    // Queue in the intermediate state, advance tail
                    // thus, even the insertion thread is down, we can still proceed
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // In quiescent state, try inserting new node
                    // more than 1 thread count insert at the same time, but it's no harm
                    // or, other thread could insert, then we fail and retry
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // Insertion succeed, now in intermediate state

                        // even we fail here, other thread could help proceed the tail pointer
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

}
