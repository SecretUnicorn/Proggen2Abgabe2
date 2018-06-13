package Filter;

import gui.Alert;

public class MonochromFilter extends PixelFilter {

    private MonoType monoType;

    /**
     * Constructor of MonochromFilter.
     *
     * @param mt enum {@link MonoType} to determine monochrome style
     */
    public MonochromFilter(MonoType mt) {
        this.monoType = mt;
    }

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

        if (monoType.equals(MonoType.MONOCHROME)) {
            int grey = ImageHelper.getLuminosity(r, g, b);
            return ImageHelper.setGreyPixel(grey);
        } else if (monoType.equals(MonoType.SEPIA)) {
            int outputRed = ImageHelper.rangeChecker((int) ((r * .393) + (g * .769) + (b * .189)));
            int outputGreen = ImageHelper.rangeChecker((int) ((r * .349) + (g * .686) + (b * .168)));
            int outputBlue = ImageHelper.rangeChecker((int) ((r * .272) + (g * .534) + (b * .131)));
            return ImageHelper.setFullColorPixel(outputRed, outputGreen, outputBlue);
        } else {
            int outputBlue = ImageHelper.rangeChecker((int) ((r * .393) + (g * .769) + (b * .189)));
            int outputGreen = ImageHelper.rangeChecker((int) ((r * .349) + (g * .686) + (b * .168)));
            int outputRed = ImageHelper.rangeChecker((int) ((r * .272) + (g * .534) + (b * .131)));
            return ImageHelper.setFullColorPixel(outputRed, outputGreen, outputBlue);
        }

    }

    @Override
    public String getName() {
        if (monoType.equals(MonoType.MONOCHROME)) {
            return "Monochrome";
        } else if (monoType.equals(MonoType.SEPIA)) {
            return "Sepia";
        } else {
            return "Cyanotype";
        }

    }
}
