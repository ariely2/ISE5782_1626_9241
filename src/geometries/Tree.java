package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Tree extends Geometries{

    public Tree(Point origin, double height, double base, double angle)
    {
        super();
        Material m = new Material().setKd(0.5).setKs(0.5).setShininess(20);
        for(int i = 0; i < 3; i++)
        {
            add(Pyramid(origin, height, base, angle, new Color(0, 100, 0), m));
            origin = origin.subtract(new Point(0,0,base/3));
        }
    }

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
