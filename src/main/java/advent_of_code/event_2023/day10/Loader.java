package advent_of_code.event_2023.day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import static advent_of_code.event_2023.day10.Loader.Vector.*;

public class Loader {

    private static final Character GROUND = '.';
    private static final Character START = 'S';
    private static final Character VISITED = 'X';
    private static final Character OUTSIDE = 'X';
    private static final Character INSIDE = 'I';

    private static final String UNEXPECTED_VALUE = "Unexpected value: ";

    private static final Function<Character, Vector> UP_GO = character -> switch (character) {
        case '|' -> UP;
        case '7' -> LEFT;
        case 'F' -> RIGHT;
        default -> throw new IllegalStateException(UNEXPECTED_VALUE + character);
    };

    private static final Function<Character, Vector> RIGHT_GO = character -> switch (character) {
        case '-' -> RIGHT;
        case 'J' -> UP;
        case '7' -> DOWN;
        default -> throw new IllegalStateException(UNEXPECTED_VALUE + character);
    };

    private static final Function<Character, Vector> DOWN_GO = character -> switch (character) {
        case '|' -> DOWN;
        case 'L' -> RIGHT;
        case 'J' -> LEFT;
        default -> throw new IllegalStateException(UNEXPECTED_VALUE + character);
    };

    private static final Function<Character, Vector> LEFT_GO = character -> switch (character) {
        case '-' -> LEFT;
        case 'F' -> DOWN;
        case 'L' -> UP;
        default -> throw new IllegalStateException(UNEXPECTED_VALUE + character);
    };

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day10/test4.txt";
    private static List<char[]> map;
    private static char[][] matrix;


    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            String emptyLine = String.valueOf(GROUND).repeat(line.length() + 2);
            map = new ArrayList<>();
            map.add(emptyLine.toCharArray());
            int y = 0;
            Point start = new Point(-1, -1);
            while (line != null) {
                map.add((GROUND + line + GROUND).toCharArray());
                int x = line.indexOf(START);
                if (x > -1) start = new Point(y + 1, x + 1);
                y++;
                line = reader.readLine();
            }
            map.add(emptyLine.toCharArray());

            List<Point> starts = new ArrayList<>(2);
            for (Vector vector : values()) {
                Point point = new Point(start.y, start.x, vector);
                try {
                    point = goStep(point);
                } catch (Exception e) {
                    continue;
                }
                starts.add(point);
            }
            Point p1 = starts.get(0);
            Point p2 = starts.get(1);
            matrix = new char[map.size()][map.get(0).length];
            for (char[] chars : matrix) {
                Arrays.fill(chars, GROUND);
            }
            int count = 1;
            while (!hasContact(p1, p2)) {
                markVisited(p1);
                markVisited(p2);
                p1 = goStep(p1);
                p2 = goStep(p2);
                count++;
            }
            markVisited(p1);
            fillOut();

            writeMatrix();
            writeMap();

            System.out.println(count);

        }
    }

    private static void fillOut() {
        Deque<Point> deque = new ArrayDeque<>();
        Point p = new Point(0, 0);
        deque.add(p);
        while (!deque.isEmpty()) {
            p = deque.pop();
            markOutside(p);
            for (Vector v : values()) {
                Point next;
                char c;
                try {
                    next = new Point(p.y + v.d.y, p.x + v.d.x);
                    c = getFromMap(next);
                } catch (Exception e) {
                    continue;
                }
                if (c == GROUND && c != OUTSIDE) deque.add(next);
            }
        }
    }

    private static void writeMatrix() {
        try (FileWriter writer = new FileWriter("matrix.txt")) {
            for (char[] line : matrix) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeMap() {
        try (FileWriter writer = new FileWriter("map.txt")) {
            for (char[] line : map) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void markOutside(Point point) {
//        setToMap(point, OUTSIDE);
        setToMap(point, OUTSIDE);
    }

    private static void markVisited(Point point) {
        //setToMap(point, VISITED);
        setToMatrix(point, VISITED);
    }

    private static char getFromMap(Point point) {
        return map.get(point.y)[point.x];
    }

    private static void setToMap(Point point, char marker) {
        map.get(point.y)[point.x] = marker;
    }

    private static void setToMatrix(Point point, char marker) {
        matrix[point.y][point.x] = marker;
    }


    private static boolean hasContact(Point p1, Point p2) {
        return p1.y == p2.y && p1.x == p2.x;
    }

    private static Point goStep(Point point) {
        Vector vector = point.v;
        int nextY = point.y + vector.d.y;
        int nextX = point.x + vector.d.x;
        char next = map.get(nextY)[nextX];
        Vector nextV = vector.function.apply(next);
        return new Point(nextY, nextX, nextV);
    }

    private static class Point {

        int y;
        int x;
        Vector v;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public Point(int y, int x, Vector v) {
            this.y = y;
            this.x = x;
            this.v = v;
        }

        @Override
        public String toString() {
            return String.format("[%d:%d]", y, x);
        }

    }

    enum Vector {
        UP(new Point(-1, 0), UP_GO),
        RIGHT(new Point(0, 1), RIGHT_GO),
        DOWN(new Point(1, 0), DOWN_GO),
        LEFT(new Point(0, -1), LEFT_GO);

        final Point d;
        final Function<Character, Vector> function;

        Vector(Point d, Function<Character, Vector> function) {
            this.d = d;
            this.function = function;
        }
    }

}
