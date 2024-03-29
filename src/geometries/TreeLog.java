package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;

public class TreeLog extends Geometries{

    /**
     * Tree log constructor (not for tree)
     * @param ray - tree log direction
     * @param radius - log radius
     * @param height - log height
     */
    public TreeLog(Ray ray, double radius, double height) {
        super();
        Color color = new Color(54,21,9);
        Cylinder c = new Cylinder(ray, radius, height);
        c.setEmission(color);
        Material wood = new Material().setKd(0.55).setKs(0.01);
        add(c.setMaterial(wood));

        Circle a = new Circle(ray.getP0(), ray.getDir(), radius); //circles to seal the edges
        a.setEmission(color);
        Vector v = ray.getDir().scale(height);
        Circle b = new Circle(ray.getP0().add(v),ray.getDir(), radius);
        b.setEmission(color);
        add(a.setMaterial(wood));
        add(b.setMaterial(wood));
    }
    public Box createBox() {
        return null;
    }
}
