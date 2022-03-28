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
    private ImageWriter imageWrite;
    private RayTracerBase rayTracer;

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

    public Camera setImageWrite(ImageWriter imageWrite) {
        this.imageWrite = imageWrite;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public Camera renderImage(){

        if (this.location == null ||
                this.to == null ||
                this.up == null ||
                this.right == null ||
                this.imageWrite == null ||
                this.height == 0 ||
                this.width == 0 ||
                this.viewPlaneDis == 0 ||
                this.rayTracer == null) // if one of the objects is Empty
            throw new UnsupportedOperationException();

        return null;
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

        //pij = pc + (xj*right + yi*up)
        if (xj != 0)
            Pij = Pij.add(right.scale(xj));
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
