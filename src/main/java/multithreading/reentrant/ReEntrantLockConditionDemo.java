package multithreading.reentrant;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//https://www.youtube.com/watch?v=fjMTaVykOpc
public class ReEntrantLockConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                runner.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finished();
    }
}
class Runner {
    private int count = 0;
    Lock lock = new ReentrantLock();
    Condition cont = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 1_00_00_000; i++) {
            count++;
        }
    }

    void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("aWaiting...");
        cont.await();
        System.out.println("aWaiting over");

        try {
            increment();
        }
        finally {
            lock.unlock();
        }
    }

    void secondThread() throws InterruptedException{
        lock.lock();
        Thread.sleep(1000);

        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Pressed return key!");

        cont.signal();
//        cont.signalAll();

        try {
            increment();
        }
        finally {
            lock.unlock();
        }
    }

    void finished() {
        System.out.println("Value of count: " + count);
    }
}