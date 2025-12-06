package advent_of_code.event_2025.day01;

import java.util.List;

import static advent_of_code.util.AdventUtils.isTest;
import static advent_of_code.util.AdventUtils.readAllFromFile;

class LoaderTest {
    public static void main(String[] args) {
        Loader.main("01", "data_test.txt", "TEST");
    }
}

class LoaderData {
    public static void main(String[] args) {
        Loader.main("01", "data.txt", "DATA");
    }
}

public class Loader {

    private static boolean isTest = false;

    private static int zeroCount = 0;
    private static int zeroTransitionCount = 0;


    public static void main(String... args) {

        List<String> strings = readAllFromFile(args);
        isTest = isTest(args);

        part1(strings);
        //1100
        part2(strings);
        //6358

    }


    private static void part2(List<String> strings) {
        int n = 50;

        for (String string : strings) {
            char c = string.charAt(0);
            int i = Integer.parseInt(string.substring(1));
            int prev = n;
            int zeroTransition = i / 100;
            i %= 100;
            if ('R' == c) {
                n += i;
                zeroTransition += n / 100;
                n %= 100;
            } else {
                if (i < n) n -= i;
                else if (i == n) {
                    zeroTransition++;
                    n = 0;
                } else {
                    if (n != 0) zeroTransition++;
                    n = 100 - (i - n);
                }
            }

            if (isTest)
                System.out.printf("%-4s %5d  => %4d  :%d\n", string, prev, n, zeroTransition);

            zeroTransitionCount += zeroTransition;
        }

        System.out.println(zeroTransitionCount);

    }

    private static void part1(List<String> strings) {
        int n = 50;

        for (String string : strings) {
            char c = string.charAt(0);
            int i = Integer.parseInt(string.substring(1));

            int prev = n;
            if (c == 'L') {
                n -= i;
                if (n < 0) {
                    n = 100 + n % 100;
                }
            } else {
                n += i;
            }

            n %= 100;

            if (n == 0) {
                zeroCount++;
            }


            if (isTest)
                System.out.printf("%-4s %5d  => %4d\n", string, prev, n);

        }

        System.out.println(zeroCount);

    }

}