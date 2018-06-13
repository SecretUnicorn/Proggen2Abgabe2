package Filter;

public class MonochromFilter extends PixelFilter {

    /**
     * Extract red, green blue value and creates a grey value.
     * @param colorPixel value to be processed
     * @return grey pixel
     * @see ImageHelper#setGreyPixel(int)
     */
    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);

        int grey = ImageHelper.getLuminosity(r, g, b);

        return ImageHelper.setGreyPixel(grey);
    }

    @Override
    public String getName() {
        return "Monochrome";
    }
}
