package Filter;

public class MonochromFilter extends PixelFilter {
    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);

        int grey = ImageHelper.getLuminosity(r, g, b);

        return ImageHelper.setGreyPixel(grey);
    }

}
