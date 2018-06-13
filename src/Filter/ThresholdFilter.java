package Filter;

/**
 * ThresholdFilter {@link PixelFilter}
 * <p>
 * Sets part of the image to certain shades of grey according to the user input
 */
public class ThresholdFilter extends PixelFilter {

    private int[] greyValue;

    /**
     * Constructor of ThresholdFilter
     * @param greyValue variable amount of grey values to create thresholds
     */
    public ThresholdFilter(int... greyValue) {
        this.greyValue = greyValue;
    }

    /**
     * Creates a pixel, with grey value between the thresholds
     * Checks if the pixel's luminosity is lesser than any, greater than any
     * or between certain thresholds, if it is the case, the grey value is set to
     * be in the exact middle of the thresholds.
     * @param colorPixel value to be processed
     * @return
     */
    @Override
    protected int calculate(int colorPixel) {
        final int whiteRGB = 0xFFFFFF;
        final int blackRGB = 0x000000;

        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);

        int brightness = ImageHelper.getLuminosity(r, g, b);
        int processedPixel = whiteRGB;
        if (brightness < greyValue[0]) {
            processedPixel = blackRGB;
        } else {
            for (int i = 0; i < greyValue.length; i++) {
                if (i < greyValue.length - 1) {
                    if (brightness >= greyValue[i] && brightness < greyValue[i + 1]) {
                        //int value = greyValue[i] + (greyValue[i + 1] - greyValue[i]) / 2;
                        int value = (greyValue[i] + greyValue[i + 1]) / 2;
                        processedPixel = ImageHelper.setGreyPixel(value);
                    }
                }
            }
        }

        return processedPixel;
    }

    @Override
    public String getName() {
        return "Threshold";
    }
}
