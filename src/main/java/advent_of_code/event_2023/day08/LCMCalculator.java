package advent_of_code.event_2023.day08;


/**
 * Вычисление Наибольшего Общего Кратного (НОК) для данного списка чисел.
 */
public class LCMCalculator {

    private LCMCalculator() {
    }

    public static long findLCM(long[] numbers) {
        long lcm = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            lcm = findLCM(lcm, numbers[i]);
        }
        return lcm;
    }

    private static long findLCM(long a, long b) {
        return (a * b) / findGCD(a, b);
    }

    private static long findGCD(long a, long b) {
        if (b == 0) {
            return a;
        }
        return findGCD(b, a % b);
    }

}
