package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{
    final Point center;
    final double radius;

    @Override
    public Vector getNormal(Point a) {
        return null;
    }

    public Sphere(Point c, double r) {
        center = c;
        radius = r;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere: " +
                "center=" + center +
                ", radius=" + radius;
    }
}
