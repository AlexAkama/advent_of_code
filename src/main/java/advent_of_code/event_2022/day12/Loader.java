package advent_of_code.event_2022.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Loader {

    private static final Point[] DELTAS = new Point[]
            {
                    new Point(0, -1),
                    new Point(+1, 0),
                    new Point(0, +1),
                    new Point(-1, 0)
            };
    private static char[][] grid;
    private static boolean[][] visited;
    private static int[][] distance;

    private static int length;
    private static int width;

    private static int startX;
    private static int startY;
    private static int endX;
    private static int endY;

    private static int min = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        parse("data.txt");

        createHelpers();
        Point start = new Point(startX, startY);
        find(start);
        int d = distance[endY][endX];
        System.out.println("RES1: " + d);

        for (int i = 0; i < length; i++) findFrom(i, 0);
        for (int i = 0; i < length; i++) findFrom(i, width - 1);
        for (int i = 0; i < width; i++) findFrom(0, i);
        for (int i = 0; i < width; i++) findFrom(length - 1, i);
        System.out.println("RES2: " + min);
    }

    private static void createHelpers() {
        distance = new int[width][length];
        for (int i = 0; i < width; i++) {
            Arrays.fill(distance[i], -1);
        }
        visited = new boolean[width][length];
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day12/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            length = line.length();
            width = 0;
            List<char[]> buffer = new ArrayList<>();
            startX = 0;
            startY = 0;
            while (line != null) {
                width++;
                char[] chars = new char[length];
                for (int i = 0; i < length; i++) {
                    char c = line.charAt(i);
                    if (c == 'S') {
                        startX = i;
                        startY = width - 1;
                        chars[i] = 'a';
                    } else if (c == 'E') {
                        chars[i] = 'z';
                        endX = i;
                        endY = width - 1;
                    } else {
                        chars[i] = c;
                    }
                }
                buffer.add(chars);
                line = reader.readLine();
            }
            grid = new char[width][length];
            for (int i = 0; i < buffer.size(); i++) {
                grid[i] = buffer.get(i);
            }
        }
    }

    private static void find(Point point) {
        Deque<Point> deque = new ArrayDeque<>();
        distance[point.y][point.x] = 0;
        deque.add(point);
        point.markVisited();
        while (!deque.isEmpty()) {
            Point current = deque.pop();
            for (Point delta : DELTAS) {
                Point next = new Point(current.x + delta.x, current.y + delta.y);
                if (!next.isOutOfGrid() && !next.isVisited() && !next.isNotCanGo(current)) {
                    next.setDistance(current.getDistance() + 1);
                    next.markVisited();
                    deque.add(next);
                }
            }
        }
    }

    private static void findFrom(int x, int y) {
        char c = grid[y][x];
        if (c != 'a') return;
        Point point = new Point(x, y);
        createHelpers();
        find(point);
        int d = distance[endY][endX];
        if (d != -1) min = Math.min(d, min);
    }

    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isOutOfGrid() {
            return x < 0 || y < 0 || x > length - 1 || y > width - 1;
        }

        public boolean isNotCanGo(Point from) {
            char next = grid[y][x];
            char cur = grid[from.y][from.x];
            return next - cur > 1;
        }

        public void markVisited() {
            if (isOutOfGrid()) return;
            visited[y][x] = true;
        }

        public boolean isVisited() {
            return visited[y][x];
        }

        public void setDistance(int i) {
            distance[y][x] = i;
        }

        public int getDistance() {
            return distance[y][x];
        }

    }


}
