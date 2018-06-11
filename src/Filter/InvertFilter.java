package Filter;

public class InvertFilter extends PixelFilter {

    @Override
    protected int calculate(int colorPixel) {
        int r = (colorPixel >> 16) & 0xFF;
        int g = (colorPixel >> 8) & 0xFF;
        int b = (colorPixel) & 0xFF;
        return (255 - r << 16) | (255 - g << 8) | (255 - b);
    }

    @Override
    public String getName() {
        return "Invert";
    }
}
