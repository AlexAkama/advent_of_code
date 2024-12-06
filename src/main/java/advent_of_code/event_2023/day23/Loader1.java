package advent_of_code.event_2023.day23;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static advent_of_code.util.AdventUtils.Direction.DOWN;
import static advent_of_code.util.AdventUtils.Direction.RIGHT;

public class Loader1 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day23/data.txt";

    private static final char FOREST = '#';
    private static final char PATH = '.';

    private static char[][] matrix;
    private static Point start;
    private static Point finish;

    private static int max;

    public static void main(String[] args) throws IOException {
        init();

        doPart1();

        System.out.println(max);
    }

    private static int doPart1() {
        int max = 0;
        Deque<Point> deque = new ArrayDeque<>();
        deque.add(start);
        while (!deque.isEmpty()) {
            Point p = deque.pop();
            for (Direction d : Direction.values()) {
                Point next = p.getNext(d);
                if (next.inMatrix() &&
                        next.isNoForest() &&
                        next.not(p.prev)) {
                    if (next.isFinish()) {
                        max = Math.max(max, p.count);
                        continue;
                    }
                    if (next.isSlope()) {
                        Direction slopeD = Direction.getBy(next.get());
                        if (slopeD != next.d) continue;
                    }
                    deque.add(next);
                }
            }
        }
        return max;
    }

    private static void init() throws IOException {
        List<String> lines = AdventUtils.readAllFromFile(INPUT);
        matrix = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            matrix[i] = lines.get(i).toCharArray();
        }
        start = new Point(1, 1, RIGHT, 2, new Point(0, 1, DOWN, 1, null));
        finish = new Point(matrix.length - 1, matrix[0].length - 2, DOWN, -1, null);
    }

    static class Point {

        int y;
        int x;
        Direction d;
        int count;
        Point prev;

        public Point(int y, int x, Direction d, int count, Point prev) {
            this.y = y;
            this.x = x;
            this.d = d;
            this.count = count;
            this.prev = prev;
        }

        char get() {
            return matrix[y][x];
        }

        boolean not(Point p) {
            return !this.equals(p);
        }

        boolean equals(Point p) {
            return y == p.y && x == p.x;
        }

        Point getNext(Direction d) {
            return new Point(y + d.dy(), x + d.dx(), d, count + 1, this);
        }

        boolean isFinish() {
            return this.equals(finish);
        }

        boolean isSlope() {
            return matrix[y][x] != FOREST && matrix[y][x] != PATH;
        }

        boolean isNoForest() {
            return matrix[y][x] != FOREST;
        }

        boolean inMatrix() {
            return y > -1 && x > -1 && y < matrix.length && x < matrix[0].length;
        }

        @Override
        public String toString() {
            return String.format("[%s:%s]%s", y, x, d.arrow());
        }

    }

}
