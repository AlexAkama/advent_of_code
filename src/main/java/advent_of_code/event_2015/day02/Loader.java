package advent_of_code.event_2015.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Loader {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/java/advent_of_code/event_2015/day02/data.txt";
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        String line;
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            line = reader.readLine();
            int res = 0;
            while (line != null) {
                String[] split = line.split("x");
                line = reader.readLine();
                int length = parseInt(split[0]);
                int width = parseInt(split[1]);
                int height = parseInt(split[2]);
                res += part2(length, width, height);
            }
            System.out.println(res);
        }
    }

    private static int part2(int length, int width, int height) {
        int min1 = length;
        int min2 = 0;
        if (width < min1) {
            min2 = min1;
            min1 = width;
        } else {
            min2 = width;
        }
        if (height < min1) {
            min2 = min1;
            min1 = height;
        } else if (height < min2) {
            min2 = height;
        }
        int bow = length * width * height;
        System.out.println(2 * min1 + 2 * min2 + bow);
        return 2 * min1 + 2 * min2 + bow;
    }


    private static int part1(int length, int width, int height) {
        int smallest;
        int squareLW = length * width;
        int squareLH = length * height;
        int squareWH = width * height;
        smallest = Math.min(squareLW, squareLH);
        smallest = Math.min(squareWH, smallest);
        return 2 * squareLW + 2 * squareLH + 2 * squareWH + smallest;
    }

}
