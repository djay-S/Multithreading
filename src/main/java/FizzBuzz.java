public class FizzBuzz {
    public static void main(String[] args) {
        fizzBuzz(100);
    }

    private static void fizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            String output = "";

            if (i % 3 == 0) {
                output += "Fizz";
            }

            if (i % 5 == 0) {
                output += "Buzz";
            }

            if (output.isBlank()){
                output += i;
            }
            System.out.println(output);
        }
    }
}
