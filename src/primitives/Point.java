package primitives;

public class Point {
    final Double3 xyz;

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
    public Double distanceSquared(Point a, Point b)
    {
        Double3 c = a.xyz.subtract(b.xyz);
        return c.d1*c.d1 + c.d2*c.d2 +c.d3*c.d3;
    }
    public Double distance(Point a, Point b)
    {
        Double squared = distanceSquared(a, b);
        return Math.sqrt(squared);
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }
}
