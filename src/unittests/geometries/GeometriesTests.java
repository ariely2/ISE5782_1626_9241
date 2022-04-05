package unittests.geometries;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1);
        Triangle tr = new Triangle(new Point(-1, 0, 0), new Point(0, 1, 0), new Point(1, 0, 0));
        Plane plane = new Plane(new Point(3, 1, 0), new Point(3, -1, 0), new Point(4, 0, 1));
        Geometries geometries = new Geometries(sphere, tr, plane);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects some of the geometries
        List<Point> result = geometries.findIntersections(new Ray(new Point(1.5, 0, 1), new Vector(0, 0.5, -1)));
        assertEquals(3, result.size()); // 2 with sphere, 1 with plane, 0 with triangle

        // =============== Boundary Values Tests ==================
        // TC11: Ray intersects one geometry
        result = geometries.findIntersections(new Ray(new Point(2.5, 0, 1), new Vector(0, 0.5, -1)));
        assertEquals(1, result.size());

        // TC12: Ray intersects all geometries
        result = geometries.findIntersections(new Ray(new Point(0.5, 0, 1), new Vector(0, 0.5, -1)));
        assertEquals(3, result.size());

        // TC13: Ray intersects no geometries
        assertNull(geometries.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(-1, 0, 2))));

        // TC13: Ray intersects no geometries (empty composite)
        geometries = new Geometries();
        assertNull(geometries.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, -1))));
    }
}