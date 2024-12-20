package advent_of_code.event_2024.day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day19/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day19/test.txt";

    private static final int P = 29;
    private static final long MODULE = 1_000_000_007L;

    private static final Map<String, Long> variants = new HashMap<>();
    private static long[] power;
    private static List<String> data;
    private static long[] prefixHashArray;

    public static void main(String[] args) {
        init(1);
        System.out.println(part2());
    }

    private static int part1() {
        int sum = 0;
        for (String datum : data) {
            buildPrefixHashArray(datum);
            long count = countCombination(datum);
            if (count > 0) sum++;
        }
        return sum;
    }

    private static long part2() {
        long sum = 0;
        for (String datum : data) {
            buildPrefixHashArray(datum);
            long count = countCombination(datum);
            if (count > 0) sum += count;
        }
        return sum;
    }

    private static long countCombination(String datum) {
        long[] possible = new long[prefixHashArray.length];
        possible[0] = 1;
        int p = 0;
        while (p < prefixHashArray.length) {
            for (Map.Entry<String, Long> entry : variants.entrySet()) {
                int stripLength = entry.getKey().length();
                if (p + stripLength > prefixHashArray.length - 1) continue;
                String s = datum.substring(p, p + stripLength);
                long poweredHash = (entry.getValue() * power[p]) % MODULE;
                long intervalHash = getIntervalHash(p, p + stripLength);
                if (poweredHash == intervalHash) {
                    // part1
                    // possible[p + stripLength]++;
                    possible[p + stripLength] += possible[p];
                }
                if (s.equals(entry.getKey()) && poweredHash != intervalHash)
                    System.out.printf("%d:\t%s[IH:%d] %s[PH:%d] (%s)\n", p, s, intervalHash, entry.getKey(), poweredHash, poweredHash == intervalHash);
            }
            p++;
            while (p < prefixHashArray.length && possible[p] == 0) {
                p++;
            }
        }

        return possible[possible.length - 1];
    }

    private static long getIntervalHash(int startIndex, int finishIndex) {
        long sub = prefixHashArray[finishIndex] - prefixHashArray[startIndex];
        return sub > -1 ? sub : sub + MODULE;
    }


    private static void buildPrefixHashArray(String s) {
        prefixHashArray = new long[s.length() + 1];
        for (int i = 1; i < s.length() + 1; i++) {
            prefixHashArray[i] = (prefixHashArray[i - 1] + getCharPowerValue(s.charAt(i - 1)) * power[i - 1]) % MODULE;
        }
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            data = strings.subList(2, strings.size());
            int max = data.stream()
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);
            initP(max);
            String[] split = strings.get(0).split(",\\s+");
            for (String s : split) {
                variants.put(s, calculatePrefixHash(s));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initP(int n) {
        power = new long[n + 1];
        power[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            power[i] = (power[i - 1] * P) % MODULE;
        }
    }

    private static long calculatePrefixHash(String s) {
        long hash = 0;
        int p = 0;
        for (char c : s.toCharArray()) {
            hash = (hash + getCharPowerValue(c) * power[p++]) % MODULE;
        }
        return hash;
    }

    private static int getCharPowerValue(char c) {
        return c - 'a' + 1;
    }

}
