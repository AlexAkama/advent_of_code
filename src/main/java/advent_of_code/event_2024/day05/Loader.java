package advent_of_code.event_2024.day05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Loader {

    private static final String TEST = "src/main/java/advent_of_code/event_2024/day05/test.txt";
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day05/data.txt";

    private static final Map<Integer, Set<Integer>> PRIORITY_MAP = new HashMap<>();
    private static final List<int[]> RAW_DATA = new ArrayList<>();

    public static void main(String[] args) {
        init();
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() {
        int result = 0;
        for (int[] ints : RAW_DATA) {
            result += checkCorrectAndGetMiddle(ints);
        }
        return result;
    }

    private static int part2() {
        int result = 0;
        for (int[] ints : RAW_DATA) {
            result += checkNotCorrectSortAndGetMiddle(ints);
        }
        return result;
    }

    private static int checkCorrectAndGetMiddle(int[] ints) {
        return isCorrect(ints) ? getMiddle(ints) : 0;
    }

    private static int checkNotCorrectSortAndGetMiddle(int[] ints) {
        return !isCorrect(ints) ? sortAndGetMiddle(ints) : 0;
    }

    private static int sortAndGetMiddle(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            int badPosition = getBadPosition(i, ints);
            if (badPosition > -1) {
                int temp = ints[badPosition];
                ints[badPosition] = ints[i];
                ints[i] = temp;
                i = -1;
            }
        }
        return getMiddle(ints);
    }

    private static boolean isCorrect(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            if (isBadPosition(i, ints)) return false;
        }
        return true;
    }

    private static boolean isBadPosition(int pos, int[] ints) {
        for (int i = pos - 1; i > -1; i--) {
            Set<Integer> set = PRIORITY_MAP.get(ints[pos]);
            if (set == null) continue;
            if (set.contains(ints[i])) return true;
        }
        return false;
    }

    private static int getBadPosition(int pos, int[] ints) {
        for (int i = pos - 1; i > -1; i--) {
            Set<Integer> set = PRIORITY_MAP.get(ints[pos]);
            if (set == null) continue;
            if (set.contains(ints[i])) return i;
        }
        return -1;
    }

    private static int getMiddle(int[] ints) {
        return ints[ints.length / 2];
    }

    private static void init() {
        File file = new File(DATA);
        try {
            FileReader fileReader = new FileReader(file);
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                String line = reader.readLine();
                while (!line.isEmpty()) {
                    String[] split = line.split("\\|");
                    int d1 = Integer.parseInt(split[0]);
                    int d2 = Integer.parseInt(split[1]);
                    PRIORITY_MAP.computeIfAbsent(d1, k -> new HashSet<>()).add(d2);
                    line = reader.readLine();
                }
                line = reader.readLine();
                while (line != null) {
                    String[] split = line.split(",");
                    int[] res = new int[split.length];
                    for (int i = 0; i < split.length; i++) {
                        res[i] = Integer.parseInt(split[i]);
                    }
                    RAW_DATA.add(res);
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
