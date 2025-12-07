package advent_of_code.event_2025.day07;

import advent_of_code.util.AdventUtils;

import java.util.List;

class LoaderTest {
    public static void main(String[] args) {
        Loader.main("07", "data_test.txt", "TEST");
    }
}

class LoaderData {
    public static void main(String[] args) {
        Loader.main("07", "data.txt", "DATA");
    }
}

public class Loader {

    private static final char EMPTY = '.';
    private static final char START = 'S';
    private static final char BEAM = '|';
    private static final char SPLITTER = '^';

    private static char[][] matrix;
    private static int n = 0;
    private static int start = 0;
    private static long count = 0;
    private static long[][] path;

    public static void main(String... args) {

        List<String> strings = AdventUtils.readAllFromFile(args);
        init(strings);

        part1();
        System.out.println(count);
        //1553

        part2();
        System.out.println(count);
        //15811946526915

    }

    private static void part2() {
        path = new long[matrix.length][matrix[0].length];
        int y = 1;
        int x = start;
        count = foo(y, x);
    }

    private static long foo(int y, int x) {
        //System.out.println(y + ":" + x);
        if (path[y][x] > 0) return path[y][x];
        if (y == n) {
            path[y][x] = 1;
            return 1;
        }
        if (matrix[y][x] == SPLITTER) {
            long res = foo(y + 1, x - 1) + foo(y + 1, x + 1);
            path[y][x] = res;
            return res;
        }
        long res = foo(y + 1, x);
        path[y][x] = res;
        return res;
    }

    private static void part1() {
        for (int y = 1; y < matrix.length; y++) {
            beamSplitProcess(matrix[y], matrix[y - 1]);
        }
    }

    private static void beamSplitProcess(char[] curs, char[] prevs) {
        for (int x = 0; x < curs.length; x++) {
            char prev = prevs[x];
            if (prev == BEAM) {
                char cur = curs[x];
                if (cur == EMPTY) curs[x] = BEAM;
                else if (cur == SPLITTER) {
                    count++;
                    curs[x - 1] = BEAM;
                    curs[x + 1] = BEAM;
                }
            }
        }
    }

    private static void init(List<String> strings) {
        matrix = new char[strings.size()][];
        for (int i = 0; i < strings.size(); i++) {
            matrix[i] = strings.get(i).toCharArray();
        }
        start = strings.get(0).indexOf(START);
        matrix[0][start] = BEAM;
        n = matrix.length - 1;
    }

}
