public class MyLinkedList {

    static Node head;

    static class Node {
        int data;
        Node next;

        Node (int data) {
            this.data = data;
            next = null;
        }

        void add(int newData) {
            Node newNode = new Node(newData);
            Node start = this;
            while (start.next != null) {
                start = start.next;
            }
            start.next = newNode;
        }

        void add(int newDate, int index) {
            Node start = this;
            Node prev = null;
            int currIdx = 1;

            while (start != null) {
                prev = start;
                start = start.next;
                if (currIdx == index) {
                    Node newNode = new Node(newDate);
                    prev.next = newNode;
                    newNode.next = start;
                }
                currIdx++;
            }
        }

        void remove(int index) {
            Node start = this;
            Node prev = null;
            int currIdx = 1;

            while (start != null) {
                prev = start;
                start = start.next;
                if (currIdx == index) {
                    prev.next = start.next;
                }
                currIdx++;
            }
        }

        public String toString() {
            String str = "";
            Node start = this;

            while (start != null) {
                str += start.data + ", ";
                start = start.next;
            }
            str = str.substring(0, str.length() - 2);
            return str;
        }

        public int size() {
            int size = 0;
            Node start = this;

            while (start != null) {
             size++;
             start = start.next;
            }

            return size;
        }
    }

    public static void main(String[] args) {

        Node node = new Node(1);
        node.add(2);
        node.add(3);
        node.add(4);
        node.add(5);
        node.add(6);
        node.add(7);
        node.add(9);
        node.add(8);

        System.out.println(node);
        System.out.println(node.size());

        node.remove(3);
        System.out.println(node);
        System.out.println(node.size());

        node.add(-1, 4);
        System.out.println(node);
        System.out.println(node.size());
    }
}
