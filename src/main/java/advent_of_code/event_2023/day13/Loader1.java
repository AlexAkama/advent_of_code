package advent_of_code.event_2023.day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader1 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day13/test.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            int sumV = 0;
            int sumH = 0;
            while (line != null) {
                List<String> list = new ArrayList<>();
                while (line != null && !line.isEmpty()) {
                    list.add(line);
                    line = reader.readLine();
                }
                char[][] matrix = new char[list.size()][list.get(0).length()];
                for (int i = 0; i < list.size(); i++) {
                    matrix[i] = list.get(i).toCharArray();
                }

                int v = findVerticalSymmetryAxis(matrix);
                if (v != -1) {
                    sumV += v;
                }
                int h = findHorizontalSymmetryAxis(matrix);
                if (h != -1) {
                    sumH += h;
                }


                line = reader.readLine();
            }
            System.out.println(sumV + sumH * 100);
            //37718
        }
    }

    private static int findHorizontalSymmetryAxis(char[][] matrix) {
        boolean[] axis = new boolean[matrix.length - 1];
        Arrays.fill(axis, true);
        for (int x = 0; x < matrix[0].length; x++) {
            for (int i = 0; i < axis.length; i++) {
                int down = i;
                int up = i + 1;
                while (down > -1 && up < matrix.length) {
                    if (matrix[down--][x] != matrix[up++][x]) {
                        axis[i] = false;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < axis.length; i++) {
            if (axis[i]) return i + 1;
        }
        return -1;
    }

    public static int findVerticalSymmetryAxis(char[][] matrix) {
        boolean[] axis = new boolean[matrix[0].length - 1];
        Arrays.fill(axis, true);
        for (char[] chars : matrix) {
            for (int i = 0; i < chars.length - 1; i++) {
                int left = i;
                int right = i + 1;
                if (axis[i]) {
                    while (left > -1 && right < matrix[0].length) {
                        if (chars[left--] != chars[right++]) {
                            axis[i] = false;
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < axis.length; i++) {
            if (axis[i]) return i + 1;
        }
        return -1;
    }

}
