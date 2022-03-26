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

    }

    public Camera setVPSize(double width, double height){
        this.height = height;
        this.width = width;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }
}
