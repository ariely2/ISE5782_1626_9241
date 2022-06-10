package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Intersectable represents geometric bodies that are intersectable
 * @author Ariel
 */
public abstract class Intersectable {
    public Box box;
    public static class GeoPoint {
        public final Geometry geometry;
        public final Point point;
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    public abstract Box createBox();

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    public Box getBox() {
        return box;
    }

    /**
     * finds geometric bodies the ray intersects with
     * @param ray
     * @return list of geometries
     */
    public List<Point> findIntersections(Ray ray) {
        if (box == null || box.ifHaveIntersection(ray)){ //if have intersection with box
            var geoList = findGeoIntersections(ray);
            return geoList == null ? null :geoList.stream().map(gp -> gp.point).toList();
        }
        return null;
    }

}


