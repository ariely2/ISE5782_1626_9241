package unittests.primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;

import static java.lang.System.out;

/**
 * Unit tests for primitives.Point class
 * @author Ariel
 */

class PointTests {

    @Test
    void subtract() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 1, 0);
        Point pr = p1.subtract(p2);
        assertEquals(pr, new Point(3,3,3));
        // =============== Boundary Values Tests ==================


    }

    @Test
    void add() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 1, 0);
        Point pr = p1.add(p2);
        assertEquals(pr, new Point(3,3,3));
        // =============== Boundary Values Tests ==================


    }

    @Test
    void distanceSquared() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 1, 0);
        Point pr = p1.add(p2);
        assertEquals(pr, new Point(3,3,3));
        // =============== Boundary Values Tests ==================


    }

    @Test
    void distance() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 0, 1);
        double pr = p1.distance(p1,p2);
        assertEquals(pr, 3);
        // =============== Boundary Values Tests ==================


    }
}