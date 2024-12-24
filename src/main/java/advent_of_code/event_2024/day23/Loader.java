package advent_of_code.event_2024.day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Loader {

    private static final int DAY = 23;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test.txt";

    private static final Map<String, Set<String>> GRAPH = new HashMap<>();

    public static void main(String[] args) {
        init(1);
        System.out.println(part1());
    }

    private static int part1() {
        Set<Set<String>> triangles = new HashSet<>();
        for (String v1 : GRAPH.keySet()) {
            for (String v2 : GRAPH.get(v1)) {
                for (String v3 : GRAPH.get(v2)) {
                    if (GRAPH.get(v1).contains(v3)) {
                        Set<String> triangle = new HashSet<>();
                        triangle.add(v1);
                        triangle.add(v2);
                        triangle.add(v3);
                        if (startWithT(v1) || startWithT(v2) || startWithT(v3)) {
                            triangles.add(triangle);
                        }
                    }
                }
            }
        }
        return triangles.size();
    }

    private static boolean startWithT(String s) {
        return s.charAt(0) == 't';
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
