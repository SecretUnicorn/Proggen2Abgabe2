package Filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * PixelGraphicFilter extends {@link AreaFilter}
 * <p>
 * Sets chunks of blocks to the same color based on the average color from all pixels in that block.
 */
public class PixelGraphicFilter extends AreaFilter {

    private int blockWidth;
    private int radius;
    private int middlePixel;
    private boolean isEven;

    /**
     * Constructor of PixelGraphicFilter.
     * @param blockWidth size of blocks to set
     */
    public PixelGraphicFilter(int blockWidth) {
        this.isEven = blockWidth % 2 == 0;
        this.blockWidth = blockWidth;
        this.middlePixel = blockWidth / 2;
        this.radius = middlePixel;

    }

    /**
     * Overridden process method, to jump from block to block.
     * @param image can contain variable amount of images, if a mask is intended
     * @return processed image
     */
    @Override
    public BufferedImage process(BufferedImage... image) {
        BufferedImage image1, image2;
        int[] pixel;
        int[] maskPixel;
        image1 = (image.length > 0) ? image[0] : null;
        image2 = (image.length > 1) ? image[1] : null;
        boolean maskIsSet = image2 != null;

        if (image1 != null) {
            int width = image1.getWidth();
            int height = image1.getHeight();

            pixel = new int[width * height];
            maskPixel = new int[pixel.length];

            ImageHelper.getRGBValues(pixel, maskPixel, maskIsSet, image1, image2);

            for (int i = middlePixel; i < height; i += (i+blockWidth >= height ? blockWidth / 2 : blockWidth )) {
                for (int j = middlePixel; j < width; j += (j+blockWidth >= width ? blockWidth / 2 : blockWidth )) {
                    calculate(pixel, maskPixel, i * width + j, width, height);
                }
            }


            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            result = ImageHelper.setRGBValues(result, pixel, width, height);
            return result;
        }
        return null;
    }

    /**
     * Sets a block of pixel to the average color.
     * @param pixel array of pixel to process
     * @param maskPixel mask pixel array
     * @param index index of pixel to be processed
     * @param width of image
     * @param height of image
     * @return value of center pixel of this block
     */
    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        ArrayList<Integer> neededForProcess = new ArrayList<>();
        int heightPosPix = index / width;
        int widthPosPix = index % width;
        for (int i = heightPosPix - radius; i <= heightPosPix + radius - (isEven ? 1 : 0); i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius - (isEven ? 1 : 0); j++) {
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    neededForProcess.add(pixel[i * width + j]);
                }
            }
        }
        int valueRed = 0;
        int valueGreen = 0;
        int valueBlue = 0;
        for (int it = 0; it < neededForProcess.size(); it++) {
            valueRed += ImageHelper.getRed(neededForProcess.get(it));
            valueGreen += ImageHelper.getGreen(neededForProcess.get(it));
            valueBlue += ImageHelper.getBlue(neededForProcess.get(it));
        }
        valueRed = Math.round(valueRed / (float) neededForProcess.size());
        valueGreen = Math.round(valueGreen / (float) neededForProcess.size());
        valueBlue = Math.round(valueBlue / (float) neededForProcess.size());

        int blockColor = ImageHelper.setFullColorPixel(valueRed, valueGreen, valueBlue);

        for (int i = heightPosPix - radius; i <= heightPosPix + radius - (isEven ? 1 : 0); i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius - (isEven ? 1 : 0); j++) {
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    if (maskPixel != null) {
                        if (maskPixel[i * width + j] != black) {
                            pixel[i * width + j] = blockColor;
                        }
                    } else {
                        pixel[i * width + j] = blockColor;
                    }
                }
            }
        }
        return pixel[index];
    }

    public String getName() {
        return "PixelGraphicFilter";
    }

}
