package Filter;

import java.awt.image.BufferedImage;

/**
 * Abstract class AreaFilter implements Interface {@link Filter}
 */
abstract class AreaFilter implements Filter {

    /**
     * Processes every pixel of an image.
     * The decision to actually calculate a pixel is done in the {@link #calculate(int[], int[], int, int, int)}
     * @param image can contain variable amount of images, if a mask is intended
     * @return
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

            for (int i = 0; i < pixel.length; i++) {
                processedPixel[i] = calculate(pixel, maskPixel, i, width, height);
            }

            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            result = ImageHelper.setRGBValues(result, processedPixel, width, height);
            return result;
        }
        return null;
    }

    /**
     * Basic calculate, no changes are applied to a pixel.
     * @param pixel array of pixel to process
     * @param maskPixel mask pixel array
     * @param index index of pixel to be processed
     * @param width of image
     * @param height of image
     * @return processed pixel
     */
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        return pixel[index];
    }

    public String getName() {
        return "";
    }
}
