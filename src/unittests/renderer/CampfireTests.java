package unittests.renderer;

import geometries.Sphere;
import geometries.Tree;
import geometries.Triangle;
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
        ImageWriter imageWriter = new ImageWriter("pyramid", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
}
