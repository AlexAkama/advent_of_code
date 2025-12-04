package advent_of_code.event_2025.day03;

import advent_of_code.util.AdventUtils;

import java.util.List;

public class Lader {

    public static final String DAY = "03";
    private static final String TEST = "src/main/java/advent_of_code/event_2025/day" + DAY + "/data_test.txt";
    private static final String DATA = "src/main/java/advent_of_code/event_2025/day" + DAY + "/data.txt";


    public static void main(String[] args) {

        List<String> strings = AdventUtils.readAllFromFile(DATA);

        long sum = 0;
        for (String string : strings) {
            sum += findMax12(string);
        }

        System.out.println(sum);
    }

    private static int findMax(String s) {
        int max = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 1; j < s.length(); j++) {
                int n1 = s.charAt(i) - '0';
                int n0 = s.charAt(j) - '0';
                int n = 10 * n1 + n0;
                if (n > max) max = n;
            }
        }
        return max;
    }

    private static int findMaxV2(String s) {
        int max = 0;
        Pair max1 = findMax(s, 0, s.length() - 1);
        int n1 = s.charAt(max1.pos) - '0';
        Pair max2 = findMax(s, max1.pos + 1, s.length());
        int n0 = s.charAt(max2.pos) - '0';
        int n = 10 * n1 + n0;
        if (n > max) max = n;
        System.out.println(max);
        return max;
    }


    private static final long P11 = 100_000_000_000L;
    private static final long P10 = 10_000_000_000L;
    private static final long P09 = 1_000_000_000L;
    private static final long P08 = 100_000_000L;
    private static final long P07 = 10_000_000L;
    private static final long P06 = 1_000_000L;
    private static final long P05 = 100_000L;
    private static final long P04 = 10_000L;
    private static final long P03 = 1_000L;
    private static final long P02 = 100L;
    private static final long P01 = 10L;

    private static long findMax12(String s) {
        long max = 0;

        Pair max01 = findMax(s, 0, s.length() - 11);
        Pair max02 = findMax(s, max01.pos + 1, s.length() - 10);
        Pair max03 = findMax(s, max02.pos + 1, s.length() - 9);
        Pair max04 = findMax(s, max03.pos + 1, s.length() - 8);
        Pair max05 = findMax(s, max04.pos + 1, s.length() - 7);
        Pair max06 = findMax(s, max05.pos + 1, s.length() - 6);
        Pair max07 = findMax(s, max06.pos + 1, s.length() - 5);
        Pair max08 = findMax(s, max07.pos + 1, s.length() - 4);
        Pair max09 = findMax(s, max08.pos + 1, s.length() - 3);
        Pair max10 = findMax(s, max09.pos + 1, s.length() - 2);
        Pair max11 = findMax(s, max10.pos + 1, s.length() - 1);
        Pair max12 = findMax(s, max11.pos + 1, s.length());

        int n11 = s.charAt(max01.pos) - '0';
        int n10 = s.charAt(max02.pos) - '0';
        int n09 = s.charAt(max03.pos) - '0';
        int n08 = s.charAt(max04.pos) - '0';
        int n07 = s.charAt(max05.pos) - '0';
        int n06 = s.charAt(max06.pos) - '0';
        int n05 = s.charAt(max07.pos) - '0';
        int n04 = s.charAt(max08.pos) - '0';
        int n03 = s.charAt(max09.pos) - '0';
        int n02 = s.charAt(max10.pos) - '0';
        int n01 = s.charAt(max11.pos) - '0';
        int n00 = s.charAt(max12.pos) - '0';

        long n = P11 * n11
                + P10 * n10
                + P09 * n09
                + P08 * n08
                + P07 * n07
                + P06 * n06
                + P05 * n05
                + P04 * n04
                + P03 * n03
                + P02 * n02
                + P01 * n01
                + n00;

        if (n > max) max = n;

        return max;

    }

    private static Pair findMax(String s, int start, int finish) {
        int pos = -1;
        int max = 0;
        for (int i = start; i < finish; i++) {
            int n0 = s.charAt(i) - '0';
            if (n0 > max) {
                max = n0;
                pos = i;
            }
        }
        return new Pair(pos, max);
    }


    static class Pair {
        int pos;
        int n;

        public Pair(int pos, int n) {
            this.pos = pos;
            this.n = n;
        }
    }

}
