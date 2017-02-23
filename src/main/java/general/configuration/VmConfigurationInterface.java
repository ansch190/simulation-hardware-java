package general.configuration;

import java.io.File;

/**
 * Created by andreas on 17.11.16.
 *
 * Interface for all future Configuration Classes
 */
public interface VmConfigurationInterface {

    /**
     * returns the specific Parameter
     *
     * @return the specific Parameter-Data
     */
    public Object getParameter(VmConfigurationParameter parameter);

    /**
     * sets the specific Parameter
     *
     * @param parameter Name of the Configuration-Parameter
     * @param o         Value of the Data
     */
    public void setParameter(VmConfigurationParameter parameter, Object o);

    /**
     * delete all Parameter and the Parameter-Values
     */
    public void clearParameter();

    /**
     * load a Configuration from a File
     *
     * @param file File with stored Config
     */
    public void loadFromFile(File file);

    /**
     * saves a Configuration to a File
     *
     * @param file File with saved Config
     */
    public void saveToFile(File file);

}
