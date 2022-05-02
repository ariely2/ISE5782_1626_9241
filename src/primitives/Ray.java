package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

public class Ray {
    private static final double DELTA = 0.1;

    final Point p0;
    final Vector dir;

    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
    }

    public Ray(Point p, Vector v , Vector normal) {
        //p0 = p;
        //dir = dir1.crossProduct(dir2).normalize();
        Vector delta = normal.scale(normal.dotProduct(v) > 0 ? DELTA : -DELTA);
        p0 = p.add(delta);
        dir = v.normalize();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray other)) return false;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "Ray: " + p0.toString() +
                ", " + dir.toString();
    }

    public Vector getDir() {
        return dir;
    }

    public Point getP0() {
        return p0;
    }

    public Point getPoint(double t)
    {
        return p0.add(dir.scale(t));
    }

    public Point getClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : getClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint getClosestGeoPoint(List<GeoPoint> points)
    {
        if (points.size() == 0)
            return null;

        double close = p0.distanceSquared(points.get(0).point);
        int index = 0;

        for (int i = 1; i < points.size(); i++) {
            if (p0.distanceSquared(points.get(i).point) < close) { // check if there is closer
                close = p0.distanceSquared(points.get(i).point);
                index = i;
            }
        }
        return points.get(index);
    }
}
