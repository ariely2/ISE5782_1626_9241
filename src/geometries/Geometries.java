package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.in;

public class Geometries extends Intersectable{
    private final List<Intersectable> shapes;
    private final List<Intersectable> InfinityShapes;

    public Geometries() {
        this.InfinityShapes = new ArrayList<>();
        this.shapes = new ArrayList<>();
        box = createBox();
    }

    public Geometries(Intersectable...geometries) {
        this.shapes = new ArrayList<>(); //correct?
        this.InfinityShapes = new ArrayList<>();
        for (Intersectable shape: geometries) {
            if (shape.getBox() == null){
                InfinityShapes.add(shape);
            }
            else
                shapes.add(shape);
        }
        box = createBox();
    }

    public void add(Intersectable... geometries){
        Collections.addAll(this.shapes, geometries);
    }

    @Override
    public Box createBox() {

        if (shapes.isEmpty()) return null;

        Point helpmin = shapes.get(0).getBox().getMin();
        double minx = helpmin.getX();
        double miny = helpmin.getY();
        double minz = helpmin.getZ();

        Point helpmax = shapes.get(0).getBox().getMax();
        double maxx = helpmax.getX();
        double maxy = helpmax.getY();
        double maxz = helpmax.getZ();
        if (shapes.size() > 1) {
            for (int i = 1; i < shapes.size(); i++) {
                helpmin = shapes.get(i).getBox().getMin();
                minx = java.lang.Math.min(helpmin.getX(), minx);
                miny = java.lang.Math.min(helpmin.getY(), miny);
                minz = java.lang.Math.min(helpmin.getZ(), minz);

                helpmax = shapes.get(i).getBox().getMax();
                maxx = java.lang.Math.max(helpmax.getX(), maxx);
                maxy = java.lang.Math.max(helpmax.getY(), maxy);
                maxz = java.lang.Math.max(helpmax.getZ(), maxz);
            }
        }

        return new Box(new Point(minx , miny , minz) , new Point(maxx , maxy , maxz));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> cut = new ArrayList<>(1);
        for (Intersectable Infinityshape : InfinityShapes) {
            var intersections = Infinityshape.findGeoIntersectionsHelper(ray, maxDistance);
            if (intersections != null) {
                List<GeoPoint> p = intersections;
                cut.addAll(p);
            }
        }
        if (box == null ||box.ifHaveIntersection(ray)){
            for (Intersectable shape : shapes) {
                var intersections = shape.findGeoIntersectionsHelper(ray, maxDistance);
                if (intersections != null) {
                    List<GeoPoint> p = intersections;
                    cut.addAll(p);
                }
            }
        }
        if (cut.isEmpty())
            return null;
        return cut;
    }
}
