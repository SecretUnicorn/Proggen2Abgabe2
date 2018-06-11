package Filter;

public class PixelMutilation extends AreaFilter {

    private int radius;

    public PixelMutilation(int radius) {
        this.radius = radius;
    }

    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        int colorPixel = pixel[index];
        int nextColor = pixel[index + radius < pixel.length ? index + radius : index - 1];
        return (int) (Math.round((colorPixel + nextColor) / 2.0));
    }
}
