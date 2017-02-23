package chip8.cpu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import basics.bits.Bits;
import chip8.gpu.GpuChip8;
import general.cpu.VmCpu;
import general.cpu.opcode.GenericCommand;
import general.vm.components.VmComponentType;
import testing.xml.OpcodeXmlEngine;

/**
 * Created by andreas on 24.05.16.
 *
 * VmCpuInterface der Chip-8 Architektur
 */
public class CpuChip8 extends VmCpu {

    public List<Bits> register;

    public Bits carryFlag = new Bits(8);  //VF

    public Bits indexRegister = new Bits(12);

    public Bits programCounter = new Bits(12);  //Adresse des aktuellen Befehls

    public List<Bits> jumpStack;

    private int OPCODE_SIZE = 16;

//    private int ADDRESS_OFFSET_FOR_ROM = 0x200;  //Offset wenn in den Ram geladen. Muss beim abspielen von einer Rom subtrahiert werden.

    //12 Bit Offset
    private Bits ADDRESS_OFFSET_FOR_ROM = new Bits(Arrays.asList(false, false, false, false, false, false, false, false, false, true, false, false));

    //Opcodes-Engine
    private OpcodeXmlEngine opcodeXmlEngine = null;

    public void initRegister(){
        register = new ArrayList<>();
        register.add(new Bits(8));  //V0
        register.add(new Bits(8));  //V1
        register.add(new Bits(8));  //V2
        register.add(new Bits(8));  //V3
        register.add(new Bits(8));  //V4
        register.add(new Bits(8));  //V5
        register.add(new Bits(8));  //V6
        register.add(new Bits(8));  //V7
        register.add(new Bits(8));  //V8
        register.add(new Bits(8));  //V9
        register.add(new Bits(8));  //VA
        register.add(new Bits(8));  //VB
        register.add(new Bits(8));  //VC
        register.add(new Bits(8));  //VD
        register.add(new Bits(8));  //VE

        jumpStack = new ArrayList<>();
    }

//    /**
//     * execute the passed Bits
//     *
//     * @param bits passed Bits
//     */
//    public void executeCommand(Bits bits) {
//        Commands cmd = findCommand(bits);
//
//        if(cmd == Commands.CLEAR_SCREEN){
//            GpuChip8 gpu = (GpuChip8) getComponent(VmComponentType.GPU);
//            gpu.executeCommand(bits);
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
////        else if(cmd == Commands.CALLS_RCA1802){
////            log.info("-> call RCA1802!");
////            GenericCommand gcmd = cmd.getCmdWithData(bits);
////        }
//        else if(cmd == Commands.RETURN_SUB){
//            log.info("-> return from subroutine!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
//        }
//        else if(cmd == Commands.JUMP_TO_NNN){
//            log.info("-> jump to address!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
////            //Weil ich die Anzahl der Bits brauche und nicht die der Bytes!!!
////            int nnn = (bits.getBits(0, 12).toInt() - 0x200) * 8;  //Todo: nur wenn von Rom gelesen!!!
////            Bits b = new Bits(12);
////            b = b.add_Math(nnn);
////            jumpStack.add(programCounter);
////            programCounter = b;
//        }
//        else if(cmd == Commands.CALL_SUB_AT_NNN){
//            log.info("-> call sub at NNN!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
////            jumpStack.add(programCounter);
////            programCounter = bits.getBits(0, 12);
//        }
//        else if(cmd == Commands.SKIP_NEXT_CMD_IF_VX_EQUALS_NN){
//            log.info("-> skip next cmd if Vx == NN!");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//            Bits nn = gcmd.getParts().get(0).getValue();
//            Bits x = gcmd.getParts().get(1).getValue();
//            if(register.get(x.toInt()).equals(nn)){
//                programCounter = programCounter.add_Math(OPCODE_SIZE);
//                log.info("skip!");
//            }
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.SKIP_NEXT_CMD_IF_VX_NOT_EQUALS_NN){
//            log.info("-> skip next cmd if vx != NN!");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//            Bits nn = gcmd.getParts().get(0).getValue();
//            Bits x = gcmd.getParts().get(1).getValue();
//            if(!register.get(x.toInt()).equals(nn)){
//                programCounter = programCounter.add_Math(OPCODE_SIZE);
//            }
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.SKIP_NEXT_CMD_IF_VX_EQUALS_VY){
//            log.info("-> skip next cmd if Vx == Vy!");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//            Bits y = gcmd.getParts().get(1).getValue();
//            Bits x = gcmd.getParts().get(2).getValue();
//            if(register.get(x.toInt()).equals(register.get(y.toInt()))){
//                programCounter = programCounter.add_Math(OPCODE_SIZE);
//            }
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.SET_VX_TO_NN){
//            log.info("-> set vx to NN!");
//            Bits b = bits.getBits(8, 12);
//            int registerIndex = b.toInt();
//            b = bits.getBits(0, 8);
//            register.set(registerIndex, b);
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.ADDS_NN_TO_VX){
//            log.info("-> add NN to Vx!");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//            Bits nn = gcmd.getParts().get(0).getValue();
//            Bits x = gcmd.getParts().get(1).getValue();
//            nn = nn.addBinaryMathNoCarry(register.get(x.toInt()));
//            register.set(x.toInt(), nn);
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.SET_VX_TO_VY){
//            log.info("-> set vx to vy!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
//        }
//        else if(cmd == Commands.SET_VX_TO_VX_OR_VY){
//            log.info("-> set vx to vx or vy!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
//        }
//        else if(cmd == Commands.SET_VX_TO_VX_AND_VY){
//            log.info("-> set vx to vx and vy!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
//        }
//        else if(cmd == Commands.SET_VX_TO_VX_XOR_VY){
//            log.info("-> set vx to vx xor vy!");
//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
//        }
//        else if(cmd == Commands.SET_I_TO_NNN){
//            log.info("-> set I to NNN!");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//            indexRegister = gcmd.getParts().get(0).getValue().subBinaryMathNoCarry(ADDRESS_OFFSET_FOR_ROM);
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.SET_VX_TO_RANDOM_NUMBER_AND_NN){
//            log.info("-> set Vx to random number and NN");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//
//            Bits nn = gcmd.getParts().get(0).getValue();
//            Bits x = gcmd.getParts().get(1).getValue();
//
////            log.info("nn: " + nn.toString());
////            log.info("x: " + x.toString());
//
//            Bits rnd = new Bits(8);
//            rnd.random();
//
////            log.info("rnd: " + rnd.toString());
//
//            nn = nn.and(rnd);
//            register.set(x.toInt(), nn);
//
////            log.info("nn & rnd: " + nn.toString());
//
////            log.error("NOT IMPLEMENTED!!!");
////            System.exit(1);
//            programCounter = programCounter.add_Math(OPCODE_SIZE);
//        }
//        else if(cmd == Commands.DRAW_SPRITE){
//            GpuChip8 gpu = (GpuChip8) getComponent(VmComponentType.GPU);
//            gpu.executeCommand(bits);
//            programCounter = programCounter.add_Math(OPCODE_SIZE);  //todo prüfen, vlt verschieben in GPU?
//        }
//        else{
//            log.info("!!! UNKNOWN OPCODE !!!");
//            log.info(bits.toString());
//            System.exit(1);
//        }
//    }

//    /**
//     * find the right Command for the passed Opcode
//     *
//     * @param bits Bits with Opcode
//     * @return a Command
//     */
//    private Commands findCommand(Bits bits){
//        List<Commands> cmds = Commands.find(bits);
//        if(cmds.size() != 1){
//            log.info("Too much OR no Command!");
//            System.exit(1);
//        }
//        return cmds.get(0);
//    }


    //------------------- NEUER CODE - XML-ENGINE -----------------------------------------

    /**
     * execute the passed Bits
     *
     * @param bits passed Bits
     */
    public void executeCommand(Bits bits) {
        GenericCommand cmd = findCommand(bits);  //Mit Dateninhalt!

        log.info("CMD-NAME: " + cmd.getName());

        if(cmd.getName().equals("CLEAR_SCREEN")){
            GpuChip8 gpu = (GpuChip8) getComponent(VmComponentType.GPU);
            gpu.executeCommand(bits);
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
//        else if(cmd == Commands.CALLS_RCA1802){
//            log.info("-> call RCA1802!");
//            GenericCommand gcmd = cmd.getCmdWithData(bits);
//        }
        else if(cmd.getName().equals("RETURN_SUB")){
            log.info("-> return from subroutine!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
        }
        else if(cmd.getName().equals("JUMP_TO_NNN")){
            log.info("-> jump to address!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
//            //Weil ich die Anzahl der Bits brauche und nicht die der Bytes!!!
//            int nnn = (bits.getBits(0, 12).toInt() - 0x200) * 8;  //Todo: nur wenn von Rom gelesen!!!
//            Bits b = new Bits(12);
//            b = b.add_Math(nnn);
//            jumpStack.add(programCounter);
//            programCounter = b;
        }
        else if(cmd.getName().equals("CALL_SUB_AT_NNN")){
            log.info("-> call sub at NNN!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
//            jumpStack.add(programCounter);
//            programCounter = bits.getBits(0, 12);
        }
        else if(cmd.getName().equals("SKIP_NEXT_CMD_IF_VX_EQUALS_NN")){
            log.info("-> skip next cmd if Vx == NN!");
            Bits nn = cmd.getParts().get(0).getValue();
            Bits x = cmd.getParts().get(1).getValue();
            if(register.get(x.toInt()).equals(nn)){
                programCounter = programCounter.add_Math(OPCODE_SIZE);
                log.info("skip!");
            }
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("SKIP_NEXT_CMD_IF_VX_NOT_EQUALS_NN")){
            log.info("-> skip next cmd if vx != NN!");
            Bits nn = cmd.getParts().get(0).getValue();
            Bits x = cmd.getParts().get(1).getValue();
            if(!register.get(x.toInt()).equals(nn)){
                programCounter = programCounter.add_Math(OPCODE_SIZE);
            }
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("SKIP_NEXT_CMD_IF_VX_EQUALS_VY")){
            log.info("-> skip next cmd if Vx == Vy!");
            Bits y = cmd.getParts().get(1).getValue();
            Bits x = cmd.getParts().get(2).getValue();
            if(register.get(x.toInt()).equals(register.get(y.toInt()))){
                programCounter = programCounter.add_Math(OPCODE_SIZE);
            }
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("SET_VX_TO_NN")){
            log.info("-> set vx to NN!");
            Bits b = bits.getBits(8, 12);
            int registerIndex = b.toInt();
            b = bits.getBits(0, 8);
            register.set(registerIndex, b);
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("ADDS_NN_TO_VX")){
            log.info("-> add NN to Vx!");
            Bits nn = cmd.getParts().get(0).getValue();
            Bits x = cmd.getParts().get(1).getValue();
            nn = nn.addBinaryMathNoCarry(register.get(x.toInt()));
            register.set(x.toInt(), nn);
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("SET_VX_TO_VY")){
            log.info("-> set vx to vy!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
        }
        else if(cmd.getName().equals("SET_VX_TO_VX_OR_VY")){
            log.info("-> set vx to vx or vy!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
        }
        else if(cmd.getName().equals("SET_VX_TO_VX_AND_VY")){
            log.info("-> set vx to vx and vy!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
        }
        else if(cmd.getName().equals("SET_VX_TO_VX_XOR_VY")){
            log.info("-> set vx to vx xor vy!");
            log.error("NOT IMPLEMENTED!!!");
            System.exit(1);
        }
        else if(cmd.getName().equals("SET_I_TO_NNN")){
            log.info("-> set I to NNN!");
            indexRegister = cmd.getParts().get(0).getValue().subBinaryMathNoCarry(ADDRESS_OFFSET_FOR_ROM);
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("SET_VX_TO_RANDOM_NUMBER_AND_NN")){
            log.info("-> set Vx to random number and NN");

            Bits nn = cmd.getParts().get(0).getValue();
            Bits x = cmd.getParts().get(1).getValue();

//            log.info("nn: " + nn.toString());
//            log.info("x: " + x.toString());

            Bits rnd = new Bits(8);
            rnd.random();

//            log.info("rnd: " + rnd.toString());

            nn = nn.and(rnd);
            register.set(x.toInt(), nn);

//            log.info("nn & rnd: " + nn.toString());

//            log.error("NOT IMPLEMENTED!!!");
//            System.exit(1);
            programCounter = programCounter.add_Math(OPCODE_SIZE);
        }
        else if(cmd.getName().equals("DRAW_SPRITE")){
            GpuChip8 gpu = (GpuChip8) getComponent(VmComponentType.GPU);
            gpu.executeCommand(bits);
            programCounter = programCounter.add_Math(OPCODE_SIZE);  //todo prüfen, vlt verschieben in GPU?
        }
        else{
            log.info("!!! UNKNOWN OPCODE !!!");
            log.info(bits.toString());
            System.exit(1);
        }
    }

    //Opcode finden. Gibt direkt den Opcode mit den Details an Daten zurück.
    private GenericCommand findCommand(Bits bits){
        return this.opcodeXmlEngine.find(bits);
    }

    public void initOpcodes(){
        opcodeXmlEngine = new OpcodeXmlEngine();
        File f = new File("src/main/resources/opcodes_chip8.xml");
        opcodeXmlEngine.loadXML(f);
        opcodeXmlEngine.parse();
    }

}
