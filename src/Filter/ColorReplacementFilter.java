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
        int r = ImageHelper.getRed(colorPixel);
        int g = ImageHelper.getGreen(colorPixel);
        int b = ImageHelper.getBlue(colorPixel);
        int newColorPixel = ImageHelper.setFullColorPixel(r, g, b);
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
        return ImageHelper.setFullColorPixel(r1, r2, r3);
    }
}
