package advent_of_code.event_2024.day06;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;
import advent_of_code.util.AdventUtils.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Loader {

    private static final String TEST = "src/main/java/advent_of_code/event_2024/day06/test.txt";
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day06/data.txt";

    private static final int LIMIT = 10_000;

    private static char[][] grid;
    private static int[][] visited;
    private static int startY;
    private static int startX;

    public static void main(String[] args) {
        init(1);
        System.out.println(part2v2());
    }

    public static int part1() {
        int y = startY;
        int x = startX;
        int count = 1;
        grid[y][x] = 'X';
        Direction direction = Direction.UP;
        int steps = 0;
        while (inGrid(y, x) && steps < LIMIT) {
            if (grid[y][x] != '#') {
                if (grid[y][x] != 'X') {
                    grid[y][x] = 'X';
                    count++;
                }
                y += direction.dy();
                x += direction.dx();
            } else {
                y -= direction.dy();
                x -= direction.dx();
                direction = getNextDirection(direction);
            }
            steps++;
        }
        return steps == LIMIT ? -1 : count;
    }

    private static int part2() {
        int y = startY;
        int x = startX;
        Direction d = Direction.UP;
        Direction rd = getNextDirection(d);
        int count = 0;
        while (inGrid(y, x)) {
            if (grid[y][x] != '#') {
                visited[y][x] = setBit(visited[y][x], d.ordinal());
                if (isCircled(y, x, rd)
                        && grid[y + d.dy()][x + d.dx()] != '#') {
                    count++;
                }
                y += d.dy();
                x += d.dx();
            } else {
                y -= d.dy();
                x -= d.dx();
                d = getNextDirection(d);
                rd = getNextDirection(d);
            }
        }
        return count;
    }

    private static int part2v2() {
        int count = 0;
        Set<Point> path = getPath();
        for (Point point : path) {
            if (isCircle(point)) count++;
        }
        return count;
    }

    private static boolean isCircle(Point p) {
        grid[p.y()][p.x()] = '#';
        int count = part1();
        grid[p.y()][p.x()] = '_';
        return count == -1;
    }

    private static Set<Point> getPath() {
        Set<Point> set = new HashSet<>();
        int y = startY;
        int x = startX;
        Direction direction = Direction.UP;
        while (inGrid(y, x)) {
            if (grid[y][x] != '#') {
                set.add(new Point(y, x));
                y += direction.dy();
                x += direction.dx();
            } else {
                y -= direction.dy();
                x -= direction.dx();
                direction = getNextDirection(direction);
            }
        }
        return set;
    }

    private static boolean isCircled(int y, int x, Direction d) {
        y += d.dy();
        x += d.dx();
        while (inGrid(y, x) && grid[y][x] != '#') {
            if (isBitSet(visited[y][x], d.ordinal())) return true;
            y += d.dy();
            x += d.dx();
        }
        return false;
    }

    private static int setBit(int num, int index) {
        return num | (1 << index);
    }

    private static boolean isBitSet(int num, int index) {
        return (num & (1 << index)) != 0;
    }

    private static Direction getNextDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    private static boolean inGrid(int y, int x) {
        return AdventUtils.inMatrix(y, x, grid);
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            grid = new char[strings.size()][];
            visited = new int[strings.size()][strings.get(0).length()];
            for (int i = 0; i < strings.size(); i++) {
                String s = strings.get(i);
                s = s.replace(".", "_");
                grid[i] = s.toCharArray();
                int pos = s.indexOf('^');
                if (s.indexOf('^') != -1) {
                    startY = i;
                    startX = pos;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

