package unittests.geometries;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Cylinder class
 * @author Ariel
 */
class CylinderTests {
    @Test
    void testGetNormal() {
        Cylinder cd = new Cylinder(new Ray(new Point(0,0,0), new Vector(0,0,1)), 1, 5);
        // ============ Equivalence Partitions Tests ==============
        // TC01: point on the side
        Point p1 = new Point(0,1,2);
        assertEquals(new Vector(0, 1, 0), cd.getNormal(p1));
        // TC02: point on base close to P0
         p1 = new Point(0,0.5,0);
        assertEquals(new Vector(0, 0, -1), cd.getNormal(p1));
        // TC03: point on base far from P0
        p1 = new Point(0,0.5,5);
        assertEquals(new Vector(0, 0, 1), cd.getNormal(p1));
        // =============== Boundary Values Tests ==================
        // TC01: point is center of close base
        p1 = new Point(0,0,0);
        assertEquals(new Vector(0, 0, -1), cd.getNormal(p1));
        // TC02: point is center of far base
        p1 = new Point(0,0,5);
        assertEquals(new Vector(0, 0, 1), cd.getNormal(p1));
    }

}