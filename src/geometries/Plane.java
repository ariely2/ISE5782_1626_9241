package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    Point q0;
    Vector normal;
    Plane(Point a, Point b, Point c)
    {
        q0 = a;
        normal = null;
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
        return "Plane: " +
                "Point=" + q0 +
                ", Normal=" + normal;
    }
}
