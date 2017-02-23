package general.gpu;

import basics.bits.Bits;
import general.vm.components.VmComponentInterface;

/**
 * Created by andreas on 25.05.16.
 *
 * Interface for the VmGPU
 */
public interface VmGpuInterface extends VmComponentInterface {

    /**
     * analysiere und führe den eingelesenen Befehl aus.
     *
     * @param bits eingelesene Daten
     */
    public void executeCommand(Bits bits);

}
