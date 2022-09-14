package multithreading;

import java.util.concurrent.CountDownLatch;

//https://www.geeksforgeeks.org/countdownlatch-in-java/?ref=lbp
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        countDownLatchMethod(4);
        countDownLatchMethod(2);
    }

    private static void countDownLatchMethod(int invitedThreadCount) throws InterruptedException {
        System.out.println("Creating countDownLatch for " + invitedThreadCount + " threads.");
        System.out.println("Number of worker threads: " + 4);
        CountDownLatch countDownLatch = new CountDownLatch(invitedThreadCount);

        Worker t1 = new Worker(1000, countDownLatch, "Worker 1");
        Worker t2 = new Worker(2000, countDownLatch, "Worker 2");
        Worker t3 = new Worker(3000, countDownLatch, "Worker 3");
        Worker t4 = new Worker(500, countDownLatch, "Worker 4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + " has finished.");
    }
}

class Worker extends Thread {

    private int delay;
    private CountDownLatch countDownLatch;

    public Worker(int delay, CountDownLatch countDownLatch, String threadName) {
        super(threadName);
        this.delay = delay;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            Thread.sleep(delay);
            countDownLatch.countDown();
            System.out.println("CountDownLatch count: " + countDownLatch.getCount());
            System.out.println(Thread.currentThread().getName() + " finished.");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}