package multithreading;

import java.util.LinkedList;

public class ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();

        Thread t1 = new Thread(() -> {
            try {
                task.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                task.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class Task {
        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 2;

        public void producer() throws InterruptedException {
            int value = 0;
            synchronized (this) {
                while (true) {
                    if (list.size() == capacity) {
                        wait();
                    }
                    System.out.println("Producer producing: " + value);

                    list.add(value++);

                    notify();

                    Thread.sleep(1000);
                }
            }
        }

        public void consumer() throws InterruptedException {
            synchronized (this) {
                while (true) {
                    if (list.isEmpty()) {
                        wait();
                    }
                    Integer value = list.removeFirst();

                    System.out.println("Consumer consuming: " + value);

                    notify();

                    Thread.sleep(1000);
                }
            }
        }
    }
}
