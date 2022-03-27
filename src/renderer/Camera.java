package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    private Point location;
    private Vector to;
    private Vector up;
    private Vector right;
    private double height;
    private double width;
    private double viewPlaneDis;

    public Point getLocation() {
        return location;
    }

    public Vector getTo() {
        return to;
    }

    public Vector getUp() {
        return up;
    }

    public Vector getRight() {
        return right;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getViewPlaneDis() {
        return viewPlaneDis;
    }

    public Camera(Point location, Vector to, Vector up){

        this.location = location;
        if (to.dotProduct(up) != 0) //if vectors not vertical
            throw new IllegalArgumentException();
        this.to = to.normalize();
        this.up = up.normalize();
        this.right = to.crossProduct(up);

    }

    public Camera setVPSize(double width, double height){
        this.height = height;
        this.width = width;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i){
        Point center = location.add(to.scale(viewPlaneDis));

        //calculate Ratio
        double Ry = height/nY;
        double Rx = width/nX;

        //calculate pixel [i ,j] center
        double yi = (-1 * (i - ((double)nY - 1)/2)) * Ry;
        double xj = (j - ((double)nX - 1)/2) * Rx;

        Point Pij = center;
        if (xj != 0)
            Pij = Pij.add(right.scale(xj)); //pij = pc + (xj*right + yi*up)
        if (yi != 0)
            Pij = Pij.add(up.scale(yi));
        Vector Vij = Pij.subtract(location);

        return new Ray(location , Vij);
    }

    public Camera setVPDistance(double distance) {
        this.viewPlaneDis = distance;
        return this;
    }
}
