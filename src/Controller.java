import Filter.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    public static void main(String[] args) throws IOException {
        BufferedImage image, mask;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Gimme filter");
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
            case "random":
                filter = new ColorReplacementFilter(0xFFFFFFFF);
                break;
            case "spec":
                filter = new ColorReplacementFilter(0xFFFFFFFF, 0x00000000);
                break;
            case "blur":
                filter = new BlurFilter(7);
                break;
        }
        try {
            image = ImageIO.read(new File("test.bmp"));
            mask = ImageIO.read(new File("mask1.bmp"));
            System.out.println("Mask oder nicht: ");
            String str = br.readLine();
            switch (str) {
                default:
                    image = filter.process(image);
                    break;
                case "mask":
                    image = filter.process(image, mask);
                    break;
            }
            if (image == null) {
                System.out.println("null");
            }
            ImageIO.write(image, "bmp", new File("output_test.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
