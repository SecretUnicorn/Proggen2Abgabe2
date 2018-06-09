package Filter;

import java.awt.image.BufferedImage;

public class AreaFilter implements Filter {

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
                processedPixel[i] = calculate(pixel, maskPixel, i, width, height);
                i++;
            }

            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int a = 0; a < height; a++) {
                for (int j = 0; j < width; j++) {
                    result.setRGB(j, a, processedPixel[a * width + j]);
                }
            }
            return result;
        }
        return null;
    }

    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        return pixel[index];
    }

    public String getName() {
        return "";
    }
}
