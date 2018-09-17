package testing.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by andreas on 15.12.16.
 *
 * Bildet die Basis für die Verarbeitung von XML-Dateien
 */
public class XmlEngine implements XmlEngineInterface {

    protected static final Logger log = LoggerFactory.getLogger(XmlEngine.class);

    protected List<Object> objectList = null;

    protected File xmlFile = null;

    public XmlEngine() {
        init();
    }

    protected void init() {
        //...
    }

    @Override
    public void parse() {
        //...
    }

    @Override
    public void loadXML(File f) {
        xmlFile = f;
    }

    @Override
    public void saveXML(File f) {
        //Todo: schreiben... wenn möglich hier, damit es vererbt werden kann.
    }

}
