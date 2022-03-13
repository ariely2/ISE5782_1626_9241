package unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Cylinder class
 * @author Ariel
 */
class CylinderTests {
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        /*Cylinder sp = new Cylinder(new Ray(new Point(1,2,3), new Vector(1,2,0)), 5, 10);
        // TC01: normal on the side
        Point p1 = new Point(4, 2, 7);
        assertEquals(p1.subtract(sp.getCenter()), sp.getNormal(p1));
        // TC02: normal on close base
        Point p2 = new Point(4, 2, 7);
        assertEquals(p2.subtract(sp.getCenter()), sp.getNormal(p1));
        // TC03: normal on close base
        Point p2 = new Point(4, 2, 7);
        assertEquals(p2.subtract(sp.getCenter()), sp.getNormal(p1));*/
        // =============== Boundary Values Tests ==================

    }

}