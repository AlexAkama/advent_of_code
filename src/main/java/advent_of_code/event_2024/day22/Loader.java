package advent_of_code.event_2024.day22;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loader {

    private static final int DAY = 22;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test.txt";

    private static final Map<Long, Long> CACHE = new HashMap<>();
    private static final int MODULE = 16777216;

    private static List<Integer> data;

    public static void main(String[] args) {
        init(1);
        System.out.println(part1());
    }

    private static long part1() {
        long sum = 0;
        for (Integer datum : data) {
            sum += getNextSecret(datum, 2000);
        }
        return sum;
    }

    private static long getNextSecret(long n, int deep) {
        long res = n;
        for (int i = 0; i < deep; i++) {
            res = getNextSecret(res);
        }
        return res;
    }

    private static long getNextSecret(long n) {
        if (CACHE.containsKey(n)) return CACHE.get(n);
        long secret = 0;
        long n1 = ((n * 64) ^ n) % MODULE;
        long n2 = ((n1 / 32) ^ n1) % MODULE;
        secret = ((n2 * 2048) ^ n2) % MODULE;
        CACHE.put(n, secret);
        return secret;
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            data = strings.stream()
                    .map(Integer::parseInt)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
