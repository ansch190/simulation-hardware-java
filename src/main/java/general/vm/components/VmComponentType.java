package general.vm.components;

/**
 * Created by andreas on 25.05.16.
 *
 * Komponenten-Arten die eine VM enthalten kann
 */
public enum VmComponentType {

    CPU,

    GPU,  //Grafikprozessor

    ROM,  //Spiele,Images

    RAM,  //Speicher

    VM,  //Die ganze virtuelle Maschine

    VM_GPU,  //VM Grafikverwaltung

    //neu zum testen
    VM_GRAPHIC_MANAGER,  //Management, Buffering und Postprocessing der Bilder

    VM_GUI,  //Gui bzw. Fenster für die Darstellung und die Bedienung durch den User.

    UNKNOWN;

}
