package advent_of_code.event_2023.day18;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static advent_of_code.util.AdventUtils.Direction;

/*
    Расчет периметра многоугольника по формуле Гауса:

    // Массив с координатами вершин многоугольника
    double[][] polygon = {{0, 0}, {0, 4}, {4, 4}, {4, 0}};

    public static double calculatePolygonArea(double[][] polygon) {
        int n = polygon.length;
        double area = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += polygon[i][0] * polygon[j][1];
            area -= polygon[i][1] * polygon[j][0];
        }
        area /= 2.0;
        return Math.abs(area);
    }

 */
public class Loader2 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day18/data.txt";

    private static final List<Point> points = new ArrayList<>();
    private static BigDecimal perimeter;

    public static void main(String[] args) throws IOException {
        init();

        BigDecimal count = count();

        System.out.println(count.add(perimeter.divide(BigDecimal.valueOf(2), RoundingMode.HALF_DOWN)).add(BigDecimal.ONE));

    }

    private static BigDecimal count() {
        int n = points.size();
        BigDecimal area = BigDecimal.ZERO;

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area = area.add(BigDecimal.valueOf(points.get(i).y).multiply(BigDecimal.valueOf(points.get(j).x)));
            area = area.subtract(BigDecimal.valueOf(points.get(i).x).multiply(BigDecimal.valueOf(points.get(j).y)));
        }
        area = area.divide(BigDecimal.valueOf(2), RoundingMode.HALF_DOWN);

        return area.abs();
    }

    private static void init() throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            Point prev = new Point(0, 0);
            String line = reader.readLine();
            perimeter = BigDecimal.ZERO;
            while (line != null) {
                String[] split = line.split("\\s+");
                String color = split[2].substring(2, split[2].length() - 1);
                Direction d = Direction.getBy(color.charAt(color.length() - 1));
                int v = HexToDecimalConverter.convert(color.substring(0, color.length() - 1));
                perimeter = perimeter.add(new BigDecimal(v));
                Point next = new Point(prev.y + v * d.dy(), prev.x + v * d.dx());
                points.add(next);
                prev = next;
                line = reader.readLine();
            }
        }
    }

    private static class Point {

        int y;
        int x;


        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return String.format("[%s:%s]", y, x);
        }

    }

}
