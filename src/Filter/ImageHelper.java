package Filter;

import java.awt.image.BufferedImage;

public class ImageHelper {

    /**
     * Extracts RGB Value out of images, and fills integer arrays.
     * Extracts RGB Values from an image and a mask, fills the matching arrays,
     * with the RGB Values from BufferedImage Method getRGB.
     * @param pixel array to contain all image pixels
     * @param maskPixel array to contain all mask pixels
     * @param maskSet a check if a mask is set
     * @param image1 image to process
     * @param image2 mask for the image
     */
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

    /**
     * Sets a BufferedImage with given pixels.
     * Fills a BufferedImage with given Pixels and returns the new image.
     * @param result image on which every shall be set
     * @param pixel array that contains every integer value of the pixels
     * @param width of the given image <code>result</code>
     * @param height of the given image <code>result</code>
     * @return BufferedImage result, that now is set with pixels
     */
    public static BufferedImage setRGBValues(BufferedImage result, int[] pixel, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result.setRGB(j, i, pixel[i * width + j]);
            }
        }
        return result;
    }

    /**
     * Checks if colorValue is in range for RGB values.
     * If not in range it will be set to the maximum or minimum value of RGBs.
     * @param colorValue value to check
     * @return a new RGB value
     */
    public static int rangeChecker(int colorValue) {
        if (colorValue < 0) {
            return 0;
        } else if (colorValue > 255) {
            return 255;
        } else {
            return colorValue;
        }
    }

    /**
     * Sets up a 24-bit long integer that represents a grey pixel.
     * One color value will be set as the red, green and blue part of a pixel.
     * @param greyScaleValue will be set for every color part
     * @return 24-bit long integer(grey colored pixel)
     */
    public static int setGreyPixel(int greyScaleValue) {
        return setRed(greyScaleValue) | setGreen(greyScaleValue) | setBlue(greyScaleValue);
    }

    /**
     * Sets a 24-bit long integer that represents a colored pixel.
     * With the given parameters, every part of the pixel will be set.
     * @param r value that represents the red part
     * @param g value that represents the green part
     * @param b value that represents the blue part
     * @return 24-bit long integer(colored pixe)
     */
    public static int setFullColorPixel(int r, int g, int b) {
        return setRed(r) | setGreen(g) | setBlue(b);
    }

    /**
     * Gets the red part of a RGB value.
     * @param rgbPixel from which the value will be extracted
     * @return red part of pixel in range [0,255]
     */
    public static int getRed(int rgbPixel) {
        return (rgbPixel >> 16) & 0xFF;
    }

    /**
     * Shifts a red value to its correct position in a 24-bit long integer.
     * @param redValue value to shift to its correct position
     * @return integer containing the red value, shifted to its correct position
     */
    public static int setRed(int redValue) {
        return (redValue << 16);
    }

    /**
     * Gets the green part of a RGB value.
     * @param rgbPixel from which the value will be extracted
     * @return green part of pixel in range [0,255]
     */
    public static int getGreen(int rgbPixel) {
        return (rgbPixel >> 8) & 0xFF;
    }

    /**
     * Shifts a green value to its correct position in a 24-bit long integer.
     * @param greenValue value to shift to its correct position
     * @return integer containing the green value, shifted to its correct position
     */
    public static int setGreen(int greenValue) {
        return (greenValue << 8);
    }

    /**
     * Gets the blue part of a RGB value.
     * @param rgbPixel from which the value will be extracted
     * @return blue part of pixel in range [0,255]
     */
    public static int getBlue(int rgbPixel) {
        return rgbPixel & 0xFF;
    }

    /**
     * Shifts a blue value to its correct position in a 24-bit long integer.
     * @param blueValue value to shift to its correct position
     * @return integer containing the blue value, shifted to its correct position
     */
    public static int setBlue(int blueValue) {
        return blueValue;
    }

    /**
     * Calculates luminosity of an pixel.
     * Calculates luminosity with regard to way, how the human eye works.
     * @param r red part of a pixel
     * @param g green part of a pixel
     * @param b blue part of a pixel
     * @return value in range [0,255]
     */
    public static int getLuminosity(int r, int g, int b) {
        return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
    }
}
