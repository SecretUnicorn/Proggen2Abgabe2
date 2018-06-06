public class ThresholdFilter extends MonochromFilter{

    private int [] greyValue;
    public ThresholdFilter(int ... greyValue) {
        this.greyValue = greyValue;
    }
    @Override
    protected int calculate(int colorPixel) {
        final int white = 0x000000;
        final int black = 0xFFFFFF;

        int r = (colorPixel>>16)&0xFF;
        int g = (colorPixel>>8)&0xFF;
        int b = (colorPixel)&0xFF;

        int checkGreyValues = greyValue.length;

        int grey = lightness(maxValue(r,g,b),minValue(r,g,b));
        int processedPixel = black;
        System.out.println("grey: " + grey);
        System.out.println("check grey: " + greyValue[0]);
        for(int i = 0; i < checkGreyValues; i++) {
            if(grey < greyValue[i]) {
                processedPixel = white;
            }
        }

        return processedPixel;
    }
}
