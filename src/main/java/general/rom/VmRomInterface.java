package general.rom;

import basics.bits.Bits;
import general.vm.components.VmComponentInterface;

import java.io.File;

/**
 * Created by andreas on 25.05.16.
 *
 * Rom Komponente der Vm, Image von Spielen als Beispiel
 */
public interface VmRomInterface extends VmComponentInterface {

    /**
     * lade ein Medium
     */
    public void load(File f);

    /**
     * Medium wieder entfernen, Stream schließen
     */
    public void close();

    /**
     * lese eine bestimmte Anzahl an Bits
     *
     * @param bitCount Anzahl der Bits, die gelesen werden sollen
     * @return
     */
    public Bits read(int bitCount);

    public Bits readAtIndex(int byteIndex, int bitCount);

}
