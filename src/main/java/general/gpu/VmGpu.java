package general.gpu;

import java.util.HashMap;

import basics.bits.Bits;
import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;

/**
 * Created by andreas on 28.05.16.
 *
 * general VmGpu Class
 */
public class VmGpu extends VmComponent implements VmGpuInterface {

    public VmGpu() {
        super();
        //init(); nur etwas ausprobiert...
    }

    @Override
    public void init() {
        super.componentType = VmComponentType.GPU;
        super.components = new HashMap<>();
    }

    @Override
    public void executeCommand(Bits bits) {
        //nothing here! This is architecture-specific
    }

}
