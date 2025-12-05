package advent_of_code.event_2025.day02;

import advent_of_code.util.AdventUtils;

import java.util.List;

public class Loader {

    public static final String DAY = "02";
    private static final String TEST = "src/main/java/advent_of_code/event_2025/day" + DAY + "/data_test.txt";
    private static final String DATA = "src/main/java/advent_of_code/event_2025/day" + DAY + "/data.txt";


    public static void main(String[] args) {

        List<String> strings = AdventUtils.readAllFromFile(DATA);
        String[] split = strings.get(0).split(",");

        long sum = 0;
        for (String s : split) {
            sum += twinSum(s);
        }

        System.out.println(sum);
        //29818212493
        //37432260594
    }

    private static long twinSum(String data) {
        String[] split = data.split("-");
        long left = Long.parseLong(split[0]);
        long right = Long.parseLong(split[1]);

        long summ = 0;

        for (long i = left; i <= right; i++) {
            if (isSequence(i)) summ += i;
        }

        return summ;
    }

    private static boolean isTwin(long num) {
        String s = String.valueOf(num);
        int length = s.length();
        if (length % 2 != 0) return false;
        String leftHalf = s.substring(0, length / 2);
        String rightHalf = s.substring(length / 2);
        return leftHalf.equals(rightHalf);
    }

    private static boolean isSequence(long num) {
        String s = String.valueOf(num);
        int length = s.length();
        for (int n = 2; n < length + 1; n++) {
            if (length % n != 0) continue;
            int partSize = length / n;
            String first = s.substring(0, partSize);
            int equalCount = 1;
            for (int i = 1; i < n; i++) {
                String next = s.substring(partSize * i, partSize * (i + 1));
                if (first.equals(next)) equalCount++;
            }
            if (n == equalCount) return true;
        }
        return false;
    }

}
