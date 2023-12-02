package advent_of_code.event_2022.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Loader {

    private static final Character OPEN = '[';
    private static final Character CLOSE = ']';

    private static final List<Pair> PARSE_PAIRS = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        parse("data.txt");

        int count = 0;
        int sum = 0;

        for (Pair parsePair : PARSE_PAIRS) {
            count++;

            String sl = parsePair.left;
            String sr = parsePair.right;
            int compare = compare(sl, sr);
            System.out.println(sl);
            System.out.println(sr);
            System.out.println(compare);
            System.out.println();

            if (compare < 0) sum += count;
        }

        System.out.println(sum);

    }


    private static int compare(String left, String right) {
        left = trimBracket(left);
        right = trimBracket(right);
        Deque<Integer> leftDQ = new ArrayDeque<>();
        Deque<Integer> rightDQ = new ArrayDeque<>();

        int leftPos = 0;
        int rightPos = 0;

        int res = 0;
        while (res == 0) {

            var leftPair = getApplicant(left, leftDQ, leftPos);
            var rightPair = getApplicant(right, rightDQ, rightPos);


            if (leftPair.applicant == 'E') return -(rightPair.applicant - '0');
            if (rightPair.applicant == 'E') return (leftPair.applicant - '0');

            if (leftPair.applicant == CLOSE) return 1;
            if (rightPair.applicant == CLOSE) return -1;

            leftPos = leftPair.pos + 1;
            rightPos = rightPair.pos + 1;

            res = leftPair.applicant - rightPair.applicant;
        }

        return res;
    }

    private static ApplicantPair getApplicant(String s, Deque<Integer> deque, int pos) {
        if (pos > s.length() - 1) return new ApplicantPair('E', -1);
        char c = s.charAt(pos);
        while (pos < s.length() && !Character.isDigit(c)) {
            c = s.charAt(pos);
            if (c == OPEN) {
                int closingBracketPos = findClosingBracket(s, pos);
                deque.add(closingBracketPos);
            }
            pos++;
        }
        return new ApplicantPair(c, pos);
    }

    static class ApplicantPair {

        private final char applicant;
        private final int pos;

        public ApplicantPair(char applicant, int pos) {
            this.applicant = applicant;
            this.pos = pos;
        }

    }

    private static String trimBracket(String s) {
        if (s == null || s.isBlank()) return s;
        if (s.charAt(0) == OPEN && s.charAt(s.length() - 1) == CLOSE) s = s.substring(1, s.length() - 1);
        return s;
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day13/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) line = reader.readLine();
                String line2 = reader.readLine();
                Pair pair = new Pair(line, line2);
                PARSE_PAIRS.add(pair);
                line = reader.readLine();
            }
        }
    }

    private static boolean isArray(String s) {
        return s.charAt(0) == OPEN;
    }

    private static int findClosingBracket(String s, int pos) {
        if (!isArray(s)) return -1;
        int res = 0;
        char[] chars = s.toCharArray();
        for (int i = pos; i < chars.length; i++) {
            char c = chars[i];
            if (c == OPEN) res++;
            if (c == CLOSE) res--;
            if (res == 0) return i;
        }
        return -1;
    }

    static class Pair {

        private final String left;
        private final String right;

        public Pair(String left, String right) {
            this.left = left;
            this.right = right;
        }

    }

}
