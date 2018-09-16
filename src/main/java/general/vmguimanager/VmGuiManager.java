package general.vmguimanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;
import general.vmgraphicmanager.VmGraphicManager;
import general.vmguimanager.threads.VmGuiThread;

/**
 * Created by andreas on 18.11.16.
 *
 * Eine Klasse für das sichtbare Graphik-Fenster
 */
public class VmGuiManager extends VmComponent {

    private static final Logger log = LoggerFactory.getLogger(VmGuiManager.class);

    private VmGraphicManager vgm = null;

    private ExecutorService es = null;

    public static boolean run = false;

    public VmGuiManager() {
        init();
    }

    @Override
    public void init() {
        componentType = VmComponentType.VM_GUI;
        components = new HashMap<>();
    }

    private void initInternal(){
        if(es == null){ es = Executors.newCachedThreadPool(); }
        if(vgm == null){ vgm = (VmGraphicManager) getComponent(VmComponentType.VM_GRAPHIC_MANAGER); }
    }

    //starte Gui-Thread
    public void start(){
        initInternal();

        VmGuiThread guiThread = new VmGuiThread(vgm);

        run = true;

        es.execute(guiThread);

        log.info("Gui-Thread started!\n");
    }

    //stoppe Gui-Thread
    public void stop(){
        run = false;
        log.info("Gui-Thread stopped!");
    }

}
