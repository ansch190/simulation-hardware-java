package general.vmgraphic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;
import general.vmgraphics.GraphicEngine;

/**
 * Created by andreas on 11.06.16.
 * <p>
 * This Class builds the Connection between VmGpu and BasicVm
 */
public class VmGraphicEngine extends VmComponent implements VmGraphicEngineInterface {

    //List 0=old Image, n=new Image;

    protected List<BufferedImage> inputImages;  //Images from the VmGpu, not calculated for the BasicVm!

    protected List<BufferedImage> outputImages;  //Images calculated and ready for the BasicVm!

    public VmGraphicEngine() {
        super();
    }

    @Override
    public void init() {
        super.componentType = VmComponentType.VM_GPU;
        super.components = new HashMap<>();

        inputImages = new ArrayList<>();
        outputImages = new ArrayList<>();
    }

    @Override
    public void addImage(BufferedImage image) {
        inputImages.add(image);
        calculateImage(inputImages.get(inputImages.size()-1));  //todo: gefällt mir noch nicht!!! vlt. ein Thread für die Bearbeitung!
    }

    @Override
    public BufferedImage getImage() {
        if(!outputImages.isEmpty()){
            return outputImages.get(0);
        }
        else{
            log.info("outputImages EMPTY!");
            System.exit(1);
        }
        return null;
    }

    //### CHIP-8 - VALUES ###

    private final int IMAGE_WIDTH_INPUT = 64;
    private final int IMAGE_HEIGHT_INPUT = 32;

    private final int IMAGE_WIDTH_OUTPUT = IMAGE_WIDTH_INPUT * 5;
    private final int IMAGE_HEIGHT_OUTPUT = IMAGE_HEIGHT_INPUT * 5;

    private void calculateImage(BufferedImage inputImage){
        BufferedImage outputImage = GraphicEngine.scale(inputImage, IMAGE_WIDTH_OUTPUT, IMAGE_HEIGHT_OUTPUT);
        if(!outputImages.isEmpty()){ outputImages.remove(0); }
        outputImages.add(outputImage);
    }

}
