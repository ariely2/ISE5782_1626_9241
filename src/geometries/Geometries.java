package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Geometries implements Intersectable{
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
    public List<Point> findIntersections(Ray ray) {
        List<Point> cut = new ArrayList<>();
        for (Intersectable shape : shapes) {
            if (shape.findIntersections(ray) != null) {
                cut.addAll(shape.findIntersections(ray));
            }
        }
        if (cut.isEmpty())
            return null;
        return cut;
    }
}
