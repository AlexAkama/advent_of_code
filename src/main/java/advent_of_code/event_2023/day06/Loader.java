package advent_of_code.event_2023.day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day06/data.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            long time = parseLineToOne(line);
            line = reader.readLine();
            long distance = parseLineToOne(line);

            System.out.println(findVariant(time, distance));
        }
    }

    public static void mainPart1(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            int[] times = parseLine(line);
            line = reader.readLine();
            int[] distances = parseLine(line);

            long mul = 1;
            for (int i = 0; i < times.length; i++) {
                mul *= findVariant(times[i], distances[i]);
            }

            System.out.println(mul);
        }
    }

    private static int findVariant(int time, int distance) {
        int count = 0;
        int timeLeft = time - 1;
        int curSpeed = 1;
        while (timeLeft > 0) {
            int curDistance = timeLeft-- * curSpeed++;
            if (curDistance > distance) count++;
        }
        return count;
    }

    private static long findVariant(long time, long distance) {
        long count = 0;
        long timeLeft = time - 1;
        long curSpeed = 1;
        while (timeLeft > 0) {
            long curDistance = timeLeft-- * curSpeed++;
            if (curDistance > distance) count++;
        }
        return count;
    }

    private static int[] parseLine(String line) {
        String[] split = line.split(":\\s+");
        split = split[1].split("\\s+");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }
        return ints;
    }

    private static long parseLineToOne(String line) {
        String[] split = line.split(":\\s+");
        String s = split[1].replaceAll("\\s+", "");
        return Long.parseLong(s);
    }

}
