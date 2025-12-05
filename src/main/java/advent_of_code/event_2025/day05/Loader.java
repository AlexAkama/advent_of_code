package advent_of_code.event_2025.day05;

import advent_of_code.util.AdventUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class LoaderTest {
    public static void main(String[] args) {
        Loader.main("05", "data_test.txt", "TEST");
    }
}

class LoaderData {
    public static void main(String[] args) {
        Loader.main("05", "data.txt", "DATA");
    }
}

public class Loader {

    private static final List<Interval> INTERVALS = new ArrayList<>();
    private static final List<Long> DATA = new ArrayList<>();

    public static void main(String... args) {

        String file = "src/main/java/advent_of_code/event_2025/day" + args[0] + "/" + args[1];
        List<String> strings = AdventUtils.readAllFromFile(file);

        init(strings);

        int count = process();
        long total = countTotal();

        System.out.println(count);
        System.out.println(total);

    }

    private static long countTotal() {
        long count = 0;
        for (Interval interval : INTERVALS) {
            count += interval.size();
        }
        return count;
    }

    static int process() {
        int count = 0;
        for (Long datum : DATA) {
            for (Interval interval : INTERVALS) {
                if (interval.included(datum)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    static void init(List<String> strings) {
        boolean isData = false;
        for (String string : strings) {
            if (string.isBlank()) {
                isData = true;
                continue;
            }
            if (isData) {
                DATA.add(Long.parseLong(string));
            } else {
                String[] split = string.split("-");
                INTERVALS.add(new Interval(
                        Long.parseLong(split[0]),
                        Long.parseLong(split[1])
                ));
            }
        }
        mergeIntervals();
    }

    private static void mergeIntervals() {
        List<Interval> intervals = INTERVALS;

        // Сортируем интервалы по возрастанию левого края
        intervals.sort(Comparator.comparingLong(i -> i.left));

        List<Interval> merged = new ArrayList<>();
        Interval current = intervals.get(0);

        for (Interval next : intervals.subList(1, intervals.size())) {
            // Проверяем, пересекается ли следующий интервал с текущим
            if (current.right >= next.left) { // Интервалы пересекаются
                // Объединяем их путем расширения правого конца текущего интервала
                current.right = Math.max(current.right, next.right);
            } else {
                // Добавляем текущий интервал в результирующий список
                merged.add(current);
                // Переходим к следующему интервалу
                current = next;
            }
        }
        // Добавляем последний обработанный интервал
        merged.add(current);

        INTERVALS.clear();
        INTERVALS.addAll(merged);
    }

    static class Interval {
        long left;
        long right;

        public Interval(long left, long right) {
            this.left = left;
            this.right = right;
        }

        boolean included(long num) {
            return num >= left && num <= right;
        }

        long size() {
            return right - left + 1;
        }

    }

}
