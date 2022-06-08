package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.isZero;
import java.util.stream.*;
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
    private boolean AntiAliasing = false;
    private int rezInX = 9;
    private int rezInY = 9;

    public Camera(Point location, Vector to, Vector up){
        this.location = location;
        if (to.dotProduct(up) != 0) //if vectors aren't vertical
            throw new IllegalArgumentException();
        this.to = to.normalize();
        this.up = up.normalize();
        this.right = to.crossProduct(up);
    }

    public boolean isAntiAliasing() {
        return AntiAliasing;
    }

    public void setAntiAliasing(boolean antiAliasing) {
        AntiAliasing = antiAliasing;
    }

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

    public int getRezInX() {
        return rezInX;
    }

    public int getRezInY() {
        return rezInY;
    }

    public void setRezInX(int rezInX) {
        this.rezInX = rezInX;
    }

    public void setRezInY(int rezInY) {
        this.rezInY = rezInY;
    }

    public Camera setImageWriter(ImageWriter imageWrite) {
        this.imageWrite = imageWrite;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public void renderImage(){
        if (this.location == null || this.to == null || this.up == null ||
                this.right == null || this.imageWrite == null || this.height == 0 ||
                this.width == 0 || this.viewPlaneDis == 0 || this.rayTracer == null) // if one of the fields is Empty
            throw new MissingResourceException("a field is empty", "Camera", "");

        Pixel.initialize(imageWrite.getNy(), imageWrite.getNx(), 6);
        IntStream.range(0, imageWrite.getNy()).parallel().forEach(i -> {
            IntStream.range(0, imageWrite.getNx()).parallel().forEach(j -> {

                imageWrite.writePixel(i, j, castRay(imageWrite.getNx(), imageWrite.getNy(), i, j));


                Pixel.pixelDone();
                Pixel.printPixel();
            });
        });

    }


    public void printGrid(int interval, Color color)
    {
        if(imageWrite == null)
            throw new MissingResourceException("Image Writer is null", "ImageWriter", "imageWrite");
        for(int i = 0; i<imageWrite.getNx();i+=interval) {
            for (int j = 0; j < imageWrite.getNy(); j++) {
                    imageWrite.writePixel(i, j, color); //paint the pixel in grid color
            }
        }
        for(int i = 0; i<imageWrite.getNx();i++) {
            for (int j = 0; j < imageWrite.getNy(); j+=interval) {
                imageWrite.writePixel(i, j, color); //paint the pixel in grid color
            }
        }
    }

    public void writeToImage()
    {
        if(imageWrite == null)
            throw new MissingResourceException("Image Writer is null", "ImageWriter", "imageWrite");
        imageWrite.writeToImage();
    }

    public Camera setVPSize(double width, double height){
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * creates a ray from camera to pixel on view plane
     * @param nX num horizontal pixels
     * @param nY num vertical pixels
     * @param j horizontal index (num of column)
     * @param i vertical index (num of row)
     * @return Ray to pixel
     */
    public List<Ray> constructRay(int nX, int nY, int j, int i){
        List<Ray> rays = new ArrayList<>();
        Point center = location.add(to.scale(viewPlaneDis));

        //calculate Ratio
        double Ry = height/nY;
        double Rx = width/nX;

        //calculate pixel [i ,j] center
        double yi = (-1 * (i - ((double)nY - 1)/2)) * Ry;
        double xj = (j - ((double)nX - 1)/2) * Rx;

        Point Pij = center;
        //pij = pc + (xj*right + yi*up)
        if (!isZero(xj) )
            Pij = Pij.add(right.scale(xj));
        if (!isZero(yi))
            Pij = Pij.add(up.scale(yi));
        if (AntiAliasing){ //if we want higher quality
            List<Point> points = createPointsAround(rezInX , rezInY , Pij , Rx , Ry); //add new points
            for (Point point : points) {
                Vector help = point.subtract(location); //calculate vector
                rays.add(new Ray(point, help)); //add to the list
            }
            return rays;
        }


        Vector Vij = Pij.subtract(location);
        rays.add(new Ray(location , Vij));
        return rays;
    }
    public List<Point> createPointsAround(int nX, int nY , Point p, double rX, double rY) {
        List<Point> points = new ArrayList<>(nX * nY);
        points.add(p);
        for (double j = -rY / 2; j < rY / 2; j += rY / nY) {
            for (double i = -rX / 2; i < rX / 2; i += rX / nX) {
                Point ijP = p;
                if (!isZero(j))
                    ijP = ijP.add(right.scale(j));
                if (!isZero(i))
                    ijP = ijP.add(up.scale(i));
                points.add(ijP);
            }
        }
        return points;
    }
    public Camera setVPDistance(double distance) {
        this.viewPlaneDis = distance;
        return this;
    }

    public Color castRay(int Nx, int Ny, int i, int j)
    {
        List<Ray> toPixel = constructRay(Nx, Ny, i, j);
        Color sum = Color.BLACK;
        Color avrage;
        for (Ray ray : toPixel) {
            sum = sum.add(rayTracer.traceRay(ray));
        }
        avrage = sum.reduce(toPixel.size());

        return avrage; //return color
    }
}
