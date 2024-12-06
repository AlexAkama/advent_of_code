package advent_of_code.util;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdventUtils {

    private AdventUtils() {
    }

    public static List<String> readAllFromFile(String filename) throws IOException {
        return Files.readAllLines(Path.of(filename));
    }

    public static void saveMatrix(char[][] chars) {
        try (FileWriter writer = new FileWriter("matrix.txt")) {
            for (char[] line : chars) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        LEFT(0, -1, '←', '>'),
        RIGHT(0, 1, '→', '<');

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

        public int dy() {
            return dy;
        }

        public int dx() {
            return dx;
        }

        public char arrow() {
            return arrow;
        }


    }

}
