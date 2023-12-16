package advent_of_code.event_2023.day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader2 {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day15/data.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            String[] split = line.split(",");
            LensBox[] boxes = new LensBox[256];
            for (int i = 0; i < boxes.length; i++) {
                boxes[i] = new LensBox();
            }
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                char[] chars = s.toCharArray();
                int pos = 0;
                sb.setLength(0);
                while (chars[pos] != '=' && chars[pos] != '-') {
                    sb.append(chars[pos]);
                    pos++;
                }
                String label = sb.toString();
                char command = chars[pos];
                int hash = getHash(label);
                int focal = command == '=' ? Integer.parseInt(String.valueOf(chars[++pos])) : 0;
                Lens lens = new Lens(label, focal);
                if (command == '=') boxes[hash].addOrUpdate(lens);
                if (command == '-') boxes[hash].deleteIfExist(lens);
            }
            int sum = 0;
            for (int i = 0; i < boxes.length; i++) {
                List<Lens> lens = boxes[i].list;
                for (int pos = 0; pos < lens.size(); pos++) {
                    sum += (i + 1) * (pos + 1) * lens.get(pos).focal;
                }
            }

            System.out.println(sum);
            //35890 Low
            //51035 Low
            //268895 High
            //263211
        }

    }

    private static int getHash(String s) {
        char[] chars = s.toCharArray();
        int hash = 0;
        for (char c : chars) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }

    private static class LensBox {

        List<Lens> list = new ArrayList<>();

        public void deleteIfExist(Lens lens) {
            int pos = getPos(lens);
            if (pos > -1) {
                list.remove(pos);
            }
        }

        public void addOrUpdate(Lens lens) {
            int pos = getPos(lens);
            if (pos > -1) {
                list.get(pos).focal = lens.focal;
            } else list.add(lens);
        }

        private int getPos(Lens lens) {
            int pos = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).type.equals(lens.type)) {
                    pos = i;
                    break;
                }
            }
            return pos;
        }

        @Override
        public String toString() {
            return Arrays.toString(list.toArray());
        }

    }

    private static class Lens {

        String type;
        int focal;

        public Lens(String type, int focal) {
            this.type = type;
            this.focal = focal;
        }

        @Override
        public String toString() {
            return type + " " + focal;
        }

    }


}
