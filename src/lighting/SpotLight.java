package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
     final private Vector direction;

     public SpotLight(Color intensity, Point position, Vector direction) {
          super(intensity, position);
          this.direction = direction;
     }

     @Override
     public Color getIntensity(Point p) {
          Color c = super.getIntensity(p);
          return c.scale(Math.max(0, direction.dotProduct(getL(p))));
     }

     @Override
     public Vector getL(Point p) {
          return super.getL(p);
     }
}
