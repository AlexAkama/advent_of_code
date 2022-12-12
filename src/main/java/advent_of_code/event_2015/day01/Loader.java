package advent_of_code.event_2015.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/java/advent_of_code/event_2015/day01/data.txt";
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        String line;
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            line = reader.readLine();
        }
        char[] chars = line.toCharArray();
        System.out.println(part2(chars));
    }

    private static int part1(char[] chars) {
        int res = 0;
        for (char aChar : chars) {
            if (aChar == '(') res++;
            if (aChar == ')') res--;
        }
        return res;
    }

    private static int part2(char[] chars) {
        int res = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') res++;
            if (chars[i] == ')') res--;
            if (res < 0) {
                res = i + 1;
                break;
            }
        }
        return res;
    }

}
