package happyangel.learnjava.Concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by lei on 17-2-9.
 *
 * to demonstrate lock-free algorithm Treiber's Algorithm
 *
 * from <java concurrency in practice>
 */
public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<>(); // use AtomicReference is to keep visibility

    // using CAS
    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;

        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;

        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while(!top.compareAndSet(oldHead, newHead));

        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
