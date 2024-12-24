package advent_of_code.event_2024.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoaderV2 {

    private static final int DAY = 22;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test2.txt";

    private static final Map<Long, Long> CACHE = new HashMap<>();
    private static final int MODULE = 16777216;

    private static List<Integer> data;
    private static List<Long> profit;
    private static List<Long> price;

    public static void main(String[] args) {
        init();
        System.out.println(part2());
    }

    private static long part2() {
        for (Integer datum : data) {
            profit = new ArrayList<>();
            price = new ArrayList<>();
            getNextSecret(datum, 2000);
            System.out.println(datum);
            System.out.println(toString2(price));
            System.out.println(toString2(profit));
        }
        return -1;
    }

    private static long getNextSecret(long n, int deep) {
        long res = n;
        long curPrice = n % 10;
        for (int i = 0; i < deep; i++) {
            res = getNextSecret(res);
            long nextPrice = res % 10;
            profit.add(nextPrice - curPrice);
            price.add(curPrice);
            curPrice = nextPrice;
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

    private static String toString2(List<Long> list) {
        return list.stream()
                .map(aLong -> String.format("%2d", aLong))
                .collect(Collectors.joining(", "));
    }

}
