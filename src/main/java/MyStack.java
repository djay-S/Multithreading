public class MyStack {

    private static Node base, head;

    static class Node {
        int data;
        int size = 0;
        Node next;

        Node(){}

        Node(int data) {
            this.data = data;
        }

        void push(int newData) {
            base = head;
            head = new Node(newData);
            head.next = base;
            size++;
        }

        int pop() {
            if (head == null) {
                throw new IllegalStateException("Stack is empty");
            }
            int poppedData = head.data;
            head = head.next;
            size--;
            return poppedData;
        }

        int peek() {
            if (head == null) {
                throw new IllegalStateException("Stack is empty");
            }
            return head.data;
        }

        int size() {
            return head != null ? head.size : 0;
        }


        boolean isEmpty() {
            return size == 0;
        }

        public String toString() {
            String str = "";
            if (isEmpty()) {
                return "[]";
            }

            Node start = head;
            int stackSize = size;
            while (stackSize > 0) {
                str += start.data + ", ";
                start = start.next;
                stackSize--;
            }
            str = str.substring(0, Math.max(str.length() - 2, 0));
            return "[" + str + "]";
        }
    }

    public static void main(String[] args) {
        Node stack = new Node();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println("Stack size: " + stack.size);

        System.out.println("Peek: " + stack.peek());

        stack.pop();
        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
        System.out.println("Stack size: " + stack.size);
    }
}
