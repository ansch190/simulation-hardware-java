package chip8.rom;

import basics.bits.Bits;
import chip8.ram.RamChip8;
import general.rom.VmRom;
import general.vm.components.VmComponentType;

/**
 * Created by andreas on 25.05.16.
 *
 * Rom-Klasse zum laden von Chip-8 Spielen/Roms
 */
public class RomChip8 extends VmRom {

    //keine Änderungen bisher nötig!

    private int ADDRESS_OFFSET = 0x200;  //Todo: löschen, wenn nicht mehr gebraucht!

    /**
     * load the complete Rom into the Memory starting at Position ADDRESS_OFFSET
     */
    public void loadRomToRam(){  //Todo: Überprüfen irgendwo ist eine Fehler!
        RamChip8 ram = (RamChip8) getComponent(VmComponentType.RAM);
        //Rom Länge lesen
        int fileLength = (int) this.medium.length();
        //Rom lesen
        Bits b = read(fileLength * 8);
        //Rom in Ram schreiben
        ram.write(512, b);
    }

}
