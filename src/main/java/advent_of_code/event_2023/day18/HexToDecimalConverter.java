package advent_of_code.event_2023.day18;

public class HexToDecimalConverter {

    private HexToDecimalConverter() {
    }

    public static int convert(String hexNumber) {
        return Integer.parseInt(hexNumber, 16);
    }

}
