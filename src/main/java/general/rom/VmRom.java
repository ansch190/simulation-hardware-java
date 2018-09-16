package general.rom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import basics.bits.Bits;
import general.vm.components.VmComponent;
import general.vm.components.VmComponentType;

/**
 * Created by andreas on 25.05.16.
 *
 * Basisklasse der VmRom-Klassen
 */
public class VmRom extends VmComponent implements VmRomInterface {

    protected File medium;  //Todo: Konzept prüfen, wird im Moment benutzt!

//    protected DataInputStream din;
    protected BufferedInputStream din;

    public VmRom() {
        super();
    }

    @Override
    public void init() {
        super.componentType = VmComponentType.ROM;
        super.components = new HashMap<>();
    }

    @Override
    public void load(File f) {
        medium = f;
        try{
//            din = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
            din = new BufferedInputStream(new FileInputStream(f));
            din.mark(0);  //mark the Stream for later reset() to StartPosition = 0
        }
        catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void close() {
        try{
            if(din != null){
                din.close();
                din = null;
            }
        }
        catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public Bits read(int bitCount) { //Todo: vielleicht auf Bytes umschreiben und nicht auf Bits?
        try{
            if(din != null){
                byte[] array = null;

                int rest = bitCount % 8;
                int count = (bitCount - rest) / 8;

                array = new byte[count];

                if(rest != 0){
                    log.info("Invalid bitCount!");
                    System.exit(1);
                }
                din.read(array);
                Bits out = new Bits(array);
                return out;
            }
        }
        catch(Exception e){ e.printStackTrace(); }
        return null;
    }

    @Override
    public Bits readAtIndex(int byteIndex, int bitCount) { //Todo: vielleicht auf Bytes umschreiben und nicht auf Bits?
        try{
            if(din != null){
                byte[] array = null;

                int rest = bitCount % 8;
                int count = (bitCount - rest) / 8;

                array = new byte[count];

                if(rest != 0){
                    log.info("Invalid bitCount!");
                    System.exit(1);
                }
                din.reset();
                din.skip(byteIndex);
                din.read(array);
                Bits out = new Bits(array);
                return out;
            }
        }
        catch(Exception e){ e.printStackTrace(); }
        return null;
    }

//    @Override
//    public Bits read(int bitCount) {
//        try{
//            if(din != null){
//                byte[] array = null;
//                if(bitCount == 8){
//                    array = new byte[1];
//                }
//                else if(bitCount == 16){
//                    array = new byte[2];
//                }
//                else if(bitCount == 24){
//                    array = new byte[3];
//                }
//                else if(bitCount == 32){
//                    array = new byte[4];
//                }
//                else if(bitCount == 40){
//                    array = new byte[5];
//                }
//                else if(bitCount == 48){
//                    array = new byte[6];
//                }
//                else if(bitCount == 56){
//                    array = new byte[7];
//                }
//                else if(bitCount == 64){
//                    array = new byte[8];
//                }
//                else if(bitCount == 72){
//                    array = new byte[9];
//                }
//                else if(bitCount == 80){
//                    array = new byte[10];
//                }
//                else if(bitCount == 128){
//                    array = new byte[16];
//                }
//                else if(bitCount == 256){
//                    array = new byte[32];
//                }
//                else if(bitCount == 512){
//                    array = new byte[64];
//                }
//                else if(bitCount == 1024){
//                    array = new byte[128];
//                }
//                else{
//                    log.info("Invalid bitCount!");  //Todo: prüfen der Methode auf Bitgenauigkeit bei einem Rest != 0, nötig? man ließt immer nur ganze Bytes!!!
//                    //System.exit(1);
//                    //132 * 8 = 1056
//
//                    int rest = bitCount % 8;
//                    int count = (bitCount - rest) / 8;
//
//                    array = new byte[count];
//                }
//                din.read(array);
//                Bits out = new Bits(array);
//                return out;
//            }
//        }
//        catch(Exception e){ e.printStackTrace(); }
//        return null;
//    }

}
