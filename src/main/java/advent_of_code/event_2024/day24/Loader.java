package advent_of_code.event_2024.day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Loader {

    private static final int DAY = 24;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test.txt";

    private static final Map<String, Integer> MAP = new HashMap<>();

    private static final Deque<String[]> q = new ArrayDeque<>();

    public static void main(String[] args) {
        init(1);
        System.out.println(part1());
    }

    private static long part1() {
        while (!q.isEmpty()) {
            String[] strings = q.removeFirst();
            try {
                doOperation(strings);
            } catch (RuntimeException e) {
                q.addLast(strings);
            }
        }
        return calculate();
    }

    private static long calculate() {
        AtomicInteger power = new AtomicInteger();
        return MAP.keySet().stream()
                .filter(s -> s.charAt(0) == 'z')
                .sorted()
                .map(MAP::get)
                .map(Long::valueOf)
                .reduce(0L, (acum, bit) -> acum + (bit << power.getAndIncrement()));
    }

    private static int doOperation(String[] strings) {
        if (!MAP.containsKey(strings[0]) || !MAP.containsKey(strings[2])) {
            throw new IllegalArgumentException("Нет значения " + strings[0] + " или " + strings[2]);
        }
        int a = MAP.get(strings[0]);
        int b = MAP.get(strings[2]);
        String operation = strings[1];
        int res = switch (operation) {
            case "XOR" -> a ^ b;
            case "OR" -> a | b;
            case "AND" -> a & b;
            default -> throw new IllegalArgumentException();
        };
        MAP.put(strings[4], res);
        return res;
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            int p = 0;
            while (!strings.get(p).isEmpty()) {
                String[] split = strings.get(p++).split(":\\s+");
                MAP.put(split[0], Integer.parseInt(split[1]));
            }
            p++;
            while (p < strings.size()) {
                String[] split = strings.get(p++).split("\\s+");
                if (MAP.containsKey(split[0]) && MAP.containsKey(split[2])) {
                    MAP.put(split[4], doOperation(split));
                } else {
                    q.addLast(split);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
