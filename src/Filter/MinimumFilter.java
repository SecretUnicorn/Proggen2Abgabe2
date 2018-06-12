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
            for (int j = widthPosPix - radius; j <= widthPosPix + radius; j++) {
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    neededForProcess.add(pixel[i * width + j]);
                }
            }
        }
        int minvalueRed = ImageHelper.getRed(neededForProcess.get(0));
        int minvalueGreen = ImageHelper.getGreen(neededForProcess.get(0));
        int minvalueBlue = ImageHelper.getBlue(neededForProcess.get(0));
        for (int it = 0; it < neededForProcess.size(); it++) {
            int checkRed = ImageHelper.getRed(neededForProcess.get(it));
            int checkGreen = ImageHelper.getGreen(neededForProcess.get(it));
            int checkBlue = ImageHelper.getBlue(neededForProcess.get(it));
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
        return maskPixel[index] != black ? ImageHelper.setFullColorPixel(minvalueRed, minvalueGreen, minvalueBlue) : pixel[index];
    }

    @Override
    public String getName() {
        return "Minimum";
    }
}
