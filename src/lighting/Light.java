package lighting;

import primitives.Color;

/**
 * Light class represents a generic type of light
 */
abstract class Light {
    private Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
