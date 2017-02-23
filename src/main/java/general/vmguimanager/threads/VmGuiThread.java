package general.vmguimanager.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

import javax.swing.*;

import general.vmgraphicmanager.VmGraphicManager;
import general.vmguimanager.VmGuiManager;

/**
 * Created by andreas on 18.11.16.
 *
 * Eigentliche Gui mit automatischer Aktualisierung der Bilder
 */
public class VmGuiThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(VmGuiThread.class);

    private VmGraphicManager vgm = null;

    //---------------------------------------------

    private JFrame jFrame;
    private BufferedImage image;
    private ImageIcon imageIcon;
    private JLabel jLabel;

    private final int IMAGE_WINDOW_BORDER_WIDTH = 6;  //40
    private final int IMAGE_WINDOW_BORDER_HEIGHT = 40;  //40

    //---------------------------------------------

    public VmGuiThread(VmGraphicManager vgm) {
        this.vgm = vgm;
    }

    private void init() {
        jFrame = new JFrame();
        jFrame.setSize(vgm.getConfig().getOutputWidth() + IMAGE_WINDOW_BORDER_WIDTH, vgm.getConfig().getOutputHeight() + IMAGE_WINDOW_BORDER_HEIGHT);
        jFrame.setTitle("VmGui - Unknown");
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);  //Mitte des Bildschirms

        image = new BufferedImage(vgm.getConfig().getInputWidth(), vgm.getConfig().getInputHeight(), BufferedImage.TYPE_INT_RGB);

        imageIcon = new ImageIcon();
        imageIcon.setImage(image);

        jLabel = new JLabel(imageIcon);

        jFrame.add(jLabel);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        init();

        //Zeitmanagement
        final int second = 1000;

        long startTime = 0;

        int user_fps = 1;

        int imageCounter = 0;

        long waitTime = 0;

        while (VmGuiManager.run) {
            if (imageCounter == 0) {
                startTime = System.currentTimeMillis();
            }

            drawImage();
            imageCounter++;

            user_fps = vgm.getConfig().getFramesPerSecond();

            if (imageCounter == user_fps) {
                imageCounter = 0;
                waitTime = 0;

                long timeDuration = System.currentTimeMillis() - startTime;

                if (timeDuration > second) {
                    long timeDiff = timeDuration - second;
                    log.info("User-Fps zu hoch! Nicht erreichbar! Fps runtersetzen! Time: " + timeDiff);
                } else if (timeDuration < second) {
                    int timeDiff = (int) (second - timeDuration);
                    waitTime = (long) ((double) (timeDiff / user_fps));
                    log.info("User-Fps zu niedrig! Programm zu schnell, Wartezeit pro Bild wird eingebaut. Time: " + waitTime);
                } else {
                    log.error("Fehler! Problem suchen!");
                }
            }

            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        log.debug("!!! GUI-THREAD ENDE !!!");
    }

//    @Override
//    public void run() {
//        init();
//
//        //Zeitmanagement
//        long time1, time2, result, fps, waitTime;
//
//        long user_fps = 10;
//
//        double factor;
//
//
////        while (!Thread.currentThread().isInterrupted()) {
//        while (VmGuiManager.run) {
//            time1 = System.currentTimeMillis();
//
//            drawImage();
//
//            time2 = System.currentTimeMillis();
//
//            result = time2 - time1;
//            log.info("result: " + result);
//
//            if (result == 0) {
//                result = 1;
//            }
//
//            fps = (long) (1000.0 / result);
//            log.info("max fps: " + fps);
//
//            factor = fps / user_fps;
//            log.info("factor: " + factor);
//
//            waitTime = ((long) (factor * result)) - result;
//            log.info("waitTime: " + waitTime);
//
//            try {
//                Thread.sleep(waitTime);
//            } catch (InterruptedException e0) {
//                e0.printStackTrace();
//                log.info("WAIT-INTERRUPTED!!!");
//                log.info("#" + Thread.currentThread().isInterrupted());
//            }
//        }
//
//        log.info("!!! THREAD-ENDE !!!");
//    }

    //neues Bild im Fenster bzw in der Gui anzeigen.
    public void drawImage() {
        //draw Image
        imageIcon.setImage(vgm.getImage());

        //Fenster aktualisieren
        jFrame.repaint();
    }

}
