package advent_of_code.event_2023.day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader2 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day19/test.txt";

    public static void main(String[] args) throws IOException {
        init();
    }

    static void init() throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (!line.isEmpty()) {
                int index = line.indexOf('{');
                String name = line.substring(0, index);
                String[] split = line.substring(index + 1, line.length() - 1).split(",");
                line = reader.readLine();
                String[] conditionSplit = split[0].split(":");
                char target = conditionSplit[0].charAt(0);
                char op = conditionSplit[0].charAt(1);
                int arg = Integer.parseInt(conditionSplit[0].substring(2));
                String res = conditionSplit[1];
                System.out.printf("%s %s %s : %s%n", target, op, arg, res);
            }
            line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
            }
        }
    }


    enum Operation {
        MORE('>'),
        LESS('<');

        private final char c;

        Operation(char c) {
            this.c = c;
        }

        static Operation getBy(char c) {
            return switch (c) {
                case '>' -> MORE;
                case '<' -> LESS;
                default -> throw new IllegalArgumentException("Illegal char: " + c);
            };
        }

    }

}
