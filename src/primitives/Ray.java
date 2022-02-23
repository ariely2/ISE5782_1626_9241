package primitives;

public class Ray {
    Point point;
    Vector vector;
    Ray(Point p, Vector v)
    {
        point = p;
        vector = v.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray other)) return false;
        return this.point.equals(other.point) && this.vector.equals(other.vector);
    }

    @Override
    public String toString() {
        return "Ray: " + point.toString() +
                ", " + vector.toString();
    }

    public Vector getVector() {
        return vector;
    }

    public Point getPoint() {
        return point;
    }
}
