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
        assertEquals(new Vector(3.0/5, 0, 4.0/5), sp.getNormal(p1));
    }
    @Test
    void findIntersections()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects sphere twice
        // TC02: Ray starts in sphere
        // TC03: Ray doesn't intersect sphere
        // TC04: Ray doesn't intersect sphere, but if it's direction was the opposite it would intersect

        // =============== Boundary Values Tests ==================
        // **** Group: p0 is in the sphere's edge
        // TC11: Ray intersects sphere
        // TC12: Ray doesn't intersect sphere

        // **** Group: Ray is tangent to sphere
        // TC13: Ray starts before sphere
        // TC14: Ray starts after sphere
        // TC14: Ray starts at sphere

        // **** Group: Ray crosses (or would've crossed) sphere's center
        // TC13: Ray starts before sphere
        // TC14: Ray starts after sphere
        // TC14: Ray starts at sphere
    }
}