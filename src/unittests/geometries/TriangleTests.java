package unittests.geometries;

import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        assertEquals(tr.getNormal(new Point(0, 0.5, 0.5)), new Vector(-1 , 0 ,0));
    }
    @Test
    void findIntersections()
    {
        Triangle tr = new Triangle(new Point(-1, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the triangle (inside)
        Point p1 = new Point(0, 0.5, 0);
        List<Point> result = tr.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(1, -0.5, -1)));
        assertEquals(1, result.size());
        assertEquals(List.of(p1), result);
        // TC02: Ray intersects a point between continuances, of two edges that start at the same vertex
        assertNull(tr.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, 0, -1))));

        // TC03: Ray intersects a point between continuances that start at the same vertex
        assertNull(tr.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(-2, -2, -1))));

        // =============== Boundary Values Tests ==================
        // TC11: Ray intersects one of the triangle's edges
        assertNull(tr.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(1.5, -1, -1))));

        // TC12: Ray intersects one of the triangle's vertices
        assertNull(tr.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, -1, -1))));

        // TC13: Ray intersects the continuance of one of the triangle's edges
        assertNull(tr.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(-0.5, -1, -1))));
    }
}