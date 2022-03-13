package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 * @author Ariel
 */
class VectorTests {

    @Test
    void subtract() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(2, 1, 0);
        Vector vr = v1.subtract(v2);
        assertEquals(vr, new Vector(3,3,3));
        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(1,2,3);
        assertThrows(IllegalArgumentException.class, ()->v1.subtract(v3));
    }

    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(2, 1, 0);
        Vector vr = v1.add(v2);
        assertEquals(vr, new Vector(3,3,3));
        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(-1,-2,-3);
        assertThrows(IllegalArgumentException.class, ()->v1.add(v3));
    }

    @Test
    void scale() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.scale(2.0);
        assertEquals(vr, new Vector(2,4,6));
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, ()->v1.scale(0.0));
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-2, -4, -6);
        Double dr = v1.dotProduct(v2);
        assertEquals(dr, -28);
        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0, 3, -2);
        assertTrue(Util.isZero(v1.dotProduct(v3)));
    }

    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(Util.isZero(vr.dotProduct(v1)));
        assertTrue(Util.isZero(vr.dotProduct(v2)));
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3));
    }

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Double dr = v1.lengthSquared();
        assertEquals(dr, 14);
    }

    @Test
    void length() {
        Vector v1 = new Vector(0, 3, 4);
        // ============ Equivalence Partitions Tests ==============
        Double dr = v1.length();
        assertEquals(dr, 5);
    }

    @Test
    void normalize() {
        Vector v1 = new Vector(1, 1, Math.sqrt(2));
        // ============ Equivalence Partitions Tests ==============
        v1.normalize();
        assertEquals(new Vector(0.5, 0.5, Math.sqrt(0.5)), v1);
        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0.5, 0.5, Math.sqrt(0.5));
        Vector v4 = new Vector(0.5, 0.5, Math.sqrt(0.5));
        v3.normalize();
        assertEquals(v4, v3);

        final Vector v5 = new Vector(1, 2, 3);
        final Vector v6 = v5.normalize();
        assertEquals(v5.length(), 1);

        assertThrows(IllegalArgumentException.class, () -> v5.crossProduct(v6));

        assertFalse(v5.dotProduct(v6) < 0);
    }
}