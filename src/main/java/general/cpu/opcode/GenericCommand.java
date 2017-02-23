package general.cpu.opcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andreas on 24.05.16.
 *
 * GenericCommand
 */
public class GenericCommand {

    private String commandName = "";

    private List<GenericCommandPart> commandParts = null;

    public GenericCommand(){
        //...
    }

    public GenericCommand(String name, GenericCommandPart... parts){
        this.commandName = name;
        this.commandParts = new ArrayList<>(parts.length);
        Collections.addAll(this.commandParts, parts);
    }

    //### GET ###

    public String getName(){
        return this.commandName;
    }

    public List<GenericCommandPart> getParts(){
        return this.commandParts;
    }

    //### SET ###

    public void setName(String s){
        this.commandName = s;
    }

    public void setParts(List<GenericCommandPart> partList){
        this.commandParts = partList;
    }

    @Override
    public String toString(){
        String s = "CmdName: " + this.commandName + "\nParts: " + this.commandParts;
        return s;
    }

}
