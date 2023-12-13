package advent_of_code.event_2023.day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Loader {

    private static final Character GROUND = '.';
    private static final Character DAMAGED = '#';
    private static final Character UNKNOWN = '?';

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day12/test.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            int sum = 0;
            while (line != null) {
                String[] split = line.split("\\s+");
                String extend1 = extendString(split[1], ',', 5);
                String[] intsSplit = extend1.split(",");
                int[] ints = Arrays.stream(intsSplit)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                String split0 = extendString(split[0], '?', 5);
                SpringRecord springRecord = new SpringRecord(split0.toCharArray(), ints);
                sum += calculate(springRecord);
                line = reader.readLine();
            }
            System.out.println(sum);
        }
    }

    private static String extendString(String base, Character delimiter, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(base).append(delimiter);
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static void testCalculate() {
        String s = "?###????????";
        SpringRecord springRecord = new SpringRecord(s.toCharArray(), new int[]{3, 2, 1});
        int calculate = calculate(springRecord);
        System.out.println(calculate);
    }

    private static int calculate(SpringRecord sr) {
        int first = findFirs(sr.chars, UNKNOWN);
        if (first == -1) {
            int[] mask = getMask(sr.chars);
            if (compareArrays(sr.sizes, mask)) return 1;
            return 0;
        }
        int sum = 0;
        sr.chars[first] = DAMAGED;
        sum += calculate(sr);
        sr.chars[first] = GROUND;
        sum += calculate(sr);
        sr.chars[first] = UNKNOWN;
        return sum;
    }

    private static void testGetMask() {
        String s = "##.###.##";
        int[] mask = getMask(s.toCharArray());
        System.out.println(Arrays.toString(mask));
    }

    private static int[] getMask(char[] chars) {
        int maskSize = chars.length / 2 + 1;
        int[] res = new int[maskSize];
        int block = 0;
        int pos = 0;
        char prev = GROUND;
        while (block < maskSize && pos < chars.length) {
            char c = chars[pos++];
            if (c == DAMAGED) res[block]++;
            if (c == GROUND && prev == DAMAGED) block++;
            prev = c;
        }
        if (chars[chars.length - 1] == DAMAGED) block++;
        return Arrays.copyOf(res, block);
    }

    private static int findFirs(char[] chars, Character needle) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == needle) return i;
        }
        return -1;
    }

    public static boolean compareArrays(int[] array1, int[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    private static class SpringRecord {

        char[] chars;
        int[] sizes;

        public SpringRecord(char[] chars, int[] sizes) {
            this.chars = chars;
            this.sizes = sizes;
        }

    }


}
