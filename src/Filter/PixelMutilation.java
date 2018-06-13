package Filter;

import java.util.Random;

public class PixelMutilation extends AreaFilter {

    private int radius;

    /**
     * Constructor of PixelMutilation
     */
    public PixelMutilation() {
        Random r = new Random();
        this.radius = r.nextInt(20);
    }

    /**
     * This calculate method is a wrong method.
     * Originally it would have been used to blur,
     * because of the fact that the result is funny, we kept this 'Filter'
     * @param pixel array of pixel to process
     * @param maskPixel mask pixel array
     * @param index index of pixel to be processed
     * @param width of image
     * @param height of image
     * @return mutilated pixel
     */
    @Override
    protected int calculate(int[] pixel, int[] maskPixel, int index, int width, int height) {
        int colorPixel = pixel[index];
        int nextColor = pixel[index + radius < pixel.length ? index + radius : index - 1];
        return (int) (Math.round((colorPixel + nextColor) / 2.0));
    }

    @Override
    public String getName() {
        return "Mutilation";
    }
}
