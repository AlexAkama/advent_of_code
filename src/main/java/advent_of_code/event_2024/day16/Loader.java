package advent_of_code.event_2024.day16;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static advent_of_code.util.AdventUtils.Direction.RIGHT;
import static advent_of_code.util.AdventUtils.Direction.UP;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day16/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day16/test2.txt";

    private static Point start;
    private static Point end;

    private static int n;
    private static int m;

    private static char[][] grid;
    private static int[][] visit;

    public static void main(String[] args) {
        init(1);
        System.out.println(part1());
    }
    //85432

    private static int part1() {
        Deque<Point> q = new ArrayDeque<>();
        q.addLast(start);
        visit[start.y()][start.x()] = 1;
        while (!q.isEmpty()) {
            Point p = q.removeFirst();
            for (Direction d : Direction.values()) {
                if (canMove(p, d)) {
                    Point next = p.next(d);
                    next.markVisited();
                    q.addLast(next);
                }
            }
        }
        AdventUtils.saveMatrix(visit);
        AdventUtils.saveMatrix(grid);
        return visit[end.y()][end.x()] - 1;
    }

    private static boolean canMove(Point p, Direction d) {
        boolean isBarrier = visit[p.y() + d.dy()][p.x() + d.dx()] == -1;
        boolean isFree = visit[p.y() + d.dy()][p.x() + d.dx()] == 0;
        boolean isGreat = visit[p.y() + d.dy()][p.x() + d.dx()] > visit[p.y()][p.x()];
        return !isBarrier && isFree || isGreat;
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            grid = new char[strings.size()][];
            for (int i = 0; i < strings.size(); i++) {
                String s = strings.get(i).replace('.', '_');
                grid[i] = s.toCharArray();
                int si = s.indexOf('S');
                int ei = s.indexOf('E');
                if (si > -1) start = new Point(i, si);
                if (ei > -1) end = new Point(i, ei);
            }
            n = grid.length;
            m = grid[0].length;
            visit = new int[n][m];
            start.count = 1;
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < m; x++) {
                    if (grid[y][x] == '#') visit[y][x] = -1;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Point extends AdventUtils.Point {

        int count;
        Direction d;

        public Point(int y, int x) {
            super(y, x);
            this.count = 0;
        }

        public Point(int y, int x, int count, Direction d) {
            super(y, x);
            this.count = count;
            this.d = d;
        }

        public void markVisited() {
            visit[y()][x()] = count;
            grid[y()][x()] = d.charArrow();
        }

        public Point next(Direction d) {
            int k = this.d == d ? 0 : 1000;
            return new Point(y() + d.dy(), x() + d.dx(), count + 1 + k, d);
        }

    }

}
