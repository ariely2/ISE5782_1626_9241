package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene a) {
        super(a);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null)
            return scene.background;
        return calcColor(ray.getClosestPoint(intersections));
    }

    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
