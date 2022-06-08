package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Tree extends Geometries{

    /**
     * Tree constructor
     * @param origin - point at top of tree
     * @param height - height of each tree "level" (pyramid)
     * @param base - base length of each tree "level" (pyramid)
     * @param angle - rotation angle
     */
    public Tree(Point origin, double height, double base, double angle)
    {
        super();
        Material foliage = new Material().setKd(0.4).setKs(0.3).setShininess(20);
        Material wood = new Material().setKd(0.001).setKs(0.1);
        for(int i = 0; i < 3; i++)
        {
            add(Pyramid(origin, height, base, angle, new Color(0, 100, 0), foliage));
            origin = origin.subtract(new Point(0,0,base/3)); //going down for the next tree level
        }
        Cylinder trunk = new Cylinder(new Ray(origin, new Vector(0, 0, -1)), height/12, base*2);
        trunk.setEmission(new Color(101, 56, 24));
        add(trunk.setMaterial(wood));
    }

    /**
     * Pyramid constructor for tree levels
     * @param origin - top points
     * @param height - height
     * @param base - base length
     * @param angle - rotation angle
     * @param color  - pyramid color
     * @param material - pyramid material
     * @return pyramid geometry
     */
    public Geometries Pyramid(Point origin, double height, double base, double angle, Color color, Material material)
    {
        Geometries pyramid = new Geometries();
        Point[] points = new Point[4];
        for(int i = 0; i < 4; i++)
        {
            double r = 2*Math.PI*i/4;
            double x = cos(r)*base/2;
            double y = sin(r)*base/2;
            double z = -height/2;
            points[i] = origin.subtract(new Point(-x*cos(angle)-y*sin(angle), -y*cos(angle) + x*sin(angle), -z));
        }
        for(int i = 0; i < 4; i++) {
            int j = (i + 1) % 4;
            pyramid.add(new Triangle(points[i], points[j], origin).setEmission(color).setMaterial(material));
        }
        return pyramid;
    }
}
