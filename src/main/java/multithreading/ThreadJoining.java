package multithreading;

public class ThreadJoining {
    public static void main(String[] args) {
        Join j1 = new Join();
        Join j2 = new Join();
        Join j3 = new Join();

        getThreadState(j1, j2, j3);

        System.out.println("With join \n");
        withJoin(j1, j2, j3);

        j1 = new Join();
        j2 = new Join();
        j3 = new Join();

        getThreadState(j1, j2, j3);

        System.out.println("Without Join\n");
        withoutJoin(j1, j2, j3);

    }

    private static void getThreadState(Join j1, Join j2, Join j3) {
        System.out.println("J1 state: " + j1.getState());
        System.out.println("J2 state: " + j2.getState());
        System.out.println("J3 state: " + j3.getState());
    }

    private static void withJoin(Join j1, Join j2, Join j3) {
        j1.start();

        try {
            j1.join();
        } catch (InterruptedException e) {
            System.out.printf("InterruptedException is thrown: %s \n", e);
        }

        j2.start();

        try {
            j2.join();
        } catch (InterruptedException e) {
            System.out.printf("InterruptedException is thrown: %s \n", e);
        }

        j3.start();

        try {
            j3.join();
        } catch (InterruptedException e) {
            System.out.printf("InterruptedException is thrown: %s \n", e);
        }

        getThreadState(j1, j2, j3);
    }

    private static void withoutJoin(Join j1, Join j2, Join j3) {
        j1.start();
        j2.start();
        j3.start();

        try {
            j1.join();
            j2.join();
            j3.join();
        } catch (InterruptedException e) {
            System.out.printf("InterruptedException is thrown: %s\n", e);
        }

        getThreadState(j1, j2, j3);
    }
}

class Join extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.printf("Current Thread: %s \n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.printf("InterruptedException is thrown: %s \n", e);
        }
    }
}