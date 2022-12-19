package advent_of_code.event_2022;

public class Utils {

    public static final String INPUT = "data.txt";
    public static final String TEST = "test.txt";

    private static final Point[] DELTAS = new Point[]
            {
                    new Point(0, -1),
                    new Point(+1, 0),
                    new Point(0, +1),
                    new Point(-1, 0)
            };

    private static final Point3D[] DELTAS_3D = new Point3D[]
            {
                    new Point3D(-1, 0, 0),
                    new Point3D(1, 0, 0),
                    new Point3D(0, -1, 0),
                    new Point3D(0, 1, 0),
                    new Point3D(0, 0, -1),
                    new Point3D(0, 0, 1)
            };

    private Utils() {
    }

}
