package advent_of_code.event_2024.day06;

import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Loader {

    private static final String TEST = "src/main/java/advent_of_code/event_2024/day06/test.txt";
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day06/data.txt";

    private static char[][] grid;
    private static int[][][] visited;
    private static int[][] stones;

    private static int startY;
    private static int startX;

    public static void main(String[] args) {
        init();
        System.out.println(part2());
    }

    public static int part1() {
        int y = startY;
        int x = startX;
        int count = 1;
        grid[y][x] = 'X';
        Direction direction = Direction.UP;
        while (inGrid(y, x)) {
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
        }
        return count;
    }

    private static int part2() {
        int y = startY;
        int x = startX;
        int count = 0;
        Direction d = Direction.UP;
        Direction rd;
        while (inGrid(y, x)) {
            rd = getNextDirection(d);
            if (grid[y][x] != '#') {
                if (canBeCircle(y, x, rd)) {
                    count++;
                    stones[y + d.dy()][x + d.dx()] = count;
                }
                visited[y][x][d.ordinal()] = 1;
                y += d.dy();
                x += d.dx();
            } else {
                y -= d.dy();
                x -= d.dx();
                d = getNextDirection(d);
            }
        }
        return count;
    }

    private static boolean canBeCircle(int y, int x, Direction d) {
        while (inGrid(y, x)) {
            if (visited[y][x][d.ordinal()] == 1) return true;
            y += d.dy();
            x += d.dx();
        }
        return false;
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
        return y >= 0 && y < grid.length && x >= 0 && x < grid[y].length;
    }

    private static void init() {
        try {
            List<String> strings = Files.readAllLines(Path.of(DATA));
            grid = new char[strings.size()][];
            visited = new int[strings.size()][strings.get(0).length()][4];
            stones = new int[strings.size()][strings.get(0).length()];
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

