package Filter;

import java.util.Random;

public class ColorReplacementFilter extends PixelFilter {
    private int toReplace;
    private int replaceWith;

    public ColorReplacementFilter(int replace) {
        toReplace = replace;
        replaceWith = createRandomFullColor();
    }

    public ColorReplacementFilter(int replace, int replaceWith) {
        this(replace);
        this.replaceWith = replaceWith;
    }

    @Override
    protected int calculate(int colorPixel) {
        int r = (colorPixel >> 16) & 0xFF;
        int g = (colorPixel >> 8) & 0xFF;
        int b = (colorPixel) & 0xFF;
        int newColorPixel = r << 16 | g << 8 | b;
        if (newColorPixel == toReplace) {
            return replaceWith;
        }
        return colorPixel;
    }

    private int createRandomFullColor() {
        Random r = new Random();
        int r1 = r.nextInt(256);
        int r2 = r.nextInt(256);
        int r3 = r.nextInt(256);
        return (r1 << 16) | ((r2) << 8) | r3;
    }

    @Override
    public String getName() {
        return "ColorReplacement";
    }
}
