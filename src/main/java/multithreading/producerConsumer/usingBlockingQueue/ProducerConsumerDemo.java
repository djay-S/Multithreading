package multithreading.producerConsumer.usingBlockingQueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//https://www.youtube.com/watch?v=Vrt5LqpH2D0&list=PLBB24CFB073F1048E&index=7
public class ProducerConsumerDemo {

//    No need to explicitly synchronise as it is taken care of by the BlockingQueue
//    If the queue is full, BlockingQueue will wait till consumes the queue
//    If the queue is empty, BlockingQueue will wait till producer adds to the queue
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private static Random random = new Random();
    private static int value = 0;


    //    Producers are responsible for producing things which can be shared across other producers using the queue
    public static void producer() throws InterruptedException {

        while (true) {
            queue.put(value++);
        }
    }

//    Consumers are responsible for consuming the things from the queue
    public static void consumer() throws InterruptedException {

        while (true) {
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                Integer takenValue = queue.take();

                System.out.printf("Taken value: %d; Queue size is: %d%n", takenValue, queue.size());
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
