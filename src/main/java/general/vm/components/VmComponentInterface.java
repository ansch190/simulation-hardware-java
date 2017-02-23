package general.vm.components;

/**
 * Created by andreas on 25.05.16.
 *
 * Komponenten einer VM (VmCpuInterface, Gpu, Ram, Laufwerke, etc..)
 */
public interface VmComponentInterface {

    /**
     * initialisiere die Komponente
     */
    public void init();

    /**
     * gibt Art der Komponente zurück
     *
     * @return
     */
    public VmComponentType getType();

    /**
     * adds a VmComponent for internal Use
     *
     * @param vmComponent a VmComponent
     */
    public void addComponent(VmComponent vmComponent);

    /**
     * returns a VmComponent for a specific VmComponentType
     *
     * @param componentType VmComponentType
     * @return VmComponent
     */
    public VmComponent getComponent(VmComponentType componentType);

}
