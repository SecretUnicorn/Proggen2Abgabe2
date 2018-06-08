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
        if (colorPixel == toReplace) {
            return replaceWith;
        }
        return colorPixel;
    }

    private int createRandomFullColor() {
        return (createRandomSpecColor() << 16) | ((createRandomSpecColor()) << 8) | createRandomSpecColor();
    }

    private int createRandomSpecColor() {
        return (int)(Math.random()*256);
    }
}
