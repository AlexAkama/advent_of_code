package advent_of_code.event_2022.day01;

import java.io.IOException;

public class Loader {

    public static void main(String[] args) throws IOException {
        advent_of_code.event_2022.day01.Parser parser = new advent_of_code.event_2022.day01.Parser();
        System.out.println(parser.getElfNumber("src/main/java/advent_of_code/day1/data.txt"));
    }

}
