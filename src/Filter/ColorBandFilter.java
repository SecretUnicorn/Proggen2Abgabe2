package Filter;

public class ColorBandFilter extends PixelFilter {

    private ColorBand colorBand;

    public ColorBandFilter(ColorBand cb) {
        colorBand = cb;
    }

    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);


        return oneColorPixel(r, g, b);
    }

    private int oneColorPixel(int r, int g, int b) {
        switch (colorBand) {
            case RED:
                return (r << 16);
            case GREEN:
                return (g << 8);
            case BLUE:
                return b;
        }
        return (r << 16) | (g << 8) | (b << 0);
    }
}
