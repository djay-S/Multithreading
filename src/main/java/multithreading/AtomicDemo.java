package multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            nonAtomicCounter();
            atomicCounter();
        }
    }

    private static void atomicCounter() throws InterruptedException {
        NormalCounter c = new NormalCounter();

        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);

        t1.start();
        t2.start();

        t1.join();
        t1.join();

        System.out.println("Non Atomic Counter: " + c.count);
    }

    private static void nonAtomicCounter() throws InterruptedException {
        AtomicCounter c = new AtomicCounter();

        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);

        t1.start();
        t2.start();

        t1.join();
        t1.join();

        System.out.println("Atomic Counter: " + c.count);
    }
}

class NormalCounter extends Thread {
    int count = 0;

    @Override
    public void run() {
        int max = 1_00_00_000;

        for (int i = 0; i < max; i++)
            count++;
    }
}

class AtomicCounter extends Thread {
    AtomicInteger count = new AtomicInteger();

    @Override
    public void run() {
        int max = 1_00_00_000;
        for (int i = 0; i < max; i++)
            count.incrementAndGet();
    }
}
