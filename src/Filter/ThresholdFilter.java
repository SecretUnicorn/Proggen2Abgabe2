package Filter;

public class ThresholdFilter extends PixelFilter {

    private int[] greyValue;

    public ThresholdFilter(int... greyValue) {
        this.greyValue = greyValue;
    }

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
                        int value = greyValue[i] + (greyValue[i + 1] - greyValue[i]) / 2;
                        processedPixel = ImageHelper.setGreyPixel(value);
                    }
                }
            }
        }

        return processedPixel;
    }
}
