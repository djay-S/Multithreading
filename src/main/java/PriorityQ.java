import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQ {
    public static void main(String[] args) {
        PriorityQueue<Integer> p = new PriorityQueue<>(new MyCompaartor());
        p.add(20);
        p.add(3);
        p.add(7);
        p.add(8);
        p.add(10);
        p.add(4);

        System.out.println(p);
        while (!p.isEmpty()) {
            System.out.println(p.poll());
        }
    }
}
class MyCompaartor implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}