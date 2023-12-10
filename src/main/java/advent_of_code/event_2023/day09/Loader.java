package advent_of_code.event_2023.day09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day09/data.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            int sum = 0;
            while (line != null) {
                String[] split = line.split("\\s+");
                int[] ints = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    ints[i] = Integer.parseInt(split[i]);
                }
                sum += calculateFirstExtrapolation(ints);
                line = reader.readLine();
            }
            System.out.println(sum);
        }
    }

    /**
     * Вычисление предстоящего экстраполированного элемента последовательности.
     *
     * @param baseSequence последовательность для вычисления.
     * @return экстраполированное значение
     */
    private static int calculateFirstExtrapolation(int[] baseSequence) {
        boolean allZero = checkAllZero(baseSequence);
        int[] diffSequence = baseSequence;
        int sum = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        while (!allZero) {
            deque.add(diffSequence[0]);
            diffSequence = getDiffSequence(diffSequence);
            allZero = checkAllZero(diffSequence);
        }
        while (!deque.isEmpty()) {
            sum = deque.pollLast() - sum;
        }
        return sum;
    }

    /**
     * Вычисление последующего экстраполированного элемента последовательности.
     *
     * @param baseSequence последовательность для вычисления.
     * @return экстраполированное значение
     */
    private static int calculateExtrapolation(int[] baseSequence) {
        boolean allZero = checkAllZero(baseSequence);
        int[] diffSequence = baseSequence;
        int sum = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        while (!allZero) {
            deque.add(diffSequence[diffSequence.length - 1]);
            diffSequence = getDiffSequence(diffSequence);
            allZero = checkAllZero(diffSequence);
        }
        while (!deque.isEmpty()) {
            sum += deque.pop();
        }
        return sum;
    }


    /**
     * Проверяем, все ли элементы массива равны 0
     *
     * @return true|false
     */
    private static boolean checkAllZero(int[] ints) {
        for (int anInt : ints) {
            if (anInt != 0) return false;
        }
        return true;
    }

    /**
     * Создаем новую последовательность для разницы между элементами.
     *
     * @param baseSequence базовая последовательность
     * @return новая последовательность состоящая разниц элементов базовой последовательности
     */
    private static int[] getDiffSequence(int[] baseSequence) {
        int[] diffSequence = new int[baseSequence.length - 1];
        for (int i = 0; i < baseSequence.length - 1; i++) {
            diffSequence[i] = baseSequence[i + 1] - baseSequence[i];
        }
        return diffSequence;
    }

}
