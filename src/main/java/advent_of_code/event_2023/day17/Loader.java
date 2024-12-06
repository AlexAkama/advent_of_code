package advent_of_code.event_2023.day17;

import advent_of_code.util.AdventUtils.Direction;
import advent_of_code.util.AdventUtils.DirectionPair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static advent_of_code.util.AdventUtils.Direction.*;
import static advent_of_code.util.AdventUtils.readAllFromFile;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day17/data.txt";
    private static final int STEP_LIMIT = 3;
    private static final int ULTRA_STEP_LIMIT = 10;
    private static final int EMPTY_STEPS = 4;

    private static final Function<Direction, DirectionPair> CHANGE_DIRECTION = direction -> switch (direction) {
        case LEFT, RIGHT -> new DirectionPair(UP, DOWN);
        case UP, DOWN -> new DirectionPair(LEFT, RIGHT);
    };

    private static int[][] matrix;
    private static Map<Direction, int[][]> map;

    private static int maxY;
    private static int maxX;
    private static final Deque<Point> DEQUE = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        init();

        Point p1 = new Point(0, 0, RIGHT, 0);
        Point p2 = new Point(0, 0, DOWN, 0);

        DEQUE.add(p1);
        DEQUE.add(p2);

        while (!DEQUE.isEmpty()) {
            Point p = DEQUE.pop();
            doUltraStep(p);
        }

        //Part#1
        //Point end = new Point(maxY - 1, maxX - 1, null, 0);
        //System.out.println(getPositiveMin(end));
        //Point end = new Point(maxY - 1, maxX - 1, null, 0);
        //System.out.println(getPositiveMin(end));


        Point end = new Point(maxY - 1, maxX - 1, null, 0);
        System.out.println(getPositiveMin(end));
        //1387
        //1404 hi

    }

    // --- Part #2 ---

    private static void doUltraStep(Point p) {
        Point next = p.getNext();
        for (int i = 0; i < ULTRA_STEP_LIMIT; i++) {
            doUltraSingleStep(next, i);
            next = next.getNext();
            if (!next.inMatrix()) break;
        }
    }

    private static void doUltraSingleStep(Point p, int step) {
        if (p.inMatrix() && p.isNotStart()) {
            int curPathSum = p.getPx();
            if (curPathSum == 0 || p.sum < curPathSum) {
                p.mark();
                if (EMPTY_STEPS - 2 < step) {
                    PointPair splitPair = p.getSplitPair();
                    if (splitPair.p1.stepsToEnd() > EMPTY_STEPS - 1) DEQUE.add(splitPair.p1);
                    if (splitPair.p2.stepsToEnd() > EMPTY_STEPS - 1) DEQUE.add(splitPair.p2);
                }
            }
        }
    }


    // --- Part #1 ---

    private static int getPositiveMin(Point end) {
        int min = Integer.MAX_VALUE;
        for (Direction d : values()) {
            end.d = d;
            int px = end.getPx();
            System.out.println(d.arrow() + " " + px);
            min = Math.min(min, px != 0 ? px : Integer.MAX_VALUE);
        }
        return min;
    }

    private static void doStep(Point p) {
        Point next = p.getNext();
        for (int i = 0; i < STEP_LIMIT; i++) {
            doSingleStep(next);
            next = next.getNext();
            if (!next.inMatrix()) break;
        }
    }

    private static void doSingleStep(Point p) {
        if (p.inMatrix() && p.isNotStart()) {
            int curPathSum = p.getPx();
            if (curPathSum == 0 || p.sum < curPathSum) {
                p.mark();
                PointPair splitPair = p.getSplitPair();
                DEQUE.add(splitPair.p1);
                DEQUE.add(splitPair.p2);
            }
        }
    }

    // --- INIT ---

    private static void init() throws IOException {
        List<String> list = readAllFromFile(INPUT);

        matrix = new int[list.size()][];
        map = new EnumMap<>(Direction.class);

        for (Direction direction : values()) {
            map.put(direction, new int[list.size()][]);
            int[][] ints = map.get(direction);
            for (int i = 0; i < ints.length; i++) {
                ints[i] = new int[list.get(0).length()];
            }
        }

        for (int y = 0; y < list.size(); y++) {
            char[] chars = list.get(y).toCharArray();
            int[] ints = new int[chars.length];
            matrix[y] = ints;
            for (int x = 0; x < chars.length; x++) {
                matrix[y][x] = Integer.parseInt(String.valueOf(chars[x]));
            }
        }

        maxY = matrix.length;
        maxX = matrix[0].length;
    }

    private static class Point {

        int y;
        int x;
        Direction d;

        int sum;

        public Point(int y, int x, Direction d, int sum) {
            this.y = y;
            this.x = x;
            this.d = d;
            this.sum = sum;
        }


        void mark() {
            int[][] curMatrix = map.get(d);
            if (curMatrix[y][x] == 0 || sum < curMatrix[y][x]) {
                curMatrix[y][x] = sum;
            }
        }

        int getMx() {
            return (inMatrix()) ? matrix[y][x] : 0;
        }

        int getPx() {
            return (inMatrix()) ? map.get(d)[y][x] : 0;
        }


        boolean inMatrix() {
            return y > -1 && x > -1 && y < maxY && x < maxX;
        }

        boolean isNotStart() {
            return y != 0 || x != 0;
        }

        Point getNext() {
            Point next = new Point(y + d.dy(), x + d.dx(), d, 0);
            next.sum = sum + next.getMx();
            return next;
        }

        int stepsToEnd() {
            int count = Integer.MAX_VALUE;
            boolean onBorder = (maxY - 1 - y) * (maxX - 1 - x) == 0;
            if (onBorder) {
                int lastY = y + d.dy() * ULTRA_STEP_LIMIT;
                int lastX = x + d.dx() * ULTRA_STEP_LIMIT;
                if (lastY > maxY - 1 || lastX > maxX - 1) {
                    count = (maxY - 1 - y) + (maxX - 1 - x);
                }
            }
            return count;
        }

        PointPair getSplitPair() {
            DirectionPair directionPair = CHANGE_DIRECTION.apply(d);
            return new PointPair(
                    new Point(y, x, directionPair.d1, sum),
                    new Point(y, x, directionPair.d2, sum)
            );
        }

        @Override
        public String toString() {
            return String.format("[%s:%s]%s", y, x, d.arrow());
        }

    }

    private static class PointPair {

        Point p1;
        Point p2;

        public PointPair(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

    }

}
