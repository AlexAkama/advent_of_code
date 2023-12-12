package advent_of_code.event_2023.day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Loader1 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day11/test.txt";

    private static final char EMPTY = '.';
    private static final char PLANET = '#';

    private static char[][] matrix;
    private static List<Point> planets;

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        List<char[]> preMap = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            boolean[] expansion = new boolean[line.length()];
            Arrays.fill(expansion, true);
            while (line != null) {
                char[] chars = line.toCharArray();
                preMap.add(chars);
                if (analyze(chars, expansion)) preMap.add(chars);
                line = reader.readLine();
            }
            doExpansion(preMap, expansion);
            findPlanet();

            int[][] pathMatrix = new int[planets.size()][planets.size()];
            for (int i = 0; i < planets.size(); i++) {
                for (int j = i + 1; j < planets.size(); j++) {
                    if (pathMatrix[i][j] == 0) {
                        int path = findShortestPath(planets.get(i), planets.get(j));
                        pathMatrix[i][j] = path;
                        pathMatrix[j][i] = path;
                    }
                }
            }
            int sum = 0;
            for (int i = 0; i < planets.size(); i++) {
                for (int j = i + 1; j < planets.size(); j++) {
                    sum += pathMatrix[j][i];
                }
            }
            System.out.println(sum);
        }
    }

    private static int findShortestPath(Point start, Point target) {
        int[][] path = new int[matrix.length][matrix[0].length];
        Deque<Point> deque = new ArrayDeque<>();
        deque.add(start);
        path[start.y][start.x] = -1;
        while (!deque.isEmpty()) {
            Point p = deque.pop();
            for (Vector v : Vector.values()) {
                Point next = v.getNext(p);
                next.d = p.d + 1;
                if (next.inMatrix() && path[next.y][next.x] == 0) {
                    if (next.y == target.y && next.x == target.x) return next.d;
                    path[next.y][next.x] = next.d;
                    deque.add(next);
                }
            }
        }
        return -1;
    }

    private static void findPlanet() {
        planets = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == PLANET) planets.add(new Point(y, x));
            }
        }
    }

    private static void doExpansion(List<char[]> preMap, boolean[] expansion) {
        int d = countToExpansion(expansion);
        matrix = new char[preMap.size()][expansion.length + d];
        for (int y = 0; y < matrix.length; y++) {
            int offset = 0;
            char[] prevChars = preMap.get(y);
            char[] chars = new char[matrix[0].length];
            Arrays.fill(chars, EMPTY);
            boolean ready = false;
            for (int x = 0; x < matrix[0].length; x++) {
                int prevX = x + offset;
                if (prevChars[prevX] == PLANET) chars[x] = PLANET;
                if (expansion[prevX] && !ready) {
                    offset--;
                    ready = true;
                } else {
                    ready = false;
                }
            }
            matrix[y] = chars;
        }
    }

    private static int countToExpansion(boolean[] expansion) {
        int count = 0;
        for (boolean b : expansion) {
            if (b) count++;
        }
        return count;
    }

    private static boolean analyze(char[] chars, boolean[] expansion) {
        boolean isEmpty = true;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c != EMPTY) {
                isEmpty = false;
                expansion[i] = false;
            }
        }
        return isEmpty;
    }

    private static class Point {

        int y;
        int x;
        int d;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        private boolean inMatrix() {
            return y > -1 && x > -1 && y < matrix.length && x < matrix[0].length;
        }

    }

    private enum Vector {
        UP(new Point(-1, 0)),
        RIGHT(new Point(0, 1)),
        DOWN(new Point(1, 0)),
        LEFT(new Point(0, -1));

        private final Point d;

        Vector(Point d) {
            this.d = d;
        }

        Point getNext(Point p) {
            return new Point(p.y + this.d.y, p.x + this.d.x);
        }
    }

}
