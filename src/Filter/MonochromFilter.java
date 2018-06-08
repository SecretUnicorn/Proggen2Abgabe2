package Filter;

public class MonochromFilter extends PixelFilter {
    @Override
    protected int calculate(int colorPixel) {
        int r = (colorPixel>>16)&0xFF;
        int g = (colorPixel>>8)&0xFF;
        int b = (colorPixel)&0xFF;

        int grey = luminosity(r,g,b);

        return (grey << 16) | (grey << 8) | (grey);
    }

    private int luminosity(int r, int g, int b) {
        return (int) Math.round(0.21*r + 0.72*g + 0.07*b);
    }
}
