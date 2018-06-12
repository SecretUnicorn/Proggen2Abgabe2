import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import Filter.*;

import javax.imageio.ImageIO;

/**
 * MainController
 * Class to execute main function.
 * Starts with args given and processes an image,
 * with a given image filter.
 */
public class MainController {

    /**
     * Main function that starts with the programm arguments.
     * @param args
     * @throws IOException because main writes a new image
     */
    public static void main(String[] args) throws IOException {
        HashMap<String,Filter> allFilters = initMap();
        BufferedImage image;
        BufferedImage mask;
        String newFilePath;
        StringBuilder sb = new StringBuilder();
        Filter filter = null;
        boolean testingMode = false;
        if(allFilters.containsKey(args[0])) {
            filter = allFilters.get(args[0]);
        } else if(args[0].equals("test")) {
            testingMode = true;
        }
        else {
            filter = filterRequest(allFilters);
        }
        if(!testingMode) {
            try {
                image = ImageIO.read(new File(args[1]));
                if (args[2].equals("-m")) {
                    mask = ImageIO.read(new File(args[3]));
                    image = filter.process(image, mask);
                    newFilePath = args[4];
                } else {
                    image = filter.process(image);
                    newFilePath = args[2];
                }
                ImageIO.write(image, "bmp", new File(newFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedImage testingImage;
                image = ImageIO.read(new File(args[1]));
                if (args[2].equals("-m")) {
                    mask = ImageIO.read(new File(args[3]));
                    newFilePath = args[4];
                    for(Map.Entry e: allFilters.entrySet()) {
                        filter = (Filter) e.getValue();
                        testingImage = filter.process(image,mask);
                        String newFileName = sb.append(newFilePath).append("_").append(e.getKey()).append(".bmp").toString();
                        ImageIO.write(testingImage, "bmp", new File(newFileName));
                        sb.delete(0,newFileName.length());
                    }
                } else {
                    newFilePath = args[2];
                    for(Map.Entry e: allFilters.entrySet()) {
                        filter = (Filter) e.getValue();
                        testingImage = filter.process(image);
                        String newFileName = sb.append(newFilePath).append("_").append(e.getKey()).append(".bmp").toString();
                        ImageIO.write(testingImage, "bmp", new File(newFileName));
                        sb.delete(0,newFileName.length());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to initialize a HashMap.
     * Initializes a HashMap filled with String keys, that matches a certain
     * initialized Filter.
     * @return a complete <code>HashMap<String,Filter></code>
     */
    public static HashMap<String, Filter> initMap() {
        HashMap<String,Filter> filter = new HashMap<String,Filter>();
        filter.put("blur_3", new BlurFilter(3));
        filter.put("blur_5", new BlurFilter(5));
        filter.put("minimum_3", new MinimumFilter(3));
        filter.put("minimum_5", new MinimumFilter(5));
        filter.put("invert", new InvertFilter());
        filter.put("monochrome", new MonochromFilter());
        filter.put("colorband_red", new ColorBandFilter(ColorBand.RED));
        filter.put("colorband_green", new ColorBandFilter(ColorBand.GREEN));
        filter.put("colorband_blue", new ColorBandFilter(ColorBand.BLUE));
        filter.put("threshold_128", new ThresholdFilter(128));
        filter.put("threshold_192", new ThresholdFilter(192));
        filter.put("multithreshold", new ThresholdFilter(64, 128, 192));
        filter.put("colorreplacement_98", new ColorReplacementFilter(ImageHelper.setGreyPixel(96)));
        filter.put("colorreplacement_160", new ColorReplacementFilter(ImageHelper.setGreyPixel(160)));
        filter.put("colorreplacement_255", new ColorReplacementFilter(ImageHelper.setGreyPixel(255)));
        filter.put("pixel_20", new PixelGraphicFilter(20));
        filter.put("pixel_40", new PixelGraphicFilter(40));
        filter.put("pixel_60", new PixelGraphicFilter(60));
        filter.put("pixel_100", new PixelGraphicFilter(100));
        Filter warhol = new ChainFilter();
        ((ChainFilter) warhol).addFilter(new ThresholdFilter(64,128,192));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(0)));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(96)));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(160)));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(255)));
        filter.put("warhol", warhol);
        filter.put("mutilation", new PixelMutilation());
        return filter;
    }

    /**
     * Handles the request for a Filter.
     * Checks if HashMap has a matching key, if not prints all entries of HashMap.
     * Then User can type a filter he wants to use.
     * Until the first time a filter is written correctly.
     * @param filterHashMap containing all keys and values to check
     * @return a filter that matches a key.
     * @throws IOException
     */
    public static Filter filterRequest(HashMap<String, Filter> filterHashMap) throws IOException{
        boolean filterFound = false;
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        while(!filterFound) {
            System.out.println("Der angegebene Filter wurde nicht gefunden.");
            System.out.println("Bitte wählen Sie einen der folgenden Filter.");
            for(Map.Entry e: filterHashMap.entrySet()) {
                System.out.println(e.getKey());
            }
            System.out.println("Tätigen sie nun ihre Eingabe: ");
            String s = b.readLine();
            if(filterHashMap.containsKey(s)) {
                return filterHashMap.get(s);
            }
        }
        return null;
    }
}
