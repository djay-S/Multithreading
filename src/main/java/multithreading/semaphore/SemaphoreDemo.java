package multithreading.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//https://www.youtube.com/watch?v=KxTRsvgqoVQ
public class SemaphoreDemo {
    public static void main(String[] args) throws InterruptedException {

        nonSemaphoreConnection();

        System.out.println("----------------------------");

        semaphoreConnection();
    }

    private static void semaphoreConnection() throws InterruptedException{
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> SemaphoreConnection.getInstance().connect());
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }

    private static void nonSemaphoreConnection() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> NonSemaphoreConnection.getInstance().connect());
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}

class NonSemaphoreConnection {
    private int connectionCount = 0;

    private static NonSemaphoreConnection instance = new NonSemaphoreConnection();

    private NonSemaphoreConnection(){}

    public static NonSemaphoreConnection getInstance() {
        return instance;
    }

    public void connect() {
        synchronized (this) {
            connectionCount++;
            System.out.println("Current connections: " + connectionCount);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connectionCount--;
        }
    }
}

class SemaphoreConnection {
    private int connectionCount = 0;
    private Semaphore semaphore = new Semaphore(10, true);

    private static SemaphoreConnection connection = new SemaphoreConnection();

    private SemaphoreConnection(){}

    public static SemaphoreConnection getInstance() {
        return connection;
    }

    public void connect() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            task();
        }
        finally {
            semaphore.release();
        }
    }

    public void task() {

        synchronized (this) {
            connectionCount++;
            System.out.println("Current connections: " + connectionCount);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connectionCount--;
        }
    }

}