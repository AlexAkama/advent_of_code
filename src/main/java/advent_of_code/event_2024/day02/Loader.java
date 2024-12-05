package advent_of_code.event_2024.day02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day02/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day02/test.txt";

    private static String filename;

    private static List<int[]> data = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        filename = TEST;
        filename = DATA;
        System.out.println(part2v2());
    }

    private static int part1() throws IOException {
        init();
        int count = 0;
        for (int[] datum : data) {
            int index = datum[0] < datum[1] ? isIncreasingSequence(datum, 3) : isDecreasingSequence(datum, 3);
            System.out.println(Arrays.toString(datum) + " " + index);
            if (index == -1) count++;
        }
        return count;
    }

    private static int part2() throws IOException {
        init();
        int count = 0;
        for (int[] datum : data) {
            int index = datum[0] < datum[1]
                    ? isIncreasingSequence(datum, 3)
                    : isDecreasingSequence(datum, 3);
            if (index != -1) {
                if (datum[index] == datum[index + 1]) {
                    continue;
                }
                int[] alternative = removeElementByIndex(datum, index);
                System.out.println("B: " + Arrays.toString(datum) + " " + index);
                int alternativeIndex = alternative[0] < alternative[1]
                        ? isIncreasingSequence(alternative, 3)
                        : isDecreasingSequence(alternative, 3);
                System.out.println("L: " + Arrays.toString(alternative) + " " + alternativeIndex);
                if (alternativeIndex != -1 && index < datum.length - 1) {
                    alternative = removeElementByIndex(datum, index + 1);
                    alternativeIndex = alternative[0] < alternative[1]
                            ? isIncreasingSequence(alternative, 3)
                            : isDecreasingSequence(alternative, 3);
                    System.out.println("R: " + Arrays.toString(alternative) + " " + alternativeIndex);
                }
                index = alternativeIndex;
                System.out.println("E: " + count);
                System.out.println();
            }
            if (index == -1) count++;
        }
        return count;
    }

    private static int part2v2() throws IOException {
        init();
        int count = 0;
        for (int[] datum : data) {
            int index = datum[0] < datum[1] ? isIncreasingSequence(datum, 3) : isDecreasingSequence(datum, 3);
            if (index != -1) {
                for (int i = 0; i < datum.length; i++) {
                    int[] temp = removeElementByIndex(datum, i);
                    int tempIndex = temp[0] < temp[1] ? isIncreasingSequence(temp, 3) : isDecreasingSequence(temp, 3);
                    if (tempIndex == -1) {
                        index = tempIndex;
                        break;
                    }
                }
            }
            if (index == -1) count++;
        }
        return count;
    }

    public static int isIncreasingSequence(int[] sequence, int k) {
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] > sequence[i + 1] || (sequence[i + 1] - sequence[i]) > k || sequence[i] == sequence[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    public static int isDecreasingSequence(int[] sequence, int k) {
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] < sequence[i + 1] || (sequence[i] - sequence[i + 1]) > k || sequence[i] == sequence[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    private static void init() throws IOException {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {
                data.add(parseStringToInts(line));
                line = reader.readLine();
            }
        }
    }

    private static int[] parseStringToInts(String s) {
        String[] split = s.split("\\s+");
        int n = split.length;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        return nums;
    }

    public static int[] removeElementByIndex(int[] ints, int indexToRemove) {
        int[] res = new int[ints.length - 1];
        int p = 0;
        for (int i = 0; i < ints.length; i++) {
            if (i != indexToRemove) res[p++] = ints[i];
        }
        return res;
    }

}
