import java.awt.image.BufferedImage;

public interface Filter {
    BufferedImage process(BufferedImage ... image);
}
