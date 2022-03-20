package geometries;

import primitives.*;

import java.util.List;

public class Plane implements Geometry{
    final Point q0;
    final Vector normal;
    public Plane(Point a, Point b, Point c)
    {
        if (a.equals(b) || a.equals(c) || b.equals(c))  //Two points are the same
            throw new IllegalArgumentException("plane error: two points are the same");
        Vector ab = b.subtract(a);
        Vector bc = c.subtract(b);
        if (ab.normalize().equals(bc.normalize()))  //same line
        {
            throw new IllegalArgumentException("plane error: two points on the same line");
        }
        else
        {
            q0 = a;
            normal = ab.crossProduct(bc).normalize();
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
        return normal;
    }
    public Vector getNormal() {
        return normal;
    }
    public Point getPoint() {
        return q0; //correct?
    }

    @Override
    public String toString() {
        return "Plane: " + q0.toString() + normal.toString();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        double numerator = normal.dotProduct(getPoint().subtract(ray.getP0()));
        double denominator = normal.dotProduct(ray.getDir());
        double t = numerator / denominator;

        if (t > 0){ //there is a point
            Point cut = ray.getP0().add(ray.getDir().scale(t)); //according to the formula: p0 + t*v
            return (List<Point>) cut;
        }

        return null; //not allowed t <= 0 because its ray
    }
}