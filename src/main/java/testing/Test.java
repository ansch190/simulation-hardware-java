package testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import basics.bits.Bits;
import chip8.cpu.CpuChip8;
import chip8.gpu.GpuChip8;
import chip8.ram.RamChip8;
import chip8.rom.RomChip8;
import general.cpu.opcode.GenericCommand;
import general.vm.BasicVm;
import general.vmgraphicmanager.VmGraphicManager;
import general.vmgraphicmanager.configuration.VmGraphicConfiguration;
import general.vmguimanager.VmGuiManager;
import testing.xml.OpcodeXmlEngine;

/**
 * Created by andreasschw on 19.05.2016.
 *
 * testing something...
 */
public class Test {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        chip8Test_0();

        //xmlOpcodeTest();
    }

    private static void chip8Test_0() {  //Test mit neuem Graphik-System und anderen Änderungen.
        BasicVm vm = new BasicVm();

        List<String> l = new ArrayList<>();

        //create Components
        CpuChip8 cpu = new CpuChip8();
        GpuChip8 gpu = new GpuChip8();
        RamChip8 ram = new RamChip8();
        RomChip8 rom = new RomChip8();

        VmGraphicConfiguration vgmConfig = new VmGraphicConfiguration();
        vgmConfig.loadFromFile(new File("src/main/resources/config/chip8/chip8_graphic_test0.cfg"));
        VmGraphicManager vgm = new VmGraphicManager();
        vgm.setConfig(vgmConfig);

        VmGuiManager vmGui = new VmGuiManager();
        vmGui.addComponent(vgm);

        //add Components
        vm.addComponent(cpu);
        vm.addComponent(gpu);
        vm.addComponent(ram);
        vm.addComponent(rom);
        vm.addComponent(vgm);
        vm.addComponent(vmGui);

        //create Dependencies
        cpu.addComponent(ram);
        cpu.addComponent(rom);
        cpu.addComponent(gpu);

        gpu.addComponent(ram);
        gpu.addComponent(rom);
        gpu.addComponent(cpu);
        gpu.addComponent(vgm);

        rom.addComponent(ram);

        //init Components
        cpu.initOpcodes();
        cpu.initRegister();
        ram.setMemorySize(4096);

        log.info("Basic-Configuration ok!");

        //Rom laden
        String path = "src/main/resources/rom/chip8/IBM Logo.ch8";
        File f = new File(path);
        rom.load(f);
        //rom.loadRomToRam();  //Fehler ohne Ende :-(

        log.info("Rom loaded!");

        log.info("Rom start..");

        vmGui.start();

        vm.mainExecutionLoop();

        log.info("Rom remove..");

        rom.close();

        log.info("completed!");
    }

    private static void xmlOpcodeTest(){
        File f = new File("src/main/resources/opcodes_chip8.xml");

        OpcodeXmlEngine opxml = new OpcodeXmlEngine();
        opxml.loadXML(f);
        opxml.parse();

        //0110 0001 0000 1000
        Bits bits = new Bits(Arrays.asList(
                false, false, false, true,
                false, false, false, false,
                true, false, false, false,
                false, true, true, false
        ));

        GenericCommand gcmd = null;

        gcmd = opxml.find(bits);

        log.info("FOUND: " + gcmd);
    }

}
