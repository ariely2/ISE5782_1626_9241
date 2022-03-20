package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Cylinder extends Tube implements Geometry{ // does it need to implement geometry?
    final double height;

    public Vector getNormal(Point a) {
        Double t = 0.0;
        if(!a.equals(axisRay.getP0()))
            t = axisRay.getDir().dotProduct(a.subtract(axisRay.getP0()));
        if(t == 0)
            return axisRay.getDir().scale((double) -1);
        else if(t == height)
            return axisRay.getDir();
        else
            return super.getNormal(a); //ok?
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
