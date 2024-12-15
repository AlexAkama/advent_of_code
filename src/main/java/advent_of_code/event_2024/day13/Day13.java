package advent_of_code.event_2024.day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigInteger.ZERO;

public class Day13 {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day13/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day13/test.txt";

    private static final BigInteger LIMIT = BigInteger.valueOf(100);
    private static final BigInteger COST_A = BigInteger.valueOf(3);
    private static final BigPair COST = new BigPair(COST_A, BigInteger.ONE);
    private static final BigInteger OFFSET = BigInteger.valueOf(10000000000000L);
    private static final List<Machine> MACHINES = new ArrayList<>();

    public static void main(String[] args) {
        var res = part2();
        System.out.println(res);
    }

    private static BigInteger part1() {
        init(false);
        return process(true);
    }

    private static BigInteger part2() {
        init(true);
        return process(false);
    }

    private static BigInteger process(boolean useLimit) {
        BigInteger res = ZERO;
        for (Machine machine : MACHINES) {
            res = res.add(machine.calculate(useLimit));
        }
        return res;
    }

    private static void init(boolean isP2) {
        try {
            File file = new File(DATA);
            FileReader fileReader = new FileReader(file);
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                String line = "";
                while (line != null) {
                    String s1 = reader.readLine();
                    String s2 = reader.readLine();
                    String s3 = reader.readLine();
                    MACHINES.add(new Machine(parse(s1), parse(s2), isP2 ? parseWithOffset(s3) : parse(s3)));
                    line = reader.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BigPoint parseWithOffset(String s) {
        BigPoint point = parse(s);
        BigInteger offsetY = point.y.add(OFFSET);
        BigInteger offsetX = point.x.add(OFFSET);
        return new BigPoint(offsetY, offsetX);
    }

    private static BigPoint parse(String s) {
        String[] split = s.split(":\\s+");
        String[] temp = split[1].split(",\\s+");
        BigInteger y = new BigInteger(temp[1].substring(2));
        BigInteger x = new BigInteger(temp[0].substring(2));
        return new BigPoint(y, x);
    }

    static class Machine {

        BigPoint a;
        BigPoint b;
        BigPoint t;

        public Machine(BigPoint a, BigPoint b, BigPoint t) {
            this.a = a;
            this.b = b;
            this.t = t;
        }

        public BigInteger calculate(boolean useLimit) {

            BigPair push = calculateButtonPush();

            if (useLimit && overLimit(push)) return ZERO;

            return isHitToTarget(push) ? push.a.multiply(COST.a).add(push.b.multiply(COST.b)) : ZERO;
        }

        private BigPair calculateButtonPush() {
            BigInteger pushB = a.y.multiply(t.x).subtract(a.x.multiply(t.y)).divide(a.y.multiply(b.x).subtract(b.y.multiply(a.x)));
            BigInteger pushA = t.y.subtract(b.y.multiply(pushB)).divide(a.y);
            return new BigPair(pushA, pushB);
        }

        private boolean isHitToTarget(BigPair push) {
            return a.y.multiply(push.a).add(b.y.multiply(push.b)).compareTo(t.y) == 0
                    && a.x.multiply(push.a).add(b.x.multiply(push.b)).compareTo(t.x) == 0;
        }

        private boolean overLimit(BigPair push) {
            return push.a.compareTo(LIMIT) > 0 || push.b.compareTo(LIMIT) > 0;
        }

    }

    static class BigPoint {

        private final BigInteger y;
        private final BigInteger x;

        public BigPoint(BigInteger y, BigInteger x) {
            this.y = y;
            this.x = x;
        }

    }

    static class BigPair {

        private final BigInteger a;
        private final BigInteger b;

        public BigPair(BigInteger a, BigInteger b) {
            this.a = a;
            this.b = b;
        }

    }

}
