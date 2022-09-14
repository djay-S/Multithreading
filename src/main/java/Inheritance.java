public class Inheritance {
    static class Dad {
        public Dad() {
            System.out.println("This is dad constructor.");
            System.out.println("Object hashcode is: " + this.hashCode());
        }

        public void speak() {
            System.out.println("Dad speaks.");
        }
    }

    static class Kid extends Dad {
        public Kid() {
            System.out.println("This is kid constructor.");
            System.out.println("Object hashcode is: " + this.hashCode());
        }

        public void speak() {
            super.speak();
            System.out.println("Kid speaks.");
        }
    }

    public static void main(String[] args) {
        System.out.println("---------------------------------");
        Kid kid = new Kid();
        kid.speak();

        System.out.println("---------------------------------");
        Dad dad = new Dad();
        dad.speak();

        System.out.println("---------------------------------");
        Dad q = new Kid();
        q.speak();

        System.out.println("---------------------------------");
    }
}
