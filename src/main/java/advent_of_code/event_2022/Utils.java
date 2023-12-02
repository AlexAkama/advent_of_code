package advent_of_code.event_2022;

import java.util.Map;

public class Utils {

    public static final String INPUT = "data.txt";
    public static final String TEST = "test.txt";

    public static final Point[] DELTAS = new Point[]
            {
                    new Point(0, -1),
                    new Point(+1, 0),
                    new Point(0, +1),
                    new Point(-1, 0)
            };

    public static final Point3D[] DELTAS_3D = new Point3D[]
            {
                    new Point3D(-1, 0, 0),
                    new Point3D(1, 0, 0),
                    new Point3D(0, -1, 0),
                    new Point3D(0, 1, 0),
                    new Point3D(0, 0, -1),
                    new Point3D(0, 0, 1)
            };

    public static final Map<String, Point> DIRECTION_MAP = Map.of(
            "N", new Point(0, -1),
            "NE", new Point(1, -1),
            "E", new Point(1, 0),
            "SE", new Point(1, 1),
            "S", new Point(0, 1),
            "SW", new Point(-1, 1),
            "W", new Point(-1, 0),
            "NW", new Point(-1, -1)
    );

    private Utils() {
    }

    public static long sign(long i) {
        if (i == 0) return i;
        if (i < 0) return -1;
        else return 1;
    }

}
