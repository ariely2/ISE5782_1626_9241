package primitives;

public class Point {
    final Double3 xyz;
    public static Point ZERO = new Point(0,0,0);
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    public Point(Double3 p) {
        xyz = p;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point other)) return false;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Point: " +
                "xyz=" + xyz.toString();
    }

    public Vector subtract(Point b){
        return new Vector(xyz.subtract(b.xyz));
    }
    public Point add(Vector b){
        return new Point(xyz.add(b.xyz));
    }
    public Double distanceSquared(Point a)
    {
        Double3 c = this.xyz.subtract(a.xyz);
        return c.d1*c.d1 + c.d2*c.d2 +c.d3*c.d3;
    }
    public Double distance(Point a)
    {
        Double squared = this.distanceSquared(a);
        return Math.sqrt(squared);
    }
    public double getX() {
        return xyz.d1;
    }

    public Double getY() {
        return xyz.d2;
    }

    public Double getZ() {
        return xyz.d3;
    }

    public Double3 getXYZ(){return xyz;}
}
