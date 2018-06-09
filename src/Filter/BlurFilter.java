package Filter;

import java.util.ArrayList;

public class BlurFilter extends AreaFilter {

    private int radius;

    public BlurFilter(int radius) {
        this.radius = radius;
    }

    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        ArrayList<Integer> neededForProcess = new ArrayList<>();
        int heightPosPix = index / width;
        int widthPosPix = index % width;
        for (int i = heightPosPix - radius; i <= heightPosPix + radius; i++) {
            for (int j = widthPosPix - radius; j <= widthPosPix + radius; j++) {// i * width + j
                if(i >= 0 && i < height && j >= 0 && j < width) {
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
        valueRed = Math.round(valueRed / (float)neededForProcess.size());
        valueGreen = Math.round(valueGreen / (float)neededForProcess.size());
        valueBlue = Math.round(valueBlue / (float)neededForProcess.size());
        return maskPixel[index] != black ? (valueRed << 16) | (valueGreen << 8) | (valueBlue) : pixel[index];
    }
}
