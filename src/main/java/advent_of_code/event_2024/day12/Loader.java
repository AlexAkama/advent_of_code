package advent_of_code.event_2024.day12;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

import static advent_of_code.util.AdventUtils.inMatrix;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day12/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day12/test.txt";

    private static final List<Area> AREAS = new ArrayList<>();

    private static char[][] grid;
    private static boolean[][] visited;

    public static void main(String[] args) {
        init();
        part1();
        int res = calculate();
        System.out.println(res);
    }

    private static int calculate() {
        int sum = 0;
        for (Area area : AREAS) {
            sum += area.calculate();
        }
        return sum;
    }

    private static void part1() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (visited[y][x]) continue;
                buildArea(y, x);
            }
        }
    }

    private static void buildArea(int y, int x) {
        Point p = new Point(y, x);
        char c = grid[y][x];
        Area area = new Area(c);
        area.add(p);
        Deque<Point> q = new ArrayDeque<>();
        q.add(p);
        while (!q.isEmpty()) {
            p = q.removeFirst();
            for (Direction d : Direction.values()) {
                Point next = new Point(p.y() + d.dy(), p.x() + d.dx());
                if (area.add(next)) q.addLast(next);
            }
        }
        AREAS.add(area);
    }

    private static void init() {
        try {
            List<String> strings = Files.readAllLines(Path.of(DATA));
            grid = new char[strings.size()][];
            for (int i = 0; i < strings.size(); i++) {
                grid[i] = strings.get(i).toCharArray();
            }
            visited = new boolean[grid.length][grid[0].length];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static class Area {
        final int ch;
        int s;
        int p;
        int angle;

        public Area(int ch) {
            this.ch = ch;
        }

        public int calculate() {
            return s * p;
        }

        private static boolean inGrid(Point p) {
            return inGrid(p.y(), p.x());
        }

        private static boolean inGrid(int y, int x) {
            return inMatrix(y, x, grid);
        }

        public boolean add(Point point) {
            if (!inGrid(point) || point.value() != ch || point.isVisited()) return false;
            if (s == 0) {
                s = 1;
                p = 4;
                angle = 4;
            } else {
                int neighbours = countNeighbours(point);
                p += 4 - neighbours * 2;
                s++;
            }
            point.setVisited();
            return true;
        }

        private int countNeighbours(Point p) {
            int count = 0;
            for (Direction d : Direction.values()) {
                Point dp = new Point(p.y() + d.dy(), p.x() + d.dx());
                if (inGrid(dp) && dp.value() == ch && dp.isVisited()) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public String toString() {
            return String.format("Area('%s')[S:%d,P:%d]", Character.toString(ch), s, p);
        }
    }

    static class Point extends AdventUtils.Point {

        public Point(int y, int x) {
            super(y, x);
        }

        public char value() {
            return grid[y()][x()];
        }

        public boolean isVisited() {
            return visited[y()][x()];
        }

        public void setVisited() {
            visited[y()][x()] = true;
        }

    }


}
