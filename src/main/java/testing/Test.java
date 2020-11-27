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
import general.cpu.VmCpu;
import general.cpu.opcode.GenericCommand;
import general.gpu.VmGpu;
import general.rom.VmRom;
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
        //test0();
        //test1();

        //test2();

//        chip8Test5();
//        chip8Test6();

        chip8Test7();

//        xmlOpcodeTest();
    }

    public static void xmlOpcodeTest(){
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

    public static void chip8Test7() {  //Test mit neuem Graphik-System und anderen Änderungen.
        BasicVm vm = new BasicVm();

        List<String> l  = new ArrayList<>();

        //create Components
        CpuChip8 cpu = new CpuChip8();
        GpuChip8 gpu = new GpuChip8();
        RamChip8 ram = new RamChip8();
        RomChip8 rom = new RomChip8();

        VmGraphicConfiguration vgmConfig = new VmGraphicConfiguration();
        vgmConfig.loadFromFile(new File("src/main/resources/config/chip8_graphic_test0.cfg"));
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
        String path = "src/main/resources/rom/IBM Logo.ch8";
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

    public static void chip8Test6() {  //Test mit neuem Graphik-System
        BasicVm vm = new BasicVm();

        //create Components
        CpuChip8 cpu = new CpuChip8();
        GpuChip8 gpu = new GpuChip8();
        RamChip8 ram = new RamChip8();
        RomChip8 rom = new RomChip8();

        VmGraphicConfiguration vgmConfig = new VmGraphicConfiguration();
        vgmConfig.loadFromFile(new File("src/main/resources/config/chip8_graphic_test0.cfg"));
        VmGraphicManager vgm = new VmGraphicManager();
        vgm.setConfig(vgmConfig);

        //VmGuiManager vmGui = new VmGuiManager();
        //vmGui.addComponent(vgm);

        //add Components
        vm.addComponent(cpu);
        vm.addComponent(gpu);
        vm.addComponent(ram);
        vm.addComponent(rom);
        vm.addComponent(vgm);
        //vm.addComponent(vmGui);

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
        cpu.initRegister();
        ram.setMemorySize(4096);

        log.info("Basic-Configuration ok!");

        //Rom laden
        String path = "src/main/resources/rom/IBM Logo.ch8";
        File f = new File(path);
        rom.load(f);
        //rom.loadRomToRam();  //Fehler ohne Ende :-(

        log.info("Rom loaded!");

        log.info("Rom start..");

        vm.mainExecutionLoop();

        log.info("Rom remove..");

        rom.close();

        log.info("completed!");
    }

    public static void chip8Test5() {
        BasicVm vm = new BasicVm();

        //create Components
        CpuChip8 cpu = new CpuChip8();
        GpuChip8 gpu = new GpuChip8();
        RamChip8 ram = new RamChip8();
        RomChip8 rom = new RomChip8();

        //add Components
        vm.addComponent(cpu);
        vm.addComponent(gpu);
        vm.addComponent(ram);
        vm.addComponent(rom);

        //create Dependencies
        cpu.addComponent(ram);
        cpu.addComponent(rom);
        cpu.addComponent(gpu);

        gpu.addComponent(ram);
        gpu.addComponent(rom);
        gpu.addComponent(cpu);

        rom.addComponent(ram);

        //init Components
        cpu.initRegister();
        ram.setMemorySize(4096);

        log.info("Basic-Configuration ok!");

        //Rom laden
        String path = "src/main/resources/rom/IBM Logo.ch8";
        File f = new File(path);
        rom.load(f);
        //rom.loadRomToRam();  //Fehler ohne Ende :-(

        log.info("Rom loaded!");

        log.info("Rom start..");

        vm.mainExecutionLoop();

        log.info("Rom remove..");

        rom.close();

        log.info("completed!");
    }

    public static void chip8Test4() {
        BasicVm vm = new BasicVm();

        //RAM
        RamChip8 ram = new RamChip8();
        vm.addComponent(ram);
        ram.setMemorySize(4096);
        //GPU
        VmGpu gpu = new VmGpu();
        vm.addComponent(gpu);
        //CPU
        CpuChip8 cpu = new CpuChip8();
        vm.addComponent(cpu);
        cpu.addComponent(ram);
        cpu.initRegister();
        //ROM
        RomChip8 rom = new RomChip8();
        vm.addComponent(rom);
        gpu.addComponent(rom);

        //Spiel laden
        String path = "src/main/resources/rom/IBM Logo.ch8";
        //String path = "src/main/resources/rom/Chip8 emulator Logo [Garstyciuks].ch8";
        //String path = "src/main/resources/rom/Tetris [Fran Dachille, 1991].ch8";
        File f = new File(path);
        rom.load(f);

        //Spiel ausführen
        vm.mainExecutionLoop();

        //Spiel entfernen
        rom.close();

        //Assert.assertEquals("Command - Not identified!", Commands.CLEAR_SCREEN, results.get(0));
    }

    public static void chip8Test3() {
        BasicVm vm = new BasicVm();

        //CPU
        VmCpu cpu = new CpuChip8();
        vm.addComponent(cpu);
        //ROM
        VmRom rom = new RomChip8();
        vm.addComponent(rom);

        //Spiel laden
        String path = "/home/andreas/IdeaProjects/emulation2016/src/main/resources/rom/IBM Logo.ch8";
        //String path = "/home/andreas/IdeaProjects/emulation2016/src/main/resources/rom/Chip8 emulator Logo [Garstyciuks].ch8";
        //String path = "/home/andreas/IdeaProjects/emulation2016/src/main/resources/rom/Tetris [Fran Dachille, 1991].ch8";
        File f = new File(path);
        rom.load(f);

        //Spiel ausführen
        vm.mainExecutionLoop();

        //Spiel entfernen
        rom.close();
    }

    public static void chip8Test2() {
        byte[] bytes = new byte[2];

        bytes[0] = 0b01011010;
        bytes[1] = 0b01110000;

        Bits bits0 = new Bits(bytes);

        log.info("BITS-ARRAY: " + bits0);
    }

    public static void chip8Test1() {
        Bits bits0 = new Bits(16);

        //0001 0000 1110 0010
        bits0.setBit(1, true);
        bits0.setBit(5, true);
        bits0.setBit(6, true);
        bits0.setBit(7, true);
        bits0.setBit(12, true);
        //log.info("Bits0: " + bits0);

        BasicVm vm = new BasicVm();

        VmCpu cpu = new CpuChip8();

        vm.addComponent(cpu);
    }

    public static void chip8Test0() {
        Bits bits0 = new Bits(16);

        //0001 0000 1110 0010
        bits0.setBit(1, true);
        bits0.setBit(5, true);
        bits0.setBit(6, true);
        bits0.setBit(7, true);
        bits0.setBit(12, true);
        //log.info("Bits0: " + bits0);

        CpuChip8 cpu = new CpuChip8();

        cpu.executeCommand(bits0);
    }

    public static void test2() {
        Bits a0 = new Bits(32);
        Bits b0 = new Bits(32);
        Bits c0 = new Bits(32);

//        //1010 0010
//        a0.setBit(1, true);
//        a0.setBit(5, true);
//        a0.setBit(7, true);
//
//        //1100 1101
//        b0.setBit(0, true);
//        b0.setBit(2, true);
//        b0.setBit(3, true);
//        b0.setBit(6, true);
//        b0.setBit(7, true);
//
//        //0001 0100
//        c0.setBit(2, true);
//        c0.setBit(4, true);

        int rounds = 1000000;

        double time = System.currentTimeMillis();

        for (int i = 0; i < rounds; i++) {
            //1010 0010
            a0.setBit(1, true);
            a0.setBit(5, true);
            a0.setBit(7, true);

            //1100 1101
            b0.setBit(0, true);
            b0.setBit(2, true);
            b0.setBit(3, true);
            b0.setBit(6, true);
            b0.setBit(7, true);

            //0001 0100
            c0.setBit(2, true);
            c0.setBit(4, true);

            a0.and(b0.invert().xor(c0.or(a0)));
            b0.shiftUp(2);
            c0.or(b0).shiftDown(1);
            c0.or(a0.xor(b0));
            c0.invert();
            c0.or(b0).and(a0);
        }

        time = System.currentTimeMillis() - time;

        System.out.println("Time(sek): " + time);
        System.out.println("Time(per Round): " + time / rounds);
        System.out.println("Time(per Operation): " + ((time / rounds) / 12));
    }

    public static void test1() {
        Bits bits0 = new Bits(8);
        Bits bits1 = new Bits(4);

        //1010 0010
        bits0.setBit(1, true);
        bits0.setBit(5, true);
        bits0.setBit(7, true);
        System.out.println(bits0);

        //1011
        bits1.setBit(0, true);
        bits1.setBit(1, true);
        bits1.setBit(3, true);
        System.out.println(bits1);

        Bits result = bits0.or(bits1);
        System.out.println("OR: " + result);

        result = bits1.or(bits0);
        System.out.println("OR: " + result);

        bits0.shiftUp(1);
        System.out.println("SHIFT-UP 1: " + bits0);
        bits0.shiftUp(5);
        System.out.println("SHIFT-UP 5: " + bits0);
        bits0.shiftDown(7);
        System.out.println("SHIFT-DOWN 7: " + bits0);
    }

    public static void test0() {
        Bits bits0 = new Bits(8);
        Bits bits1 = new Bits(8);

        bits0.getBit(0);
        //System.out.println(bits0);

        bits0.clearBits();
        //System.out.println(bits0);

        bits0.setBit(0, true);
        //System.out.println(bits0);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        System.out.println(bits0);

        //0010 0111
        bits1.setBit(0, true);
        bits1.setBit(1, true);
        bits1.setBit(2, true);
        bits1.setBit(5, true);
        System.out.println(bits1);

        Bits result = bits0.and(bits1);
        System.out.println("AND: " + result);

        result = bits0.or(bits1);
        System.out.println("OR: " + result);

        result = bits0.xor(bits1);
        System.out.println("XOR: " + result);

        result.clearBits();
        System.out.println("CLEAR-FALSE: " + result);

        System.out.println("NORMAL: " + bits0);
        System.out.println("INVERT: " + bits0.invert());

        result = bits0.getBits(3, 7);
        System.out.println("GET-BITS: " + result);

        System.out.println("Bin->Byte: " + bits0.toByte());
    }

}
