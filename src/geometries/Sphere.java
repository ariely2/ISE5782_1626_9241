package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends Geometry{
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double tm, d;
        if (!center.equals(ray.getP0())) {
            Vector u = center.subtract(ray.getP0());
            tm = u.dotProduct(ray.getDir());
            d = Math.sqrt((u.lengthSquared()) - (tm * tm));
        } else {
            tm = 0;
            d = 0;
        }

        if (d >= radius) // if (d ≥ r) there are no intersections
            return null;

        double th = Math.sqrt((radius * radius) - d * d);
        // two options
        double t1 = tm + th;
        double t2 = tm - th;

        //add to point list
        List<GeoPoint> cuts = new ArrayList<>(1);
        boolean intersects = false;
        if (t1 > 0 && Util.alignZero(t1 - maxDistance) <= 0) {
            intersects = true;
            Point p = ray.getPoint(t1);
            cuts.add(new GeoPoint(this, p));
        }
        if (t2 > 0 && Util.alignZero(t2 - maxDistance) <= 0) {
            intersects = true;
            Point p = ray.getPoint(t2);
            cuts.add(new GeoPoint(this, p));
        }

        if (intersects)
            return cuts;

        return null;
    }
}
