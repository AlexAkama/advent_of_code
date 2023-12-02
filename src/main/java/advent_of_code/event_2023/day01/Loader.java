package advent_of_code.event_2023.day01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day01/data.txt";

    private static final Map<String, Character> DIGIT = Map.of(
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
    );

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        long sum = 0;
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {
                sum += parseToTwinsInt(line);
                line = reader.readLine();
            }
        }
        System.out.println(sum);
    }

    public static int parseToTwinsInt(String s) {
        char first = findFirstDigit(s);
        char last = findLastDigit(s);
        return Integer.parseInt("" + first + last);
    }

    private static char findLastDigit(String s) {
        TextDigit textDigit = findLastTextDigit(s);
        int pos = s.length() - 1;
        int limit = textDigit == null ? -1 : textDigit.pos;
        while (pos > limit) {
            char c = s.charAt(pos--);
            if (Character.isDigit(c)) return c;
        }
        return textDigit == null ? '0' : textDigit.c;
    }

    private static TextDigit findLastTextDigit(String s) {
        int maxIndex = -1;
        String maxKey = null;
        for (String key : DIGIT.keySet()) {
            int index = s.lastIndexOf(key);
            if (index != -1 && index > maxIndex) {
                maxIndex = index;
                maxKey = key;
            }
        }
        return maxKey == null ? null : new TextDigit(maxIndex, DIGIT.get(maxKey));
    }

    private static char findFirstDigit(String s) {
        TextDigit textDigit = findFirstTextDigit(s);
        int pos = 0;
        int limit = textDigit == null ? s.length() : textDigit.pos;
        while (pos < limit) {
            char c = s.charAt(pos++);
            if (Character.isDigit(c)) return c;
        }
        return textDigit == null ? '0' : textDigit.c;
    }

    private static TextDigit findFirstTextDigit(String s) {
        int minIndex = s.length();
        String minKey = null;
        for (String key : DIGIT.keySet()) {
            int index = s.indexOf(key);
            if (index != -1 && index < minIndex) {
                minIndex = index;
                minKey = key;
            }
        }
        return minKey == null ? null : new TextDigit(minIndex, DIGIT.get(minKey));
    }

    private static class TextDigit {

        int pos;
        char c;

        public TextDigit(int pos, char c) {
            this.pos = pos;
            this.c = c;
        }

    }


}
