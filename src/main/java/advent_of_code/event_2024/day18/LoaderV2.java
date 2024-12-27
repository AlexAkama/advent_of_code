package advent_of_code.event_2024.day18;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static advent_of_code.util.AdventUtils.inMatrix;

public class LoaderV2 {

    private static final int DAY = 18;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test%s.txt";

    private static final char STONE = '#';
    private static final char AIR = '.';

    private static int n;
    private static int limit;
    private static List<String> strings;
    private static char[][] grid;
    private static int[][] cost;

    public static void main(String[] args) {
        init();
        System.out.println(part2());
    }

    private static String part2() {
        for (int i = limit; i < strings.size(); i++) {
            String[] split = strings.get(i).split(",");
            grid[Integer.parseInt(split[1])][Integer.parseInt(split[0])] = STONE;
            cost = new int[n][n];
            int res = part1();
            if (res == -1) return strings.get(i);
        }
        return "дрявая жопа";
    }

    private static int part1() {
        Deque<Point> q = new ArrayDeque<>();
        Point start = new Point(0, 0);
        q.addLast(start);
        start.setCost(1);
        while (!q.isEmpty()) {
            Point p = q.removeFirst();
            for (Direction d : Direction.values()) {
                Point next = new Point(p.y() + d.dy(), p.x() + d.dx());
                if (next.inGrid() && next.canMove()
                        && (next.cost() == 0 || next.cost() > p.cost() + 1)) {
                    q.add(next);
                    next.setCost(p.cost() + 1);
                }
            }
        }
        return cost[n - 1][n - 1] - 1;
    }

    private static void init(int... test) {
        try {
            String filename = test.length == 0 ? DATA : String.format(TEST, test[0] == 0 ? "" : test[0]);
            n = test.length == 0 ? 71 : 7;
            limit = test.length == 0 ? 1024 : 12;
            grid = new char[n][n];
            cost = new int[n][n];
            strings = Files.readAllLines(Path.of(filename));
            for (int i = 0; i < limit; i++) {
                String[] split = strings.get(i).split(",");
                grid[Integer.parseInt(split[1])][Integer.parseInt(split[0])] = STONE;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Point extends AdventUtils.Point {

        public Point(int y, int x) {
            super(y, x);
        }

        public boolean inGrid() {
            return inMatrix(this.y(), this.x(), grid);
        }

        public boolean canMove() {
            return grid[this.y()][this.x()] != STONE;
        }

        private int cost() {
            return cost[y()][x()];
        }

        private void setCost(int i) {
            cost[y()][x()] = i;
        }

    }

}
