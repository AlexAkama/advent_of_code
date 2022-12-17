package advent_of_code.event_2022.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoaderPart2 {

    private static final char AIR = '_';
    private static final char ROCK = 'x';
    private static final char SAND = 'o';

    private static final int START_SAND_POS = 500;

    private static int maxX = Integer.MIN_VALUE;
    private static int maxY = Integer.MIN_VALUE;

    private static List<List<Point>> data;
    private static char[][] grid;

    private static int count = 0;

    public static void main(String[] args) throws IOException {
        parse("data.txt");

        createGrid();
        renderRocks(data);

        dropSand();

        System.out.println(count);

    }

    private static void dropSand() {
        Sand sand = new Sand(START_SAND_POS, 0);
        while (sand.y != grid.length - 1) {
            while (sand.moveDown() || sand.moveLeftDiagonally() || sand.moveRightDiagonally()) {
            }
            count++;
            sand.mark();
            if (sand.y == 0) return;
            sand.x = START_SAND_POS;
            sand.y = 0;
        }
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day14/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        List<List<Point>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(" -> ");
                List<Point> list = new ArrayList<>();
                for (String s : split) {
                    Point point = parsePoint(s);
                    list.add(point);
                }
                data.add(list);
                line = reader.readLine();
            }
            LoaderPart2.data = data;
        }
    }

    private static void renderRocks(List<List<Point>> data) {
        for (List<Point> list : data) {
            for (int i = 0; i < list.size() - 1; i++) {
                drawRockLine(list.get(i), list.get(i + 1));
            }
        }
        drawFloor();
    }

    private static void drawRockLine(Point start, Point end) {
        int deltaX = start.x - end.x;
        int deltaY = start.y - end.y;
        int length = Math.max(Math.abs(deltaX), Math.abs(deltaY));
        int signX = sign(deltaX);
        int signY = sign(deltaY);
        for (int i = 0; i < length + 1; i++) {
            int x = start.x - i * signX;
            int y = start.y - i * signY;
            grid[y][x] = ROCK;
        }
    }

    private static void drawFloor() {
        for (int i = 0; i < grid[0].length; i++) {
            grid[grid.length - 1][i] = ROCK;
        }
    }

    private static void createGrid() {
        int sizeX = maxX + 1 + 500;
        int sizeY = maxY + 1 + 2;
        createGrid(sizeX, sizeY);
    }

    private static void createGrid(int sizeX, int sizeY) {
        grid = new char[sizeY][sizeX];
        for (char[] chars : grid) {
            Arrays.fill(chars, AIR);
        }
    }

    private static Point parsePoint(String s) {
        int splitPos = s.indexOf(',');
        int x = Integer.parseInt(s.substring(0, splitPos));
        int y = Integer.parseInt(s.substring(splitPos + 1));
        Point point = new Point(x, y);
        updateMinMax(point);
        return point;
    }

    private static void updateMinMax(Point point) {
        maxX = Math.max(maxX, point.x);
        maxY = Math.max(maxY, point.y);
    }

    private static int sign(int i) {
        if (i == 0) return i;
        if (i < 0) return -1;
        else return 1;
    }

    static class Sand extends Point {

        public Sand(int x, int y) {
            super(x, y);
        }

        public boolean moveDown() {
            if (grid[y + 1][x] != AIR) return false;
            y++;
            return true;
        }

        public boolean moveLeftDiagonally() {
            if (grid[y + 1][x - 1] != AIR) return false;
            this.y++;
            x--;
            return true;
        }

        public boolean moveRightDiagonally() {
            if (grid[y + 1][x + 1] != AIR) return false;
            y++;
            x++;
            return true;
        }

        public void mark() {
            grid[y][x] = SAND;
        }

    }

    static class Point {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }

    }

}
