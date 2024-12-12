package advent_of_code.event_2024.day12;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static advent_of_code.util.AdventUtils.Direction.DOWN;
import static advent_of_code.util.AdventUtils.Direction.LEFT;
import static advent_of_code.util.AdventUtils.Direction.RIGHT;
import static advent_of_code.util.AdventUtils.Direction.UP;
import static advent_of_code.util.AdventUtils.inMatrix;

public class LoaderV2 {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day12/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day12/test.txt";

    private static final List<Area> AREAS = new ArrayList<>();

    private static char[][] grid;
    private static boolean[][] visited;

    public static void main(String[] args) {
        init();
        part2();
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

    private static void part2() {
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
        int angle;

        public Area(int ch) {
            this.ch = ch;
        }

        public int calculate() {
            return s * angle;
        }

        @Override
        public String toString() {
            return String.format("Area('%s')[S:%d,A:%d]", Character.toString(ch), s, angle);
        }

        public boolean add(Point p) {
            if (!p.inGrid() || p.value() != ch ||p.isVisited()) return false;
            p.setVisited();
            s++;
            Point up = new Point(p.y() + UP.dy(), p.x() + UP.dx());
            Point right = new Point(p.y() + RIGHT.dy(), p.x() + RIGHT.dx());
            Point down = new Point(p.y() + DOWN.dy(), p.x() + DOWN.dx());
            Point left = new Point(p.y() + LEFT.dy(), p.x() + LEFT.dx());
            Point upLeft = new Point(p.y() + UP.dy(), p.x() + LEFT.dx());
            Point downLeft = new Point(p.y() + DOWN.dy(), p.x() + LEFT.dx());
            Point upRight = new Point(p.y() + UP.dy(), p.x() + RIGHT.dx());
            Point downRight = new Point(p.y() + DOWN.dy(), p.x() + RIGHT.dx());
            if (hasAngle(p, up, left, upLeft)) angle++;
            if (hasAngle(p, down, left, downLeft)) angle++;
            if (hasAngle(p, up, right, upRight)) angle++;
            if (hasAngle(p, down, right, downRight)) angle++;
            return true;
        }

        private boolean hasAngle(Point p, Point p1, Point p2, Point cross) {
            return p.equalValue(p1) && p.equalValue(p2) && !p.equalValue(cross)
                    || !p.equalValue(p1) && !p.equalValue(p2);
        }

    }

    static class Point extends AdventUtils.Point {

        public Point(int y, int x) {
            super(y, x);
        }

        public char value() {
            return grid[y()][x()];
        }

        public boolean equalValue(Point p) {
            if (!p.inGrid()) return false;
            return value() == p.value();
        }

        public boolean isVisited() {
            return visited[y()][x()];
        }

        public void setVisited() {
            visited[y()][x()] = true;
        }

        private boolean inGrid() {
            return inGrid(y(), x());
        }

        private static boolean inGrid(int y, int x) {
            return inMatrix(y, x, grid);
        }

    }


}
