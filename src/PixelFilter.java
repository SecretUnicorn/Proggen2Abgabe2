import java.awt.image.BufferedImage;

public class PixelFilter implements Filter {
    @Override
    public  BufferedImage process(BufferedImage... image) {
        BufferedImage image1, image2;
        int [] pixel;
        int [] processedPixel;
        image1 = (image.length > 0) ? image[0] : null;
        image2 = (image.length > 1) ? image[1] : null;

        if (image1 != null) {
            int width = image1.getWidth();
            int height = image1.getHeight();

            pixel = new int[width*height];
            processedPixel = new int[pixel.length];

            for (int i = 0; i < width; i++) {
                for( int j = 0; j < height; j++) {
                    pixel[i * height + j] = image1.getRGB(i,j);
                }
            }

            int i = 0;
            for(int specPixel: pixel) {
                processedPixel[i] = calculate(specPixel);
                i++;
            }
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int a = 0; a < width; a++) {
                for( int j = 0; j < height; j++) {
                    result.setRGB(a,j,processedPixel[a*height + j]);
                }
            }
            return result;
        }
        return null;
    }

    protected int calculate(int colorPixel) {
        return colorPixel;
    }
}
