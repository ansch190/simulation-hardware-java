package general.vmgraphicmanager;

import java.awt.image.BufferedImage;

import general.vmgraphicmanager.configuration.VmGraphicConfiguration;

/**
 * Created by andreas on 18.11.16.
 *
 * Interface für den GraphicManager
 */
public interface VmGraphicManagerInterface {

    /**
     * returns an Image for the Gui. Only calculated Images were returned
     *
     * @return an calculated
     */
    public BufferedImage getImage();

    /**
     * add an Image from the VmGpu to the VmGraphicManager for Postprocessing
     *
     * @param image an BufferedImage
     */
    public void addImage(BufferedImage image);

    /**
     * set specific Architecture(Chip-8,GBA,...) Configuration
     *
     * @param config the Graphical-Config
     */
    public void setConfig(VmGraphicConfiguration config);

    /**
     * return the specific Graphical-Configuration
     *
     * @return the Graphical-Config
     */
    public VmGraphicConfiguration getConfig();

}
