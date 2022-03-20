package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable{
    private List<Intersectable> shapes;

    public Geometries() {
        this.shapes = new ArrayList<>();
    }

    public Geometries(Intersectable...geometries) {
      //  this.shapes = List.of(geometries); //correct?
    }

    public void add(Intersectable... geometries){
       // this.shapes.add(geometries);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
