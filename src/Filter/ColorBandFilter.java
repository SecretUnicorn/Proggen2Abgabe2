package Filter;

public class ColorBandFilter extends PixelFilter {

    private ColorBand colorBand;

    /**
     * Constructor of ColorBandFilter.
     * @param cb initializes Colorband Enum to determine which channel should be returned.
     */
    public ColorBandFilter(ColorBand cb) {
        colorBand = cb;
    }

    /**
     * Splits the colored pixel into its channels.
     * Checks which channel to return.
     * @param colorPixel value to be processed
     * @return a one color channel pixel (e.g. 0x FF 00 00)
     */
    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);


        return oneColorPixel(r, g, b);
    }

    /**
     * Actually determines which channel to return.
     * @param r red value of pixel
     * @param g green value of pixel
     * @param b blue value of pixel
     * @return 24-bit long integer with only one channel
     */
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

    @Override
    public String getName() {
        return "ColorBand";
    }

}
