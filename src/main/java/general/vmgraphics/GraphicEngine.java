package general.vmgraphics;

import general.vmgraphics.calculator.ImageCalculatorGrey;
import general.vmgraphics.calculator.ImageCalculatorRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by andreasschw on 02.06.2016.
 * <p>
 * graphical operations
 */
public class GraphicEngine {

    protected static final Logger log = LoggerFactory.getLogger(GraphicEngine.class);

    private static ExecutorService ex;

    private static int cpuCores = 1;

    public GraphicEngine() {
        init();
    }

    private static void init() {
        cpuCores = Runtime.getRuntime().availableProcessors();
        ex = Executors.newCachedThreadPool();
    }

    //### THREADING-FUNCTIONS ###

    /**
     * create Callable for calculating Lines of the Image
     *
     * @param image the Image
     * @param call the special Calculation
     * @return
     */
    private static List<Future> createThreads(BufferedImage image, Callable call) {
        List<Future> futures = new ArrayList<>();
        Future f = null;
        //create Threads
        for (int y = 0; y < image.getHeight(); y++) {
            int[] yData = new int[image.getWidth() + 1];
            image.getRGB(0, y, image.getWidth(), 1, yData, 1, 0);

            yData[0] = y;

            if(call.getClass() == ImageCalculatorRandom.class){
                f = ex.submit(new ImageCalculatorRandom(yData));
            }
            else if(call.getClass() == ImageCalculatorGrey.class){
                f = ex.submit(new ImageCalculatorGrey(yData));
            }
            else{
                log.info("Unknown Class!");
                System.exit(0);
            }

            futures.add(f);
        }
        return futures;
    }

    /**
     * collects the Results of the Threads
     *
     * @param imageHeight Image Height
     * @param futures
     * @return the calculated Results
     */
    private static List<int[]> getResults(int imageHeight, List<Future> futures){
        List<int[]> results = new ArrayList<>();
        //get Results
        int x = 0;
        while (x != imageHeight) {
            for (int i = 0; i < futures.size(); i++) {
                Future f = futures.get(i);
                if (f.isDone()) {
                    f = futures.remove(i);
                    try {
                        results.add((int[]) f.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    x++;
                }
            }
        }
        return results;
    }

    /**
     * saves the collected Results to an Image
     *
     * @param image
     * @param rgbData
     */
    private static void setImageRGB(BufferedImage image, List<int[]> rgbData){
        for (int i = 0; i < rgbData.size(); i++) {
            int[] rgb = rgbData.get(i);
            image.setRGB(0, rgb[0], rgb.length - 1, 1, rgb, 1, 0);
        }
    }

    //### IMAGE-FUNCTIONS ###

    /**
     * generates an Image with random Values for Tests
     *
     * @param width Image-Width
     * @param height Image.Height
     * @return the random Image
     */
    public static BufferedImage generateRandomImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //create Threads
        int[] data = new int[1];
        List<Future> futures = createThreads(image, new ImageCalculatorRandom(data));
        //get Results
        List<int[]> results = getResults(image.getHeight(), futures);
        //save Results
        setImageRGB(image, results);

        return image;
    }

    /**
     * change the Color of the given Image to Grey
     *
     * @param image the Image
     * @return the new Image
     */
    public static BufferedImage toGrey(BufferedImage image) {
        //create Threads
        int[] data = new int[1];
        List<Future> futures = createThreads(image, new ImageCalculatorGrey(data));
        //get Results
        List<int[]> results = getResults(image.getHeight(), futures);
        //save Results
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        setImageRGB(result, results);

        return result;
    }

    /**
     * scales the given Image to new Dimensions
     *
     * @param image the given Image
     * @param newWidth the new Width
     * @param newHeight the new Height
     * @return the new Image
     */
    public static BufferedImage scale(BufferedImage image, int newWidth, int newHeight) {
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();

        double scaleWidth = (double) newWidth / (double) oldWidth;
        double scaleHeight = (double) newHeight / (double) oldHeight;

        BufferedImage result = new BufferedImage(newWidth, newHeight, image.getType());

        if (scaleWidth < 1.0 && scaleHeight < 1.0) {
            //kleiner machen
            result = scaleDown(image, result, scaleWidth, scaleHeight);
        } else if (scaleWidth > 1.0 && scaleHeight > 1.0) {
            //größer machen
            result = scaleUp(image, result, scaleWidth, scaleHeight);
        } else {
            System.out.println("Error! Wrong Size!");
        }

        return result;
    }

    private static BufferedImage scaleDown(BufferedImage source, BufferedImage result, double scaleX, double scaleY) {
        int rgb, newX, newY;
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                //Get Color
                rgb = source.getRGB(x, y);
                //calculate new Position
                newX = (int) (x * scaleX);
                newY = (int) (y * scaleY);
                //Set Color
                result.setRGB(newX, newY, rgb);
            }
        }
        return result;
    }

    private static BufferedImage scaleUp(BufferedImage source, BufferedImage result, double scaleX, double scaleY) {
        int rgb, newX, newY;
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                //calculate new Position
                newX = (int) (x * (1.0 / scaleX));
                newY = (int) (y * (1.0 / scaleY));
                //Get Color
                rgb = source.getRGB(newX, newY);
                //Set Color
                result.setRGB(x, y, rgb);
            }
        }
        return result;
    }

}
