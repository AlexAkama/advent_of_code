package advent_of_code.event_2022;

public class ListNode {

    public final int name;
    public ListNode next;
    public ListNode prev;

    public ListNode(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
//        return "(" + name + ")[" + (prev != null ? prev.name : "---") + ":" + (next != null ? next.name : "---") + "]";
        return "(" + name + ")";
    }

}
