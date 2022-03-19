package geometries;
import primitives.*;
import java.util.List;

/**
 * Intersectable represents geometric bodies that are intersectable
 * @author Ariel
 */
public interface Intersectable {
    /**
     * finds geometric bodies the ray intersects with
     * @param ray
     * @return list of geometries
     */
    public List<Point> findIntersections(Ray ray);
}
