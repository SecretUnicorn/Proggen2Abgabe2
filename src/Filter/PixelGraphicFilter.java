package Filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PixelGraphicFilter extends AreaFilter {

    private int blockWidth;
    private int radius;
    private int middlePixel;
    private boolean isEven;

    public PixelGraphicFilter(int blockWidth) {
        this.isEven = blockWidth % 2 == 0;
        this.blockWidth = blockWidth;
        this.middlePixel = blockWidth / 2;
        this.radius = middlePixel;

    }

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
            // wenn block 11 breit dann 11 / 2 = 5 mitte
            // wenn block 11 breit dann ((11-(11/2))/2) radius
            for (int i = middlePixel; i < height; i += blockWidth) {
                for (int j = middlePixel; j < width; j += blockWidth) {
                    calculate(pixel, maskPixel, i * width + j, width, height);
                }
            }


            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int a = 0; a < height; a++) {
                for (int j = 0; j < width; j++) {
                    result.setRGB(j, a, pixel[a * width + j]);
                }
            }
            return result;
        }
        return null;
    }

    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        ArrayList<Integer> neededForProcess = new ArrayList<>();
        int heightPosPix = index / width;
        int widthPosPix = index % width;
        for (int i = heightPosPix - radius; i <= heightPosPix + radius - (isEven ? 1 : 0); i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius - (isEven ? 1 : 0); j++) {// i * width + j
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    neededForProcess.add(pixel[i * width + j]);
                }
            }
        }
        int valueRed = 0;
        int valueGreen = 0;
        int valueBlue = 0;
        for (int it = 0; it < neededForProcess.size(); it++) {
            valueRed += (neededForProcess.get(it) >> 16) & 0xFF;
            valueGreen += (neededForProcess.get(it) >> 8) & 0xFF;
            valueBlue += (neededForProcess.get(it)) & 0xFF;
        }
        valueRed = Math.round(valueRed / (float) neededForProcess.size());
        valueGreen = Math.round(valueGreen / (float) neededForProcess.size());
        valueBlue = Math.round(valueBlue / (float) neededForProcess.size());

        int blockColor = (valueRed << 16) | (valueGreen << 8) | valueBlue;

        for (int i = heightPosPix - radius; i <= heightPosPix + radius - (isEven ? 1 : 0); i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius - (isEven ? 1 : 0); j++) {// i * width + j
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
