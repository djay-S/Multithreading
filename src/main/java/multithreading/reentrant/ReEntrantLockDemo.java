package multithreading.reentrant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//https://www.youtube.com/watch?v=fjMTaVykOpc
public class ReEntrantLockDemo {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            normalExample();
            lockedExample();
            Thread.sleep(1000);
        }
    }

    private static void normalExample() throws InterruptedException {
        NormalRunner normalRunner = new NormalRunner();

        Thread t1 = new Thread(normalRunner::firstThread);

        Thread t2 = new Thread(normalRunner::secondThread);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        normalRunner.finished();
    }

    private static void lockedExample() throws InterruptedException {
        LockedRunner lockedRunner = new LockedRunner();

        Thread t1 = new Thread(() -> lockedRunner.firstThread());

        Thread t2 = new Thread(lockedRunner::secondThread);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        lockedRunner.finished();
    }
}

class NormalRunner {
    private int count = 0;

    void increment() {
        for (int i = 0; i < 1_00_00_000; i++) {
            count++;
        }
    }

    void firstThread() {
        increment();
    }

    void secondThread() {
        increment();
    }

    void finished() {
        System.out.println("Value of normalCount: " + count);
    }
}

class LockedRunner {

    private int count = 0;
    Lock lock = new ReentrantLock();

    private void increment() {
        for (int i = 0; i < 1_00_00_000; i++) {
            count++;
        }
    }

    void firstThread() {
        lock.lock();
        try {
            increment();
        }
        finally {
            lock.unlock();
        }
    }

    void secondThread() {
        lock.lock();
        try {
            increment();
        }
        finally {
            lock.unlock();
        }
    }

    void finished() {
        System.out.println("Value of lockedCount: " + count);
    }
}