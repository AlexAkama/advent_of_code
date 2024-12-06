package advent_of_code.event_2023.day24;

import advent_of_code.util.AdventUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/*
https://uchet-jkh.ru/i/kak-naiti-peresecenie-dvux-pryamyx/
 */
public class Loader {

    private static final String TEST_INPUT = "src/main/java/advent_of_code/event_2023/day24/test.txt";
    private static final String DATA_INPUT = "src/main/java/advent_of_code/event_2023/day24/test.txt";
    private static List<Line> lines;

    private static Area area;

    private static final boolean test = true;

    public static void main(String[] args) throws IOException {
        init();

        boolean[] intersection = new boolean[lines.size()];
        int count = 0;
        int total = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                count++;
                Line one = lines.get(i);
                Line two = lines.get(j);
                Point cross = one.getCross(two);
                if (test) {
                    System.out.println(count);
                    System.out.println("A: " + one + (intersection[i] ? " - было пересечение" : ""));
                    System.out.println("B: " + two + (intersection[j] ? " - было пересечение" : ""));
                    System.out.println(cross);
                }
                if (cross != null) {
                    boolean inArea = cross.inArea();
                    if (test) System.out.println("inArea: " + inArea);
                    if (inArea) {
                        if (!intersection[i] && !intersection[j]) {
                            total++;
                        }
                        intersection[i] = true;
                        intersection[j] = true;
                    }
                }
                if (test) System.out.println();
            }
        }

        System.out.println(total);
    }

    static void init() throws IOException {
        String file = test ? TEST_INPUT : DATA_INPUT;
        List<String> input = AdventUtils.readAllFromFile(file);
        lines = new ArrayList<>();
        for (String s : input) {
            String[] lineSplit = s.split("\\s+@\\s+");
            String[] positionSplit = lineSplit[0].split(",\\s+");
            String[] directionSplit = lineSplit[1].split(",\\s+");
            Line line = new Line(
                    new BigInteger(positionSplit[0]),
                    new BigInteger(positionSplit[1]),
                    new BigInteger(positionSplit[2]),
                    new Line.LineDirection(
                            Integer.parseInt(directionSplit[0]),
                            Integer.parseInt(directionSplit[1]),
                            Integer.parseInt(directionSplit[2])
                    )
            );
            line.formula();
            lines.add(line);

            Area testArea = new Area(
                    BigDecimal.valueOf(7), BigDecimal.valueOf(7),
                    BigDecimal.valueOf(27), BigDecimal.valueOf(27)
            );
            Area dataArea = new Area(
                    new BigDecimal("200000000000000"), new BigDecimal("200000000000000"),
                    new BigDecimal("400000000000000"), new BigDecimal("400000000000000")
            );

            area = test ? testArea : dataArea;
        }
    }

    static class Area {

        BigDecimal minX;
        BigDecimal minY;
        BigDecimal maxX;
        BigDecimal maxY;

        public Area(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY) {
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
        }

    }

    static class Line {

        BigInteger x;
        BigInteger y;
        BigInteger z;
        LineDirection d;

        BigDecimal k1;
        BigDecimal k0;

        public Line(BigInteger x, BigInteger y, BigInteger z, LineDirection d) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.d = d;
        }

        void formula() {
            if (d.dx == 0) {
                k1 = BigDecimal.ZERO;
                k0 = new BigDecimal(x);
            } else {
                BigDecimal x1 = new BigDecimal(x);
                BigDecimal y1 = new BigDecimal(y);
                BigDecimal x2 = x1.add(BigDecimal.valueOf(d.dx));
                BigDecimal y2 = y1.add(BigDecimal.valueOf(d.dy));
                k1 = (y2.subtract(y1)
                        .divide(
                                x2.subtract(x1), 3, RoundingMode.HALF_DOWN)
                );
                k0 = y1.subtract(k1.multiply(x1));
            }
        }

        Point getCross(Line line) {
            if (k1.equals(line.k1)) return null;
            BigDecimal px = (line.k0.subtract(k0)).divide(k1.subtract(line.k1), 3, RoundingMode.HALF_DOWN);
            BigDecimal py = k1.multiply(px).add(k0);
            return new Point(px, py.setScale(3, RoundingMode.HALF_DOWN));
        }

        @Override
        public String toString() {
            return test
                    ? String.format("p:(%2s,%2s,%2s), v:%s, k1: %-6s k0: %6s", x, y, z, d, k1, k0)
                    : String.format("p:(%15s,%15s,%15s), v:%s, k1: %-6s k0: %6s", x, y, z, d, k1, k0);
        }

        private static class LineDirection {

            int dx;
            int dy;
            int dz;

            public LineDirection(int dx, int dy, int dz) {
                this.dx = dx;
                this.dy = dy;
                this.dz = dz;
            }

            @Override
            public String toString() {
                return test ? String.format("[%2s,%2s,%2s]", dx, dy, dz)
                        : String.format("[%4s,%4s,%4s]", dx, dy, dz);
            }

        }

    }

    static class Point {

        BigDecimal x;
        BigDecimal y;

        public Point(BigDecimal x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

        boolean inArea() {
            return x.compareTo(area.minX) >= 0 &&
                    y.compareTo(area.minY) >= 0 &&
                    x.compareTo(area.maxX) <= 0 &&
                    y.compareTo(area.maxY) <= 0;
        }

        @Override
        public String toString() {
            return String.format("[dx=%s, dy=%s]", x, y);
        }

    }

}
