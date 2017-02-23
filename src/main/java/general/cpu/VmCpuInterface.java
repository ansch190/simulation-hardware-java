package general.cpu;

import basics.bits.Bits;
import general.vm.components.VmComponentInterface;

/**
 * Created by andreas on 25.05.16.
 *
 * CPU der VM
 */
public interface VmCpuInterface extends VmComponentInterface {

    //Todo: allgemeine Schnittstelle definieren!

    /**
     * analysiere und führe den eingelesenen Befehl aus.
     *
     * @param bits eingelesene Daten
     */
    public void executeCommand(Bits bits);

}
