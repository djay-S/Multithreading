package multithreading.synchronised;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//https://www.youtube.com/watch?v=8sgDgXUUJ68&list=PLBB24CFB073F1048E&index=4
public class SynchronisedBlockDemo {

    private static List<Integer> list1 = new ArrayList<>();
    private static List<Integer> list2 = new ArrayList<>();

    private static Random random = new Random();

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    private static void incrementList(List<Integer> list1) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        list1.add(random.nextInt());
    }

    private static void clearList() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
    }

    public static void stage1() {
        incrementList(list1);
    }

    public static void stage2() {
        incrementList(list2);
    }

    public static synchronized void syncStage1() {
        incrementList(list1);
    }

    public static synchronized void syncStage2() {
        incrementList(list2);
    }

    public static void objectSyncStage1() {
        synchronized (lock1) {
            incrementList(list1);
        }
    }

    public static void objectSyncStage2() {
        synchronized (lock2) {
            incrementList(list1);
        }
    }

    public static void process() {
        for (int i = 0; i < 1000; i++) {
            stage1();
            stage2();
        }
    }

    public static void stageSynchronisedProcess() {
        for (int i = 0; i < 1000; i++) {
            syncStage1();
            syncStage2();
        }
    }

    public static void objectSynchronisedProcess(){
        for (int i = 0; i < 1000; i++) {
            objectSyncStage1();
            objectSyncStage2();
        }
    }

    private static void blockLevelSynchronized() {
//        The synchronise lock is on an object 'lock1' for stage1() and 'lock2' for stage2()
//        Hence threads dont have to wait for the lock to be released since the menthods are synchronised on different objects
        System.out.println("Starting object synchronised...");
        Stopwatch timer = Stopwatch.createStarted();

        Thread t1 = new Thread(SynchronisedBlockDemo::objectSynchronisedProcess);
        Thread t2 = new Thread(SynchronisedBlockDemo::objectSynchronisedProcess);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stop();
        System.out.println("Time taken: " + timer);
        System.out.printf("List1 size: %d, List2 size: %d.%n%n", list1.size(), list2.size());
        clearList();
    }

    private static void methodLevelSynchronized() {
//        The intrinsic lock is on the SynchronisedBlockDemo class
//        Hence if one thread locks a method, the other thread has to wait for the lock to be released to access any method
        System.out.println("Starting stage synchronised...");
        Stopwatch timer = Stopwatch.createStarted();

        Thread t1 = new Thread(SynchronisedBlockDemo::stageSynchronisedProcess);
        Thread t2 = new Thread(SynchronisedBlockDemo::stageSynchronisedProcess);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stop();
        System.out.println("Time taken: " + timer);
        System.out.printf("List1 size: %d, List2 size: %d.%n%n", list1.size(), list2.size());
        clearList();
    }

    private static void normalMultiThreaded() {
        System.out.println("Starting no synchronised...");
        Stopwatch timer = Stopwatch.createStarted();

//        process();

        Thread t1 = new Thread(SynchronisedBlockDemo::process);
        Thread t2 = new Thread(SynchronisedBlockDemo::process);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stop();
        System.out.println("Time taken: " + timer);
        System.out.printf("List1 size: %d, List2 size: %d.%n%n", list1.size(), list2.size());
        clearList();
    }

    public static void main(String[] args) {
        normalMultiThreaded();
        methodLevelSynchronized();
        blockLevelSynchronized();
    }

}
