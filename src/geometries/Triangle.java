package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Triangle extends Polygon{
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
