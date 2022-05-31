package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube { // does it need to implement geometry?
    final double height;

    public Vector getNormal(Point a) {
        /**Double t = 0.0;
        if(!a.equals(axisRay.getP0()))
            t = axisRay.getDir().dotProduct(a.subtract(axisRay.getP0()));
        if(t == 0)
            return axisRay.getDir().scale((double) -1);
        else if(t == height)
            return axisRay.getDir();
        else
            return super.getNormal(a); //ok?*/
        double t = axisRay.getDir().dotProduct(a.subtract(axisRay.getP0()));
        if (isZero(t) || isZero(t - height))
            return axisRay.getDir();
        else
            return super.getNormal(a);
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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> res = new ArrayList<>();
        List<GeoPoint> lst = super.findGeoIntersectionsHelper(ray, maxDistance);
        if (lst != null)
            for (GeoPoint geoPoint : lst) {
                double distance = alignZero(geoPoint.point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()));
                if (distance > 0 && distance <= height)
                    res.add(geoPoint);
            }

        if (res.size() == 0)
            return null;
        return res;
    }
}
