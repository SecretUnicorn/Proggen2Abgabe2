package Filter;

import java.util.Random;

/**
 * ColorReplacementFilter extends {@link PixelFilter}
 * <p>
 * Replaces a certain color with another color.
 */
public class ColorReplacementFilter extends PixelFilter {
    private int toReplace;
    private int replaceWith;

    /**
     * Constructor of ColorReplacementFilter.
     * Private member {@link #replaceWith} will be set on a random color.
     * @param replace initialize with a color to be replaced
     */
    public ColorReplacementFilter(int replace) {
        toReplace = replace;
        replaceWith = createRandomFullColor();
    }

    /**
     * Overloaded Constructor of ColorReplacementFilter.
     * @param replace initialize with a color to be replaced
     * @param replaceWith now specified with a certain color
     */
    public ColorReplacementFilter(int replace, int replaceWith) {
        this(replace);
        this.replaceWith = replaceWith;
    }

    /**
     * If colorPixel matches {@link #toReplace} it replaces with {@link #replaceWith}
     * @param colorPixel value to be processed
     * @return {@link #replaceWith} or colorPixel
     */
    @Override
    protected int calculate(int colorPixel) {
        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);
        int newColorPixel = ImageHelper.setFullColorPixel(r, g, b);
        if (newColorPixel == toReplace) {
            return replaceWith;
        }
        return colorPixel;
    }

    /**
     * Creates full color pixel with random red, green and blue values.
     * @return random pixel
     */
    private int createRandomFullColor() {
        Random r = new Random();
        int r1 = r.nextInt(256);
        int r2 = r.nextInt(256);
        int r3 = r.nextInt(256);
        return ImageHelper.setFullColorPixel(r1, r2, r3);
    }

    @Override
    public String getName() {
        return "ColorReplacement";
    }
}
