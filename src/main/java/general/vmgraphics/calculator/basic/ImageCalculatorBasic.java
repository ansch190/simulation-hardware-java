package general.vmgraphics.calculator.basic;

import java.util.concurrent.Callable;

/**
 * Created by andreas on 11.06.16.
 * <p>
 * Basic Template for create other Calculations on Images
 */
public class ImageCalculatorBasic implements Callable {

    protected final int[] rgbData;

    public ImageCalculatorBasic(int[] rgbData) {
        this.rgbData = rgbData;
    }

    @Override
    public Object call() throws Exception {
        return this.rgbData;
    }

}
