package advent_of_code.event_2023.day08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day08/data.txt";

    private static final String START_NAME = "AAA";
    private static final String FINISH_MANE = "ZZZ";
    private static final Map<String, Point> MAP = new HashMap<>();
    private static final List<Point> START_POINTS = new ArrayList<>();

    private static char[] commands;
    private static int pos = 0;

    static {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        long count = 0;
        int finished = 0;
        Point[] curs = START_POINTS.toArray(new Point[0]);
        long[] control = new long[curs.length];
        while (finished != curs.length) {
            Character command = getNextCommand();
            count++;
            for (int i = 0; i < curs.length; i++) {
                Point cur = curs[i];
                cur = cur.getByCommand(command);
                curs[i] = cur;
                if (cur.lastChar == 'Z') {
                    control[i] = count;
                    finished++;
                }
            }
        }
        long lcm = LCMCalculator.findLCM(control);
        System.out.println(lcm);
    }

    public static void mainPart1(String[] args) {
        Point cur = MAP.get(START_NAME);
        Point finish = MAP.get(FINISH_MANE);
        int count = 0;
        while (cur != finish) {
            Character command = getNextCommand();
            cur = cur.getByCommand(command);
            count++;
        }
        System.out.println(count);
    }

    private static boolean equals(char[] chars1, char[] chars2) {
        if (chars1.length != chars2.length) return false;
        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] != chars2[i]) return false;
        }
        return true;
    }

    private static Character getNextCommand() {
        char c = commands[pos];
        pos = (pos + 1) % commands.length;
        return c;
    }

    private static void init() throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            commands = line.toCharArray();
            reader.readLine();
            line = reader.readLine();
            while (line != null) {
                String[] split = line.split("\\s+=\\s+");
                String name = split[0];
                Character lastChar = split[0].charAt(split[0].length() - 1);
                String dataLine = split[1].substring(1, split[1].length() - 1);
                Point point = new Point(name, lastChar, dataLine);
                MAP.put(name, point);
                if (point.lastChar == 'A') START_POINTS.add(point);
                line = reader.readLine();
            }
        }
        for (Point point : MAP.values()) {
            String rowData = point.rowData;
            String[] split = rowData.split(",\\s+");
            point.left = MAP.get(split[0]);
            point.right = MAP.get(split[1]);
        }
    }

    private static class Point {

        String name;
        Character lastChar;
        String rowData;
        Point left;
        Point right;

        public Point(String name, Character lastChar, String rowData) {
            this.name = name;
            this.lastChar = lastChar;
            this.rowData = rowData;
        }

        public Point getByCommand(Character c) {
            return switch (c) {
                case 'L' -> left;
                case 'R' -> right;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public String toString() {
            return name;
        }

    }


}
