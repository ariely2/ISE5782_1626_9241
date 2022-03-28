package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.*;

public class ImageWriterTest {

    @Test
    void testWriteImage()
    {
        ImageWriter photo = new ImageWriter("pic_1", 800, 500);
        for(int i = 0; i<800;i++) {
            for (int j = 0; j < 500; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    photo.writePixel(i, j, Color.BLACK);
                else
                    photo.writePixel(i, j, new Color(153, 252, 255));
            }
        }
        photo.writeToImage();
    }
}
