package chip8.cpu.opcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import basics.bits.Bits;
import general.cpu.opcode.GenericCommand;
import general.cpu.opcode.GenericCommandPart;

/**
 * Created by andreas on 24.05.16.
 *
 * Chip-8 Opcodes/Commands
 */
public enum Commands {

    //Opcodes haben 16-Bit

    //Reihenfolge für die richtige Erkennung eventuell nötig!!!

    DRAW_SPRITE(
            "DRAW_SPRITE",
            new GenericCommandPart("N", 0, 3),
            new GenericCommandPart("Y", 4, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("D", 12, 15, new Bits(Arrays.asList(true, false, true, true)))
    ),

    CLEAR_SCREEN(
            "CLEAR_SCREEN",
            new GenericCommandPart("0", 0, 3, new Bits(Arrays.asList(false, false, false, false))),
            new GenericCommandPart("E", 4, 7, new Bits(Arrays.asList(false, true, true, true))),
            new GenericCommandPart("0", 8, 11, new Bits(Arrays.asList(false, false, false, false))),
            new GenericCommandPart("0", 12, 15, new Bits(Arrays.asList(false, false, false, false)))
    ),

//    CALLS_RCA1802(
//            "CALLS_RCA1802",
//            new GenericCommandPart("NNN", 0, 11),
//            new GenericCommandPart("0", 12, 15, new Bits(Arrays.asList(false, false, false, false)))
//    ),

    RETURN_SUB(
            "RETURN_SUB",
            new GenericCommandPart("E", 0, 3, new Bits(Arrays.asList(false, true, true, true))),
            new GenericCommandPart("E", 4, 7, new Bits(Arrays.asList(false, true, true, true))),
            new GenericCommandPart("0", 8, 11, new Bits(Arrays.asList(false, false, false, false))),
            new GenericCommandPart("0", 12, 15, new Bits(Arrays.asList(false, false, false, false)))
    ),

    JUMP_TO_NNN(
            "JUMP_TO_NNN",
            new GenericCommandPart("NNN", 0, 11),
            new GenericCommandPart("1", 12, 15, new Bits(Arrays.asList(true, false, false, false)))
    ),

    CALL_SUB_AT_NNN(
            "CALL_SUB_AT_NNN",
            new GenericCommandPart("NNN", 0, 11),
            new GenericCommandPart("2", 12, 15, new Bits(Arrays.asList(false, true, false, false)))
    ),

    SKIP_NEXT_CMD_IF_VX_EQUALS_NN(
            "SKIP_NEXT_CMD_IF_VX_EQUALS_NN",
            new GenericCommandPart("NN", 0, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("3", 12, 15, new Bits(Arrays.asList(true, true, false, false)))
    ),

    SKIP_NEXT_CMD_IF_VX_NOT_EQUALS_NN(
            "SKIP_NEXT_CMD_IF_VX_NOT_EQUALS_NN",
            new GenericCommandPart("NN", 0, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("4", 12, 15, new Bits(Arrays.asList(false, false, true, false)))
    ),

    SKIP_NEXT_CMD_IF_VX_EQUALS_VY(
            "SKIP_NEXT_CMD_IF_VX_EQUALS_VY",
            new GenericCommandPart("0", 0, 3, new Bits(Arrays.asList(false, false, false, false))),
            new GenericCommandPart("Y", 4, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("5", 12, 15, new Bits(Arrays.asList(true, false, true, false)))
    ),

    SET_VX_TO_NN(
            "SET_VX_TO_NN",
            new GenericCommandPart("NN", 0, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("6", 12, 15, new Bits(Arrays.asList(false, true, true, false)))
    ),

    ADDS_NN_TO_VX(
            "ADDS_NN_TO_VX",
            new GenericCommandPart("NN", 0, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("7", 12, 15, new Bits(Arrays.asList(true, true, true, false)))
    ),

    SET_VX_TO_VY(
            "SET_VX_TO_VY",
            new GenericCommandPart("0", 0, 3, new Bits(Arrays.asList(false, false, false, false))),
            new GenericCommandPart("Y", 4, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("8", 12, 15, new Bits(Arrays.asList(false, false, false, true)))
    ),

    SET_VX_TO_VX_OR_VY(
            "SET_VX_TO_VX_OR_VY",
            new GenericCommandPart("1", 0, 3, new Bits(Arrays.asList(true, false, false, false))),
            new GenericCommandPart("Y", 4, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("8", 12, 15, new Bits(Arrays.asList(false, false, false, true)))
    ),

    SET_VX_TO_VX_AND_VY(
            "SET_VX_TO_VX_AND_VY",
            new GenericCommandPart("2", 0, 3, new Bits(Arrays.asList(false, true, false, false))),
            new GenericCommandPart("Y", 4, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("8", 12, 15, new Bits(Arrays.asList(false, false, false, true)))
    ),

    SET_VX_TO_VX_XOR_VY(
            "SET_VX_TO_VX_XOR_VY",
            new GenericCommandPart("3", 0, 3, new Bits(Arrays.asList(true, true, false, false))),
            new GenericCommandPart("Y", 4, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("8", 12, 15, new Bits(Arrays.asList(false, false, false, true)))
    ),

    SET_I_TO_NNN(
            "SET_I_TO_NNN",
            new GenericCommandPart("NNN", 0, 11),
            new GenericCommandPart("A", 12, 15, new Bits(Arrays.asList(false, true, false, true)))
    ),

    SET_VX_TO_RANDOM_NUMBER_AND_NN(
            "SET_VX_TO_RANDOM_NUMBER_AND_NN",
            new GenericCommandPart("NN", 0, 7),
            new GenericCommandPart("X", 8, 11),
            new GenericCommandPart("C", 12, 15, new Bits(Arrays.asList(false, false, true, true)))
    ),

    //Todo: restlichen Opcodes eintragen!

    ;

    private static final Logger log = LoggerFactory.getLogger(Commands.class);

    private GenericCommand cmd = null;

    private Commands(String name, GenericCommandPart... parts){
        this.cmd = new GenericCommand(name, parts);
    }

    //### ANALYSE ###

    public static List<Commands> find(Bits bits){
        List<Commands> results = new ArrayList<>();
        int needed = 0;
        int checked = 0;
        for(Commands c : Commands.values()){
            needed = 0;
            checked = 0;

            if(c == Commands.SKIP_NEXT_CMD_IF_VX_EQUALS_NN){
                log.info("start Debug!");
            }

            for(GenericCommandPart p : c.getCmd().getParts()){
                if(p.isNeeded()){
                    needed++;
                    if(checkPart(p, bits)){
                        checked++;
                    }
                }
            }
            if(needed == checked){
                results.add(c);
            }
        }
        if(results.size() > 1 || results.isEmpty()){
            log.info("Error - More than One Command indentified OR nothing!");
            log.info("Results: " + results.toString());
            log.info("Bits: " + bits.toString());
            System.exit(1);
        }
        return results;
    }

    private static boolean checkPart(GenericCommandPart p, Bits bits){
        //Bits ausschneiden und vergleichen
        Bits res = bits.getBits(p.getStartIndex(), p.getEndIndex()+1);

        //Vergleich
        if(res.equals(p.getValue())){
            return true;
        }
        else{
            return false;
        }
    }

    //### GET ###

    public GenericCommand getCmd(){
        return this.cmd;
    }

    public GenericCommand getCmdWithData(Bits bits){
        for(GenericCommandPart p : this.cmd.getParts()){
            if(!p.isNeeded()){
                Bits res = bits.getBits(p.getStartIndex(), p.getEndIndex()+1);
                p.setValue(res);
            }
        }
        return this.cmd;
    }

    @Override
    public String toString(){
        String s = this.cmd.toString();
        return s;
    }

}
