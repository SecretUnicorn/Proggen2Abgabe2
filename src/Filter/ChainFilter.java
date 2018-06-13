package Filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ChainFilter implements Filter {
    private ArrayList<Filter> filter = new ArrayList<>();

    /**
     * Processes the same image, with any filter added.
     * An image will be processed over and over with any filter in {@link #filter}.
     * Invokes every process method with an image and if a mask is set, also with a mask.
     * @param image can contain variable amount of images, if a mask is intended
     * @return processed image
     */
    @Override
    public BufferedImage process(BufferedImage... image) {
        BufferedImage image1, image2;
        image1 = (image.length > 0) ? image[0] : null;
        image2 = (image.length > 1) ? image[1] : null;
        boolean maskIsSet = image2 != null;

        if (image1 != null) {
            for (int i = 0; i < filter.size(); i++) {
                if (maskIsSet) {
                    image1 = filter.get(i).process(image1, image2);
                } else {
                    image1 = filter.get(i).process(image1);
                }
            }
            return image1;
        }
        return null;
    }

    /**
     * Adds a filter to an array list {@link #filter}.
     * Added filter will be processed in the same order they were added.
     * @param filter to be added, type: {@link Filter}
     */
    public void addFilter(Filter filter) {
        this.filter.add(filter);
    }

    public String getName() {
        return "";
    }

    /**
     * Return private member of type {@link ArrayList<Filter>}
     * @return {@Link #filter}
     */
    public ArrayList<Filter> getArrayList() {
        return filter;
    }

}
