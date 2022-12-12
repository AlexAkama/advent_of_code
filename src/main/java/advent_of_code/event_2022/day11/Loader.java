package advent_of_code.event_2022.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static advent_of_code.event_2022.day11.Loader.Operation.ADD;
import static advent_of_code.event_2022.day11.Loader.Operation.MULTIPLY;
import static advent_of_code.event_2022.day11.Loader.Operation.getBy;

public class Loader {

    // 110264
    // 23612457316

    private static final List<Monkey> MONKEYS = new ArrayList<>();

    private static final int ROUNDS = 10_000;
    private static int totalDivider;
    private static boolean log = true;

    public static void main(String[] args) throws IOException {
        parse("data.txt");
        calculateAndSetDivider();

        System.out.println("MONKEYS:");
        for (Monkey monkey : MONKEYS) {
            System.out.println(monkey.toStringWithLogic());
        }
        System.out.println();

        for (int i = 0; i < ROUNDS; i++) {
            part2(i);
        }

        System.out.println("\nResult:");
        printMonkeyInspectStatus();

        System.out.println("SOLUTION RESULT: " + calculateResult());
    }

    private static void part2(int round) {
        log = round == 0 || round == 19 || (round + 1) % 1000 == 0;
        if (log) printRoundHeader(round);
        for (Monkey monkey : MONKEYS) {
            monkey.inspect();
        }
        if (log) printMonkeyInspectStatus();
    }

    private static void part1(int round) {
        printRoundHeader(round);
        for (Monkey monkey : MONKEYS) {
            monkey.inspect();
        }
        printMonkeysStatus();
    }

    private static void printRoundHeader(int round) {
        System.out.println("--- ROUND " + (round + 1) + " ---\n");
    }

    private static void printMonkeysStatus() {
        System.out.println("Monkey status:");
        MONKEYS.forEach(System.out::println);
        System.out.println();
    }

    private static BigInteger calculateResult() {
        long[] longs = MONKEYS.stream()
                .mapToLong(Monkey::getInspectCount)
                .sorted()
                .toArray();
        int n = longs.length;
        return BigInteger.valueOf(longs[n - 2]).multiply(BigInteger.valueOf(longs[n - 1]));
    }

    private static void printMonkeyInspectStatus() {
        for (Monkey monkey : MONKEYS) {
            System.out.println(monkey.name + " inspected " + monkey.inspectCount);
        }
        System.out.println();
    }

    private static void calculateAndSetDivider() {
        int divider = 1;
        for (Monkey monkey : MONKEYS) {
            divider *= monkey.divider;
        }
        totalDivider = divider;
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day11/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {

                // Name
                if (!MONKEYS.isEmpty()) line = reader.readLine();
                String name = line.substring(0, line.length() - 1);
                var monkey = new Monkey();
                monkey.name = name;

                // Starting
                line = reader.readLine();
                monkey.items = parseItems(line);

                // Operation
                line = reader.readLine();
                monkey.operationPair = parseOperation(line);

                // Test
                line = reader.readLine();
                monkey.divider = parseLastInt(line);

                // True
                line = reader.readLine();
                monkey.nextMonkeyIdIfTrueLogic = parseLastInt(line);

                // False
                line = reader.readLine();
                monkey.nextMonkeyIdIfFalseLogic = parseLastInt(line);

                MONKEYS.add(monkey);

                line = reader.readLine();
            }
        }
    }

    private static Deque<Item> parseItems(String s) {
        s = s.substring(s.indexOf(':') + 2);
        String[] split = s.split(", ");
        Deque<Item> items = new ArrayDeque<>();
        for (String sl : split) {
            Item item = new Item(Integer.parseInt(sl));
            items.add(item);
        }
        return items;
    }

    private static OperationPair parseOperation(String s) {
        var operation = getBy(s.charAt(s.lastIndexOf(' ') - 1));
        int volume = (Character.isDigit(s.charAt(s.lastIndexOf(' ') + 1)))
                ? parseLastInt(s)
                : -1;
        return new OperationPair(operation, volume);
    }

    private static int parseLastInt(String s) {
        return Integer.parseInt(s.substring(s.lastIndexOf(' ') + 1));
    }

    static class OperationPair {

        Operation operation;
        int volume;

        public OperationPair(Operation operation, int volume) {
            this.operation = operation;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return operation + ":" + volume;
        }

    }

    static class Monkey {

        String name;
        int divider;
        OperationPair operationPair;
        int nextMonkeyIdIfTrueLogic;
        int nextMonkeyIdIfFalseLogic;
        Deque<Item> items;
        long inspectCount;

        public void inspect() {
            if (ROUNDS < 21) System.out.println(name + ":");
            var count = items.size();
            for (int i = 0; i < count; i++) {
                Item item = items.pollFirst();
                assert item != null;
                inspect(item);
            }
            if (ROUNDS < 21) System.out.println();
        }

        public void inspect(Item item) {
            long prev = item.worryLevel;
            item.worryLevel = calculateNewWorryLevel(item.worryLevel) % totalDivider;

            int nextMonkeyId = (item.worryLevel % divider == 0)
                    ? nextMonkeyIdIfTrueLogic
                    : nextMonkeyIdIfFalseLogic;

            MONKEYS.get(nextMonkeyId).items.addLast(item);
            inspectCount++;
            if (ROUNDS < 21)
                System.out.printf("%4d: inspect: %-4d -> %-4d -> Monkey %d%n", this.inspectCount, prev, item.worryLevel, nextMonkeyId);

        }

        private long calculateNewWorryLevel(long old) {
            long level = 0;
            long volume = (operationPair.volume != -1) ? operationPair.volume : old;
            if (operationPair.operation == ADD) level = old + volume;
            else if (operationPair.operation == MULTIPLY) level = old * volume;
            if (ROUNDS < 21) level /= 3;
            return level;
        }

        @Override
        public String toString() {
            return name + " " + items;
        }

        public String toStringWithLogic() {
            return String.format("%s %-34s %-14s D:%s", name, items, operationPair, divider);
        }

        public long getInspectCount() {
            return inspectCount;
        }

    }

    static class Item {

        private long worryLevel;

        public Item(int worryLevel) {
            this.worryLevel = worryLevel;
        }

        @Override
        public String toString() {
            return String.valueOf(worryLevel);
        }

    }

    enum Operation {
        ADD,
        MULTIPLY;

        public static Operation getBy(Character c) {
            if (c == '+') return ADD;
            if (c == '*') return MULTIPLY;
            return null;
        }
    }

}
