package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{
    final Ray axisRay;
    final double radius;
    @Override
    public Vector getNormal(Point a) {
        return null;
    }
    public Tube(Ray _ray, double _radius)
    {
        axisRay = _ray;
        radius = _radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube: " + axisRay.toString() +
                ", radius=" + radius;
    }
}
