package general.vmgraphic;

import general.vm.components.VmComponentInterface;

import java.awt.image.BufferedImage;

/**
 * Created by andreas on 11.06.16.
 *
 * Interface for the VmGraphicEngine
 */
public interface VmGraphicEngineInterface extends VmComponentInterface {

    //gibt das aktuelle Bild zurück, auch wenn es keine Änderungen enthält!
    public BufferedImage getImage();

    //fügt ein Bild aus einer VmGpu hinzu
    public void addImage(BufferedImage image);

}
