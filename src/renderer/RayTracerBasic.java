package renderer;

import lighting.LightSource;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.util.List;
import geometries.Intersectable.GeoPoint;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene a) {
        super(a);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return scene.background;
        return calcColor(ray.getClosestGeoPoint(intersections), ray);
    }

    private Color calcColor(GeoPoint point, Ray ray) {
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission()).add(calcLocalEffects(point, ray));
    }

     private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = Util.alignZero(n.dotProduct(v));

        if (nv == 0)
            return Color.BLACK;

        int nShininess = intersection.geometry.getShininess();
        Double3 kd = intersection.geometry.getKd(), ks = intersection.geometry.getKs();
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(l.dotProduct(n));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2*(n.dotProduct(l))));
        return lightIntensity
                .scale(ks.scale(Math.pow(Math.max(0, r.dotProduct(v.scale(-1.0))), nShininess)));
    }

    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(n.dotProduct(l))));
    }
}
