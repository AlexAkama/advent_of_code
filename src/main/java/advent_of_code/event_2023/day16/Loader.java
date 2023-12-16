package advent_of_code.event_2023.day16;

import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static advent_of_code.event_2023.day16.Loader.Mirror.LEFT_MIRROR;
import static advent_of_code.event_2023.day16.Loader.Mirror.RIGHT_MIRROR;
import static advent_of_code.event_2023.day16.Loader.Splitter.HORIZONTAL_SPLITTER;
import static advent_of_code.event_2023.day16.Loader.Splitter.VERTICAL_SPLITTER;
import static advent_of_code.util.AdventUtils.Direction.DOWN;
import static advent_of_code.util.AdventUtils.Direction.LEFT;
import static advent_of_code.util.AdventUtils.Direction.RIGHT;
import static advent_of_code.util.AdventUtils.Direction.UP;
import static advent_of_code.util.AdventUtils.readAllFromFile;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day16/data.txt";

    private static final char AIR = '.';
    private static final char VISITED = '#';

    private static final UnaryOperator<Direction> LEFT_MIRROR_TURN = d -> switch (d) {
        case UP -> LEFT;
        case DOWN -> RIGHT;
        case LEFT -> UP;
        case RIGHT -> DOWN;
    };

    private static final UnaryOperator<Direction> RIGHT_MIRROR_TURN = d -> switch (d) {
        case UP -> RIGHT;
        case DOWN -> LEFT;
        case LEFT -> DOWN;
        case RIGHT -> UP;
    };

    private static final Function<Direction, Pair> VERTICAL_SPLIT = direction -> switch (direction) {
        case UP, DOWN -> null;
        case LEFT, RIGHT -> new Pair(UP, DOWN);
    };

    private static final Function<Direction, Pair> HORIZONTAL_SPLIT = direction -> switch (direction) {
        case UP, DOWN -> new Pair(LEFT, RIGHT);
        case LEFT, RIGHT -> null;
    };

    private static int maxY;
    private static int maxX;
    private static char[][] matrix;
    private static char[][] visited;

    public static void main(String[] args) throws IOException {
        init();

        int max = 0;
        int sum;
        Point start = new Point(0, 0);
        for (int y = 0; y < maxY; y++) {
            start.y = y;

            start.x = 0;
            start.d = RIGHT;
            sum = foo(start);
            max = Math.max(max, sum);

            start.x = maxX - 1;
            start.d = LEFT;
            sum = foo(start);
            max = Math.max(max, sum);
        }
        for (int x = 0; x < maxX; x++) {
            start.x = x;

            start.y = 0;
            start.d = DOWN;
            sum = foo(start);
            max = Math.max(max, sum);

            start.y = maxY - 1;
            start.d = UP;
            sum = foo(start);
            max = Math.max(max, sum);

        }

        System.out.println(max);
    }

    private static int foo(Point start) {
        initVisited();

        Deque<Point> deque = new ArrayDeque<>();
        if (start.isMirror()) {
            Mirror mirror = Mirror.getMirror(start.getChar());
            start.d = mirror.function.apply(start.d);
        } else if (start.isSplitter()) {
            Splitter splitter = Splitter.getSplitter(start.getChar());
            Pair pair = splitter.function.apply(start.d);
            if (pair != null) {
                start.d = pair.d1;
                Point split = new Point(start.y, start.x, pair.d2);
                deque.add(split);
            }
        }
        deque.add(start);

        while (!deque.isEmpty()) {
            Point p = deque.pop();
            p.mark();
            int nextY = p.y + p.d.dy();
            int nextX = p.x + p.d.dx();
            Point next = new Point(nextY, nextX, p.d);
            if (next.inMatrix()) {
                if (next.d.arrow() == visited[next.y][next.x]) {
                    continue;
                }
                if (next.getChar() == AIR) {
                    deque.add(next);
                    continue;
                }
                if (next.isMirror()) {
                    Mirror mirror = Mirror.getMirror(next.getChar());
                    next.d = mirror.function.apply(next.d);
                    deque.add(next);
                }
                if (next.isSplitter()) {
                    Splitter splitter = Splitter.getSplitter(next.getChar());
                    Pair pair = splitter.function.apply(next.d);
                    if (pair != null) {
                        next.d = pair.d1;
                        deque.add(next);
                        Point split = new Point(next.y, next.x, pair.d2);
                        deque.add(split);
                    } else {
                        deque.add(next);
                    }
                }
            }
        }
        return count();
    }

    private static int count() {
        int count = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (visited[y][x] != AIR) count++;
            }
        }
        return count;
    }

    private static void init() throws IOException {
        List<String> list = readAllFromFile(INPUT);
        maxY = list.size();
        maxX = list.get(0).length();
        matrix = new char[maxY][];
        for (int i = 0; i < maxY; i++) {
            matrix[i] = list.get(i).toCharArray();
        }
        initVisited();
    }

    private static void initVisited() {
        visited = new char[matrix.length][];
        for (int i = 0; i < maxY; i++) {
            char[] chars = new char[maxX];
            Arrays.fill(chars, AIR);
            visited[i] = chars;
        }
    }

    private static class Point {

        int y;
        int x;
        Direction d;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public Point(int y, int x, Direction d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }

        boolean inMatrix() {
            return y > -1 && x > -1 && y < maxY && x < maxX;
        }

        void mark() {
            char c = visited[y][x];
            if (matrix[y][x] != AIR) visited[y][x] = matrix[y][x];
            else if (c == AIR && matrix[y][x] == AIR) visited[y][x] = d.arrow();
            else visited[y][x] = '#';
        }

        char getChar() {
            return matrix[y][x];
        }

        boolean isSplitter() {
            char c = getChar();
            return VERTICAL_SPLITTER.c == c || HORIZONTAL_SPLITTER.c == c;
        }

        boolean isMirror() {
            char c = getChar();
            return LEFT_MIRROR.c == c || RIGHT_MIRROR.c == c;
        }

        @Override
        public String toString() {
            return String.format("[%s:%d](%s)", y, x, d.name());
        }

    }

    static class Pair {

        Direction d1;
        Direction d2;

        public Pair(Direction d1, Direction d2) {
            this.d1 = d1;
            this.d2 = d2;
        }

    }

    enum Mirror {
        LEFT_MIRROR('\\', LEFT_MIRROR_TURN),
        RIGHT_MIRROR('/', RIGHT_MIRROR_TURN);

        private final char c;
        private final UnaryOperator<Direction> function;

        Mirror(char c, UnaryOperator<Direction> function) {
            this.c = c;
            this.function = function;
        }

        public static Mirror getMirror(char c) {
            return switch (c) {
                case '\\' -> LEFT_MIRROR;
                case '/' -> RIGHT_MIRROR;
                default -> throw new IllegalArgumentException("Illegal char: " + c);
            };
        }
    }

    enum Splitter {
        VERTICAL_SPLITTER('|', VERTICAL_SPLIT),
        HORIZONTAL_SPLITTER('-', HORIZONTAL_SPLIT);

        private final char c;
        private final Function<Direction, Pair> function;

        Splitter(char c, Function<Direction, Pair> function) {
            this.c = c;
            this.function = function;
        }

        public static Splitter getSplitter(char c) {
            return switch (c) {
                case '|' -> VERTICAL_SPLITTER;
                case '-' -> HORIZONTAL_SPLITTER;
                default -> throw new IllegalArgumentException("Illegal char: " + c);
            };
        }

    }

}
