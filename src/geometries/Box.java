package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;

public class Box {
    private Point min;
    private Point max;

    public Box(Point min , Point max) {
        this.min = min;
        this.max = max;
    }

    public Point getMin() {
        return min;
    }

    public Point getMax() {
        return max;
    }

    public void setMin(Point min) {
        this.min = min;
    }

    public void setMax(Point max) {
        this.max = max;
    }

    public Boolean ifHaveIntersection(Ray ray){
        Double3 p0 = ray.getP0().getXYZ();
        Double3 r = ray.getDir().getXYZ();

        Double3 t1 = min.getXYZ().subtract(p0).reduce(r);
        Double3 t2 = max.getXYZ().subtract(p0).reduce(r);

        double tMinX = Math.min(t1.d1, t2.d1);
        double tMaxX = Math.max(t1.d1, t2.d1);
        double tMinY = Math.min(t1.d2, t2.d2);
        double tMaxY = Math.max(t1.d2, t2.d2);
        double tMinZ = Math.min(t1.d3, t2.d3);
        double tMaxZ = Math.max(t1.d3, t2.d3);

        double tMin = Math.max(tMinX, Math.max(tMinY, tMinZ));
        double tMax = Math.min(tMaxX, Math.min(tMaxY, tMaxZ));

        return tMin <= tMax && tMax >= 0;
    }


}
