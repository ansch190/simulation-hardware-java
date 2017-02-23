package general.vm;

import java.util.HashMap;

import basics.bits.Bits;
import chip8.cpu.CpuChip8;
import chip8.ram.RamChip8;
import chip8.rom.RomChip8;
import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;
import general.vmguimanager.VmGuiManager;

/**
 * Created by andreas on 25.05.16.
 *
 * Basic VM die mit Objekten für die spezifische Architektur ergänzt wird
 */
public class BasicVm extends VmComponent {

    public BasicVm() {
        init();
    }

    @Override
    public void init() {
        componentType = VmComponentType.VM;
        components = new HashMap<>();
    }

    //GameLoop bzw. hier die Schleife, die alles ausführt!
    public void mainExecutionLoop() {
        CpuChip8 cpu = (CpuChip8) components.get(VmComponentType.CPU);
        RamChip8 ram = (RamChip8) components.get(VmComponentType.RAM);
        RomChip8 rom = (RomChip8) components.get(VmComponentType.ROM);

        int OPCODE_SIZE = 16;
        Bits bits;

        int rounds = 0;
        int maxRounds = 1000;  //23 für IBM-Logo

        cpu.programCounter = new Bits(12);  //Position im Code

//        processGraphics();  //nur einmal die Gui starten, dann kümmert sie sich selber um alles andere.

        while (true) {
            log.info("Cycle START!");

            //Befehl lesen/holen
            bits = rom.readAtIndex(cpu.programCounter.toInt() / 8, OPCODE_SIZE);

            //Befehl ausführen auf der CPU
            cpu.executeCommand(bits);

            log.info("Cycle END!\n");

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
            rounds++;

            if (rounds == maxRounds) {
                break;
            }
        }

        gui.stop();
    }

    private VmGuiManager gui = null;

    //Gui aktualisieren
    private void processGraphics(){
        if(gui == null){
            gui = (VmGuiManager) getComponent(VmComponentType.VM_GUI);
        }
        gui.start();
    }

}
