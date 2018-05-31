public class MonochromFilter extends PixelFilter {
    @Override
    protected int calculate(int colorPixel) {
        String color = Integer.toBinaryString(colorPixel);
        int r, g, b;
        r = bitToInteger(color.substring(0, 8));
        g = bitToInteger(color.substring(8, 16));
        b = bitToInteger(color.substring(16, 24));

        int grey = lightness(maxValue(r,g,b),minValue(r,g,b));
        String binaryGrey = Integer.toBinaryString(grey);

        StringBuilder newColorString = new StringBuilder();

        newColorString.append(binaryGrey).append(binaryGrey).append(binaryGrey);

        return bitToInteger(newColorString.toString());
    }

    private int bitToInteger(String bitString) {
        final int exp = bitString.length() - 1;
        int result = 0;
        for (int i = 0; i < bitString.length(); i++) {
            int bit = bitString.charAt(i);
            result += bit % 48 * Math.pow(2, (double) exp - i);
        }
        return result;
    }

    private int maxValue(int r, int g, int b) {
        if (r > g) {
            return (r > b) ? r : b;
        } else {
            return (g > b) ? g : b;

        }
    }

    private int minValue(int r, int g, int b) {
        if (r > g) {
            return (g > b) ? b : g;
        } else {
            return (r > b) ? b : r;

        }
    }

    private int lightness(int max, int min) {
        return (max + min) / 2;
    }
}
