package general.configuration;

/**
 * Created by andreas on 17.11.16.
 *
 * Used Configuration-Parameters
 */
public enum VmConfigurationParameter {

    //### General Parameters ###

    TEST_PARAMETER(VmConfigurationParameterType.GENERAL),

    //### Graphical Parameters ###

    OUTPUT_FRAMES_PER_SECOND(VmConfigurationParameterType.GRAPHIC),  //Integer

    OUTPUT_SCALING_IMAGE_WIDTH(VmConfigurationParameterType.GRAPHIC),  //Integer
    OUTPUT_SCALING_IMAGE_HEIGHT(VmConfigurationParameterType.GRAPHIC),  //Integer

    INPUT_IMAGE_WIDTH(VmConfigurationParameterType.GRAPHIC),
    INPUT_IMAGE_HEIGHT(VmConfigurationParameterType.GRAPHIC),

    ;

    private VmConfigurationParameterType type;

    private VmConfigurationParameter(VmConfigurationParameterType type){
        this.type = type;
    }

    public VmConfigurationParameterType getType(){
        return this.type;
    }

}
