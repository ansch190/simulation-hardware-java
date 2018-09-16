package general.vmgraphics.calculator;

import java.util.Random;

import general.vmgraphics.calculator.basic.ImageCalculatorBasic;

/**
 * Created by andreas on 11.06.16.
 *
 * calculate Random RGB-Data for an Image
 */
public class ImageCalculatorRandom extends ImageCalculatorBasic {

    public ImageCalculatorRandom(int[] rgbData) {
        super(rgbData);
    }

    @Override
    public Object call() throws Exception {
        Random rnd = new Random();
        for(int i=1; i<rgbData.length; i++){
            rgbData[i] = rnd.nextInt();
        }

        return rgbData;
    }

}
