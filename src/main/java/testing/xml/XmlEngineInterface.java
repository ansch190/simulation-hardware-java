package testing.xml;

import java.io.File;

/**
 * Created by andreas on 15.12.16.
 *
 * Interface für die Grundlegenden Funktionen der XML-Engine
 */
public interface XmlEngineInterface {

    /**
     * parse die intern vorliegende Datei und erstelle die Objekte.
     */
    public void parse();

    /**
     * lädt einen XML-File in die Klasse, verwendet ihn aber nicht.
     * Es wird nur der File gespeichert.
     *
     * @param f the XML-File
     */
    public void loadXML(File f);

    /**
     * speichert die internen vorhandenen Objekte als XML-Datei in der gegebenen Datei ab.
     *
     * @param f the XML-File
     */
    public void saveXML(File f);

}
