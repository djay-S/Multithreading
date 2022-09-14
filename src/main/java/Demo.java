import com.google.common.base.Stopwatch;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        getMaxCharacterInString();
        System.out.println("\n");

        reverseANumber();
        System.out.println("\n");

        sumOfSquaresOfEvenNumbers();

    }

    private static void sumOfSquaresOfEvenNumbers() {
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Stopwatch stopwatch = Stopwatch.createStarted();
        System.out.println("Using Java Streams MaptoInt: " + usingJavaStreamsMapToInt(nums));
        stopwatch.stop();
        System.out.println("Time taken: " + stopwatch);

        stopwatch = Stopwatch.createStarted();
        System.out.println("Using Java Streams Reduce: " + usingJavaStreamsReduce(nums));
        stopwatch.stop();
        System.out.println("Time taken: " + stopwatch);

        stopwatch = Stopwatch.createStarted();
        System.out.println("Using Java loops: " + usingForLoop(nums));
        stopwatch.stop();
        System.out.println("Time taken: " + stopwatch);
    }

    private static int usingJavaStreamsReduce(List<Integer> nums) {
        return nums.stream().filter(i -> i % 2 == 0).map(i -> i * i).reduce(0, Integer::sum);
    }

    private static int usingForLoop(List<Integer> nums) {
        int sum = 0;

        for (Integer num : nums) {
            if (num % 2 == 0) {
                sum += (num * num);
            }
        }
        return sum;
    }

    private static int usingJavaStreamsMapToInt(List<Integer> nums) {
        return nums.stream().filter(i -> i % 2 == 0).mapToInt(i -> i * i).sum();
    }

    private static void reverseANumber() {
        int num = 1234;
        int n = num;
        int revNum = 0;

        int digit = 0;

        while (num > 0) {
            digit = num % 10;

            revNum = (revNum * 10) + digit;

            num = num / 10;
        }

        System.out.println("Reverse of " + n + " is: " + revNum);
    }

    private static void getMaxCharacterInString() {
        String s = "elkdqwltlgnsqofqosnnabqtcbkmoctaqybuyilboocaabrqhseybukixndwujdhepyjefduxzwpbqimeetabawzagmwdzbntofwrizhiahtircriudsdvgibdctnmadtesossnexqeyjregwqhwnlcxdfcgvwbhftdmdnwquywnhtxwbhsoqjptajkcae";

        System.out.println("Max occurring character in \n" + s + " is: " + maxCharacterUsingMap(s));

    }

    private static char maxCharacterUsingMap(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            int count = 0;
            if (map.containsKey(s.charAt(i))) {
                count = map.get(s.charAt(i));
            }
            map.put(s.charAt(i), ++count);
        }

        System.out.println(map);

        int maxCount = 0;
        char maxChar = s.charAt(maxCount);

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxChar = entry.getKey();
            }
        }
        return maxChar;
    }
}
