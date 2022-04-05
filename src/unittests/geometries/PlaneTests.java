package unittests.geometries;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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
        assertDoesNotThrow(()->new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)));
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

    @Test
    void testFindIntersections()
    {
        Plane plane = new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray doesn't intersect the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 0), new Vector(-5, 5, 5))));
        // TC01: Ray intersects the plane
        Point p1 = new Point(0, 1, 1);
        List<Point> result = plane.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(5, 5, 5)));
        assertEquals(1, result.size());
        assertEquals(List.of(p1), result);
        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: Ray is included in the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 0.5, 0), new Vector(0, 2, 1))));

        // TC12: Ray is not included in the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 0), new Vector(0, 1,0))));

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray is before the plane
        p1 = new Point(0,0.5,0);
        result = plane.findIntersections(new Ray(new Point(-1, 0.5, 0), new Vector(1,0,0)));
        assertEquals(1, result.size());
        assertEquals(List.of(p1), result);

        // TC14: Ray is after the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 0), new Vector(-1, 0,0))));

        // TC15: Ray is in the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 0.5, 0), new Vector(-1,0,0))));

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0,0.5,0), new Vector(-5,5,5))));

        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in point which appears as reference point in the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 1, 0), new Vector(-5, 5, 5))));

    }
}