package unittests.primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static java.lang.System.out;

/**
 * Unit tests for primitives.Point class
 * @author Ariel
 */

class PointTests {

    @Test
    void subtract() {
        Point p1 = new Point(2, 3, 4);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(1, 2, 3);
        Vector vr = p1.subtract(p2);
        assertEquals(vr, new Vector(1, 1, 1));
        // =============== Boundary Values Tests ==================
        Point p3 = new Point(2, 3, 4);
        assertThrows(IllegalArgumentException.class, ()-> p1.subtract(p3));
    }

    @Test
    void add() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(2, 1, 0);
        assertEquals(p1.add(v2), new Point(3,3,3));
    }

    @Test
    void distanceSquared() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 1, 0);
        Double dr = p1.distanceSquared(p2);
        assertEquals(dr, 11);
        // =============== Boundary Values Tests ==================
        //TC11: Test for zero distance
        assertEquals(p1.distanceSquared(new Point(1,2,3)), 0);
    }

    @Test
    void distance() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 1, 0);
        Double dr = p1.distance(p2);
        assertEquals(dr, Math.sqrt(11));
        // =============== Boundary Values Tests ==================
        //TC11: Test for zero distance
        assertEquals(p1.distance(new Point(1,2,3)), 0);
    }
}