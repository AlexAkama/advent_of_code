package advent_of_code.event_2024.day14;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day14/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day14/test.txt";

    private static final int TIME = 100;
    private static List<Robot> robots;
    private static int[][] grid;
    private static int n;
    private static int m;

    public static void main(String[] args) {
        init(1);
        System.out.println(part2());
    }

    private static BigInteger part1() {
        moveRobots(TIME);
        BigInteger total = BigInteger.ONE;
        for (int y = 0; y < n; y += n / 2 + 1) {
            for (int x = 0; x < m; x += m / 2 + 1) {
                int i = calculateBlock(new Point(y, x));
                total = total.multiply(BigInteger.valueOf(i));
            }
        }
        return total;
    }

    private static int part2() {
        int count = 0;
        while (!printGrid()) {
            moveRobots(1);
            count++;
        }
        return count;
    }

    private static boolean printGrid() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                sb.append(grid[y][x] == 0 ? ' ' : 'X');
            }
            sb.append(System.lineSeparator());
        }
        String s = sb.toString();
        boolean b = s.contains("XXXXXXXX");
        if (b) {
            System.out.println(sb);
        }
        return b;
    }

    private static void moveRobots(int time) {
        for (Robot robot : robots) {
            grid[robot.pos.y()][robot.pos.x()]--;
            robot.move(time);
            grid[robot.pos.y()][robot.pos.x()]++;
        }
    }

    private static void init(Object... sw) {
        try {
            String filename;
            if (sw.length == 0) {
                filename = TEST;
                n = 7;
                m = 11;
            } else {
                filename = DATA;
                n = 103;
                m = 101;
            }
            grid = new int[n][m];
            List<String> strings = Files.readAllLines(Path.of(filename));
            robots = new ArrayList<>(strings.size());
            for (String string : strings) {
                String[] split = string.split("\\s+");
                Robot robot = new Robot(parsePoint(split[0]), parsePoint(split[1]));
                robots.add(robot);
                grid[robot.pos.y()][robot.pos.x()]++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int calculateBlock(Point start) {
        int sum = 0;
        for (int y = start.y(); y < start.y() + n / 2; y++) {
            for (int x = start.x(); x < start.x() + m / 2; x++) {
                sum += grid[y][x];
            }
        }
        return sum == 0 ? 1 : sum;
    }

    private static Point parsePoint(String s) {
        String[] split = s.split(",");
        return new Point(Integer.parseInt(split[1]), Integer.parseInt(split[0].substring(2)));
    }

    static class Robot {

        Point pos;
        Point vector;

        public Robot(Point pos, Point vector) {
            this.pos = pos;
            this.vector = vector;
        }

        public void move(int time) {
            int nextY = (pos.y() + vector.y() * time) % n;
            int nextX = (pos.x() + vector.x() * time) % m;
            pos.setY(nextY < 0 ? n + nextY : nextY);
            pos.setX(nextX < 0 ? m + nextX : nextX);
        }

    }

}
