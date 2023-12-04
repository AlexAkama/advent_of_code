package advent_of_code.event_2023.day04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day04/data.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            List<String> list = new ArrayList<>();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }
            int[] count = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                count[i]++;
                int n = getWinCount(s);
                for (int j = 1; j < n + 1 && i + j < list.size(); j++) {
                    count[i + j] += count[i];
                }
            }
            int sum = 0;
            for (int i : count) {
                sum += i;
            }
            System.out.println(sum);
        }
    }

    private static int getWinCount(String line) {
        String[] split1 = line.split(":\\s+", 2);
        String[] split2 = split1[1].split("\\s+\\|\\s+");
        String[] winNums = split2[0].split("\\s+");
        String[] cardNums = split2[1].split("\\s+");

        Set<Integer> winSet = new HashSet<>();
        for (String winNum : winNums) {
            winSet.add(Integer.parseInt(winNum));
        }
        int count = 0;
        for (String cardNum : cardNums) {
            if (winSet.contains(Integer.parseInt(cardNum))) count++;
        }
        return count;
    }

    public static void mainPart1(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            int sum = 0;
            while (line != null) {
                int count = getWinCount(line);
                sum += count == 0 ? 0 : 1 << (count - 1);
                line = reader.readLine();
            }
            System.out.println(sum);
        }
    }


}
