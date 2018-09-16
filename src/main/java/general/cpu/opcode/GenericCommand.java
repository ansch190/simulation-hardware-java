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

    //Deep Object Copy
    public GenericCommand copy(){
        String target_name = getName();

        List<GenericCommandPart> source_parts = getParts();
        List<GenericCommandPart> target_parts = new ArrayList<>(source_parts.size());

        for(GenericCommandPart p : source_parts){
            target_parts.add(p.copy());
        }

        GenericCommand gc = new GenericCommand();
        gc.setName(target_name);
        gc.setParts(target_parts);

        return gc;
    }

    @Override
    public String toString(){
        String s = "CmdName: " + this.commandName + "\nParts: " + this.commandParts;
        return s;
    }

}
