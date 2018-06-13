package Filter;

import java.util.ArrayList;

public class SharpenFilter extends AreaFilter {
    private static final int radius = 1;

    /**
     * Considers every pixel in the fixed {@link #radius}.
     * And tries to highlight the pixel.
     * @param pixel array of pixel to process
     * @param maskPixel mask pixel array
     * @param index index of pixel to be processed
     * @param width of image
     * @param height of image
     * @return processed pixel
     */
    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {

        ArrayList<Integer> neededForProcess = new ArrayList<>();
        int heightPosPix = index / width;
        int widthPosPix = index % width;
        for (int i = heightPosPix - radius; i <= heightPosPix + radius; i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius; j++) {
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    neededForProcess.add(pixel[i * width + j]);
                }
            }
        }
        int valueRed = 0;
        int valueGreen = 0;
        int valueBlue = 0;
        for (int it = 0; it < neededForProcess.size(); it++) {
            if(it == 1 || it == 3 || it == 5 || it == 7) {
                valueRed -= ImageHelper.getRed(neededForProcess.get(it));
                valueGreen -= ImageHelper.getGreen(neededForProcess.get(it));
                valueBlue -= ImageHelper.getBlue(neededForProcess.get(it));
            } else if (it == 4){
                valueRed += ImageHelper.getRed(neededForProcess.get(it)) * 5;
                valueGreen += ImageHelper.getGreen(neededForProcess.get(it)) * 5;
                valueBlue += ImageHelper.getBlue(neededForProcess.get(it)) * 5;
            }
        }
        valueRed = Math.round(ImageHelper.rangeChecker(valueRed) / 1.1F);
        valueGreen = Math.round(ImageHelper.rangeChecker(valueGreen) / 1.1F);
        valueBlue = Math.round(ImageHelper.rangeChecker(valueBlue) / 1.1F);
        return maskPixel[index] != black ? ImageHelper.setFullColorPixel(valueRed, valueGreen, valueBlue) : pixel[index];
    }

    @Override
    public String getName() {
        return "Sharpen";
    }
}
