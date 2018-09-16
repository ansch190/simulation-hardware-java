package chip8;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import chip8.cpu.CpuChip8;
import chip8.ram.RamChip8;
import chip8.rom.RomChip8;
import general.gpu.VmGpu;
import general.vm.BasicVm;

/**
 * Created by andreas on 28.05.16.
 *
 * Tests zur vollständigen Ausführung der Chip8-VM
 */
public class Chip8VmTest {

    private final Logger log = LoggerFactory.getLogger(Chip8Test.class);

    @Before
    public void before(){
        //...
    }

    @Test
    public void vmTest() throws Exception {
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

        //Spiel laden
        String path = "src/main/resources/rom/chip8/IBM Logo.ch8";
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

    @After
    public void after(){
        //...
    }

}
