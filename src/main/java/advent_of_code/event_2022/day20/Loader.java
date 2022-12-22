package advent_of_code.event_2022.day20;

import advent_of_code.event_2022.ListNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static advent_of_code.event_2022.Utils.*;
import static advent_of_code.event_2022.Utils.TEST;

public class Loader {

    private static final List<ListNode> NODES = new ArrayList<>();
    private static ListNode zero;

    public static void main(String[] args) throws IOException {
        parse(INPUT);

        for (ListNode node : NODES) move(node);

        int res = getFromZeroAfter(1000 % NODES.size())
                + getFromZeroAfter(2000 % NODES.size())
                + getFromZeroAfter(3000 % NODES.size());
        System.out.println(res);

    }

    private static int getFromZeroAfter(int i) {
        ListNode node = zero;
        while (i > 0) {
            node = node.next;
            i--;
        }
        return node.name;
    }

    public static void move(ListNode node) {
        int offset = node.name;
        int sign = sign(offset);
        if (sign == 0) return;
        offset = Math.abs(offset);
        ListNode cur = node;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        while (offset != 0) {
            if (sign > 0) node = node.next;
            if (sign < 0) node = node.prev;
            offset--;
        }
        if (sign > 0) {
            ListNode next = node.next;
            node.next = cur;
            cur.prev = node;
            cur.next = next;
            next.prev = cur;
        }
        if (sign < 0) {
            ListNode prev = node.prev;
            cur.next = node;
            cur.prev = prev;
            prev.next = cur;
            node.prev = cur;
        }
    }

    private static void printList(ListNode node) {
        String separator = " -> ";
        StringBuilder sb = new StringBuilder();
        sb.append(node.toString());
        ListNode next = node.next;
        while (next != node) {
            sb.append(separator);
            sb.append(next.toString());
            next = next.next;
        }
        System.out.println(sb);
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day20/".concat(fileName);
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            ListNode start = null;
            ListNode prev = null;
            while (line != null) {
                int i = Integer.parseInt(line);
                ListNode node = new ListNode(i);
                NODES.add(node);
                if (i == 0) zero = node;
                node.prev = prev;
                if (prev == null) start = node;
                else prev.next = node;
                prev = node;
                line = reader.readLine();
            }
            start.prev = prev;
            prev.next = start;
        }
    }

}
