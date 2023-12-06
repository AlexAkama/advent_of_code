package advent_of_code.event_2023.day05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day05/data.txt";
    private static final Filter[] FILTERS = new Filter[7];
    private static long[] seeds;

    static {
        init();
    }

    public static void main(String[] args) {
        long minPos = Long.MAX_VALUE;
        for (int i = 0; i < seeds.length / 2; i++) {
            long start = seeds[2 * i];
            long range = seeds[2 * i + 1];
            for (long j = start; j < start + range; j++) {
                long pos = filerChain(j);
                if (pos < minPos) minPos = pos;
            }
        }

        System.out.println(minPos);

    }

    public static void mainPart1(String[] args) {
        long minPos = Long.MAX_VALUE;
        for (long seed : seeds) {
            long pos = filerChain(seed);
            if (pos < minPos) minPos = pos;
        }
        System.out.println(minPos);
    }

    private static long filerChain(long seed) {
        long res = seed;
        for (Filter filter : FILTERS) {
            res = filter.doFilter(res);
        }
        return res;
    }

    private static void init() {
        initFilters();
        try {
            initData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initData() throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            String[] split = line.split(":\\s+");
            split = split[1].split("\\s+");
            seeds = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                seeds[i] = Long.parseLong(split[i]);
            }
            int pos = -1;
            line = reader.readLine();
            while (pos < 6 || line != null) {
                if (line.isEmpty()) line = reader.readLine();
                if (line.contains("map:")) {
                    pos++;
                    line = reader.readLine();
                    continue;
                }
                split = line.split("\\s+");
                long start = Long.parseLong(split[1]);
                long range = Long.parseLong(split[2]);
                long value = Long.parseLong(split[0]);
                Interval interval = new Interval(start, range, value);
                FILTERS[pos].addInterval(interval);
                line = reader.readLine();
            }
        }
    }

    private static void initFilters() {
        for (int i = 0; i < FILTERS.length; i++) {
            FILTERS[i] = new Filter();
        }
    }

    private static class Filter {

        List<Interval> intervals = new ArrayList<>();

        void addInterval(Interval interval) {
            intervals.add(interval);
        }

        long doFilter(long v) {
            for (Interval interval : intervals) {
                if (interval.start <= v && v < interval.start + interval.range) {
                    long offset = v - interval.start;
                    return interval.value + offset;
                }
            }
            return v;
        }

    }

    private static class Interval {

        long start;
        long range;
        long value;

        public Interval(long start, long range, long value) {
            this.start = start;
            this.range = range;
            this.value = value;
        }

    }


}
