package general.ram;

import basics.bits.Bits;
import general.vm.components.VmComponentInterface;

/**
 * Created by andreas on 28.05.16.
 *
 * Interface für den generellen VM-Ram
 */
public interface VmRamInterface extends VmComponentInterface {

    /**
     * returns the Size of the Memory
     *
     * @return Size of Memory
     */
    public int getMemorySize();

    /**
     * sets the Memory to the specific Size
     *
     * @param memorySize the Size of Memory
     */
    public void setMemorySize(int memorySize);

    /**
     * read Memory at specific Position with specific Number of Bits
     *
     * @param startIndex Position to start with reading (0-n)
     */
    public Bits read(int startIndex, int bitCount);

    /**
     * writes Bits with specific Size at a specific Position to the Memory
     *
     * @param startIndex Position to start with write (0-n)
     * @param data Bits to write
     */
    public void write(int startIndex, Bits data);

}
