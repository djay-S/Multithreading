package multithreading.waitNotify;

import java.util.Scanner;

//https://www.youtube.com/watch?v=gx_YUORX5vk&list=PLBB24CFB073F1048E&index=8
public class WaitNotifyDemo {
    public static void main(String[] args) throws InterruptedException {
        Processor processor = new Processor();

        Thread processorThread = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumeThread = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        processorThread.start();
        consumeThread.start();

        processorThread.join();
        consumeThread.join();
    }
}

class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running.");
//            This waits the thread with less resources and it can onlyu be called within synchronised blocks
//            This also hands the control of their lock onto the synchronised lock that is locked on (Here the Processor class intrinsic lock)
//            This regains control when another thread locked on the same object, runs the notify() or notifyAll()
            wait();
            System.out.println("Resumed after notify consumer releases the lock.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Press the Return Key..");
            scanner.nextLine();
            System.out.println("Return key pressed");

//            Notifies the thread that locks on the same object to wake up
//            This does not release the lock immediately
            notify();
            Thread.sleep(3000);
        }
    }
}