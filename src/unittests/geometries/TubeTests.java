package unittests.geometries;

import geometries.Sphere;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Tube class
 * @author Ariel
 */
class TubeTests {
    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple test here
        Tube tb = new Tube(new Ray(new Point(0,0,0), new Vector(0,0,1)), 1);
        Point p1 = new Point(0,1,2);
        assertEquals(new Vector(0, 1, 0), tb.getNormal(p1));
        // =============== Boundary Values Tests ==================
        // TC11: if the vector from the point to p0 is orthogonal to the axis
        p1 = new Point(0,1,0);
        assertEquals(new Vector(0, 1, 0), tb.getNormal(p1));
    }
}