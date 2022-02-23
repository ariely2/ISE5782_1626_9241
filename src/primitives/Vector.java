package primitives;

public class Vector extends Point{
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if(point.equals(Double3.ZERO)) throw new IllegalArgumentException("Error: Zero Vector Entered");
    }

    @Override
    public String toString() {
        return "Vector: " + point.toString();
    }

    public Double3 GetPoint(){
        return point;
    }
    public Vector subtract(Vector b) {
        return super.subtract(b);
    }
    public Vector add(Vector b) {
        Double3 a = super.add(b).point;
        return new Vector(a.d1, a.d2, a.d3);
    }
    public Vector scale(Double b){
        Double3 c = point.scale(b);
        return new Vector(c.d1, c.d2, c.d3);
    }
    public Double dotProduct(Vector b)
    {
        Double3 d = point.product(b.point);
        return d.d1+d.d2+d.d3;
    }
    public Vector crossProduct(Vector b)
    {
        return new Vector(point.d2*b.point.d3-point.d3*b.point.d2,
                          point.d3*b.point.d1-point.d1*b.point.d3,
                          point.d1*b.point.d2-point.d2*b.point.d1);
    }
    public Double lengthSquared()
    {
        Point b = new Point(0, 0, 0);
        return distanceSquared(this, b);
    }
    public Double length()
    {
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize()
    {
        Double3 b = point.reduce(length());
        return new Vector(b.d1, b.d2, b.d3);
    }
}
