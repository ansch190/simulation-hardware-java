package chip8.gpu;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basics.bits.Bits;
import chip8.cpu.CpuChip8;
import chip8.cpu.opcode.Commands;
import chip8.rom.RomChip8;
import general.gpu.VmGpu;
import general.vm.components.VmComponentType;
import general.vmgraphicmanager.VmGraphicManager;

/**
 * Created by andreas on 28.05.16.
 *
 * Gpu-Class for the Chip8 Architecture
 */
public class GpuChip8 extends VmGpu {

    private final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB; //todo: vielleicht in oberklasse veschieben!!! aber wie !?! wtf!!!

    private Map<String, List<Bits>> fontSet;

    private VmGraphicManager vgm = null;

    public GpuChip8() {
        super();
    }

    @Override
    public void init() {
        super.init();

        initFontSet();
    }

    @Override
    public void executeCommand(Bits bits) {
        BufferedImage image = null;

        if(vgm == null){
            vgm = (VmGraphicManager) getComponent(VmComponentType.VM_GRAPHIC_MANAGER);
        }

        Commands cmd = findCommand(bits);

        if(cmd == Commands.CLEAR_SCREEN){
            log.info("-> clear screen!");
            image = clearScreen();
        }
        else if(cmd == Commands.DRAW_SPRITE){
            log.info("-> draw sprite!");
            image = drawSprites(bits);
        }
        else{
            log.info("!!! UNKNOWN OPCODE !!!");
        }

        if(image != null){
            vgm.addImage(image);
        }
    }

    /**
     * find the right Command for the passed Opcode
     *
     * @param bits Bits with Opcode
     * @return a Command
     */
    private Commands findCommand(Bits bits){
        List<Commands> cmds = Commands.find(bits);
        if(cmds.size() != 1){
            log.info("Too much OR no Command!");
            System.exit(1);
        }
        return cmds.get(0);
    }

    //### GRAPHIC ###

    //Colors
    private final int RGB_WHITE = 0xFFFFFF;
    private final int RGB_BLACK = 0x0;
    private final int RGB_RED = 0xFF0000;

    private BufferedImage backBufferImage = null;

    private BufferedImage drawSprites(Bits bits){
        //BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_TYPE);
        if(backBufferImage == null){
            backBufferImage = new BufferedImage(vgm.getConfig().getInputWidth(), vgm.getConfig().getInputHeight(), IMAGE_TYPE);
        }
        BufferedImage image = backBufferImage;

        CpuChip8 cpu = (CpuChip8) getComponent(VmComponentType.CPU);
        RomChip8 rom = (RomChip8) getComponent(VmComponentType.ROM);

        int registerX = bits.getBits(8, 12).toInt();
        int registerY = bits.getBits(4, 8).toInt();

        int drawPositionX = cpu.register.get(registerX).toInt();
        int drawPositionY = cpu.register.get(registerY).toInt();

        int numberOfLines = bits.getBits(0, 4).toInt();

        //int ADDRESS_OFFSET_FOR_ROM = 0x200;

        int startIndexForSprites = cpu.indexRegister.toInt();  //- ADDRESS_OFFSET_FOR_ROM;

        //Bild malen mit den Sprites
        for(int j=0; j<numberOfLines; j++){
            Bits sprite = null;
            if(j == 0){
                sprite = rom.readAtIndex(startIndexForSprites, 8);
            }
            else{
                sprite = rom.read(8);
            }
            for(int i=0; i<8; i++){
                boolean b = sprite.getBit(7-i);
                if(b){
                    image.setRGB(drawPositionX + i, drawPositionY + j, RGB_WHITE);
                }
                else{
                    image.setRGB(drawPositionX + i, drawPositionY + j, RGB_BLACK);
                }
            }
        }

        //Set CarryFlag = 1 if Pixel changed!  //Todo: noch ändern, hier wird nicht geprüft, ob sich etwas geändert hat!
        cpu.carryFlag.clearBits();
        cpu.carryFlag.setBit(0, true);

        return image;
    }

    private BufferedImage clearScreen(int color){
        BufferedImage image = new BufferedImage(vgm.getConfig().getInputWidth(), vgm.getConfig().getInputHeight(), IMAGE_TYPE);

        for(int x=0; x< vgm.getConfig().getInputWidth(); x++){
            for(int y=0; y< vgm.getConfig().getInputHeight(); y++){
                image.setRGB(x, y, color);
            }
        }
        return image;
    }

    private BufferedImage clearScreen(){
        return clearScreen(RGB_BLACK);
    }

    //### FONTS ###

    private void initFontSet(){
        fontSet = new HashMap<>();
        List<Bits> tmp = new ArrayList<>();
        //### 0 ###
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        fontSet.put("0", tmp);
        //### 1 ###
        tmp.clear();
        tmp.add(new Bits(0x20));
        tmp.add(new Bits(0x60));
        tmp.add(new Bits(0x20));
        tmp.add(new Bits(0x20));
        tmp.add(new Bits(0x70));
        fontSet.put("1", tmp);
        //### 2 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        fontSet.put("2", tmp);
        //### 3 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0xF0));
        fontSet.put("3", tmp);
        //### 4 ###
        tmp.clear();
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0x10));
        fontSet.put("4", tmp);
        //### 5 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0xF0));
        fontSet.put("5", tmp);
        //### 6 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        fontSet.put("6", tmp);
        //### 7 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0x20));
        tmp.add(new Bits(0x40));
        tmp.add(new Bits(0x40));
        fontSet.put("7", tmp);
        //### 8 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        fontSet.put("8", tmp);
        //### 9 ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x10));
        tmp.add(new Bits(0xF0));
        fontSet.put("9", tmp);
        //### A ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0x90));
        fontSet.put("A", tmp);
        //### B ###
        tmp.clear();
        tmp.add(new Bits(0xE0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xE0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xE0));
        fontSet.put("B", tmp);
        //### C ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        fontSet.put("C", tmp);
        //### D ###
        tmp.clear();
        tmp.add(new Bits(0xE0));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0x90));
        tmp.add(new Bits(0xE0));
        fontSet.put("D", tmp);
        //### E ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        fontSet.put("E", tmp);
        //### F ###
        tmp.clear();
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        tmp.add(new Bits(0x80));
        tmp.add(new Bits(0xF0));
        fontSet.put("F", tmp);
    }

}
