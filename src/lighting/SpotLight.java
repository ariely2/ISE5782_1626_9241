package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;

import static primitives.Util.isZero;

public class SpotLight extends PointLight{
     private Vector direction;

     /**
      * Spotlight constructor
      * @param intensity - color
      * @param position - source point of light
      * @param direction - light direction
      */
     public SpotLight(Color intensity, Point position, Vector direction) {
          super(intensity, position);
          this.direction = direction.normalize();
     }

     public SpotLight(Color intensity, Point position, double radius, Vector direction, int n) {
          super(intensity, position, radius, n);
          this.direction = direction;
     }

     @Override
     public Color getIntensity(Point p) {
          Color c = super.getIntensity(p);
          return c.scale(Math.max(0, direction.dotProduct(getL(p))));
     }

     public Vector getDirection(){
          return direction;
     }

     /**
      * getting random points on area of light, for soft shadows
      * @return list of points on area of light
      */
     @Override
     public LinkedList<Point> createPoints() {
          Vector v;
          if(direction.getY()==0)
               v = new Vector(direction.getZ(),0, -direction.getX());
          else
               v = new Vector(0, direction.getZ(), -direction.getY());
          Vector w = v.crossProduct(direction).normalize();

          LinkedList<Point> points = new LinkedList<>(); //array list is better?
          for(int i = 0; i < nR; i++) {
               var angle = Math.random() * Math.PI * 2; //getting a random angle
               var r = Math.random() + Math.random(); //random number between 0 and 2
               if(r > 1)
                    r = 2-r;
               double x = Math.cos(angle) * r;
               double y = Math.sin(angle) * r;
               if(!isZero(x) && !isZero(y))
                    points.add(position.add(w.scale(x).add(v.scale(y)).scale(radius)));
               else if(!isZero(x))
                    points.add(w.scale(x).scale(radius));
               else if(!isZero(y))
                    points.add(v.scale(y).scale(radius));
               else
                    points.add(position);
          }
          this.points = points;
          return points;
     }
}
