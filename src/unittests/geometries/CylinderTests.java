package unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Cylinder class
 * @author Ariel
 */
class CylinderTests {
    @Test
    void testGetNormal() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(0,0,0) , new Vector(1,1,1)) ,2 , 80);
        assertEquals(null, cylinder.getNormal(new Point(0,0,0)));
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================


    }

}