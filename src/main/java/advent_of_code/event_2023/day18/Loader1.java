package advent_of_code.event_2023.day18;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static advent_of_code.util.AdventUtils.*;

/*
 * Вариант в лоб: с рисованием и прямым пересчетом
 */
public class Loader1 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day18/data.txt";

    private static final char GROUND = '.';
    private static final char PIT = '#';

    private static int maxY;
    private static int maxX;

    private static Point start;

    private static char[][] matrix;

    public static void main(String[] args) throws IOException {
        init();

        saveMatrix(matrix);

        fill(new Point(start.y + 1, start.x + 1));

        int sum = count();

        System.out.println(sum);

    }

    private static int count() {
        int count = 0;
        Point p = new Point(0, 0);
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                p.y = y;
                p.x = x;
                if (p.inMatrix() && p.isPit()) count++;
            }
        }
        return count;
    }

    private static void fill(Point start) {
        Deque<Point> deque = new ArrayDeque<>();
        deque.add(start);
        while (!deque.isEmpty()) {
            Point p = deque.pop();
            for (Direction d : Direction.values()) {
                Point next = p.getNext(d);
                if (next.inMatrix() && next.isNotPit()) {
                    deque.add(next);
                    next.mark();
                }
            }
        }
    }

    private static void init() throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            int minPreY = 0;
            int minPreX = 0;
            int maxPreY = 0;
            int maxPreX = 0;
            int y = 0;
            int x = 0;
            List<Command> commands = new ArrayList<>();
            while (line != null) {
                String[] split = line.split("\\s+");
                Direction d = Direction.getBy(split[0]);
                int v = Integer.parseInt(split[1]);
                switch (d) {
                    case UP -> {
                        y -= v;
                        minPreY = Math.min(minPreY, y);
                    }
                    case DOWN -> {
                        y += v;
                        maxPreY = Math.max(maxPreY, y);
                    }
                    case LEFT -> {
                        x -= v;
                        minPreX = Math.min(minPreX, x);
                    }
                    case RIGHT -> {
                        x += v;
                        maxPreX = Math.max(maxPreX, x);
                    }
                }
                String color = split[2].substring(1, split[2].length() - 1);
                commands.add(new Command(d, v, color));
                line = reader.readLine();
            }
            maxY = maxPreY - minPreY + 1;
            maxX = maxPreX - minPreX + 1;

            matrix = new char[maxY][];
            for (int i = 0; i < matrix.length; i++) {
                char[] chars = new char[maxX];
                Arrays.fill(chars, GROUND);
                matrix[i] = chars;
            }
            start = new Point(-minPreY, -minPreX);
            Point p = new Point(-minPreY, -minPreX);
            p.mark();
            for (Command cmd : commands) {
                for (int i = 0; i < cmd.v; i++) {
                    p = p.getNext(cmd.d);
                    p.mark();
                }
            }

        }
    }


    private static class Point {

        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        void mark() {
            matrix[y][x] = PIT;
        }

        boolean inMatrix() {
            return y > -1 && x > -1 && y < maxY && x < maxX;
        }

        boolean isNotPit() {
            return !isPit();
        }

        boolean isPit() {
            return matrix[y][x] == PIT;
        }


        Point getNext(Direction d) {
            return new Point(y + d.dy(), x + d.dx());
        }

        @Override
        public String toString() {
            return String.format("[%s:%s]", y, x);
        }

    }

    private static class Command {

        Direction d;
        int v;
        String color;

        public Command(Direction d, int v, String color) {
            this.d = d;
            this.v = v;
            this.color = color;
        }

    }

}
