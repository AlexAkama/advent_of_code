package advent_of_code.event_2024.day25;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private static final int DAY = 25;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test%s.txt";

    private static final List<int[]> KEYS = new ArrayList<>();
    private static final List<int[]> LOCKS = new ArrayList<>();

    public static void main(String[] args) {
        init();
        System.out.println(part1());
    }

    private static int part1() {
        int count = 0;
        for (int[] lock : LOCKS) {
            for (int[] key : KEYS) {
                if (compare(lock, key)) count++;
            }
        }
        return count;
    }

    private static boolean compare(int[] lock, int[] key) {
        for (int i = 0; i < 5; i++) {
            if (lock[i] + key[i] > 5) return false;
        }
        return true;
    }

    private static void init(int... test) {
        try {
            String filename = test.length == 0 ? DATA : String.format(TEST, test[0] == 0 ? "" : test[0]);
            List<String> strings = Files.readAllLines(Path.of(filename));
            int p = 0;
            while (p < strings.size()) {
                String first = strings.get(p++);
                int[] device = new int[5];
                int r = 0;
                while (r < 5) {
                    String s = strings.get(p++);
                    char[] chars = s.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == '#') device[i]++;
                    }
                    r++;
                }
                p++;
                if (first.equals("#####")) {
                    LOCKS.add(device);
                } else {
                    KEYS.add(device);
                }
                p++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
