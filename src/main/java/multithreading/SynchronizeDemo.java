package multithreading;

public class SynchronizeDemo {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            synchronisedExample();
            notSynchronisedExample();
        }
    }

    private static void synchronisedExample() throws InterruptedException {
        SyncCounter c = new SyncCounter();

        Thread t1 = new Thread(c, "Thread1");
        Thread t2 = new Thread(c, "Thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Synchronised: " + c.count);
    }

    private static void notSynchronisedExample() throws InterruptedException {
        Counter c = new Counter();

        Thread t1 = new Thread(c, "Thread1");
        Thread t2 = new Thread(c, "Thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Not Synchronised: " + c.count);
    }
}

class Counter extends Thread {

    int count = 0;

    @Override
    public void run() {
        int max = 1_00_00_000;

        for (int i = 0; i < max; i++)
            count++;
    }
}

class SyncCounter extends Thread {

    int count = 0;

    @Override
    public synchronized void run() {
        int max = 1_00_00_000;

        for (int i = 0; i < max; i++)
            count++;
    }
}