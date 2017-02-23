package general.vm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreas on 25.05.16.
 *
 * Vm-Komponente Basisklasse
 */
public class VmComponent implements VmComponentInterface {

    protected final Logger log = LoggerFactory.getLogger(VmComponent.class);

    protected VmComponentType componentType = null;

    protected Map<VmComponentType, VmComponent> components = null;

    public VmComponent() {
        init();
    }

    @Override
    public void init() {
        componentType = VmComponentType.UNKNOWN;
        components = new HashMap<>();
    }

    @Override
    public VmComponentType getType() {
        return componentType;
    }

    @Override
    public void addComponent(VmComponent vmComponent) {
        components.put(vmComponent.getType(), vmComponent);
    }

    @Override
    public VmComponent getComponent(VmComponentType componentType) {
        return components.get(componentType);
    }

}
