package happyangel.learnjava;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xionglei on 16-7-21.
 */
public class Bank {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }
//
//    public void transfer(int from, int to, double amount) throws InterruptedException {
//        bankLock.lock();
//        try {
//            while (accounts[from] < amount) {
//                sufficientFunds.await();
//            }
//            System.out.print(Thread.currentThread());
//            accounts[from] -= amount;
//            System.out.printf("%10.2f from %d to %d", amount, from, to);
//            accounts[to] += amount;
//
//        }
//    }
}
