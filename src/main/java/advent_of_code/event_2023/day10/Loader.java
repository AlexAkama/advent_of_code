package advent_of_code.event_2023.day10;

import advent_of_code.util.AdventUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static advent_of_code.event_2023.day10.Loader.Vector.DOWN;
import static advent_of_code.event_2023.day10.Loader.Vector.LEFT;
import static advent_of_code.event_2023.day10.Loader.Vector.RIGHT;
import static advent_of_code.event_2023.day10.Loader.Vector.UP;
import static advent_of_code.event_2023.day10.Loader.Vector.values;

//
// Идея для как определить точка внутри многоугольника или нет очень проста
// "Луч из точки пересечет стороны многоугольника нечетное кол-во раз"
// Использую луч из точки вправо
//
public class Loader {

    private static final Character GROUND = '.';
    private static final Character START = 'S';
    private static final Character OUTSIDE = '*';

    private static final String UNEXPECTED_VALUE = "Unexpected value: ";

    private static final BiFunction<Vector, Vector, Character> GET_START_CHAR = (v1, v2) -> {

        switch (v1) {
            case LEFT -> {
                return switch (v2) {
                    case LEFT -> GROUND;
                    case UP -> 'J';
                    case RIGHT -> '-';
                    case DOWN -> '7';
                };
            }
            case UP -> {
                return switch (v2) {
                    case LEFT -> 'J';
                    case UP -> GROUND;
                    case RIGHT -> 'L';
                    case DOWN -> '|';
                };
            }
            case RIGHT -> {
                return switch (v2) {
                    case LEFT -> '-';
                    case UP -> 'L';
                    case RIGHT -> GROUND;
                    case DOWN -> 'F';
                };
            }
            case DOWN -> {
                return switch (v2) {
                    case LEFT -> '7';
                    case UP -> '-';
                    case RIGHT -> 'F';
                    case DOWN -> GROUND;
                };
            }

        }
        return null;
    };

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

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day10/data.txt";
    private static char[][] map;
    private static char[][] path;

    private static Point startPoint;

    public static void main(String[] args) throws IOException {
        init();

        List<Point> starts = getStartPair();
        Point p1 = starts.get(0);
        Point p2 = starts.get(1);
        char startChar = getStartChar(p1, p2);

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

        path[startPoint.y][startPoint.x] = startChar;
        int countIn = countIn();

        System.out.println(count);
        //6903
        System.out.println(countIn);
        //265

    }

    private static char getStartChar(Point p1, Point p2) {
        return GET_START_CHAR.apply(p1.v, p2.v);
    }

    private static List<Point> getStartPair() {
        List<Point> starts = new ArrayList<>(2);
        for (Vector vector : values()) {
            Point point = new Point(startPoint.y, startPoint.x, vector);
            try {
                point = goStep(point);
            } catch (Exception e) {
                continue;
            }
            starts.add(point);
        }
        return starts;
    }

    private static void init() throws IOException {
        List<String> list = AdventUtils.readAllFromFile(INPUT);
        char[] emptyChars = String.valueOf(GROUND).repeat(list.get(0).length() + 2).toCharArray();
        map = new char[list.size() + 2][];
        map[0] = emptyChars;
        map[map.length - 1] = emptyChars;
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            map[i + 1] = (GROUND + line + GROUND).toCharArray();
            int pos = line.indexOf(START);
            if (pos > -1) startPoint = new Point(i + 1, pos + 1);
        }
        path = new char[map.length][map[0].length];
        for (char[] chars : path) {
            Arrays.fill(chars, GROUND);
        }
    }

    private static int countIn() {
        int count = 0;
        for (int y = 0; y < path.length; y++) {
            for (int x = 0; x < path[0].length; x++) {
                char c = path[y][x];
                if (c == '.' && checkIn(y, x)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean checkIn(int y, int x) {
        int count = 0;
        char prev = path[y][x];
        for (int i = x + 1; i < path[0].length; i++) {
            char c = path[y][i];
            if (c != GROUND && c != OUTSIDE && c != '-') {
                if ((c == '|') ||
                        (prev == 'L' && c == '7') || (prev == 'F' && c == 'J')) {
                    count++;
                }
                prev = c;
            }
        }
        return count % 2 != 0;
    }

    private static void fillOut() {
        Deque<Point> deque = new ArrayDeque<>();
        Point p = new Point(0, 0);
        deque.add(p);
        while (!deque.isEmpty()) {
            p = deque.pop();
            for (Vector v : values()) {
                Point next;
                char c;
                try {
                    next = new Point(p.y + v.d.y, p.x + v.d.x, v);
                    c = getFromPath(next);
                } catch (Exception e) {
                    continue;
                }
                if (c == GROUND) {
                    markOutside(next);
                    deque.add(next);
                }
            }
        }
    }

    private static void markOutside(Point point) {
        setToPath(point, OUTSIDE);
    }

    private static void markVisited(Point point) {
        setToPath(point, getFromMap(point));
    }

    private static void setToPath(Point point, char marker) {
        path[point.y][point.x] = marker;
    }

    private static char getFromPath(Point point) {
        return getFrom(path, point);
    }

    private static char getFromMap(Point point) {
        return getFrom(map, point);
    }

    private static char getFrom(char[][] matrix, Point point) {
        return matrix[point.y][point.x];
    }

    private static boolean hasContact(Point p1, Point p2) {
        return p1.y == p2.y && p1.x == p2.x;
    }

    private static Point goStep(Point point) {
        Vector vector = point.v;
        int nextY = point.y + vector.d.y;
        int nextX = point.x + vector.d.x;
        char next = map[nextY][nextX];
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
