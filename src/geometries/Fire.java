package geometries;

import primitives.*;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class Fire extends Geometries{
    /**
     * Fire constructor
     * @param center - center of fire circle
     * @param radius - radius of
     * @param num - num of flames
     */
    public Fire(Point center, double radius, int num){
        super();
        List<Point> gPoints = getPoints(center, radius, 2*num, 0, false); //points on circle
        List<Point> aPoints = getPoints(new Point(center.getX(), center.getY(), center.getZ()+radius), //points in air
                0.75*radius, num, radius/2, true);
        //Material flame = new Material().setKd(0.1).setKt(0.1);
        Material flame = new Material().setKd(0.1).setKt(0.7); //fire flame material
        for(int i = 0; i < num; i+=2) //creating lots of triangles - each flame is a triangle
        {
            Triangle f = new Triangle(gPoints.get(i), gPoints.get(i+1), aPoints.get(i/2));
            //add(f.setEmission(new Color(255, Util.random(0, 160), 0)).setMaterial(flame));
            add(f.setEmission(new Color(255, Util.random(0, 60), 0)).setMaterial(flame));
        }

        Vector v1 = new Vector(1.5, 1, 0).normalize();
        Vector v2 = new Vector(1.5, -1, 0).normalize();
        Ray r1 = new Ray(center.add(v1.scale(-1.2*radius)), v1);
        Ray r2 = new Ray(center.add(v2.scale(-1.2*radius)), v2);
        add(new TreeLog(r1, 3, 2.4*radius)); //adding 2 tree logs in fire
        add(new TreeLog(r2, 3, 2.4*radius));


    }

    /**
     * get list of random points in a circle/on a circle
     * @param center - center of circle
     * @param radius - radius of circle
     * @param num - num of points
     * @param rh - height random min max values
     * @param rr - true if points can be in circle
     * @return list of random points in/on circle
     */
    public List getPoints(Point center, double radius, int num, double rh, boolean rr)
    {
        List<Point> points = new LinkedList<>();
        double cX = center.getX();
        double cY = center.getY();
        double cZ = center.getZ();

        for(int i = 0; i < num; i++) {
            var angle = Math.random() * Math.PI * 2;
            double x, y;
            if(!rr) {
                x = Math.cos(angle) * radius;
                y = Math.sin(angle) * radius;

            }
            else {
                x = Math.cos(angle) * radius * Math.random();
                y = Math.sin(angle) * radius * Math.random();
            }
            points.add(new Point(cX+x, cY+y, cZ+ Util.random(-rh, rh))); //does this go into the get every time?
    }
        return points;
    }
}
