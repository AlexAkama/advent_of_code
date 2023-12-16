package advent_of_code.event_2023.day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader1 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day15/data.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            String[] split = line.split(",");
            int sum = 0;
            for (String s : split) {
                sum += getHash(s);
            }
            System.out.println(sum);
            //505379
        }

    }

    private static int getHash(String s) {
        char[] chars = s.toCharArray();
        int hash = 0;
        for (char c : chars) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }

}
