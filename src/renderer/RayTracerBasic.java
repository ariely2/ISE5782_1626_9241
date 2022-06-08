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
import static primitives.Util.isZero;

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
        GeoPoint closestintersection = findClosestIntersection(ray);
        if(closestintersection == null)
            return scene.background;
        return calcColor(closestintersection, ray);
    }

    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(findClosestIntersection(ray), ray, MAX_CALC_COLOR_LEVEL , new Double3 (INITIAL_K))
                .add(scene.ambientLight.getIntensity());
    }
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

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
                //if(unshaded(intersection, l, n, lightSource)) {
                Double3 ktr = calcKtr(intersection, lightSource, l, n, nv);
                //Double3 ktr = transparency(intersection, lightSource, l, n);
                //Color lightIntensity = lightSource.getIntensity(intersection.point);
                if(!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) && !(ktr.product(k).equals(MIN_CALC_COLOR_K))) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
         }
        return color;
    }


    private Double3 calcKtr(GeoPoint geoPoint, LightSource ls, Vector l, Vector n, double nv)
    {
        int nR = ls.getNr();
        if(nR < 2 || ls.getPosition() == null)
                return transparency(geoPoint, ls, l, n, null);

        LinkedList<Point> points;
        if(ls.getClass() == PointLight.class)
                points = ((PointLight) ls).getPoints();
        else
            points = ((SpotLight)ls).getPoints();
        Double3 res = Double3.ZERO;
        for (Point p: points) {
            Vector m = geoPoint.point.subtract(p).normalize();
            double nl = alignZero(n.dotProduct(m));
            if (nl*nv > 0)  // sign(nl) == sing(nv)
                res = res.add(transparency(geoPoint, ls, m, n, p)); //same n?
        }
        return res.reduce(nR);
    }
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

    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(n.dotProduct(l))));
    }

    /*private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1.0); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        return intersections == null;
    }*/
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1.0); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        if (intersections == null)
            return true;
        intersections.removeIf(x->!(x.geometry.getMaterial().kT.equals(0)));
        return intersections.isEmpty();
    }
    /**
     * returns value of unshaded-ness (1 if unshaded, 0 if shaded)
     * @param geoPoint - point
     * @param l - vector from light to point
     * @param n - point's normal from geometry
     * @return true if point is unshaded, otherwise false.
     */

    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n, Point lp) {
        Vector lightDirection = l.scale(-1.0); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance;
        if(lp == null)
            lightDistance = ls.getDistance(geoPoint.point);
        else //if the light source has an area, we use the distance to the point the ray gets to
            lightDistance = lp.distance(geoPoint.point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return new Double3(1.0);
        Double3 ktr = new Double3(1.0);

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
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
    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(p, r, n);
    }

    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v, n);
    }

    public LinkedList<Point> CirclePoints(int n, double radius, Vector u, Point center)
    {
        Vector v;
        if(u.getY()==0)
            v = new Vector(u.getZ(),0, -u.getX());
        else
            v = new Vector(0, u.getZ(), -u.getY());
        Vector w = v.crossProduct(u).normalize();

        LinkedList<Point> points = new LinkedList<>();
        for(int i = 0; i < n; i++) {
            var angle = Math.random() * Math.PI * 2; //getting a random angle
            var r = Math.random() + Math.random(); //random number between 0 and 2
            if(r > 1)
                r = 2-r;
            double x = Math.cos(angle) * r;
            double y = Math.sin(angle) * r;
            if(!isZero(x) && !isZero(y))
                points.add(center.add(w.scale(x).add(v.scale(y)).scale(radius)));
            else if(!isZero(x))
                points.add(w.scale(x).scale(radius));
            else if(!isZero(y))
                points.add(v.scale(y).scale(radius));
            else
                points.add(center);
        }
        return points;
    }
}
