package Filter;

public class ThresholdFilter extends PixelFilter {

    private int[] greyValue;

    public ThresholdFilter(int ... greyValue) {
        this.greyValue = greyValue;
    }

    @Override
    protected int calculate(int colorPixel) {
        final int white = 0xFFFFFF;
        final int black = 0x000000;

        int r = (colorPixel >> 16) & 0xFF;
        int g = (colorPixel >> 8) & 0xFF;
        int b = (colorPixel) & 0xFF;

        int brightness = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
        int processedPixel = white;
        if (brightness < greyValue[0]) {
            processedPixel = black;
        } else {
            for (int i = 0; i < greyValue.length; i++) {
                if (i < greyValue.length - 1) {
                    if (brightness >= greyValue[i] && brightness < greyValue[i + 1]) {
                       int value = (int) Math.round((greyValue[i] + greyValue[i + 1]) / 2.0);
                       processedPixel = value << 16 | value << 8 | value;
                    }
                }
            }
        }

        return processedPixel;
    }
}
