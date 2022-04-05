package primitives;

public class Vector extends Point{
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if(xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Error: Zero Vector Entered");
    }

    public Vector(Double3 p) {
        super(p);
        if(xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Error: Zero Vector Entered");
    }

    @Override
    public String toString() {
        return "Vector: " + xyz.toString();
    }

    public Double3 GetPoint(){
        return xyz;
    }
    public Vector subtract(Vector b) {
        return super.subtract(b);
    }
    public Vector add(Vector b) {
        return new Vector(super.add(b).xyz);
    }
    public Vector scale(Double b){
        return new Vector(xyz.scale(b));
    }
    public Double dotProduct(Vector b)
    {
        Double3 d = xyz.product(b.xyz);
        return d.d1+d.d2+d.d3;
    }
    public Vector crossProduct(Vector b)
    {
        return new Vector(xyz.d2*b.xyz.d3- xyz.d3*b.xyz.d2,
                          xyz.d3*b.xyz.d1- xyz.d1*b.xyz.d3,
                          xyz.d1*b.xyz.d2- xyz.d2*b.xyz.d1);
    }
    public Double lengthSquared()
    {
        Point b = new Point(0, 0, 0);
        return this.distanceSquared(b);
    }
    public Double length()
    {
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize()
    {
        Double3 b = xyz.reduce(length());
        return new Vector(b.d1, b.d2, b.d3);
    }
}
