package Filter;

public class InvertFilter extends PixelFilter {

    /**
     * Calculates opposite colorValue of this pixel.
     * @param colorPixel value to be processed
     * @return inverted pixel
     */
    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.getRed(colorPixel);
        r = 255 - r;
        int g = ImageHelper.getGreen(colorPixel);
        g = 255 - g;
        int b = ImageHelper.getBlue(colorPixel);
        b = 255 - b;
        return ImageHelper.setFullColorPixel(r,g,b);
    }

    @Override
    public String getName() {
        return "Invert";
    }
}
