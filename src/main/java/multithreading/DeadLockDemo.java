package multithreading;


//https://www.geeksforgeeks.org/deadlock-in-java-multithreading/?ref=lbp
public class DeadLockDemo {

    public static void main(String[] args) throws InterruptedException {
        Task t1 = new Task();
        Task t2 = new Task();

        Worker1 w1 = new Worker1(t1, t2);
        Worker2 w2 = new Worker2(t1, t2);

        w1.start();
        w2.start();

        Thread.sleep(2000);
    }
}
class Task {
    synchronized void test1(Task t2) throws InterruptedException {
        System.out.println("Task 1 started");
        Thread.sleep(1000);

        t2.test2();

        System.out.println("Task 1 ended");
    }

    synchronized void test2() throws InterruptedException {
        System.out.println("Task 2 started");
        Thread.sleep(1000);

        System.out.println("Task 2 ended");
    }
}

class Worker1 extends Thread {
    private Task t1;
    private Task t2;

    public Worker1(Task t1, Task t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public void run() {
        try {
            t1.test1(t2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Worker2 extends Thread {
    private Task t1;
    private Task t2;

    public Worker2(Task t1, Task t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public void run() {
        try {
            t2.test1(t1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}