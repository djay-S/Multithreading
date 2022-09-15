package multithreading.callableNfuture;

import java.util.Random;
import java.util.concurrent.*;

//https://www.youtube.com/watch?v=lnbWFV4b7M4
public class CallableNFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

//        callableUsingLambda(executorService);

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IllegalStateException(String.format("Duration %sms is too long.", duration));
                }

                System.out.printf("Start task of duration: %sms.%n", duration);

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Task Finished");
                return duration;
            }
        });

        System.out.println("Is task finished: " + future.isDone());
        executorService.shutdown();

        try {
            System.out.println("Duration as returned by callable:" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e);
        }
        System.out.println("Is task finished: " + future.isDone());
    }

    private static void callableUsingLambda(ExecutorService executorService) {
        executorService.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);

            if (duration > 2000) {
                throw new IllegalStateException(String.format("Duration %sms is too long.", duration));
            }

            System.out.printf("Start task of duration: %sms.%n", duration);

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Task Finished");
            return duration;
        });
    }
}
