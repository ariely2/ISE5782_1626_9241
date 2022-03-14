package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{
    final Ray axisRay;
    final double radius;
    @Override
    public Vector getNormal(Point a) {
        // according to the formula:
        // t = v * (p - p0)
        // o = p0 + t * v
        // n = normalize(a - center)
        double t = axisRay.getDir().dotProduct(a.subtract(axisRay.getP0()));
        Point o = axisRay.getDir().scale(t).add(axisRay.getP0());
        return a.subtract(o).normalize();
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
