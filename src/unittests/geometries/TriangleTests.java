package unittests.geometries;

import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        assertEquals(tr.getNormal(new Point(0, 0.5, 0.5)), new Vector(-1 , 0 ,0));
    }
}