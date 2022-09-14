package multithreading;

public class VolatileDemo {

    static volatile int a = 0, b = 0;

    static void increment() {
        a++;
        b++;
    }

    static void display() {
        System.out.println("a: " + a + ", b: " + b);
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++)
                increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++)
                display();
        });

        t1.start();
        t2.start();
    }
}
