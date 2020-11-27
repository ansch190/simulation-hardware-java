package general.vmgraphics;

import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * Created by andreasschw on 02.06.2016.
 *
 * testing...
 */
public class Test {

  public static void main(String[] args) {
      test0();
  }

  public static void test0() {
    GraphicEngine ge = new GraphicEngine();

    int WIDTH_WINDOW = 1700;
    int HEIGHT_WINDOW = 800;

    BufferedImage image = ge.generateRandomImage(WIDTH_WINDOW, HEIGHT_WINDOW);
    //BufferedImage image2 = ge.toGrey(image);
    BufferedImage image2 = image;
    BufferedImage image3 = ge.scale(image2, 1387, 576);

    //System.exit(0);

    JFrame jFrame = new JFrame();
    jFrame.setTitle("test");
    jFrame.setSize(WIDTH_WINDOW + 50, HEIGHT_WINDOW + 50);
    jFrame.setResizable(false);
    jFrame.setLocationRelativeTo(null);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    ImageIcon icon = new ImageIcon(image3);
    JLabel label = new JLabel(icon);

    jFrame.add(label);

    //jFrame.pack();
    jFrame.setVisible(true);
  }

}
