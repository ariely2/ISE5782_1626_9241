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
    private Scene scene = new Scene("Test scene");


    @Test
    public void PyramidTest()
    {
        Camera camera = new Camera(new Point(0, -50, 50), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(150, 150).setVPDistance(50);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

       // scene.lights.add( //
         //       new SpotLight(new Color(255, 255, 255), new Point(-30, -50, 50), new Vector(0,1,0)) //
           //             .setKl(0.0004).setKq(0.00006));


        //trees
        scene.geometries.add(new Tree(new Point(-55, 10, 20), 35, 20, 20));
        scene.geometries.add(new Tree(new Point(-85, 15, 10), 37, 18, 70));
        scene.geometries.add(new Tree(new Point(-85, 45, 10), 42, 20, 70));
        scene.geometries.add(new Tree(new Point(-115, 25, 23), 30, 15, 10));
        scene.geometries.add(new Tree(new Point(-100, 45, 25), 40, 20, 30));
        scene.geometries.add(new Tree(new Point(-90, 56, 17), 35, 25, 0));
        scene.geometries.add(new Tree(new Point(-120, 76, 24), 45, 22, 50));
        scene.geometries.add(new Tree(new Point(-110, 66, 17), 39, 23, 45));

        scene.geometries.add(new Tree(new Point(-95, 90, 22), 38, 24, 60));
        scene.geometries.add(new Tree(new Point(-75, 99, 20), 35, 20, 20));
        scene.geometries.add(new Tree(new Point(-50, 85, 15), 37, 18, 70));
        scene.geometries.add(new Tree(new Point(-20, 100, 23), 35, 19, 10));
        scene.geometries.add(new Tree(new Point(-25, 70, 25), 40, 20, 30));

        scene.geometries.add(new Tree(new Point(-120, 96, 17), 35, 25, 0));
        scene.geometries.add(new Tree(new Point(-140, 106, 24), 45, 22, 50));
        scene.geometries.add(new Tree(new Point(-165, 115, 22), 38, 24, 60));
        scene.geometries.add(new Tree(new Point(-195, 129, 20), 35, 20, 20));
        scene.geometries.add(new Tree(new Point(-210, 121, 15), 37, 18, 70));
        scene.geometries.add(new Tree(new Point(-235, 120, 23), 35, 19, 10));
        scene.geometries.add(new Tree(new Point(-245, 130, 25), 40, 20, 30));

        scene.geometries.add(new Tree(new Point(-250, 146, 24), 45, 22, 50));
        scene.geometries.add(new Tree(new Point(-265, 158, 22), 38, 24, 60));
        scene.geometries.add(new Tree(new Point(-285, 173, 20), 35, 20, 20));
        scene.geometries.add(new Tree(new Point(-300, 185, 15), 37, 18, 70));
        scene.geometries.add(new Tree(new Point(-215, 190, 23), 35, 19, 10));
        scene.geometries.add(new Tree(new Point(-245, 200, 25), 40, 20, 30));
        scene.geometries.add(new Tree(new Point(-310, 215, 17), 35, 25, 0));


        scene.geometries.add(new Tree(new Point(-310, 300, 23), 35, 19, 10));
        scene.geometries.add(new Tree(new Point(-335, 295, 24), 45, 22, 50));
        scene.geometries.add(new Tree(new Point(-350, 275, 22), 38, 24, 60));
        scene.geometries.add(new Tree(new Point(-381, 260, 20), 35, 20, 20));
        scene.geometries.add(new Tree(new Point(-400, 220, 25), 40, 20, 30));
        scene.geometries.add(new Tree(new Point(-215, 305, 15), 37, 18, 70));
        scene.geometries.add(new Tree(new Point(-240, 330, 23), 35, 19, 10));
        scene.geometries.add(new Tree(new Point(-278, 315, 24), 42, 22, 50));
        scene.geometries.add(new Tree(new Point(-299, 325, 22), 38, 24, 60));
        scene.geometries.add(new Tree(new Point(-150, 310, 30), 45, 22, 30));
        scene.geometries.add(new Tree(new Point(-185, 305, 15), 37, 18, 70));
        scene.geometries.add(new Tree(new Point(-110, 330, 23), 35, 19, 10));
        scene.geometries.add(new Tree(new Point(-75, 315, 24), 45, 22, 50));
        scene.geometries.add(new Tree(new Point(-50, 325, 22), 38, 24, 60));
        scene.geometries.add(new Tree(new Point(-31, 340, 20), 35, 20, 20));
        scene.geometries.add(new Tree(new Point(0, 300, 25), 40, 20, 30));
        scene.geometries.add(new Tree(new Point(26, 295, 17), 35, 25, 0));

//        scene.geometries.add(new Tree(new Point(65, 15, 30), 45, 22, 30));

        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, -50, 80), new Vector(-1, 0.5, -1)));

        //sky
        Triangle sky1 = new Triangle(new Point(-850,500, -100), new Point(850, 500, -100), new Point(-850, 500, 900));
        Triangle sky2 = new Triangle(new Point(850,500, -100), new Point(-850, 500, 900), new Point(850, 500, 900));
        Material sky = new Material().setKd(0.01);
        scene.geometries.add(sky1.setEmission(new Color(12, 20, 69)).setMaterial(sky));
        scene.geometries.add(sky2.setEmission(new Color(12, 20, 69)).setMaterial(sky));


        //ground
        Triangle surface1 = new Triangle(new Point(-100,0, -40), new Point(100, 0, -40), new Point(850, 500, -40));
        Triangle surface2 = new Triangle(new Point(-100,0, -40), new Point(-850, 400, -40), new Point(850, 500, -40));
        Material surface = new Material().setKd(0.2);
        scene.geometries.add(surface1.setEmission(new Color(58, 46, 39)).setMaterial(surface));
        scene.geometries.add(surface2.setEmission(new Color(58, 46, 39)).setMaterial(surface));



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
       // Ray r = new Ray(new Point(30, 0, 0), new Vector(2, 1, 0));
        //scene.geometries.add(new TreeLog(r, 3, 45));

        //fire
        //scene.geometries.add(new Fire(new Point(0, 40, -37), 12, 90));
        //scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, -50, 50)).setKl(0.00001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("pyramid", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
}
