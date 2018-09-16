package general.cpu;

import java.util.HashMap;

import basics.bits.Bits;
import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;

/**
 * Created by andreas on 25.05.16.
 *
 * Basisklasse für eine Cpu der Vm
 */
public class VmCpu extends VmComponent implements VmCpuInterface {

    public VmCpu(){
        init();
    }

    @Override
    public void init() {
        super.componentType = VmComponentType.CPU;
        super.components = new HashMap<>();
    }

    @Override
    public void executeCommand(Bits bits) {
        //nothing here! This ist architecture-specific!
    }

}
