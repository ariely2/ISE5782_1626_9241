package lighting;

import primitives.Color;
import primitives.Point;

/**
 * Light class represents a generic type of light
 */
abstract class Light {
    private Color intensity;
    private double radius = 0;
    private Point position;
    private int nR = 1;
    /**
     * constructor for a light source with an area
     * @param intensity - color intensity
     * @param radius - radius of the light source's circle area
     */
    protected Light(Color intensity, Point position, double radius, int nR) {
        this.intensity = intensity;
        this.position = position;
        this.radius = radius;
        this.nR = nR;
    }

    /**
     * constructor for a light source without an area
     * @param intensity - color intensity
     */
    protected Light(Color intensity, Point position) {
        this.intensity = intensity;
        this.position = position;
    }

    public Color getIntensity() {
        return intensity;
    }

    public double getRadius() {
        return radius;
    }

    public int getNr(){ return nR;}

    public Point getPosition() {
        return position;
    }

}
