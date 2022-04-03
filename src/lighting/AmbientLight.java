package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    Color intensity;

    public AmbientLight(Color c, Double3 d) {
        this.intensity = c.scale(d);
    }

    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }
}
