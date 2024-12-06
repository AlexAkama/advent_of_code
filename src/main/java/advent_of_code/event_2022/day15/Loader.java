package advent_of_code.event_2022.day15;

import advent_of_code.event_2022.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private static final List<ParsePair> DATA = new ArrayList<>();

    private static final int SEARCH_LINE = 10;

    public static void main(String[] args) throws IOException {
        parse("test.txt");

        calculate();

        System.out.println();
    }

    private static int calculate() {
        for (ParsePair d : DATA) {
            int dX = d.sensor.x - d.beacon.x;
            int dY = d.sensor.y - d.beacon.y;
            int r = (int) Math.sqrt(dX * dX + dY * dY);
        }
        return 0;
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day15/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(":");
                Point sensor = getPoint(split[0]);
                Point beacon = getPoint(split[1]);
                DATA.add(new ParsePair(sensor, beacon));
                line = reader.readLine();
            }
        }
    }

    private static Point getPoint(String s) {
        int x = Integer.parseInt(s.substring(s.indexOf("dx=") + 2, s.indexOf(',')));
        int y = Integer.parseInt(s.substring(s.indexOf("dy=") + 2));
        return new Point(x, y);
    }

    static class ParsePair {

        private final Point sensor;
        private final Point beacon;

        public ParsePair(Point sensor, Point beacon) {
            this.sensor = sensor;
            this.beacon = beacon;
        }

    }

}
