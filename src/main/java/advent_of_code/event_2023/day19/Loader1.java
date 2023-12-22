package advent_of_code.event_2023.day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import static advent_of_code.event_2023.day19.Loader1.Operation.GO_TO;

public class Loader1 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day19/test.txt";

    private static final BiPredicate<Integer, Integer> PR_MORE = (i1, i2) -> i1 > i2;
    private static final BiPredicate<Integer, Integer> PR_LESS = (i1, i2) -> i1 < i2;

    private static final Map<String, Pipeline> WORK_FLOW_MAP = new HashMap<>();
    private static final List<Part> PARTS = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        int sum = 0;
        for (Part part : PARTS) {
            if (Status.ACCEPTED == flow(part)) {
                sum += part.getRating();
            }
        }
        System.out.println(sum);

    }

    private static Status flow(Part part) {
        Pipeline pipeline = WORK_FLOW_MAP.get("in");
        Deque<Condition> deque = new ArrayDeque<>();
        deque.addAll(pipeline.conditions);
        while (!deque.isEmpty()) {
            Condition condition = deque.pop();
            if (condition.isTerminal()) {
                return Status.getBy(condition.result);
            }
            if (condition.operation == GO_TO) {
                deque.clear();
                deque.addAll(WORK_FLOW_MAP.get(condition.result).conditions);
                continue;
            }
            int target = part.getTarget(condition.target);
            if  (condition.operation.function.test(target, condition.argument)) {
                deque.clear();
                deque.add(new Condition('-', GO_TO, 0, condition.result));
            }
        }
        return null;
    }

    static void init() throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (!line.isEmpty()) {
                int index = line.indexOf('{');
                String name = line.substring(0, index);
                String[] split = line.substring(index + 1, line.length() - 1).split(",");
                List<Condition> conditions = new ArrayList<>();
                for (String s : split) {
                    Condition condition;
                    if (s.contains(":")) {
                        String[] conditionSplit = s.split(":");
                        char target = conditionSplit[0].charAt(0);
                        Operation op = Operation.getBy(conditionSplit[0].charAt(1));
                        int arg = Integer.parseInt(conditionSplit[0].substring(2));
                        condition = new Condition(target, op, arg, conditionSplit[1]);
                    } else {
                        condition = new Condition('-', GO_TO, 0, s);
                    }
                    conditions.add(condition);
                    //System.out.println("\t" + condition);
                }
                Pipeline pipeline = new Pipeline(name, conditions);
                WORK_FLOW_MAP.put(name, pipeline);
                //System.out.println(name);
                line = reader.readLine();
            }
            line = reader.readLine();
            while (line != null) {
                String[] split = line.split("\\D+");
                Part part = new Part(
                        Integer.parseInt(split[1]),
                        Integer.parseInt(split[2]),
                        Integer.parseInt(split[3]),
                        Integer.parseInt(split[4])
                );
                PARTS.add(part);
                //System.out.println(part);
                line = reader.readLine();
            }
        }
    }

    static class Pipeline {

        String name;
        List<Condition> conditions;

        public Pipeline(String name, List<Condition> conditions) {
            this.name = name;
            this.conditions = conditions;
        }

    }

    static class Condition {

        char target;
        Operation operation;
        int argument;
        String result;

        public Condition(char target, Operation operation, int argument, String result) {
            this.target = target;
            this.operation = operation;
            this.argument = argument;
            this.result = result;
        }

        public boolean isTerminalResult() {
            return result.equals("A") || result.equals("R");
        }

        public boolean isTerminal() {
            return operation == GO_TO && isTerminalResult();
        }

        @Override
        public String toString() {
            return operation == GO_TO
                    ? String.format("[->%s]", result)
                    : String.format("[%s%s%s->%s]", target, operation.c, argument, result);
        }

    }

    static class Part {

        int x;
        int m;
        int a;
        int s;

        public Part(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        int getTarget(char c) {
            return switch (c) {
                case 'x' -> x;
                case 'm' -> m;
                case 'a' -> a;
                case 's' -> s;
                default -> throw new IllegalArgumentException("Illegal c: " + c);
            };
        }

        int getRating() {
            return x + m + a + s;
        }

        @Override
        public String toString() {
            return String.format("[x=%s,m=%s,a=%s,s=%s]", x, m, a, x);
        }

    }

    enum Operation {
        MORE('>', PR_MORE),
        LESS('<', PR_LESS),
        GO_TO('^', null);

        private final char c;
        private final BiPredicate<Integer, Integer> function;

        Operation(char c, BiPredicate<Integer, Integer> function) {
            this.c = c;
            this.function = function;
        }

        static Operation getBy(char c) {
            return switch (c) {
                case '>' -> MORE;
                case '<' -> LESS;
                default -> throw new IllegalArgumentException("Illegal char: " + c);
            };
        }

    }

    enum Status {
        ACCEPTED,
        REJECTED;

        static Status getBy(String s) {
            return switch (s) {
                case "A" -> ACCEPTED;
                case "R" -> REJECTED;
                default -> throw new IllegalArgumentException("Illegal string: " + s);
            };
        }
    }

}
