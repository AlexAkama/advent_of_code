package advent_of_code.event_2023.day07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day07/data.txt";

    private static final Map<Character, Integer> MAP;

    static {
        MAP = new HashMap<>(13);
        MAP.put('2', 1);
        MAP.put('3', 2);
        MAP.put('4', 3);
        MAP.put('5', 4);
        MAP.put('6', 5);
        MAP.put('7', 6);
        MAP.put('8', 7);
        MAP.put('9', 8);
        MAP.put('T', 9);
        MAP.put('J', 10);
        MAP.put('Q', 11);
        MAP.put('K', 12);
        MAP.put('A', 13);
    }

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            PriorityQueue<Hand> queue = new PriorityQueue<>();
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split("\\s+");
                char[] chars = split[0].toCharArray();
                Type type = getType(chars);
                Hand hand = new Hand(chars, Integer.parseInt(split[1]), type);
                queue.add(hand);
                line = reader.readLine();
            }
            long count = 0;
            long sum = 0;
            while (!queue.isEmpty()) {
                Hand hand = queue.poll();
                sum += ++count * hand.bidAmount;
            }
            System.out.println(sum);
            //251106089
        }
    }

    private static Type getType(char[] chars) {
        int[] w = new int[MAP.size() + 1];
        for (char aChar : chars) w[MAP.get(aChar)]++;
        int[] w2 = new int[6];
        for (int j : w) if (j > 0) w2[j]++;
        if (w2[5] != 0) return Type.FIVE_OF_KIND;
        if (w2[4] != 0) return Type.FOUR_OF_KIND;
        if (w2[3] != 0 && w2[2] != 0) return Type.FULL_HOUSE;
        if (w2[3] != 0) return Type.THREE_OR_KIND;
        if (w2[2] == 2) return Type.TWO_PAIR;
        if (w2[2] == 1) return Type.ONE_PAIR;
        if (w2[1] == 5) return Type.HIGH_CARD;
        return null;
    }

    private static class Hand implements Comparable<Hand> {

        final char[] cards;
        final int bidAmount;
        final Type type;

        public Hand(char[] cards, int bidAmount, Type type) {
            this.cards = cards;
            this.bidAmount = bidAmount;
            this.type = type;
        }


        @Override
        public int compareTo(Hand o) {
            int typeCompare = typeCompare(type, o.type);
            return typeCompare != 0 ? typeCompare : cardsCompare(cards, o.cards);
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "cards=" + Arrays.toString(cards) +
                    ", bidAmount=" + bidAmount +
                    ", type=" + type +
                    '}';
        }

        private static int typeCompare(Type type1, Type type2) {
            return Integer.compare(type1.ordinal(), type2.ordinal());
        }

        private static int cardsCompare(char[] cards1, char[] chars2) {
            if (cards1.length != chars2.length) return Integer.compare(cards1.length, chars2.length);
            for (int i = 0; i < cards1.length; i++) {
                int cardCompare = cardCompare(cards1[i], chars2[i]);
                if (cardCompare != 0) return cardCompare;
            }
            return 0;
        }

        private static int cardCompare(Character card1, Character card2) {
            return Integer.compare(MAP.get(card1), MAP.get(card2));
        }

    }

    private enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OR_KIND,
        FULL_HOUSE,
        FOUR_OF_KIND,
        FIVE_OF_KIND
    }

}
