package renderer;

import lighting.LightSource;
import geometries.Intersectable;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    //private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;

    public RayTracerBasic(Scene a) {
        super(a);
    }

    /**
     * trace ray to get a pixel's color, based on local and global effects
     * @param ray - ray from pixel
     * @return pixel's color
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestIntersection = findClosestIntersection(ray);
        if(closestIntersection == null)
            return scene.background;
        return calcColor(closestIntersection, ray); //getting the color of intersection point
    }

    /**
     * calc the color of a geopoint, also based on the direction from viewpoint to the geopoint
     * @param intersection - the point
     * @param ray - viewing direction
     * @return color of point when looking at it from viewpoint
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL , new Double3(INITIAL_K))
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * calc color of point: local effects+global effects
     * @param intersection - point
     * @param ray - viewing direction
     * @param level - recursion level
     * @param k - const for help
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

    /**
     * calc color of local effects: point geometry emission + light and shadows
     * @param intersection
     * @param ray
     * @param k
     * @return color of local effects
     */
     private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
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
             double nl = alignZero(l.dotProduct(n)); //without these two a test fails.
             if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = calcKtr(intersection.point, lightSource, l, n, nv); //getting value of un-shading
                if(!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) && !(ktr.product(k).equals(MIN_CALC_COLOR_K))) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr); //light color
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity)); //color of light effects on point
                }
            }
         }
        return color;
    }

    /**
     * returns value of shadow shade
     * @param point - point
     * @param ls - light source
     * @param l - light direction
     * @param n - point normal from geometry
     * @param nv
     * @return ktr, value of shadow shade
     */
    private Double3 calcKtr(Point point, LightSource ls, Vector l, Vector n, double nv)
    {
        int nR = ls.getNr();
        if(nR < 2 || ls.getPosition() == null) //soft shadow isn't activated
                return transparency(point, ls, l, n, null);

        LinkedList<Point> points;
        if(ls.getClass() == PointLight.class)
                points = ((PointLight) ls).getPoints(); //getting random points on light source area
        else
            points = ((SpotLight)ls).getPoints();
        Double3 res = Double3.ZERO;
        for (Point p: points) { //getting ktr for each point
            Vector m = point.subtract(p).normalize();
            double nl = alignZero(n.dotProduct(m));
            if (nl*nv > 0)  // sign(nl) == sing(nv)
                res = res.add(transparency(point, ls, m, n, p)); //same n?
        }
        return res.reduce(nR); //getting average ktr
    }

    /**
     * color of global effects: reflection and refraction
     * @param gp - point
     * @param v - viewing direction
     * @param level - recursion level
     * @param k - const helper
     * @return color of global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = material.kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K) && !kkr.equals(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);

        Double3 kkt = material.kT.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K) && !kkt.equals(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2*(n.dotProduct(l))));
        return lightIntensity
                .scale(ks.scale(Math.pow(Math.max(0, r.dotProduct(v.scale(-1.0))), nShininess)));
    }

    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * returns value of unshaded-ness (1 if unshaded, 0 if shaded)
     * @param point - point
     * @param l - vector from light to point
     * @param n - point's normal from geometry
     * @return true if point is unshaded, otherwise false.
     */

    private Double3 transparency(Point point, LightSource ls, Vector l, Vector n, Point lp) {
        Ray lightRay = new Ray(point, l.scale(-1.0), n);
        double lightDistance;
        if(lp == null)
            lightDistance = ls.getDistance(point);
        else //if the light source has an area, we use the distance to the point the ray gets to
            lightDistance = lp.distance(point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return new Double3(1.0);
        Double3 ktr = new Double3(1.0);

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }

    private GeoPoint findClosestIntersection(Ray ray){
        List<Intersectable.GeoPoint> a = scene.geometries.findGeoIntersections(ray); //find all Intersections
        if (a == null) // if there is no intersection
            return null;
        return ray.getClosestGeoPoint(a); //find the closest one
    }

    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(p, r, n);
    }

    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v, n);
    }
}
