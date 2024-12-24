package advent_of_code.util;

import java.util.List;

public class CyclicIterator<T> {
    private final List<T> list;
    private int currentIndex;

    public CyclicIterator(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        this.list = list;
        this.currentIndex = 0;
    }

    public T next() {
        T nextElement = list.get(currentIndex);
        currentIndex = (currentIndex + 1) % list.size();
        return nextElement;
    }
}
