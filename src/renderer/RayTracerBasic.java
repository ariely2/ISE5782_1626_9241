package renderer;

import lighting.LightSource;
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
        List<Point> intersections = scene.geometries.findGeoIntersectionsHelper(ray);
        if(intersections == null)
            return scene.background;
        return calcColor(ray.getClosestPoint(intersections));
    }

    /* private Color calcLocalEffects(Point intersection, Ray ray) {
        Vector v = ray.getDir (); Vector n = intersection.geometry.getNormal();
        double nv = alignZero(n.dotProduct(v); if (nv == 0) return Color.BLACK;
        int nShininess = intersection.geometry.getShininess();
        double kd = intersection.geometry.getKd(), ks = intersection.geometry.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l);
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    } */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
