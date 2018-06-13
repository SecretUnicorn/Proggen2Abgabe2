package Filter;

/**
 * BrightnessFilter extends {@link PixelFilter}
 * Darkens or brightens an Image
 */
public class BrightnessFilter extends PixelFilter {

    private double multiplier;

    /**
     * Constructor of MonochromFilter.
     *
     * @param multiplier if below 1 it will darken the image and if above 1 it will brighten the image
     */
    public BrightnessFilter(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.rangeChecker((int) (ImageHelper.getRed(colorPixel) * multiplier));
        int g = ImageHelper.rangeChecker((int) (ImageHelper.getGreen(colorPixel) * multiplier));
        int b = ImageHelper.rangeChecker((int) (ImageHelper.getBlue(colorPixel) * multiplier));

        return ImageHelper.setFullColorPixel(r, g, b);
    }

    @Override
    public String getName() {
        if (multiplier < 1) {
            return "DarkenFilter";
        } else {
            return "BrightenFilter";
        }
    }
}
