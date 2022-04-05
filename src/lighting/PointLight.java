package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight setKc(double c)
    {
        this.kC = c;
        return this;
    }

    public PointLight setKl(double l)
    {
        this.kL = l;
        return this;
    }

    public PointLight setKq(double q)
    {
        this.kQ = q;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double denominator = kC + kL*distance + kQ*distance*distance;
        return super.getIntensity().reduce(denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
