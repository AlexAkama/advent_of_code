package advent_of_code.event_2023.day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day03/data.txt";
    private static final int[][] DELTA = {{0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {-1, 1}, {1, -1}, {1, 0}, {1, 1}};
    private static final char EMPTY = '.';
    private static final char GEAR = '*';
    private static final char[][] BUFFER = new char[3][];
    private static int length;
    private static int sum;

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            length = line.length();
            String emptyLine = ".".repeat(length + 2);
            BUFFER[1] = emptyLine.toCharArray();
            BUFFER[2] = extendLine(line).toCharArray();
            line = reader.readLine();
            while (line != null) {
                bufferUpdate(line);
                //bufferParse();
                bufferGearParse();
                line = reader.readLine();
            }
            bufferUpdate(emptyLine);
            //bufferParse();
            bufferGearParse();

            System.out.println(sum);
        }
    }

    // --- PART #2 ---

    private static void bufferGearParse() {
        for (int i = 1; i < length + 1; i++) {
            char c = BUFFER[1][i];
            if (c == GEAR) {
                int[][] digitPair = findDigits(i);
                if (digitPair.length < 2) continue;
                parseDigitAncCalculate(i, digitPair);
            }
        }
    }

    private static void parseDigitAncCalculate(int pos, int[][] digitPair) {
        long m1 = parseDigitFromPosition(BUFFER[1 + digitPair[0][0]], pos + digitPair[0][1]);
        long m2 = parseDigitFromPosition(BUFFER[1 + digitPair[1][0]], pos + digitPair[1][1]);
        sum += m1 * m2;
    }

    private static long parseDigitFromPosition(char[] chars, int pos) {
        int left = pos;
        int right = pos;
        while (Character.isDigit(chars[--left])) {
        }
        while (Character.isDigit(chars[++right])) {
        }
        return parseDigitFromInterval(chars, left, right);
    }

    private static long parseDigitFromInterval(char[] chars, int left, int right) {
        StringBuilder sb = new StringBuilder();
        for (int i = left + 1; i < right; i++) {
            sb.append(chars[i]);
        }
        return Long.parseLong(sb.toString());
    }

    private static int[][] findDigits(int pos) {
        int count = 0;
        int[][] res = new int[2][];
        for (int[] vector : DELTA) {
            char c = BUFFER[1 + vector[0]][pos + vector[1]];
            if (Character.isDigit(c)) {
                if (count == 0) {
                    res[count++] = vector;
                    continue;
                }
                if (count == 1) {
                    if (res[0][0] == vector[0] && res[0][1] + vector[1] != 0) {
                        res[0] = vector;
                    } else {
                        res[count++] = vector;
                    }
                }
                if (count == 2) return res;
            }
        }
        return new int[0][0];
    }

    // --- PART #1 ---

    private static void bufferParse() {
        StringBuilder digitBuffer = new StringBuilder();
        for (int i = 0; i < length + 2; i++) {
            char c = BUFFER[1][i];
            if ((c == EMPTY || correct(c)) && !digitBuffer.isEmpty()) {
                boolean readyToFlash = findContact(i - digitBuffer.length(), i);
                if (readyToFlash) {
                    sum += Integer.parseInt(digitBuffer.toString());
                    digitBuffer.setLength(0);
                }
            }
            if (c == EMPTY) {
                digitBuffer.setLength(0);
            }
            if (Character.isDigit(c)) digitBuffer.append(c);
        }
    }

    private static boolean findContact(int from, int to) {
        for (int i = from - 1; i < to + 1; i++) {
            if (correct(BUFFER[0][i]) || correct(BUFFER[2][i])) return true;
        }
        return correct(BUFFER[1][from - 1]) || correct(BUFFER[1][to]);
    }

    private static boolean correct(char c) {
        return c != EMPTY && !Character.isLetterOrDigit(c);
    }

    private static void bufferUpdate(String line) {
        BUFFER[0] = BUFFER[1];
        BUFFER[1] = BUFFER[2];
        BUFFER[2] = extendLine(line).toCharArray();
    }

    private static String extendLine(String line) {
        return EMPTY + line + EMPTY;
    }


}
