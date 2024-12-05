package advent_of_code.event_2024.day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day03/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day03/test1.txt";
    private static final String data[] = new String[6];

    private static String input;

    public static void main(String[] args) throws IOException {
        int sw = 1;
        input = sw == 0 ? TEST : DATA;
        System.out.println(part2());
    }

    private static long part1() throws IOException {
        init();
        long sum = 0;
        for (int i = 0; i < 6; i++) {
            String[] split = data[i].split("mul\\(");
            for (String s : split) {
                int index = s.indexOf(")");
                if (index == -1) continue;
                String ds = s.substring(0, index);
                String[] splitDs = ds.split(",");
                try {
                    int m1 = Integer.parseInt(splitDs[0]);
                    int m2 = Integer.parseInt(splitDs[1]);
                    sum += m1 * m2;
                } catch (Exception e) {
                    System.out.print(".");
                }
            }
        }
        System.out.println();
        return sum;
    }

    private static long part2() throws IOException {
        init();
        long sum = 0;
        boolean available = true;
        for (int i = 0; i < 6; i++) {
            if (data[i] == null) continue;
            String[] split = data[i].split("mul\\(");
            for (String s : split) {
                if (available) {
                    int index = s.indexOf(")");
                    if (index == -1) continue;
                    String ds = s.substring(0, index);
                    String[] splitDs = ds.split(",");
                    try {
                        int m1 = Integer.parseInt(splitDs[0]);
                        int m2 = Integer.parseInt(splitDs[1]);
                        sum += m1 * m2;
                    } catch (Exception e) {
                        System.out.print(".");
                    }
                }
                int dont = s.indexOf("don't()");
                int doo = s.indexOf("do()");
                if (dont != -1) available = false;
                if (doo != -1) available = true;
            }
        }
        System.out.println();
        return sum;
    }

    private static void init() throws IOException {
        File file = new File(input);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            for (int i = 0; i < 6; i++) {
                data[i] = reader.readLine();
            }

        }
    }

}
