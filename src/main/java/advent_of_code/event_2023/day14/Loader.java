package advent_of_code.event_2023.day14;

import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.util.List;

import static advent_of_code.util.AdventUtils.Direction.DOWN;
import static advent_of_code.util.AdventUtils.Direction.LEFT;
import static advent_of_code.util.AdventUtils.Direction.RIGHT;
import static advent_of_code.util.AdventUtils.Direction.UP;
import static advent_of_code.util.AdventUtils.readAllFromFile;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day14/data.txt";

    private static final char STONE = 'O';
    private static final char AIR = '.';

    private static char[][] matrix;

    public static void main(String[] args) throws IOException {
        createMatrix(readAllFromFile(INPUT));

        for (int i = 1; i < 500; i++) {
            doCycle();
            System.out.println(i + ":" + countLoad());
        }
        //107951
    }

    private static void doCycle() {
        move(UP);
        move(LEFT);
        move(DOWN);
        move(RIGHT);
    }

    private static int countLoad() {
        int sum = 0;
        for (int y = 0; y < matrix.length; y++) {
            int count = 0;
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == STONE) count++;
            }
            sum += count * (matrix.length - y);
        }
        return sum;
    }

    private static void move(Direction direction) {
        switch (direction) {
            case UP -> moveUP();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
        }
    }

    private static void moveUP() {
        for (int y = 1; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                goStone(y, x, UP);
            }
        }
    }

    private static void moveDown() {
        for (int y = matrix.length - 2; y > -1; y--) {
            for (int x = 0; x < matrix[0].length; x++) {
                goStone(y, x, DOWN);
            }
        }
    }

    private static void moveLeft() {
        for (int x = 1; x < matrix[0].length; x++) {
            for (int y = 0; y < matrix.length; y++) {
                goStone(y, x, LEFT);
            }
        }
    }

    private static void moveRight() {
        for (int x = matrix[0].length - 2; x > -1; x--) {
            for (int y = 0; y < matrix.length; y++) {
                goStone(y, x, RIGHT);
            }
        }
    }

    private static void goStone(int y, int x, Direction direction) {
        if (matrix[y][x] != STONE) return;
        int nextY = y;
        int nextX = x;
        int tempY = y + direction.y();
        int tempX = x + direction.x();
        while (canGoToHere(tempY, tempX)) {
            nextY = tempY;
            nextX = tempX;
            tempY += direction.y();
            tempX += direction.x();
        }
        if (nextY != y || nextX != x) {
            matrix[nextY][nextX] = matrix[y][x];
            matrix[y][x] = AIR;
        }
    }

    private static boolean canGoToHere(int y, int x) {
        return inMatrix(y, x) && matrix[y][x] == AIR;
    }

    private static boolean inMatrix(int y, int x) {
        return y > -1 && x > -1 && y < matrix.length && x < matrix[0].length;
    }

    private static void createMatrix(List<String> list) {
        matrix = new char[list.size()][list.get(0).length()];
        for (int y = 0; y < list.size(); y++) {
            matrix[y] = list.get(y).toCharArray();
        }
    }

}
