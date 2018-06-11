package Filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ChainFilter implements Filter {
    private ArrayList<Filter> filter = new ArrayList<>();

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

    public void addFilter(Filter filter) {
        this.filter.add(filter);
    }

}
