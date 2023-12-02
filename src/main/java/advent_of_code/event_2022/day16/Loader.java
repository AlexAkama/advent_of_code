package advent_of_code.event_2022.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static advent_of_code.event_2022.Utils.TEST;
import static advent_of_code.event_2022.day16.Loader.Status.*;

public class Loader {

    private static final Map<String, Gateway> MAP = new HashMap<>();
    private static final int TIME_LIMIT = 30;

    public static void main(String[] args) throws IOException {
        parse(TEST);

        find("AA", 0, 0);

        System.out.println();
    }

    private static void parse(String fileName) throws IOException {
        fileName = "src/main/java/advent_of_code/event_2022/day16/".concat(fileName);
        java.io.File file = new java.io.File(fileName);
        FileReader fileReader = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            while (line != null) {

                String[] split = line.split(";");
                var name = split[0].substring(6, 8);
                var rate = Integer.parseInt(split[0].substring(23));
                var gatewayString = split[1].substring(split[1].indexOf(" ", 22) + 1);
                MAP.putIfAbsent(name, new Gateway());
                var gateway = MAP.get(name);
                gateway.name = name;
                gateway.rate = rate;

                var gatewaySplit = gatewayString.split(", ");
                for (String linkName : gatewaySplit) {
                    MAP.putIfAbsent(linkName, new Gateway());
                    var link = MAP.get(linkName);
                    gateway.links.add(link);
                }

                line = reader.readLine();
            }
        }
        System.out.println();
    }

    private static int find(String startGatewayName, int time, int releasing) {
        var gateway = MAP.get(startGatewayName);
        if (gateway.rate > 0) {
            if (gateway.status == CLOSE) {

            } else {

            }
        }
        for (Gateway link : gateway.links) {
            find(link.name, time + 1, releasing);
        }

        return 0;
    }

    static class Gateway {

        private String name;
        private Status status = CLOSE;
        private int rate;
        private final List<Gateway> links = new ArrayList<>();


    }

    enum Status {
        CLOSE,
        OPEN
    }

}
