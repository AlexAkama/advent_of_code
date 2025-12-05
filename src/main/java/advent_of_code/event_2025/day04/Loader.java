package advent_of_code.event_2025.day04;

import advent_of_code.util.AdventUtils;

import java.util.List;

import static advent_of_code.util.AdventUtils.inMatrix;

class LoaderTest {
    public static void main(String[] args) {
        Loader.main("04", "data_test.txt", "TEST");
    }
}

class LoaderData {
    public static void main(String[] args) {
        Loader.main("04", "data.txt", "DATA");
    }
}

public class Loader {

    private static final char EMPTY = '.';

    private static final int[][] DIRECTION = new int[][]{
            new int[]{-1, 0},
            new int[]{-1, 1},
            new int[]{0, 1},
            new int[]{1, 1},
            new int[]{1, 0},
            new int[]{1, -1},
            new int[]{0, -1},
            new int[]{-1, -1},
    };

    public static void main(String... args) {

        String file = "src/main/java/advent_of_code/event_2025/day" + args[0] + "/" + args[1];
        List<String> strings = AdventUtils.readAllFromFile(file);

        int n = strings.get(0).length();
        int m = strings.size();

        char[][] map = new char[m][n];
        for (int i = 0; i < strings.size(); i++) {
            map[i] = strings.get(i).toCharArray();
        }

        int total = 0;
        int toDelete = findAndMark(map);
        System.out.println(toDelete);

        while (toDelete > 0) {
            total +=toDelete;
            delete(map);
            toDelete = findAndMark(map);
        }

        System.out.println(total);

    }

    private static int findAndMark(char[][] map) {
        int m = map.length;
        int n = map[0].length;
        int total = 0;
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                char c0 = map[y][x];
                if (c0 == EMPTY) continue;
                int count = 0;
                for (int[] d : DIRECTION) {
                    int nextY = y + d[0];
                    int nextX = x + d[1];
                    if (!inMatrix(nextY, nextX, map)) continue;
                    char c = map[nextY][nextX];
                    if (c != EMPTY) count++;
                }
                if (count < 4) {
                    total++;
                    map[y][x] = 'x';
                }
            }
        }
        return total;
    }

    private static void delete(char[][] map) {
        int m = map.length;
        int n = map[0].length;
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                char c = map[y][x];
                if (c == 'x') map[y][x] = EMPTY;
            }
        }
    }

}
