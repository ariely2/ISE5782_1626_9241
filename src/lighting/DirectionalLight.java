package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    /**
     * constructor of Directional Light
     * @param intensity - color
     * @param direction - light direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity, null);
        this.direction = direction.normalize();
    }
    @Override //need this?
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
