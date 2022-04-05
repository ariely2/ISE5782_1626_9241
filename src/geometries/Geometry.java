package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Get the material of geometry
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point a);

    public Double3 getKd() {
        return material.kD;
    }

    public Double3 getKs() {
        return material.kS;
    }

    public int getShininess() {
        return material.nShininess;
    }
}
