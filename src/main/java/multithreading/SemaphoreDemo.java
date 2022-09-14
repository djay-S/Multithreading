package multithreading;

import java.util.concurrent.Semaphore;

import static multithreading.SemaphoreDemo.SLEEP_DURATION;
import static multithreading.SemaphoreDemo.TASK_LIMIT;

//https://www.geeksforgeeks.org/semaphore-in-java/?ref=lbp
public class SemaphoreDemo {

    static int PERMIT_COUNT = 1;
    static int TASK_LIMIT = 5;
    static int SLEEP_DURATION = 1000;

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(PERMIT_COUNT);
        System.out.println("Semaphore permit count: " + semaphore.availablePermits());

        MyThread t1 = new MyThread(semaphore, "A");
        MyThread t2 = new MyThread(semaphore, "B");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + SharedCount.count);
    }
}

class MyThread extends Thread {

    Semaphore semaphore;
    String threadName;

    public MyThread(Semaphore semaphore, String threadName) {
        super(threadName);
        this.semaphore = semaphore;
        this.threadName = threadName;
    }

    public void run() {
        try {
            if (threadName.equalsIgnoreCase("A")) {
                executeAdderLogic();
            } else {
                executeRemoverLogic();
            }
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private void executeRemoverLogic() throws InterruptedException {
        System.out.println("Starting " + threadName);

        System.out.println(threadName + " is waiting for a permit.");
        semaphore.acquire();

        System.out.println(threadName + " gets a permit.");
        System.out.println("Permits remaining: " + semaphore.availablePermits());

        for (int i = 0; i < TASK_LIMIT; i++) {
            System.out.println(threadName + ": " + SharedCount.count--);
//            SharedCount.count--;
            Thread.sleep(SLEEP_DURATION);
        }

        System.out.println(threadName + " releases the permit.");
        semaphore.release();
        System.out.println("Permits remaining: " + semaphore.availablePermits());
    }

    private void executeAdderLogic() throws InterruptedException {
        System.out.println("Starting " + threadName);

        System.out.println(threadName + " is waiting for a permit.");
        semaphore.acquire();

        System.out.println(threadName + " gets a permit.");
        System.out.println("Permits remaining: " + semaphore.availablePermits());

        for (int i = 0; i < TASK_LIMIT; i++) {
            SharedCount.count++;
            System.out.println(threadName + ": " + SharedCount.count);
            Thread.sleep(SLEEP_DURATION);
        }

        System.out.println(threadName + " releases the permit.");
        semaphore.release();
        System.out.println("Permits remaining: " + semaphore.availablePermits());
    }
}

class SharedCount {
    static int count = 0;
}
