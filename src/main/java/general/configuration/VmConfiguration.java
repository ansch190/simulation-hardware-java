package general.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by andreas on 17.11.16.
 *
 * General Configuration File
 */
public class VmConfiguration implements VmConfigurationInterface {

    protected static final Logger log = LoggerFactory.getLogger(VmConfiguration.class);

    protected HashMap<VmConfigurationParameter, Object> parameterHashMap = null;

    public VmConfiguration(){
        parameterHashMap = new HashMap<>();
    }

    @Override
    public Object getParameter(VmConfigurationParameter parameter) {
        if(parameterHashMap.containsKey(parameter)){
            return parameterHashMap.get(parameter);
        }
        else{
            log.error("ConfigurationParameter not found! Check loaded Config-File!");
        }
        return null;
    }

    @Override
    public void setParameter(VmConfigurationParameter parameter, Object o) {
        parameterHashMap.put(parameter, o);
    }

    @Override
    public void clearParameter() {
        parameterHashMap.clear();
    }

    @Override
    public void loadFromFile(File file) {
        clearParameter();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String lineData;
            while( (lineData = br.readLine()) != null ){
                String[] array = lineData.split("#");
                for(VmConfigurationParameter parameter : VmConfigurationParameter.values()){
                    if(array[0].equals(parameter.toString())){
                        parameterHashMap.put(parameter, Integer.parseInt(array[1]));  //Todo: Werte in Moment auf Integer limitiert!
                    }
                }
            }
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile(File file) {
        try{
            PrintWriter pw = new PrintWriter(file);
            for(VmConfigurationParameter p : parameterHashMap.keySet()){
                pw.println(p + "#" + parameterHashMap.get(p));
            }
            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
