package unittests.primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class RayTests {
    @Test
    void testClosestPoint()
    {
        Ray ray = new Ray(new Point(1, 1, 0), new Vector(0, 2, 1));
        Point a = new Point(7, 2, 0);
        Point b = new Point(8, 3, -2);
        Point c = new Point(3, 2, 1);
        // ============ Equivalence Partitions Tests ==============
        //TC01: closest point in the middle of list
        assertEquals(c, ray.getClosestPoint(List.of(a, c, b)));

        // =============== Boundary Values Tests ==================
        //TC11: empty list
        List<Point> d = new ArrayList<>();
        assertNull(ray.getClosestPoint(d));

        //TC12: closest point at the beginning of list
        assertEquals(c, ray.getClosestPoint(List.of(c, b, a)));

        //TC13: closest point at the end of list
        assertEquals(c, ray.getClosestPoint(List.of(a, b, c)));
    }
}
