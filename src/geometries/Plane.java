package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    final Point q0;
    final Vector normal;
    public Plane(Point a, Point b, Point c)
    {
        Vector ab = b.subtract(a);
        Vector bc = c.subtract(b);
        if (ab.normalize().equals(bc.normalize()))  //same line
        {
            throw new IllegalArgumentException("plane error: to point on the same line");
        }
        else if (a.equals(b) || a.equals(c) || b.equals(c))  //same point
        {
            throw new IllegalArgumentException("plane error: to point the same");
        }
        else
        {
            q0 = a;
            normal = null;
        }

        /* Vector ab = a.subtract(b);
        Vector bc = b.subtract(c);
        normal = ab.crossProduct(bc).normalize(); */
    }

    Plane(Point a, Vector b)
    {
        q0 = a;
        normal = b.normalize();
    }
    @Override
    public Vector getNormal(Point point) {
        return normal; //correct?
    }
    public Vector getNormal() {
        return normal; //correct?
    }
    public Point getPoint() {
        return q0; //correct?
    }

    @Override
    public String toString() {
        return "Plane: " + q0.toString() + normal.toString();
    }
}