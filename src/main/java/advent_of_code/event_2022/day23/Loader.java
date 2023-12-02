package advent_of_code.event_2022.day23;

import advent_of_code.event_2022.Point;
import advent_of_code.event_2022.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static advent_of_code.event_2022.Utils.DIRECTION_MAP;

public class Loader {

    private static final List<String> NORTH = List.of("N", "NE", "NW");
    private static final List<String> SOUTH = List.of("S", "SE", "SW");
    private static final List<String> WEST = List.of("W", "NW", "SW");
    private static final List<String> EAST = List.of("E", "NE", "SE");

    private static final List<List<String>> DIRECTIONS = List.of(NORTH, SOUTH, WEST, EAST);

    private static final char[][] GRID = new char[6][5];

    private static final char FREE = '.';
    private static final char ELF = '#';
    private static final char HIP = '?';

    static {
        for (char[] ints : GRID) {
            Arrays.fill(ints, FREE);
        }
    }

    public static void main(String[] args) {

        Elf elf1 = new Elf(new Point(2, 1));
        Elf elf2 = new Elf(new Point(3, 1));
        Elf elf3 = new Elf(new Point(2, 2));
        Elf elf4 = new Elf(new Point(2, 4));
        Elf elf5 = new Elf(new Point(3, 4));
        List<Elf> elves = new ArrayList<>();
        elves.add(elf1);
        elves.add(elf2);
        elves.add(elf3);
        elves.add(elf4);
        elves.add(elf5);

        elves.forEach(Elf::showOnGrid);

        elves.forEach(Elf::propose);

        printGrid();
    }

    private static void printGrid() {
        for (int i = 0; i < GRID.length; i++) {
            for (int j = 0; j < GRID[0].length; j++) {
                System.out.print(GRID[i][j]);
            }
            System.out.println();
        }
    }

    static class Elf {

        Point position;
        int directionNumber = 0;

        public Elf(Point position) {
            this.position = position;
        }

        void incrementDirectionNumber() {
            directionNumber++;
            if (directionNumber == DIRECTIONS.size()) directionNumber = 0;
        }

        void showOnGrid() {
            GRID[position.y][position.x] = ELF;
        }

        void propose() {
            List<String> directionList = DIRECTIONS.get(directionNumber);
            for (String key : directionList) {
                Point next = DIRECTION_MAP.get(key);
                if (GRID[position.y + next.y][position.x + next.x] == FREE) {
                    GRID[position.y + next.y][position.x + next.x] = HIP;
                    break;
                }
            }
        }

    }

}
