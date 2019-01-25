package Filter;

import java.awt.image.BufferedImage;

/**
 * Abstract class PixelFilter implements Interface {@link Filter}
 * Processes every pixel in image step by step.
 */
abstract class PixelFilter implements Filter {
    /**
     * Processes every pixel in image.
     * Function will process every pixel in image, if a mask is given,
     * it checks if the value equals black and then does not process the pixel
     * @param image can contain variable amount of images, if a mask is intended
     * @return processed image
     */
    @Override
    public BufferedImage process(BufferedImage... image) {
        BufferedImage image1, image2;
        int[] pixel;
        int[] maskPixel;
        int[] processedPixel;
        image1 = (image.length > 0) ? image[0] : null;
        image2 = (image.length > 1) ? image[1] : null;
        boolean maskIsSet = image2 != null;

        if (image1 != null) {
            int width = image1.getWidth();
            int height = image1.getHeight();

            pixel = new int[width * height];
            processedPixel = new int[pixel.length];
            maskPixel = new int[pixel.length];

            ImageHelper.getRGBValues(pixel, maskPixel, maskIsSet, image1, image2);

            int i = 0;
            for (int specPixel : pixel) {
                if (maskIsSet) {
                    if (maskPixel[i] != black) {
                        processedPixel[i] = calculate(specPixel);
                    } else {
                        processedPixel[i] = specPixel;
                    }
                } else {
                    processedPixel[i] = calculate(specPixel);
                }
                i++;
            }

            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            result = ImageHelper.setRGBValues(result, processedPixel, width, height);
            return result;
        }
        return null;
    }

    /**
     * Basic calculate, no changes are applied to a pixel.
     * @param colorPixel value to be processed
     * @return same pixel
     */
    protected int calculate(int colorPixel) {
        return colorPixel;
    }

    public String getName() {
        return "";
    }
}
