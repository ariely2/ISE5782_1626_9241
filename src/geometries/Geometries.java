package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.in;

public class Geometries extends Intersectable{
    private final List<Intersectable> shapes;

    public Geometries() {
        this.shapes = new ArrayList<>();
    }

    public Geometries(Intersectable...geometries) {
        this.shapes = List.of(geometries); //correct?
    }

    public void add(Intersectable... geometries){
        Collections.addAll(this.shapes, geometries);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> cut = new ArrayList<>(1);
        for (Intersectable shape : shapes) {
            var intersections = shape.findGeoIntersectionsHelper(ray, maxDistance);
            if (intersections != null) {
                List<GeoPoint> p = intersections;
                cut.addAll(p);
            }
        }
        if (cut.isEmpty())
            return null;
        return cut;
    }
}
