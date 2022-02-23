package geometries;

import primitives.Point; //correct?
import primitives.Vector;

public interface Geometry {
    Vector getNormal(Point a);
}
