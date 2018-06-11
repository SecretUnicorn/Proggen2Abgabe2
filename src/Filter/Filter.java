package Filter;

import java.awt.image.BufferedImage;

public interface Filter {

    int black = 0xFF000000;

    BufferedImage process(BufferedImage... image);
}
