package advent_of_code.util;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class AdventUtils {

    private AdventUtils() {
    }

    public static List<String> readAllFromFile(String... args) {
        String year = "2025";
        String file = "src/main/java/advent_of_code/event_" + year + "/day" + args[0] + "/" + args[1];
        return readAllFromFile(file);
    }

    public static List<String> readAllFromFile(String filename) {
        try {
            return Files.readAllLines(Path.of(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] parseStringToInts(String s) {
        String[] split = s.split("\\s+");
        int n = split.length;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        return nums;
    }

    public static void saveMatrix(char[][] chars) {
        try (FileWriter writer = new FileWriter("chars.txt")) {
            for (char[] line : chars) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMatrix(int[][] ints) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < ints.length; y++) {
            for (int x = 0; x < ints[y].length; x++) {
                sb.append(String.format("%03d", ints[y][x])).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        try (FileWriter writer = new FileWriter("matrix.txt")) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> boolean inMatrix(int y, int x, T[][] matrix) {
        return y >= 0 && y < matrix.length && x >= 0 && x < matrix[y].length;
    }

    public static boolean inMatrix(int y, int x, char[][] matrix) {
        return y >= 0 && y < matrix.length && x >= 0 && x < matrix[y].length;
    }

    public static boolean inMatrix(int y, int x, int[][] matrix) {
        return y >= 0 && y < matrix.length && x >= 0 && x < matrix[y].length;
    }

    public static class Point {

        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int y() {
            return y;
        }

        public int x() {
            return x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void move(Direction d) {
            y += d.dy;
            x += d.dx;
        }

        @Override
        public Point clone() {
            return new Point(y, x);
        }

        @Override
        public String toString() {
            return String.format("[%s,%s]", y, x);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return y == point.y && x == point.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }

    }

    public static class DirectionPair {

        public AdventUtils.Direction d1;
        public AdventUtils.Direction d2;

        public DirectionPair(AdventUtils.Direction d1, AdventUtils.Direction d2) {
            this.d1 = d1;
            this.d2 = d2;
        }

    }

    public enum Direction {
        UP(-1, 0, '↑', '^'),
        DOWN(1, 0, '↓', 'v'),
        LEFT(0, -1, '←', '<'),
        RIGHT(0, 1, '→', '>');

        final int dy;
        final int dx;

        final char arrow;
        final char c;

        Direction(int dy, int dx, char arrow, char c) {
            this.dy = dy;
            this.dx = dx;
            this.arrow = arrow;
            this.c = c;
        }

        public static Direction getBy(char c) {
            return switch (c) {
                case 'U', '3', '^' -> UP;
                case 'D', '1', 'v' -> DOWN;
                case 'L', '2', '<' -> LEFT;
                case 'R', '0', '>' -> RIGHT;
                default -> throw new IllegalArgumentException("Illegal char: " + c);
            };
        }

        public static Direction getBy(String s) {
            return switch (s) {
                case "U" -> UP;
                case "D" -> DOWN;
                case "L" -> LEFT;
                case "R" -> RIGHT;
                default -> throw new IllegalArgumentException("Illegal string: " + s);
            };
        }

        @Override
        public String toString() {
            return String.format("%s(%d,%d)", this.name(), dy, dx);
        }

        public int dy() {
            return dy;
        }

        public int dx() {
            return dx;
        }

        public char arrow() {
            return arrow;
        }

        public char charArrow() {
            return c;
        }

    }


}
