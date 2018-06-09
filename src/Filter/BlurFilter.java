package Filter;

public class BlurFilter extends AreaFilter {

    private int radius;
    public BlurFilter(int radius) {
        this.radius = radius;
    }

    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        int colorPixel = pixel[index];
        int minBoundX = pixel[index-radius];
        int maxBoundX = pixel[index+radius];
        return super.calculate(pixel, maskPixel, index, width, height);
    }
}
