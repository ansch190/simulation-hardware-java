package general.vmgraphicmanager.configuration;

import general.configuration.VmConfiguration;
import general.configuration.VmConfigurationParameter;

/**
 * Created by andreas on 17.11.16.
 *
 * Configurations Parameters for Graphics
 */
public class VmGraphicConfiguration extends VmConfiguration {

    public VmGraphicConfiguration() {
        super();
    }

    //### Graphical Methods for specific Parameters ###

    public int getFramesPerSecond(){
        return (int) getParameter(VmConfigurationParameter.OUTPUT_FRAMES_PER_SECOND);
    }

    public void setFramesPerSecond(int fps){
        setParameter(VmConfigurationParameter.OUTPUT_FRAMES_PER_SECOND, fps);
    }

    //### Input ###

    public int getInputWidth(){
        return (int) getParameter(VmConfigurationParameter.INPUT_IMAGE_WIDTH);
    }

    public void setInputWidth(int width){
        setParameter(VmConfigurationParameter.INPUT_IMAGE_WIDTH, width);
    }

    public int getInputHeight(){
        return (int) getParameter(VmConfigurationParameter.INPUT_IMAGE_HEIGHT);
    }

    public void setInputHeight(int height){
        setParameter(VmConfigurationParameter.INPUT_IMAGE_HEIGHT, height);
    }

    //### Output ###

    public int getOutputWidth(){
        return (int) getParameter(VmConfigurationParameter.OUTPUT_SCALING_IMAGE_WIDTH);
    }

    public void setOutputWidth(int width){
        setParameter(VmConfigurationParameter.OUTPUT_SCALING_IMAGE_WIDTH, width);
    }

    public int getOutputHeight(){
        return (int) getParameter(VmConfigurationParameter.OUTPUT_SCALING_IMAGE_HEIGHT);
    }

    public void setOutputHeight(int height){
        setParameter(VmConfigurationParameter.OUTPUT_SCALING_IMAGE_HEIGHT, height);
    }

}
