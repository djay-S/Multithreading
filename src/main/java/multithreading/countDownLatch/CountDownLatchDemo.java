package multithreading.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//https://www.youtube.com/watch?v=1H-Vfu1v_2g&list=PLBB24CFB073F1048E&index=6
public class CountDownLatchDemo {
    public static void main(String[] args) {

//        Sets a count down so that the threads wait for the latch to reach 0 after which the threads start
        CountDownLatch latch = new CountDownLatch(4);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 4; i++) {
            executorService.submit(new Processor(latch, i));
        }

        try {
//            This waits until the latch counts down to 0
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }
        System.out.println("Completed.");
    }
}

class Processor implements Runnable {

    private CountDownLatch latch;
    private int id;

    public Processor(CountDownLatch latch, int id) {
        this.latch = latch;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Started: " + id);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed: " + id);
        latch.countDown();
    }
}