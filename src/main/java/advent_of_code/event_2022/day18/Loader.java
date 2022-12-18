package advent_of_code.event_2022.day18;

import advent_of_code.event_2022.Point3D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static advent_of_code.event_2022.InputName.INPUT;
import static advent_of_code.event_2022.InputName.TEST;

public class Loader {

    private static final List<Point3D> PARSE_DATA = new ArrayList<>();

    private static final int OFFSET = 1;

    private static final List<Point3D> DELTAS = List.of(
            new Point3D(-1, 0, 0),
            new Point3D(1, 0, 0),
            new Point3D(0, -1, 0),
            new Point3D(0, 1, 0),
            new Point3D(0, 0, -1),
            new Point3D(0, 0, 1)
    );

    private static int maxX = 0;
    private static int maxY = 0;
    private static int maxZ = 0;

    private static boolean[][][] existMap;
    private static boolean[][][] visitedMap;

    public static void main(String[] args) throws IOException {
        parse(INPUT);

        int i = calculate();
        System.out.println(i);

        i = calculateOutSide();
        System.out.println(i);
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day18/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                int z = Integer.parseInt(split[2]);
                Point3D point3D = new Point3D(x + OFFSET, y + OFFSET, z + OFFSET);
                PARSE_DATA.add(point3D);
                fixMaxParams(point3D);
                line = reader.readLine();
            }
            calculate();
        }
    }

    private static void fixMaxParams(Point3D point3D) {
        maxX = Math.max(maxX, point3D.x);
        maxY = Math.max(maxY, point3D.y);
        maxZ = Math.max(maxZ, point3D.z);
    }

    private static int calculate() {
        existMap = new boolean[maxZ + 1 + OFFSET * 2][maxY + OFFSET * 2][maxX + 1 + OFFSET * 2];
        int counter = 0;
        for (Point3D point3D : PARSE_DATA) {
            existMap[point3D.z][point3D.y][point3D.x] = true;
            counter += 6;
            if (point3D.z > 0 && existMap[point3D.z - 1][point3D.y][point3D.x]) counter -= 2;
            if (existMap[point3D.z + 1][point3D.y][point3D.x]) counter -= 2;
            if (point3D.y > 0 && existMap[point3D.z][point3D.y - 1][point3D.x]) counter -= 2;
            if (existMap[point3D.z][point3D.y + 1][point3D.x]) counter -= 2;
            if (point3D.x > 0 && existMap[point3D.z][point3D.y][point3D.x - 1]) counter -= 2;
            if (existMap[point3D.z][point3D.y][point3D.x + 1]) counter -= 2;
        }
        return counter;
    }

    private static int calculateOutSide() {
        visitedMap = new boolean[maxZ + 1 + OFFSET * 2][maxY + OFFSET * 2][maxX + 1 + OFFSET * 2];
        int counter = 0;
        var start = new Air(0, 0, 0);
        Deque<Air> deque = new ArrayDeque<>();
        deque.add(start);
        while (!deque.isEmpty()) {
            Air current = deque.pop();
            for (Point3D d : DELTAS) {
                Air next = new Air(current.x + d.x, current.y + d.y, current.z + d.z);
                if (!next.isOut() && !next.isVisited()) {
                    if (next.exist()) {
                        counter++;
                        continue;
                    }
                    deque.add(next);
                    next.markVisited();
                }
            }
        }
        return counter;
    }

    static class Air extends Point3D {

        public Air(int x, int y, int z) {
            super(x, y, z);
        }

        public void markVisited() {
            visitedMap[z][y][x] = true;
        }

        public boolean isOut() {
            return x < 0 || y < 0 || z < 0
                    || x == visitedMap[0][0].length
                    || y == visitedMap[0].length
                    || z == visitedMap.length;
        }

        public boolean exist() {
            return existMap[z][y][x];
        }

        public boolean isVisited() {
            return visitedMap[z][y][x];
        }

    }

}
