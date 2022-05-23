package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * LightSource interface represents a light source.
 */
public interface LightSource {
    /**
     *  Gets the color of a point based on the light source
     * @param p  - A point
     * @return color of a point based on the light source
     */
    Color getIntensity(Point p);

    double getRadius();

    Point getPosition();
    /**
     * ?
     * @param p - point
     * @return direction from light to point?
     */
    Vector getL(Point p);

    double getDistance(Point point);

    int getNr();
}
