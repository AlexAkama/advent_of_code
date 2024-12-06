package advent_of_code.event_2023.day21;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static advent_of_code.util.AdventUtils.*;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day21/data.txt";
    private static final int STEP_LIMIT = 64;

    public static final char START = 'S';
    public static final char STOP = 'O';
    public static final char GROUND = '.';
    public static final char ROCK = '#';


    private static final Deque<Point> DEQUE = new ArrayDeque<>();
    private static char[][] matrix;
    private static Point start;

    public static void main(String[] args) throws IOException {
        init();

        int count = 0;
        while (!DEQUE.isEmpty()) {
            Point p = DEQUE.pop();
            for (Direction d : Direction.values()) {
                Point next = new Point(p.y + d.dy(), p.x + d.dx(), p.count + 1);
                if (next.canGoTo()) {
                    if (next.count < STEP_LIMIT + 1) {
                        DEQUE.add(next);
                        if (next.count % 2 == 0) {
                            next.markFinish();
                            count++;
                        }
                    }
                }
            }
        }

        System.out.println(++count);
    }


    private static void init() throws IOException {
        List<String> list = readAllFromFile(INPUT);
        matrix = new char[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            matrix[i] = line.toCharArray();
            int pos = line.indexOf(START);
            if (pos > 0) {
                start = new Point(i, pos, 0);
                DEQUE.add(start);
            }
        }
    }

    static class Point {

        int y;
        int x;
        int count;

        public Point(int y, int x, int count) {
            this.y = y;
            this.x = x;
            this.count = count;
        }

        boolean canGoTo() {
            return inMatrix() && matrix[y][x] == GROUND;
        }

        boolean inMatrix() {
            return y > -1 & x > -1 && y < matrix.length && x < matrix[0].length;
        }

        public char get() {
            return matrix[y][x];
        }

        public void markFinish() {
            matrix[y][x] = STOP;
        }


        @Override
        public String toString() {
            return String.format("[%s:%s]", y, x);
        }

    }

}
