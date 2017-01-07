package happyangel.learnjava.Concurrent;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Lei on 2017/1/7.
 *
 * from <java concurrency in practice>
 *
 * to demonstrate thread stop strategy
 *
 * 1. reservations allwo to stop gracefully;
 * 2. use synchronized shutdown to force no more log after sending shutdown command;
 */
public class LogService {
    private final BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    private final LoggerThread loggerThread = new LoggerThread();
    private final PrintWriter writer;

    private boolean isShutdown;
    private int reservations;

    public LogService() throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            ++reservations;
        }

        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public void run() {
            try {
                while(true) {
                    try {
                        synchronized (this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (this) {--reservations;}
                        writer.println(msg);
                    } catch (InterruptedException e) { /* retry */}
                }
            } finally {
                writer.close();
            }
        }
    }
}
