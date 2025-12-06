package advent_of_code.event_2025.day06;

import advent_of_code.util.AdventUtils;

import java.util.ArrayList;
import java.util.List;

class LoaderTest {
    public static void main(String[] args) {
        Loader.main("06", "data_test.txt", "TEST");
    }
}

class LoaderData {
    public static void main(String[] args) {
        Loader.main("06", "data.txt", "DATA");
    }
}

public class Loader {

    public static void main(String... args) {

        List<String> strings = AdventUtils.readAllFromFile(args);

        part1(strings);
        //4805473544166
        part2(strings);
        //8907730960817

    }

    private static void part2(List<String> strings) {
        long total = 0;
        int length = strings.get(0).length();
        int n = strings.size() - 1;
        String operations = strings.get(n);
        List<Long> buffer = new ArrayList<>();
        for (int x = length - 1; x > -1; x--) {
            int i = extractInt(strings, x);
            if (i == 0) continue;
            buffer.add((long) i);
            char operation = operations.charAt(x);
            if (operation != ' ') {
                long count = operation == '+' ?
                        buffer.stream().mapToLong(Long::valueOf).sum() :
                        buffer.stream().reduce(1L, (n1, n2) -> n1 * n2);
                total += count;
                buffer.clear();
            }
        }

        System.out.println(total);
    }

    private static int extractInt(List<String> strings, int x) {
        int n = strings.size() - 1;
        StringBuilder sb = new StringBuilder(n);
        for (int y = 0; y < n; y++) {
            sb.append(strings.get(y).charAt(x));
        }
        String trimmed = sb.toString().trim();
        return trimmed.isBlank() ? 0 : Integer.parseInt(trimmed);
    }

    private static void part1(List<String> strings) {
        int n = strings.size() - 1;

        int[][] matrix = new int[n][];
        for (int y = 0; y < n; y++) {
            String[] split = strings.get(y).trim().split("\\s+");
            int[] ints = new int[split.length];
            for (int x = 0; x < split.length; x++) {
                ints[x] = Integer.parseInt(split[x]);
            }
            matrix[y] = ints;
        }
        String[] operations = strings.get(n).split("\\s+");

        long total = 0;
        for (int x = 0; x < matrix[0].length; x++) {
            long count = matrix[0][x];
            char operation = operations[x].charAt(0);
            for (int y = 1; y < n; y++) {
                if (operation == '+') count += matrix[y][x];
                if (operation == '*') {
                    count = count * matrix[y][x];
                }
            }
            total += count;
        }

        System.out.println(total);
    }


}
