package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{
    final Point center;
    final double radius;

    @Override
    public Vector getNormal(Point a) {
        return a.subtract(center).normalize();
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
    @Override
    public List<Point> findIntersections(Ray ray) {

        Vector u = center.subtract(ray.getP0());
        double tm = u.dotProduct(ray.getDir());
        double d = Math.sqrt(( u.length() * u.length() ) - ( tm * tm ));

        if (d >= radius) { // if (d ≥ r) there are no intersections
            return null;
        }

        double th = Math.sqrt(( u.length() * u.length() ) - d*d);
        // two options
        double t1 = tm + th;
        double t2 = tm - th;

        //add to point list
        List<Point> cut = null;
        if (t1 > 0) {
            assert false;
            cut.add(ray.getPoint(t1));
        }
        if (t2 > 0) {
            assert false;
            cut.add(ray.getPoint(t2));
        }

        return cut;
    }
}
