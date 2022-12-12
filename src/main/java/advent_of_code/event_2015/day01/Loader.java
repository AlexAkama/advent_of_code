package advent_of_code.event_2015.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/java/advent_of_code/event_2015/day1/data.txt";
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        String line;
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            line = reader.readLine();
        }
        char[] chars = line.toCharArray();
        int floor = 0;
        int basementPos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') floor++;
            if (chars[i] == ')') floor--;
            if (floor < 0) {
                basementPos = i + 1;
                break;
            }
        }
        System.out.println(basementPos);
    }

}
