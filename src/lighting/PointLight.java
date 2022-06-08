package lighting;

import primitives.*;

import java.util.LinkedList;

import static primitives.Util.isZero;

public class PointLight extends Light implements LightSource{
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;
    protected LinkedList<Point> points;
    /**
     * Point Light constructor
     * @param intensity - color
     * @param position - source point of light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity, position);
    }

    public PointLight(Color intensity, Point position, double radius, int n) { //need this and other ctor?
        super(intensity, position, radius, n);
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
        return super.getIntensity().scale(1/denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    public LinkedList<Point> getPoints()
    {
        if(points != null)
            return points;
        else
            return createPoints();
    }

    /**
     * getting random points on area of light, for soft shadows
     * @return list of points on area of light
     */
    public LinkedList<Point> createPoints() {
        LinkedList<Point> points = new LinkedList<>();

        for(int i = 0; i < nR; i++) {
            var angle = Math.random() * Math.PI * 2; //getting a random angle
            var theta = Math.acos(Util.random(-1,1)); //random number between
            var u = Math.random();
            var r = radius*Math.cbrt(u);
            double x = r*Math.sin(theta)*Math.cos(angle);
            double y = r*Math.sin(theta)*Math.sin(angle);
            double z = r*Math.cos(theta);
            points.add(position.add(new Vector(x,y,z)));
        }
        this.points = points;
        return points;
    }
}
