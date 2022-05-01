package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight represents an ambient light source.
 */
public class AmbientLight extends Light {

    /**
     * Ambient Light constructor
     * @param c - color
     * @param d - attenuation of color
     */
    public AmbientLight(Color c, Double3 d) {
        super(c.scale(d));
    }

    /**
     * default constructor of Ambient Light  - darkness (black light)
      */
    public AmbientLight() {
        super(Color.BLACK);
    }

}
