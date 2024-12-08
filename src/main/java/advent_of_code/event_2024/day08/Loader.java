package advent_of_code.event_2024.day08;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day08/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day08/test2.txt";

    private static final Map<Character, List<Point>> MAP = new HashMap<>();
    private static char[][] grid;
    private static int[][] nodes;

    public static void main(String[] args) {
        init();
        System.out.println(part2());
    }

    private static int part1() {
        mark();
        return calculate();
    }

    private static int part2() {
        markAllInLine();
        return calculate();
    }

    private static void markAllInLine() {
        for (Map.Entry<Character, List<Point>> entry : MAP.entrySet()) {
            List<Point> list = entry.getValue();
            if (list.size() > 1) {
                markAllInFrequency(list);
            }
        }
    }

    private static void markAllInFrequency(List<Point> list) {
        int n = list.size();
        for (int left = 0; left < n - 1; left++) {
            for (int right = left + 1; right < n; right++) {
                Point p1 = list.get(left);
                Point p2 = list.get(right);
                markAllNodes(p1, p2);
            }
        }
    }

    private static void markAllNodes(Point p1, Point p2) {
        int dy = p2.y() - p1.y();
        int dx = p2.x() - p1.x();
        markLine(p1, dy, dx);
        markLine(p2, -dy, -dx);
    }

    private static void markLine(Point p, int dy, int dx) {
        markNode(p.y(), p.x());
        int nextY = p.y() - dy;
        int nextX = p.x() - dx;
        while (inGrid(nextY, nextX)) {
            markNode(nextY, nextX);
            nextY -= dy;
            nextX -= dx;
        }
    }

    private static void mark() {
        for (Map.Entry<Character, List<Point>> entry : MAP.entrySet()) {
            List<Point> list = entry.getValue();
            if (list.size() > 1) {
                markFrequency(list);
            }
        }
    }

    private static void markFrequency(List<Point> list) {
        int n = list.size();
        for (int left = 0; left < n - 1; left++) {
            for (int right = left + 1; right < n; right++) {
                Point p1 = list.get(left);
                Point p2 = list.get(right);
                markNodes(p1, p2);
            }
        }
    }

    private static void markNodes(Point p1, Point p2) {
        int dy = p2.y() - p1.y();
        int dx = p2.x() - p1.x();
        markNode(p1.y() - dy, p1.x() - dx);
        markNode(p2.y() + dy, p2.x() + dx);
    }

    private static void markNode(int y, int x) {
        if (inGrid(y, x)) {
            nodes[y][x] = 1;
        }
    }

    private static void init() {
        try {
            List<String> strings = Files.readAllLines(Path.of(DATA));
            grid = new char[strings.size()][];
            nodes = new int[strings.size()][strings.get(0).length()];
            for (int y = 0; y < strings.size(); y++) {
                String s = strings.get(y);
                grid[y] = s.toCharArray();
                for (int x = 0; x < grid[y].length; x++) {
                    char c = grid[y][x];
                    if (c != '.') {
                        MAP.computeIfAbsent(c, character -> new ArrayList<>()).add(new Point(y, x));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean inGrid(int y, int x) {
        return AdventUtils.inMatrix(y, x, grid);
    }

    private static int calculate() {
        int count = 0;
        for (int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[0].length; x++) {
                if (nodes[y][x] == 1) count++;
            }
        }
        return count;
    }

}
