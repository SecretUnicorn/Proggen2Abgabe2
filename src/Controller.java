import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    public static void main(String[] args) throws IOException {
        BufferedImage image;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        Filter filter;
        switch (s) {
            default:
                filter = new MonochromFilter();
                break;
            case "red":
                filter = new ColorBandFilter(ColorBand.RED);
                break;
            case "green":
                filter = new ColorBandFilter(ColorBand.GREEN);
                break;
            case "blue":
                filter = new ColorBandFilter(ColorBand.BLUE);
                break;
            case "trash":
                filter = new ThresholdFilter(64, 128, 192);
                break;
        }
        try {
            image = ImageIO.read(new File("test_image.bmp"));
            image = filter.process(image);
            if (image == null) {
                System.out.println("null");
            }
            ImageIO.write(image, "bmp", new File("output_test.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
