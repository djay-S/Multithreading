public class MyQueue {

    //[] -> [1] -> [1, 2] -> [1, 2, 3]
    static Node head, tail;
    static class Node {
        int data;
        int length;
        Node next;

        Node(){}

        Node(int data) {
            this.data = data;
        }

        void enqueue(int data){
            Node newNode = new Node(data);
            if (head == null) {
                tail = newNode;
                head = tail;
            }
            else {
                tail.next = newNode;
                tail = tail.next;
            }
                length++;
        }

        int dequeue(){
            if (head == null) {
                throw new IllegalStateException("Queue is empty");
            }
            int headData = head.data;
            head = head.next;
            length--;
            return headData;
        }

        int getLength() {
            return length;
        }

        public String toString() {
            if (head == null) {
                return "[]";
            }
            String str = "";
            Node start = head;

            while (start != null) {
                str += start.data + ", ";
                start = start.next;
            }
            str = str.substring(0, str.length() - 2);
            return "[" + str + "]";
        }
    }

    public static void main(String[] args) {
        Node queue = new Node();
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println(queue);
        System.out.println("Queue length: " + queue.getLength());
        System.out.println("Queue dequeued: " + queue.dequeue());
        queue.enqueue(10);
        System.out.println(queue);
        System.out.println("Queue length: " + queue.getLength());
        System.out.println("Queue dequeued: " + queue.dequeue());
        System.out.println(queue);
        System.out.println("Queue length: " + queue.getLength());
        System.out.println("Queue dequeued: " + queue.dequeue());
        System.out.println("Queue dequeued: " + queue.dequeue());

    }
}
