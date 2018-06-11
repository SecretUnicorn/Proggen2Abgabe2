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
                return ImageHelper.setRed(r);
            case GREEN:
                return ImageHelper.setGreen(g);
            case BLUE:
                return ImageHelper.setBlue(b);
        }
        return ImageHelper.setFullColorPixel(r, g, b);
    }
}
