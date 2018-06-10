import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import Filter.*;

public class MainController {
    public static void main(String[] args) {
        HashMap<String,Filter> allFilters = initMap();
        Filter filter = null;
        boolean filterFound = false;
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        if(allFilters.containsKey(args[0])) {
            filter = allFilters.get(args[0]);
        } else {
            while(!filterFound) {
                System.out.println("Der angegebene Filter wurde nicht gefunden.");
                System.out.println("Bitte wählen Sie einen der folgenden Filter.");

            }
        }

    }

    public static HashMap<String, Filter> initMap() {
        HashMap<String,Filter> filter = new HashMap<String,Filter>();
        filter.put("blur_3", new BlurFilter(3));
        filter.put("blur_5", new BlurFilter(5));
        filter.put("monochrome", new MonochromFilter());
        filter.put("colorband_red", new ColorBandFilter(ColorBand.RED));
        filter.put("colorband_green", new ColorBandFilter(ColorBand.GREEN));
        filter.put("colorband_blue", new ColorBandFilter(ColorBand.BLUE));
        filter.put("threshold_128", new ThresholdFilter(128));
        filter.put("threshold_192", new ThresholdFilter(192));
        filter.put("multithreshold", new ThresholdFilter(64, 128, 192));
        filter.put("colorreplacement_98", new ColorReplacementFilter((98 << 16) | (98 << 8) | 98));
        filter.put("colorreplacement_160", new ColorReplacementFilter((160 << 16) | (160 << 8) | 160));
        filter.put("colorreplacement_255", new ColorReplacementFilter((255 << 16) | (255 << 8) | 255));
        filter.put("pixel_20", new PixelGraphicFilter(20));
        filter.put("pixel_40", new PixelGraphicFilter(40));
        filter.put("pixel_60", new PixelGraphicFilter(60));
        Filter warhol = new ChainFilter();
        ((ChainFilter) warhol).addFilter(new ThresholdFilter(64,128,192));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(0));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(98));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(160));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(255));
        filter.put("warhol", warhol);
        return filter;
    }
}
