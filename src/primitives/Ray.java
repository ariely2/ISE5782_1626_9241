package primitives;

public class Ray {
    final Point p0;
    final Vector dir;
    Ray(Point p, Vector v)
    {
        p0 = p;
        dir = v.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray other)) return false;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "Ray: " + p0.toString() +
                ", " + dir.toString();
    }

    public Vector getDir() {
        return dir;
    }

    public Point getP0() {
        return p0;
    }
}
