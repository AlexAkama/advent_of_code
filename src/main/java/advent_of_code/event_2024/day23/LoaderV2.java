package advent_of_code.event_2024.day23;

import advent_of_code.util.CyclicIterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LoaderV2 {

    private static final int DAY = 23;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test2.txt";

    private static final CyclicIterator<Character> CHCI = new CyclicIterator<>(List.of('\\', '|', '/', '-'));

    private static final Map<String, Set<String>> GRAPH = new HashMap<>();

    private static final Set<String> visited = new HashSet<>();
    private static final List<String> currentPath = new ArrayList<>();
    private static List<String> longestCycle;
    private static int count = 0;

    public static void main(String[] args) {
        init(1);
        System.out.println(part2());
    }

    private static String part2() {
        longestCycle = new ArrayList<>();

        for (String v : GRAPH.keySet()) {
            findCircleByDfs(v, v);
            visited.add(v);
        }

        return buildResponse();
    }

    private static void findCircleByDfs(String start, String v) {
        currentPath.add(v);
        visited.add(v);

        for (String next : GRAPH.get(v)) {
            if (!visited.contains(next)) {
                findCircleByDfs(start, next);
            } else if (next.equals(start) && currentPath.size() > longestCycle.size()) {
                longestCycle = new ArrayList<>(currentPath);
                System.out.println("\b" + currentPath.size() + ": " + Arrays.toString(longestCycle.toArray()));
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(v);
        if (count++ == 1000) {
            System.out.print("\b" + CHCI.next());
            count = 0;
        }
    }

    private static String buildResponse() {
        return longestCycle.stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            for (String s : strings) {
                String[] split = s.split("-");
                GRAPH.computeIfAbsent(split[0], k -> new HashSet<>()).add(split[1]);
                GRAPH.computeIfAbsent(split[1], k -> new HashSet<>()).add(split[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
