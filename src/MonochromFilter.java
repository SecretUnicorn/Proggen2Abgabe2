public class MonochromFilter extends PixelFilter {
    @Override
    protected int calculate(int colorPixel) {
        int r = (colorPixel>>16)&0xFF;
        int g = (colorPixel>>8)&0xFF;
        int b = (colorPixel)&0xFF;

        int grey = lightness(maxValue(r,g,b),minValue(r,g,b));

        return (grey << 16) | (grey << 8) | (grey);
    }

    protected int maxValue(int r, int g, int b) {
        if (r > g) {
            return (r > b) ? r : b;
        } else {
            return (g > b) ? g : b;

        }
    }

    protected int minValue(int r, int g, int b) {
        if (r > g) {
            return (g > b) ? b : g;
        } else {
            return (r > b) ? b : r;

        }
    }

    protected int lightness(int max, int min) {
        return (max + min) / 2;
    }
}
