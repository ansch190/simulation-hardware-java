package general.vmgraphicmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;
import general.vmgraphicmanager.configuration.VmGraphicConfiguration;
import general.vmgraphics.GraphicEngine;

/**
 * Created by andreas on 17.11.16.
 *
 * Management der Graphik und Schnittstelle zwischen Vm und Gpu
 */
public class VmGraphicManager extends VmComponent implements VmGraphicManagerInterface {

    protected static final Logger log = LoggerFactory.getLogger(VmGraphicManager.class);

    private VmGraphicConfiguration graphicConfiguration = null;

    private Queue<BufferedImage> inputQueue = null;  //maximale Anzahl vielleicht mal einführen mit automatischem verwerfen von alten Bildern

    private Queue<BufferedImage> outputQueue = null;

    private boolean picCopy = false;

    public VmGraphicManager() {
        init();
    }

    @Override
    public void init() {
        super.componentType = VmComponentType.VM_GRAPHIC_MANAGER;
        super.components = new HashMap<>();

        inputQueue = new ConcurrentLinkedQueue<>();
        outputQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public VmComponentType getType() {
        return super.getType();
    }

    @Override
    public BufferedImage getImage(){
        log.trace("outputqueue size: " + outputQueue.size());
        if(outputQueue.size() == 1){
            log.trace("pic copy!");
            picCopy = true;
            return outputQueue.peek();
        }
        else if(outputQueue.size() == 2){
            if(picCopy){
                outputQueue.remove();
                log.trace("pic skip and copy!");
                return outputQueue.peek();
            }
            else{
                log.trace("pic return!");
                return outputQueue.poll();
            }
        }
        else if(outputQueue.size() > 2){
            if(picCopy){
                outputQueue.remove();
                picCopy = false;
                log.trace("pic skip!");
                return outputQueue.poll();
            }
            else{
                log.trace("pic return!");
                return outputQueue.poll();
            }
        }
        else{
//            log.error("outputqueue ist leer! Darf nicht passieren!");
            //Leeres Bild senden als Rettung xD
//            System.exit(1);
            return new BufferedImage(getConfig().getOutputWidth(), getConfig().getOutputHeight(), BufferedImage.TYPE_INT_RGB);
        }
    }

    @Override
    public void addImage(BufferedImage image){
        if(image != null){
            inputQueue.offer(image);
            image = inputQueue.poll();
            image = calculateImage(image);
            outputQueue.offer(image);
        }
        else{
            log.error("Input-Image == NULL ! nicht erlaubt!");
        }
    }

    private BufferedImage calculateImage(BufferedImage image){
        //Todo: Veränderte Bildverbesserung je nach Architektur. in die Configuration übernehmen
        image = GraphicEngine.scale(image, getConfig().getOutputWidth(), getConfig().getOutputHeight());
        return image;
    }

    @Override
    public void setConfig(VmGraphicConfiguration config) {
        graphicConfiguration = config;
    }

    @Override
    public VmGraphicConfiguration getConfig() {
        return graphicConfiguration;
    }

}
