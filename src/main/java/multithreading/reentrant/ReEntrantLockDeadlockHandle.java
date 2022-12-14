package multithreading.reentrant;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//https://www.youtube.com/watch?v=ghCUBi41bGA
public class ReEntrantLockDeadlockHandle {
    public static void main(String[] args) throws InterruptedException {
        unlocked();

        System.out.println("With locking.");

        locked();
    }

    private static void locked() throws InterruptedException {
        final RunnerLocked runner = new RunnerLocked();

        Thread t1 = new Thread(() -> {
            try {
                runner.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    private static void unlocked() throws InterruptedException {
        final RunnerUnlocked runner = new RunnerUnlocked();

        Thread t1 = new Thread(runner::firstThread);
        Thread t2 = new Thread(runner::secondThread);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finished();
    }
}

class RunnerLocked {
    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {

        while (true){
            boolean getFirstLock = false;
            boolean getSecondLock = false;

            try{
                getFirstLock = firstLock.tryLock();
                getSecondLock = secondLock.tryLock();
            }
            finally {
                if (getFirstLock && getSecondLock) {
                    return;
                }
                if (getFirstLock) {
                    firstLock.unlock();
                }
                if (getSecondLock) {
                    secondLock.unlock();
                }
            }

            Thread.sleep(1);
        }
    }

    void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {

            acquireLocks(lock1, lock2);
            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            }
            finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {

//            Works fine
//            lock1.lock();
//            lock2.lock();

//            Will cause Deadlock
//            lock2.lock();
//            lock1.lock();

//            Always works
//            acquireLocks(lock1, lock2);
            acquireLocks(lock2, lock1);
            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            }
            finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1  balance: " + acc1.getBalance());
        System.out.println("Account 2  balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
    }
}

class RunnerUnlocked {
    private Account acc1 = new Account();
    private Account acc2 = new Account();

    void firstThread(){
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            Account.transfer(acc1, acc2, random.nextInt(100));
        }
    }

    void secondThread(){
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            Account.transfer(acc2, acc2, random.nextInt(100));
        }
    }

    public void finished() {
        System.out.println("Account 1  balance: " + acc1.getBalance());
        System.out.println("Account 2  balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}