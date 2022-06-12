package geometries;

import primitives.Point;
import primitives.Vector;
//import static java.lang.Math.random;
import static primitives.Util.random;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Forest extends Geometries{
//maybe add as a function in Tree?
    public Forest(Point start, double w, double h, int rows, int cols, double rotate)
    {
        double xd = w/cols;
        double yd = h/rows;
        for (int ix = 0; ix < cols; ix++) {
            for (int iy = 0; iy < rows; iy++) {
                double height = random(30, 46);
                double base = random(height/2, height/1.4);
                double temp;
                if(height > 44)
                    temp = random(0, 5);
                else
                    temp = random(-2, 0);
                double angle = random(0, Math.PI/2);
                double x = xd*(ix+random(-0.4, 0.4));
                double y = yd*(iy+random(-0.4, 0.4));
                double z = 20+temp;

                Point p = start.subtract(new Point(-x*cos(rotate)-y*sin(rotate), -y*cos(rotate) + x*sin(rotate), -z));
                add(new Tree(p, height, base, angle));
            }

        }
    }
}
