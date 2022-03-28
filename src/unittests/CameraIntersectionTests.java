package unittests;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import renderer.Camera;
import primitives.*;

import java.awt.*;


class CameraIntersectionTests{
    static final Point ZERO_POINT = new Point(0, 0, 0);

    private int numIntersections(Intersectable shape, Camera c)
    {
        int num = 0;
        for(int i = 0; i<c.getWidth();i++)
        {
           for(int j = 0; j<c.getHeight(); j++) {
               var points = shape.findIntersections(c.constructRay(3, 3, j, i));
               if (points != null)
                   num += points.size();
           }
        }
        return num;
    }

    @Test
    void TriangleIntersections()
    {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
        Triangle tr = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, numIntersections(tr, camera));

        tr = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, numIntersections(tr, camera));
    }

    @Test
    void PlaneIntersections()
    {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
        Plane plane = new Plane(new Point(0, 5, 0), new Point(1, 0, -2), new Point(-1, 0, -2));
        assertEquals(9, numIntersections(plane, camera));

        plane = new Plane(new Point(1, 0, -10), new Point(-1, 0, -10), new Point(0, 20, 0));//tweak?
        assertEquals(9, numIntersections(plane, camera));

        plane = new Plane(new Point(1, 0, -10), new Point(-1, 0, -10), new Point(0, 5, 0)); //tweak?
        assertEquals(6, numIntersections(plane, camera));
    }

    @Test
    void SphereIntersections()
    {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
        Sphere sphere = new Sphere(new Point (0, 0, -3), 1);
        assertEquals(2, numIntersections(sphere, camera));

        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
        sphere = new Sphere(new Point (0, 0, -2.5), 2.5);
        assertEquals(18, numIntersections(sphere, camera));

        sphere = new Sphere(new Point (0, 0, -2), 2);
        assertEquals(10, numIntersections(sphere, camera));

        camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
        sphere = new Sphere(new Point (0, 0, -2), 4); //works?
        assertEquals(9, numIntersections(sphere, camera));

        sphere = new Sphere(new Point (0, 0,1), 0.5);
        assertEquals(0, numIntersections(sphere, camera));
    }
}
