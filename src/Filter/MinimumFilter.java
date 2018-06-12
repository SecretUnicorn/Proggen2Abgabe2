package Filter;

import java.util.ArrayList;

public class MinimumFilter extends AreaFilter {

    private int radius;


    public MinimumFilter(int radius) {
        this.radius = radius;
    }

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
        int minvalueRed = (neededForProcess.get(0) >> 16) & 0xFF;
        int minvalueGreen = (neededForProcess.get(0) >> 8) & 0xFF;
        int minvalueBlue = (neededForProcess.get(0)) & 0xFF;
        for (int it = 0; it < neededForProcess.size(); it++) {
            int checkRed = (neededForProcess.get(it) >> 16) & 0xFF;
            int checkGreen = (neededForProcess.get(it) >> 8) & 0xFF;
            int checkBlue = (neededForProcess.get(it)) & 0xFF;
            if (checkRed < minvalueRed) {
                minvalueRed = checkRed;
            }
            if (checkGreen < minvalueGreen) {
                minvalueGreen = checkGreen;
            }
            if (checkBlue < minvalueBlue) {
                minvalueBlue = checkBlue;
            }
        }
        return maskPixel[index] != black ? (minvalueRed << 16) | (minvalueGreen << 8) | (minvalueBlue) : pixel[index];
    }

    @Override
    public String getName() {
        return "Minimum";
    }
}
