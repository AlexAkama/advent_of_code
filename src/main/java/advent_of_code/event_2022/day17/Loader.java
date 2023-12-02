package advent_of_code.event_2022.day17;

import advent_of_code.event_2022.Point;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static advent_of_code.event_2022.Utils.INPUT;
import static advent_of_code.event_2022.Utils.TEST;
import static advent_of_code.event_2022.day17.Loader.WindDirection.LEFT;
import static advent_of_code.event_2022.day17.Loader.WindDirection.RIGHT;
import static advent_of_code.event_2022.day17.Loader.WindDirection.findBy;

public class Loader {

    private static final char ROCK = 'X';
    private static final char AIR = '.';
    private static final int START_DX = 2;
    private static final int START_DY = 3;
    private static final int WIDE = 7;

    private static final Shape LINE = new Shape("Line", 1, 4, 1, List.of(
            new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)
    ));
    private static final Shape PLUS = new Shape("Plus", 2, 3, 3, List.of(
            new Point(1, 0),
            new Point(0, 1), new Point(1, 1), new Point(2, 1),
            new Point(1, 2)
    ));
    private static final Shape ANGLE = new Shape("Angle", 3, 3, 3, List.of(
            new Point(0, 0), new Point(1, 0), new Point(2, 0),
            new Point(2, 1),
            new Point(2, 2)
    ));
    private static final Shape PILLAR = new Shape("Pillar", 4, 1, 4, List.of(
            new Point(0, 0),
            new Point(0, 1),
            new Point(0, 2),
            new Point(0, 3)
    ));
    private static final Shape BOX = new Shape("Box", 5, 2, 2, List.of(
            new Point(0, 0), new Point(1, 0),
            new Point(0, 1), new Point(1, 1)
    ));
    private static final List<Shape> SHAPES = List.of(LINE, PLUS, ANGLE, PILLAR, BOX);

    private static final int LIMIT = 2022;
    private static final long PART2_LIMIT = 1_000_000_000_000L;
    private static char[][] GRID;

    private static int shapePointer;
    private static String windMap;
    private static int windPointer;

    private static int lastMax = 0;
    private static int accumMax = 0;

    private static boolean startBuildingMap = false;
    private static final List<Integer> maxMap = new ArrayList<>();
    private static long prevRockCount = 0;
    private static int shapeCounter = 0;

    public static void main(String[] args) throws IOException {
        parse(TEST);
        createHelpers();

        Point point;
        Point next;

        for (long i = 0; i < LIMIT; i++) {
            point = new Point(START_DX, lastMax + START_DY);
            next = point;
            var shape = getNextShape();
            while (true) {

                var wind = getNextWind();
                if (wind == LEFT) next = new Point(point.x - 1, point.y);
                if (wind == RIGHT) next = new Point(point.x + 1, point.y);
                if (!isOutPosition(next, shape) && canState(next, shape)) point = next;
                next = new Point(point.x, point.y - 1);
                if (!canState(next, shape)) break;
                else point = next;
            }
            fixShape(point, shape);
            lastMax = Math.max(lastMax, point.y + shape.height);
            if (isStopper(point)) {
                System.out.println(point);
            }
        }
        for (int i = 0; i < 200; i++) {
            System.out.print('|');
            for (int j = 0; j < WIDE; j++) {
                System.out.print(GRID[i][j]);
            }
            System.out.print('|');
            System.out.println();
        }
        System.out.println(accumMax + lastMax);
    }

    private static void moveToDown(Point point) {
        for (int i = point.y; i < lastMax; i++) {
            GRID[i - point.y] = Arrays.copyOf(GRID[i], GRID[i].length);
            Arrays.fill(GRID[i], AIR);
        }
    }

    private static void clearTo(Point point) {
        for (int i = 0; i < point.y; i++) {
            for (int j = 0; j < WIDE; j++) {
                GRID[i][j] = AIR;
            }
        }
    }

    private static boolean isStopper(Point point) {
        for (int i = 0; i < WIDE; i++) {
            if (GRID[point.y][i] == AIR && GRID[point.y + 1][i] == AIR) return false;
        }
        return true;
    }

    private static boolean canState(Point point, Shape shape) {
        for (Point rock : shape.rocks) {
            if (point.y + rock.y < 0 || GRID[point.y + rock.y][point.x + rock.x] != AIR) return false;
        }
        return true;
    }

    private static boolean isOutPosition(Point point, Shape shape) {
        return point.x < 0 || point.x + shape.width > WIDE;
    }

    private static void fixShape(Point point, Shape shape) {
        for (Point rock : shape.rocks) {
            GRID[point.y + rock.y][point.x + rock.x] = (char) ('0' + shape.pos);
        }
    }

    private static void clearShape(Point point, Shape shape) {
        for (Point rock : shape.rocks) {
            GRID[point.y + rock.y][point.x + rock.x] = AIR;
        }
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day17/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            windMap = reader.readLine();
        }
        System.out.println();
    }

    private static void createHelpers() {
//        int deep = SHAPES.size() * windMap.length() * 3;
        int deep = 8000;
        GRID = new char[deep][WIDE];
        for (char[] chars : GRID) {
            Arrays.fill(chars, AIR);
        }
    }

    private static Shape getNextShape() {
        shapeCounter++;
        if (shapePointer == SHAPES.size()) shapePointer = 0;
        return SHAPES.get(shapePointer++);
    }

    private static WindDirection getNextWind() {
        if (windPointer == windMap.length()) windPointer = 0;
        return findBy(windMap.charAt(windPointer++));
    }

    private static class Shape {

        final String name;
        final int pos;
        final int width;
        final int height;

        final List<Point> rocks = new ArrayList<>();


        private Shape(String name, int pos, int width, int height, List<Point> rocks) {
            this.name = name;
            this.pos = pos;
            this.width = width;
            this.height = height;
            this.rocks.addAll(rocks);
        }

    }

    enum WindDirection {
        LEFT,
        RIGHT;

        public static WindDirection findBy(Character c) {
            if (c == '>') return RIGHT;
            if (c == '<') return LEFT;
            return null;
        }
    }

}
