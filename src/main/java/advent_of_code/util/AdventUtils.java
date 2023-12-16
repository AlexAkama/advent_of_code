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

    public enum Direction {
        UP(-1, 0, '↑'),
        DOWN(1, 0, '↓'),
        LEFT(0, -1, '←'),
        RIGHT(0, 1, '→');

        final int dy;
        final int dx;

        final char arrow;

        Direction(int dy, int dx, char arrow) {
            this.dy = dy;
            this.dx = dx;
            this.arrow = arrow;
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
