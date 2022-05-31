package unittests.renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.red;

public class CampfireTests {
    private Scene scene = new Scene("Test scene");


    @Test
    public void PyramidTest()
    {
        Camera camera = new Camera(new Point(0, -50, 50), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(150, 150).setVPDistance(50);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
        scene.lights.add( //
                new SpotLight(new Color(255, 255, 255), new Point(-30, -50, 50), new Vector(0,1,0)) //
                        .setKl(0.0004).setKq(0.00006));

        scene.geometries.add(new Tree(new Point(-50, 20, 80), 70, 40, 20));
        scene.geometries.add(new Tree(new Point(0, 20, 80), 90, 40, 30));
        Triangle surface1 = new Triangle(new Point(-100,0, -40), new Point(100, 0, -40), new Point(850, 500, -40));
        Triangle surface2 = new Triangle(new Point(-100,0, -40), new Point(-850, 400, -40), new Point(850, 500, -40));
        Material surface = new Material().setKd(0.2);
        scene.geometries.add(surface1.setEmission(new Color(58, 46, 39)).setMaterial(surface));
        scene.geometries.add(surface2.setEmission(new Color(58, 46, 39)).setMaterial(surface));
        //Sphere moon = new Sphere(new Point(350, 300, 300), 30);
        Circle moon = new Circle(new Point(350, 300, 300), new Vector(0, -1, 0), 50);
        scene.geometries.add(moon.setEmission(new Color(255,243,109)));
        Ray r = new Ray(new Point(40, 20, 0), new Vector(2, 1, 0));
        scene.geometries.add(new TreeLog(r, 10, 150));

        ImageWriter imageWriter = new ImageWriter("pyramid", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
}
