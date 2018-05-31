import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    public static void main(String [] args) {
        BufferedImage image;
        Filter filter = new PixelFilter();
        try {
            image = ImageIO.read(new File("test_image.bmp"));
            image = filter.process(image);
            if( image  == null) {
                System.out.println("null");
            }
            ImageIO.write(image, "bmp", new File("output_test.bmp"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
