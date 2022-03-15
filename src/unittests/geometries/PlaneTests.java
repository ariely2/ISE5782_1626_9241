package unittests.geometries;

import geometries.Plane;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Ariel
 */
class PlaneTests {
    @Test
    void testPlane() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane creation
        try {
             new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        }
        catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // =============== Boundary Values Tests ==================

        // TC11: two points are the same
        assertThrows(IllegalArgumentException.class ,()->new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 0, 1)));
        // TC12: two points on the same line
        assertThrows(IllegalArgumentException.class ,()->new Plane(new Point(0, 0, 0), new Point(0, 1, 1), new Point(0, 2, 2)));
    }
    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        assertEquals(new Vector(-1 , 0 ,0), plane.getNormal());
    }
}