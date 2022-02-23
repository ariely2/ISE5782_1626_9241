package primitives;

public class Point {
    final Double3 point;

    public Point(double d1, double d2, double d3) {
        this.point = new Double3(d1, d2, d3);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point other)) return false;
        return this.point.equals(other.point);
    }

    @Override
    public String toString() {
        return "Point: " + point.toString();
    }

    public Vector subtract(Point b){
        Double3 c = point.subtract(b.point);
        return new Vector(c.d1, c.d2, c.d3);
    }
    public Point add(Point b){
        Double3 c = point.add(b.point);
        return new Point(c.d1, c.d2, c.d3);
    }
    public Double distanceSquared(Point a, Point b)
    {
        Double3 c = a.point.subtract(b.point);
        return c.d1*c.d1 + c.d2*c.d2 +c.d3*c.d3;
    }
    public Double distance(Point a, Point b)
    {
        Double squared = distanceSquared(a, b);
        return Math.sqrt(squared);
    }
}
