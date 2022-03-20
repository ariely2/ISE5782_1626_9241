package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Triangle extends Polygon{
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        if (plane.findIntersections(ray) == null) // if it's not cut the plane its don't cut the Triangle
            return null;

        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        //The point is inside if all 𝒗 ∙ 𝒏i have the same sign (+/-)

        if (ray.getDir().dotProduct(n1) < 0 &&
                ray.getDir().dotProduct(n2) < 0 &&
                ray.getDir().dotProduct(n3) < 0) // if its same sign
            return plane.findIntersections(ray);

        else if (ray.getDir().dotProduct(n1) > 0 &&
                ray.getDir().dotProduct(n2) > 0 &&
                ray.getDir().dotProduct(n3) > 0 ) // if its same sign
            return plane.findIntersections(ray);

        return null;
    }
}
