package advent_of_code.event_2024.day15;

import advent_of_code.util.AdventUtils;
import advent_of_code.util.AdventUtils.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static advent_of_code.util.AdventUtils.Direction.LEFT;
import static advent_of_code.util.AdventUtils.Direction.RIGHT;

public class LoaderV2 {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day15/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day15/test2.txt";

    private static final char EMPTY = '_';
    private static final char L_BOX = '[';
    private static final char R_BOX = ']';
    private static final char BARRIER = '#';
    private static final char ROBOT = '@';

    private static char[][] grid;
    private static char[][] commands;

    private static Robot robot;

    public static void main(String[] args) {
        init();
        System.out.println(part2());
    }

    private static int part2() {
        for (char[] commandLine : commands) {
            for (char command : commandLine) {
                Direction direction = Direction.getBy(command);
                robot.move(direction);
            }
        }
        return calculate();
    }

    private static int calculate() {
        int sum = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == R_BOX) sum += (y * 100 + x);
            }
        }
        return sum;
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            List<String> strings = Files.readAllLines(Path.of(filename));
            List<char[]> tempGrid = new ArrayList<>();
            List<char[]> tempCommands = new ArrayList<>();
            int pos = 0;
            for (String s : strings) {
                if (s.isBlank()) continue;
                s = s.replace(".", "__");
                s = s.replace("#", "##");
                s = s.replace("O", "[]");
                s = s.replace("@", "@_");
                char[] chars = s.toCharArray();
                if (s.startsWith(String.valueOf(BARRIER))) {
                    tempGrid.add(chars);
                    int index = s.indexOf(ROBOT);
                    if (index > -1) robot = new Robot(pos, index);
                } else tempCommands.add(chars);
                pos++;
            }
            grid = new char[tempGrid.size()][];
            commands = new char[tempCommands.size()][];
            pos = 0;
            for (char[] chars : tempGrid) {
                grid[pos++] = chars;
            }
            pos = 0;
            for (char[] chars : tempCommands) {
                commands[pos++] = chars;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Robot extends AdventUtils.Point {

        public Robot(int y, int x) {
            super(y, x);
        }


        public void move(Direction d) {
            int nextY = y() + d.dy();
            int nextX = x() + d.dx();
            char next = grid[nextY][nextX];
            if (next == BARRIER) return;
            if (next == EMPTY) {
                step(d);
                return;
            }
            boolean canMove = false;
            if (d == LEFT || d == RIGHT) {
                while (next == L_BOX || next == R_BOX) {
                    nextY += d.dy() * 2;
                    nextX += d.dx() * 2;
                    next = grid[nextY][nextX];
                    if (next == EMPTY) canMove = true;
                }
                if (canMove) {
                    while (next != ROBOT) {
                        grid[nextY][nextX] = grid[nextY - d.dy()][nextX - d.dx()];
                        nextY -= d.dy();
                        nextX -= d.dx();
                        next = grid[nextY][nextX];
                    }
                    step(d);
                }
            } else {
                if (canVerticalMove(robot.y(), robot.x(), d)) {
                    int k = grid[robot.y() + d.dy()][robot.x()] == L_BOX ? 0 : 1;
                    verticalMove(robot.y(), robot.x() - k, d);
                    step(d);
                }
            }
        }

        private boolean canVerticalMove(int y, int x, Direction d) {
            if (d == LEFT || d == RIGHT) return false;
            int nextY = y + d.dy();
            int nextX = x + d.dx();
            char next = grid[nextY][nextX];
            if (next == BARRIER) return false;
            if (next == EMPTY) return true;
            if (grid[nextY][nextX] == L_BOX) {
                return canVerticalMove(nextY, nextX, d) && canVerticalMove(nextY, nextX + 1, d);
            } else {
                return canVerticalMove(nextY, nextX, d) && canVerticalMove(nextY, nextX - 1, d);
            }
        }

        private void verticalMove(int y, int x, Direction d) {
            int nextY = y + d.dy();
            int nextX = x + d.dx();
            char next = grid[y][x];



            if (grid[nextY][nextX] == EMPTY && grid[nextY][nextX + 1] == EMPTY) {
                grid[nextY][nextX] = grid[nextY - d.dy()][nextX];
                grid[nextY][nextX + 1] = grid[nextY - d.dy()][nextX + 1];
                grid[nextY - d.dy()][nextX] = EMPTY;
                grid[nextY - d.dy()][nextX + 1] = EMPTY;
                return;
            }
        }

        private void step(Direction d) {
            int nextY = y() + d.dy();
            int nextX = x() + d.dx();
            grid[y()][x()] = EMPTY;
            setY(nextY);
            setX(nextX);
            grid[nextY][nextX] = ROBOT;
        }

    }


}
