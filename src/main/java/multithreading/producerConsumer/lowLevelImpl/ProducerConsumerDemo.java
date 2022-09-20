package multithreading.producerConsumer.lowLevelImpl;

import java.util.LinkedList;
import java.util.Random;

//https://www.youtube.com/watch?v=wm1O8EE0X8k&list=PLBB24CFB073F1048E&index=9
public class ProducerConsumerDemo {

    private static LinkedList<Integer> list = new LinkedList<>();
    private static int LIMIT = 10;
    private static Object lock = new Object();
    private static int value = 0;

    public static void producer() throws InterruptedException {

        while (true) {
            synchronized (lock) {
                while (list.size() == LIMIT) {
//                    Wait and notify both are called on the Object lock here
                    lock.wait();
                }

                list.add(value++);
                lock.notify();
            }
        }
    }

    public static void consumer() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Thread.sleep(100);
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }

                if (random.nextInt(10) == 0) {
                    int removedValue = list.removeFirst();
                    System.out.printf("Taken value: %d; Queue size is: %d%n", removedValue, list.size());
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread producerThread = new Thread(() -> {
            try {
                producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}
