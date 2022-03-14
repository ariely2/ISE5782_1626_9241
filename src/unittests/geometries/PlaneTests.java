package unittests.geometries;

import geometries.Plane;
import primitives.Point;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for geometries.Plane class
 * @author Ariel
 */
class PlaneTests {
    void testGetNormal() {
        Plane plane = new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        assertEquals(plane.getNormal(), new Vector(1 , 0 ,0));

        plane = new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 0, 1));
        assertThrows(IllegalArgumentException.class , plane::getNormal);

        plane = new Plane(new Point(0, 0, 0), new Point(0, 1, 1), new Point(0, 2, 2));
        assertThrows(IllegalArgumentException.class , plane::getNormal);

    }
}