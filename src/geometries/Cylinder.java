package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Cylinder  extends Tube implements Geometry{ //need to implement geometry?
    final double height;

    public Vector getNormal(Point a) {//need to have this here or is the one in tube enouogh?
        return null;
    }
    public Cylinder(Ray _ray, double _radius, double _height)
    {
        super(_ray, _radius);
        height = _height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder: " +
                "height = " + height +
                ", axisRay = " + axisRay.toString() +
                ", radius = " + radius;
    }
}
