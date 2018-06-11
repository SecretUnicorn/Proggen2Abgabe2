package Filter;

import java.awt.image.BufferedImage;

public class ImageHelper {

    public static void getRGBValues(int[] pixel, int[] maskPixel, boolean maskSet, BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        for (int i = 0; i < image1.getHeight(); i++) {
            for (int j = 0; j < width; j++) {
                pixel[i * width + j] = image1.getRGB(j, i);
                if (maskSet) {
                    maskPixel[i * width + j] = image2.getRGB(j, i);
                }
            }
        }
    }

    public static BufferedImage setRGBValues(BufferedImage result, int[] pixel, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result.setRGB(j, i, pixel[i * width + j]);
            }
        }
        return result;
    }

    public static int setGreyPixel(int greyScaleValue) {
        return greyScaleValue << 16 | greyScaleValue << 8 | greyScaleValue;
    }

    public static int getRed(int rgbPixel) {
        return (rgbPixel >> 16) & 0xFF;
    }

    public static int getGreen(int rgbPixel) {
        return (rgbPixel >> 8) & 0xFF;
    }

    public static int getBlue(int rgbPixel) {
        return rgbPixel & 0xFF;
    }
}
