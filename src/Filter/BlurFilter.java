package Filter;

import java.util.ArrayList;

public class BlurFilter extends AreaFilter {

    private int radius;

    /**
     * Constructor of BlurFilter.
     * @param radius initializes the radius of pixels to calculate
     */
    public BlurFilter(int radius) {
        this.radius = radius;
    }

    /**
     * Considers the color values in range of the radius around the pixel.
     * Checks if the pixel in the mask is black, to determine returning
     * the processed pixel or not.
     * @param pixel array of pixel to process
     * @param maskPixel mask pixel array
     * @param index index of pixel to be processed
     * @param width of image
     * @param height of image
     * @return
     */
    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        ArrayList<Integer> neededForProcess = new ArrayList<>();
        int heightPosPix = index / width;
        int widthPosPix = index % width;
        for (int i = heightPosPix - radius; i <= heightPosPix + radius; i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius; j++) {// i * width + j
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
        return maskPixel[index] != black ? ImageHelper.setFullColorPixel(valueRed, valueGreen, valueBlue) : pixel[index];
    }

    @Override
    public String getName() {
        return "Blur";
    }
}
