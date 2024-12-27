package advent_of_code.event_2024.day17;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Loader {

    private static final int DAY = 17;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test%s.txt";

    private static final StringBuilder out = new StringBuilder();

    private static int a;
    private static int b;
    private static int c;

    private static InstructionBox box;

    public static void main(String[] args) {
        init();
        part1();
        out.setLength(out.length() - 1);
        System.out.println(out);
    }

    private static int part1() {
        while (box.hasNext()) {
            doCommand(box.getNext());
        }
        return -1;
    }

    private static void doCommand(int code) {
        switch (code) {
            case 0 -> command0();
            case 1 -> command1();
            case 2 -> command2();
            case 3 -> command3();
            case 4 -> command4();
            case 5 -> command5();
            case 6 -> command6();
            case 7 -> command7();
            default -> throw new IllegalArgumentException("жопа в команде!!!");
        }
    }

    private static void command0() {
        a /= Math.pow(2, getComboOperand(box.getNext()));
    }

    private static void command1() {
        b ^= box.getNext();
    }

    private static void command2() {
        b = getComboOperand(box.getNext()) % 8;
    }

    private static void command3() {
        int next = box.getNext();
        if (a != 0) box.p = next;
    }

    private static void command4() {
        b ^= c;
        box.getNext();
    }

    private static void command5() {
        int comboOperand = getComboOperand(box.getNext()) % 8;
        log(comboOperand);
    }

    private static void command6() {
        b = (int) (a / Math.pow(2, getComboOperand(box.getNext())));
    }

    private static void command7() {
        c = (int) (a / Math.pow(2, getComboOperand(box.getNext())));
    }

    private static int getComboOperand(int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalArgumentException("жопа в операнде!!!");
        };
    }

    private static void log(Object o) {
        out.append(o).append(',');
    }

    private static void init(int... test) {
        try {
            String filename = test.length == 0 ? DATA : String.format(TEST, test[0] == 0 ? "" : test[0]);
            List<String> strings = Files.readAllLines(Path.of(filename));
            a = getData(strings.get(0))[0];
            b = getData(strings.get(1))[0];
            c = getData(strings.get(2))[0];
            box = new InstructionBox(getData(strings.get(4)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int[] getData(String s) {
        String[] split = s.split(":\\s+");
        String[] data = split[1].split(",");
        int[] res = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            res[i] = Integer.parseInt(data[i]);
        }
        return res;
    }

    static class InstructionBox {

        private int p = 0;
        int[] instruction;

        public InstructionBox(int[] instruction) {
            this.instruction = instruction;
        }

        boolean hasNext() {
            return p < instruction.length;
        }

        int getNext() {

            if (p == instruction.length) {
                System.out.println(out);
                System.exit(0);
            }
            return instruction[p++];
        }

    }

}
