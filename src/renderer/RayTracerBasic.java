package renderer;

import lighting.LightSource;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.util.List;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.isZero;
import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;

    public RayTracerBasic(Scene a) {
        super(a);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closesintersection = findClosestIntersection(ray);
        if(closesintersection == null)
            return scene.background;
        return calcColor(closesintersection, ray);
    }


     private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));

        if (nv == 0)
            return Color.BLACK;

        int nShininess = intersection.geometry.getShininess();
        Double3 kd = intersection.geometry.getKd(), ks = intersection.geometry.getKs();
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(l.dotProduct(n));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                if(unshaded(intersection, l, n, lightSource)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
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

    /**
     * checks if nothing is blocking the light between aa point and a light source
     * @param gp - point
     * @param l - vector from light to point
     * @param n - point's normal from geometry
     * @return true if point is unshaded, otherwise false.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1.0); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;
        intersections.removeIf(x->!x.geometry.getMaterial().kT.equals(0.0));
        return intersections.isEmpty();
    }

    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale((double)-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = ls.getDistance(geoPoint.point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return new Double3(1.0);
        Double3 ktr = new Double3(1.0);

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distanceSquared(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return new Double3(0.0);
            }
        }
        return ktr;
    }

    private GeoPoint findClosestIntersection(Ray ray){
        List<Intersectable.GeoPoint> a = scene.geometries.findGeoIntersections(ray); //find all Intersections
        if (a == null) // if there is no intersection
            return null;
        return ray.getClosestGeoPoint(a); //find the closes one
    }

    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(findClosestIntersection(ray), ray, MAX_CALC_COLOR_LEVEL , new Double3 (INITIAL_K))
                .add(scene.ambientLight.getIntensity());
    }
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = material.kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K) && !kkr.equals(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);

        Double3 kkt = material.kT.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K) && !kkt.equals(MIN_CALC_COLOR_K))
            //check if this is accurate enough
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));

        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        //find the closest intersection
        return (gp == null ? scene.background //if its don't have intersection -> return the background
                : calcColor(gp, ray, level - 1, kkx)).scale(kx); //else -> calc the color
    }

    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(p, r);
    }

    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v);
    }

}
