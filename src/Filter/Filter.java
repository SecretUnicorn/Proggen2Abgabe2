package Filter;

import java.awt.image.BufferedImage;

/**
 * Interface Filter, shared functions of any filter that processes an image.
 */
public interface Filter {

    int black = 0xFF000000;

    /**
     * Interface function to process an image
     * @param image can contain variable amount of images, if a mask is intended
     * @return a new processed image
     */
    BufferedImage process(BufferedImage ... image);

    /**
     * Return the name of the given filter.
     * @return string of name
     */
    String getName();
}
