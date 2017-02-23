package general.configuration;

import java.io.File;

import general.vmgraphicmanager.configuration.VmGraphicConfiguration;

/**
 * Created by andreas on 17.11.16.
 *
 * Test for the VmConfiguration
 */
public class VmConfigurationTest {

    public static void main(String[] args){
        test0();
    }

    public static void test0(){
        VmConfiguration vmc = new VmConfiguration();

        vmc.setParameter(VmConfigurationParameter.TEST_PARAMETER, 12);
        int x = -1;
        System.out.println(x);
        x = (int) vmc.getParameter(VmConfigurationParameter.TEST_PARAMETER);
        System.out.println(x);
        vmc.clearParameter();
        try{
//            x = (int) vmc.getParameter(VmConfigurationParameter.TEST_PARAMETER);
//            System.out.println(x);
        }catch(Exception e){ e.printStackTrace(); }

        VmGraphicConfiguration vmgc = new VmGraphicConfiguration();
        vmgc.setFramesPerSecond(60);
        vmgc.setOutputWidth(100);
        vmgc.setOutputHeight(80);
        System.out.println(vmgc.getFramesPerSecond() + " " + vmgc.getOutputWidth() + " " + vmgc.getOutputHeight());
        try{
//            vmgc.clearParameter();
//            System.out.println(vmgc.getFramesPerSecond() + " " + vmgc.getWidth() + " " + vmgc.getHeight());
        }
        catch (Exception e){ e.printStackTrace(); }
        vmgc.saveToFile(new File("src/test/resources/config/testGraphicConfig0.cfg"));
        vmgc.loadFromFile(new File("src/test/resources/config/testGraphicConfig0.cfg"));
        System.out.println(vmgc.getFramesPerSecond() + "#" + vmgc.getOutputWidth() + "#" + vmgc.getOutputHeight());
    }

}
