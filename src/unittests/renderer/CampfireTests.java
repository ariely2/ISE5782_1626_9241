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

public class CampfireTests {
    private Scene scene = new Scene("Final Picture");


    @Test
    public void PyramidTest()
    {
        //Camera camera = new Camera(new Point(0, -50, 50), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
          //      .setVPSize(150, 150).setVPDistance(50);
        Camera camera = new Camera(new Point(0, -10, 10), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
              .setVPSize(150, 150).setVPDistance(50);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        camera.setAntiAliasing(true);
        //trees
       scene.geometries.add(new Forest(new Point(-100, 10, 18), 100, 300, 7, 3, -0.8));
       scene.geometries.add(new Forest(new Point(-250, 350, 18), 100, 300, 6, 3, Math.PI/2));
       scene.geometries.add(new Forest(new Point(-60, 100, 18), 60, 350, 6, 2, Math.PI/2));
       scene.geometries.add(new Forest(new Point(0, 100, 18), 500, 300, 8, 6, 0));
       scene.geometries.add(new Forest(new Point(30, 35, 18), 90, 60, 3, 3, 0));
        //scene.geometries.add(new Forest(new Point(70, 40, 20), 100, 150, 4, 2, 1.5));

        //scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, -50, 80), new Vector(-1, 0.5, -1)));

        //sky
        Plane sky1 = new Plane(new Point(-850,500, -100), new Point(850, 500, -100), new Point(-850, 500, 900));
        Material sky = new Material().setKd(0.01);
        scene.geometries.add(sky1.setEmission(new Color(12, 20, 69)).setMaterial(sky));


        //ground
        Plane surface1 = new Plane(new Point(-100,0, -40), new Point(100, 0, -40), new Point(850, 500, -40));
        Material surface = new Material().setKd(0.2);
        scene.geometries.add(surface1.setEmission(new Color(58, 46, 39)).setMaterial(surface));

        //mountains
        Triangle mountain1 = new Triangle(new Point(-700, 400, -40), new Point(0, 400, -40), new Point(-350, 400, 300));
        Triangle mountain2 = new Triangle(new Point(-200, 400, -40), new Point(400, 400, -40), new Point(100, 400, 500));
        Triangle mountain3 = new Triangle(new Point(200, 400, -40), new Point(1000, 400, -40), new Point(600, 400, 350));
        Triangle mountain4 = new Triangle(new Point(-800, 400, -40), new Point(-800, 400, 400), new Point(-400, 400, -40));
        scene.geometries.add(mountain1.setEmission(new Color(42, 51, 60)).setMaterial(sky));
        scene.geometries.add(mountain2.setEmission(new Color(23, 30, 35)).setMaterial(sky));
        scene.geometries.add(mountain3.setEmission(new Color(31, 38, 48)).setMaterial(sky));
        scene.geometries.add(mountain4.setEmission(new Color(35, 32, 50)).setMaterial(sky));

        //moon
        Circle moon = new Circle(new Point(350, 300, 300), new Vector(0, -1, 0), 50);
        scene.geometries.add(moon.setEmission(new Color(255,243,109)));


        //lake
        Material water = new Material().setKs(0.8).setShininess(30);
        Circle lake = new Circle(new Point(-100, 200, -39), new Vector(0, 0, 1), 75);
        scene.geometries.add(lake.setEmission(new Color(73, 232, 255)).setMaterial(water));

        //tree logs
        Ray r1 = new Ray(new Point(-40, 30, -37), new Vector(1, 3, 0));
        Ray r2 = new Ray(new Point(20, 30, -37), new Vector(-1, 3, 0));

        scene.geometries.add(new TreeLog(r1, 5, 25));
        scene.geometries.add(new TreeLog(r2, 5, 25));


        //fire
        scene.geometries.add(new Fire(new Point(-10, 40, -37), 12, 90));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, -50, 100), 25, 9).setKl(0.00001).setKq(0.0001));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, -50, 50), 25, 9).setKl(0.0001).setKq(0.0001));

        camera.setRezInX(9);
        camera.setRezInY(9);

        ImageWriter imageWriter = new ImageWriter("finalpic", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
}
