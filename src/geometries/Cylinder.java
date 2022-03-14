package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Cylinder  extends Tube implements Geometry{ // does it need to implement geometry?
    final double height;

    public Vector getNormal(Point a) {
        Double t = axisRay.getDir().dotProduct(axisRay.getP0().subtract(a));
        Vector norman;
        if(t == 0)
            norman =  axisRay.getDir().scale((double) -1);
        else if(t == height)
            norman =  axisRay.getDir();
        else
            norman = super.getNormal(a); //ok?
        return norman.normalize();
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
