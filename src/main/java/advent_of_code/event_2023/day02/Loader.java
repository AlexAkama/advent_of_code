package advent_of_code.event_2023.day02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day02/data.txt";
    private static final int N = 100;

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        String line;
        Round max = new Round();
        max.blue = 14;
        max.green = 13;
        max.red = 12;
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            int sum = 0;
            for (int i = 0; i < N; i++) {
                line = reader.readLine();
                Game game = parseGame(line);
                // Part #1
                // if (game.isValid(max)) {
                //     sum += game.num;
                //     System.out.println(game.num);
                // }
                // Part #2
                sum += game.getPower();
            }
            System.out.println(sum);
        }
    }

    private static Game parseGame(String s) {
        int pos = s.indexOf(":");
        int num = Integer.parseInt(s, 5, pos, 10);

        List<Round> roundList = new ArrayList<>();
        String roundsLine = s.substring(pos + 2);
        String[] rounds = roundsLine.split("; ");
        for (String roundLine : rounds) {
            Round round = new Round();
            String[] cubesLine = roundLine.split(", ");
            for (String cubLine : cubesLine) {
                String[] cub = cubLine.split(" ", 2);
                int v = Integer.parseInt(cub[0]);
                switch (cub[1]) {
                    case "blue" -> round.blue = v;
                    case "green" -> round.green = v;
                    case "red" -> round.red = v;
                    default -> throw new IllegalStateException("Unexpected value: " + s);
                }
            }
            roundList.add(round);
        }
        return new Game(num, roundList);
    }

    private static class Game {

        int num;
        List<Round> rounds;

        public Game(int num, List<Round> rounds) {
            this.num = num;
            this.rounds = rounds;
        }

        public boolean isValid(Round max) {
            for (Round round : rounds) {
                if (round.blue > max.blue ||
                        round.green > max.green ||
                        round.red > max.red) return false;
            }
            return true;
        }

        public int getPower() {
            int maxBlue = 0;
            int maxGreen = 0;
            int maxRed = 0;
            for (Round round : rounds) {
                if (round.blue > maxBlue) maxBlue = round.blue;
                if (round.green > maxGreen) maxGreen = round.green;
                if (round.red > maxRed) maxRed = round.red;
            }
            return maxBlue * maxGreen * maxRed;
        }

    }

    private static class Round {

        int blue;
        int green;
        int red;

    }

}
