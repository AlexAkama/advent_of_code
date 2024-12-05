package advent_of_code.event_2023.day20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static advent_of_code.event_2023.day20.Loader.Impulse.HI;
import static advent_of_code.event_2023.day20.Loader.Impulse.LOW;
import static advent_of_code.event_2023.day20.Loader.Status.OFF;
import static advent_of_code.event_2023.day20.Loader.Status.ON;

public class Loader {

    private static final String INPUT = "src/main/java/advent_of_code/event_2023/day../test.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(INPUT);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
        }
    }

    class Communicator {

    }

    class Trigger {

        Status status = ON;

        public Impulse receive(Impulse impulse) {
            if (impulse == LOW) {
                if (status == ON) {
                    status = OFF;
                    return LOW;
                } else {
                    status = ON;
                    return HI;
                }
            }
            return null;
        }

    }

    enum Status {
        OFF,
        ON
    }

    enum Impulse {
        LOW,
        HI
    }

}
