package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight represents an ambient light source.
 */
public class AmbientLight extends Light {

    public AmbientLight(Color c, Double3 d) {
        super(c.scale(d));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }

}
