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
        return setRed(greyScaleValue) | setGreen(greyScaleValue) | setBlue(greyScaleValue);
    }

    public static int setFullColorPixel(int r, int g, int b) {
        return setRed(r) | setGreen(g) | setBlue(b);
    }

    public static int getRed(int rgbPixel) {
        return (rgbPixel >> 16) & 0xFF;
    }

    public static int setRed(int redValue) {
        return (redValue << 16);
    }

    public static int getGreen(int rgbPixel) {
        return (rgbPixel >> 8) & 0xFF;
    }

    public static int setGreen(int greenValue) {
        return (greenValue << 8);
    }

    public static int getBlue(int rgbPixel) {
        return rgbPixel & 0xFF;
    }

    public static int setBlue(int blueValue) {
        return blueValue;
    }

    public static int getLuminosity(int r, int g, int b) {
        return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
    }
}
