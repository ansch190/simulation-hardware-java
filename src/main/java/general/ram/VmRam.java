package general.ram;

import basics.bits.Bits;
import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;

import java.util.HashMap;

/**
 * Created by andreas on 28.05.16.
 *
 * general Vm-Ram Class
 */
public class VmRam extends VmComponent implements VmRamInterface {

    protected Bits memoryData;

    public VmRam() {
        super();
    }

    @Override
    public void init() {
        super.componentType = VmComponentType.RAM;
        super.components = new HashMap<>();
    }

    @Override
    public int getMemorySize() {
        if(memoryData != null){
            return memoryData.size();
        }
        else{
            log.info("Ram not initialized!");
            System.exit(1);
        }
        return -1;
    }

    @Override
    public void setMemorySize(int memorySize) {
        if(memorySize > 0){
            memoryData = new Bits(memorySize);
        }
        else{
            log.info("MemorySize < 1");
            System.exit(1);
        }
    }

    @Override
    public Bits read(int startIndex, int bitCount) {
        if(memoryData != null){
            if(startIndex > -1){
                if(bitCount > 0){
                    return memoryData.getBits(startIndex, startIndex + bitCount);
                }
                else{
                    log.info("bitCount < 1!");
                    System.exit(1);
                }
            }
            else{
                log.info("startIndex < 0!");
                System.exit(1);
            }
        }
        else{
            log.info("Ram not initialized!");
            System.exit(1);
        }
        return null;
    }

    @Override
    public void write(int startIndex, Bits data) {
        if(memoryData != null){
            if(startIndex > -1){
                if(data != null && data.size() > 0){
                    memoryData.setBits(startIndex, data);
                }
                else{
                    log.info("Data == null OR Data.size() < 1!");
                    System.exit(1);
                }
            }
            else{
                log.info("startIndex < 0!");
                System.exit(1);
            }
        }
        else{
            log.info("Ram not initialized!");
            System.exit(1);
        }
    }

}
