package general.vmgraphics.calculator;

import java.awt.*;

import general.vmgraphics.calculator.basic.ImageCalculatorBasic;

/**
 * Created by andreasschw on 02.06.2016.
 * <p>
 * calculates something
 */
public class ImageCalculatorGrey extends ImageCalculatorBasic {

    public ImageCalculatorGrey(int[] data) {
        super(data);
    }

    @Override
    public int[] call() {
        Color c;
        int x;
        for (int i = 1; i < rgbData.length; i++) {
            c = new Color(rgbData[i]);
            x = (int) ((c.getRed() * 0.2989) + (c.getGreen() * 0.587) + (c.getBlue() * 0.114));
            c = new Color(x, x, x);
            rgbData[i] = c.getRGB();
        }
        return rgbData;
    }

}
