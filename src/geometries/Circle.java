package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Circle extends Geometry{
    double radius;
    Point center;
    Plane plane;

    /**
     *
     * @param center - center of circle
     * @param normal - vector orthogonal to circle
     * @param radius - radius of circle
     */
    public Circle(Point center, Vector normal, double radius)
    {
        normal = normal.normalize();
        Vector dir1, dir2;
        if(normal.getX() != 0)
            dir1 = new Vector(-normal.getZ(), 0, normal.getX()).normalize().scale(radius);
        else
            dir1 = new Vector(0, normal.getZ(), -normal.getY()).normalize().scale(radius);
        dir2 = normal.crossProduct(dir1).normalize().scale(radius);
        Point a = center.add(dir1);
        Point b = center.add(dir2);
        this.radius = radius;
        this.center = center;
        plane = new Plane(center, a, b);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        var point = plane.findGeoIntersectionsHelper(ray, maxDistance);
        if (point == null) // if it's not cut the plane its don't cut the Triangle
            return null;
        Point p = point.get(0).point;
        if(center.distance(p) <= radius)
            return List.of(new GeoPoint(this, p));
        return null;
    }

    @Override
    public Vector getNormal(Point a) {
        return plane.getNormal(a);
    }
}
