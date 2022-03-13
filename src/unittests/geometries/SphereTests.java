package unittests.geometries;

import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Sphere class
 * @author Ariel
 */
class SphereTests {
    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(new Point(1,2,3), 5);
        Point p1 = new Point(4, 2, 7);
        assertEquals(p1.subtract(sp.getCenter()), sp.getNormal(p1));
    }
}