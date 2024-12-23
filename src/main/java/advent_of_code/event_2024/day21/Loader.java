package advent_of_code.event_2024.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Loader {

    private static final int DAY = 21;
    private static final String DATA = "src/main/java/advent_of_code/event_2024/day" + DAY + "/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day" + DAY + "/test_2.txt";

    private static final char[][] numpad = new char[][]{
            new char[]{'7', '8', '9'},
            new char[]{'4', '5', '6'},
            new char[]{'1', '2', '3'},
            new char[]{' ', '0', 'A'}
    };

    private static final char[][] robotPanel = new char[][]{
            new char[]{' ', '^', 'A'},
            new char[]{'<', 'v', '>'}
    };

    private static List<String> input;

    public static void main(String[] args) {
        init();
        System.out.println(part1());
        //130490
        //128962
    }

    private static int part1() {
        int sum = 0;
        for (String s : input) {
            String path = calculatePath(s);
            int n = Integer.parseInt(s.replace("A", ""));
            System.out.println(path.length());
            sum += n * path.length();
        }
        return sum;
    }

    private static String calculatePath(String s) {
        char cur = 'A';
        StringBuilder sb = new StringBuilder();
        for (char next : s.toCharArray()) {
            String path = getNextNumpadPress(cur, next);
            sb.append(path);
            cur = next;
        }
        String path1 = sb.toString();
        String path2 = expandToRobot(path1);
        String path3 = expandToRobot(path2);
        return path3;
    }

    private static String expandToRobot(String s) {
        char cur = 'A';
        StringBuilder sb = new StringBuilder();
        for (char next : s.toCharArray()) {
            String path = getNextRobotPanelButton(cur, next);
            sb.append(path);
            cur = next;
        }
        return sb.toString();
    }

    private static String getNextNumpadPress(char cur, char next) {
        return switch (cur) {
            case 'A' -> getNextNumpadPressFromA(next);
            case '0' -> getNextNumpadPressFrom0(next);
            case '1' -> getNextNumpadPressFrom1(next);
            case '2' -> getNextNumpadPressFrom2(next);
            case '3' -> getNextNumpadPressFrom3(next);
            case '4' -> getNextNumpadPressFrom4(next);
            case '5' -> getNextNumpadPressFrom5(next);
            case '6' -> getNextNumpadPressFrom6(next);
            case '7' -> getNextNumpadPressFrom7(next);
            case '8' -> getNextNumpadPressFrom8(next);
            case '9' -> getNextNumpadPressFrom9(next);
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFromA(char next) {
        return switch (next) {
            case 'A' -> "A";
            case '0' -> "<A";
            case '1' -> "^<<A";
            case '2' -> "^<A";
            case '3' -> "^A";
            case '4' -> "^^<<A";
            case '5' -> "^^<A";
            case '6' -> "^^A";
            case '7' -> "^^^<<A";
            case '8' -> "^^^<A";
            case '9' -> "^^^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom0(char next) {
        return switch (next) {
            case 'A' -> ">A";
            case '0' -> "A";
            case '1' -> "^<A";
            case '2' -> "^A";
            case '3' -> "^>A";
            case '4' -> "^^<A";
            case '5' -> "^^A";
            case '6' -> ">^^A";
            case '7' -> "^^^<A";
            case '8' -> "^^^A";
            case '9' -> "^^^>A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom1(char next) {
        return switch (next) {
            case 'A' -> ">>vA";
            case '0' -> ">vA";
            case '1' -> "A";
            case '2' -> ">A";
            case '3' -> ">>A";
            case '4' -> "^A";
            case '5' -> "^>A";
            case '6' -> "^>>A";
            case '7' -> "^^A";
            case '8' -> ">^^A";
            case '9' -> "^^>>A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom2(char next) {
        return switch (next) {
            case 'A' -> "v>A";
            case '0' -> "vA";
            case '1' -> "<A";
            case '2' -> "A";
            case '3' -> ">A";
            case '4' -> "^<A";
            case '5' -> "^A";
            case '6' -> "^>A";
            case '7' -> "^^<A";
            case '8' -> "^^A";
            case '9' -> ">^^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom3(char next) {
        return switch (next) {
            case 'A' -> "vA";
            case '0' -> "v<A";
            case '1' -> "<<A";
            case '2' -> "<A";
            case '3' -> "A";
            case '4' -> "^<<A";
            case '5' -> "^<A";
            case '6' -> "^A";
            case '7' -> "<<^^A";
            case '8' -> "<^^A";
            case '9' -> "^^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom4(char next) {
        return switch (next) {
            case 'A' -> ">>vvA";
            case '0' -> ">vvA";
            case '1' -> "vA";
            case '2' -> "v>A";
            case '3' -> "v>>A";
            case '4' -> "A";
            case '5' -> ">A";
            case '6' -> ">>A";
            case '7' -> "^A";
            case '8' -> "^>A";
            case '9' -> "^>>A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom5(char next) {
        return switch (next) {
            case 'A' -> "vv>A";
            case '0' -> "vvA";
            case '1' -> "v<A";
            case '2' -> "vA";
            case '3' -> "v>A";
            case '4' -> "<A";
            case '5' -> "A";
            case '6' -> ">A";
            case '7' -> "<^A";
            case '8' -> "^A";
            case '9' -> "^>A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom6(char next) {
        return switch (next) {
            case 'A' -> "vvA";
            case '0' -> "vv>A";
            case '1' -> "v<<A";
            case '2' -> "v<A";
            case '3' -> "vA";
            case '4' -> "<<A";
            case '5' -> "<A";
            case '6' -> "A";
            case '7' -> "<<^A";
            case '8' -> "<^A";
            case '9' -> "^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom7(char next) {
        return switch (next) {
            case 'A' -> ">>vvvA";
            case '0' -> ">vvvA";
            case '1' -> "vvA";
            case '2' -> "vv>A";
            case '3' -> "vv>>A";
            case '4' -> "vA";
            case '5' -> "v>A";
            case '6' -> "v>>A";
            case '7' -> "A";
            case '8' -> ">A";
            case '9' -> ">>A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom8(char next) {
        return switch (next) {
            case 'A' -> "vvv>A";
            case '0' -> "vvvA";
            case '1' -> "vv<A";
            case '2' -> "vvA";
            case '3' -> "vv>A";
            case '4' -> "v<A";
            case '5' -> "vA";
            case '6' -> "v>A";
            case '7' -> "<A";
            case '8' -> "A";
            case '9' -> ">A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextNumpadPressFrom9(char next) {
        return switch (next) {
            case 'A' -> "vvvA";
            case '0' -> "vvv<A";
            case '1' -> "vv<<A";
            case '2' -> "vv<A";
            case '3' -> "vvA";
            case '4' -> "v<<A";
            case '5' -> "v>A";
            case '6' -> "vA";
            case '7' -> "<<A";
            case '8' -> "<A";
            case '9' -> "A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextRobotPanelButton(char cur, char next) {
        return switch (cur) {
            case 'A' -> getNextRobotPanelButtonFromA(next);
            case '<' -> getNextRobotPanelButtonFromLeft(next);
            case '^' -> getNextRobotPanelButtonFromUp(next);
            case 'v' -> getNextRobotPanelButtonFromDown(next);
            case '>' -> getNextRobotPanelButtonFromRight(next);
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextRobotPanelButtonFromA(char next) {
        return switch (next) {
            case '<' -> "v<<A";
            case '>' -> "vA";
            case '^' -> "<A";
            case 'v' -> "v<A";
            case 'A' -> "A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextRobotPanelButtonFromLeft(char next) {
        return switch (next) {
            case '<' -> "A";
            case '>' -> ">>A";
            case '^' -> ">^A";
            case 'v' -> ">A";
            case 'A' -> ">>^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextRobotPanelButtonFromUp(char next) {
        return switch (next) {
            case '<' -> "v<A";
            case '>' -> "v>A";
            case '^' -> "A";
            case 'v' -> "vA";
            case 'A' -> ">A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextRobotPanelButtonFromDown(char next) {
        return switch (next) {
            case '<' -> "<A";
            case '>' -> ">A";
            case '^' -> "^A";
            case 'v' -> "A";
            case 'A' -> ">^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getNextRobotPanelButtonFromRight(char next) {
        return switch (next) {
            case '<' -> "<<A";
            case '>' -> "A";
            case '^' -> "<^A";
            case 'v' -> "<A";
            case 'A' -> "^A";
            default -> throw new IllegalArgumentException();
        };
    }

    private static void init(Object... sw) {
        try {
            String filename = sw.length == 0 ? TEST : DATA;
            input = Files.readAllLines(Path.of(filename));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
