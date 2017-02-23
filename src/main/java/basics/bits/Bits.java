package basics.bits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andreasschw on 20.05.2016.
 *
 * the generic Bits Class. A Class for LowLevel Bit Operations in Java
 */
public class Bits {

    private static final Logger log = LoggerFactory.getLogger(Bits.class);

    //LSB = Index 0
    //MSB = Index > 0

    private List<Boolean> bitList;

    public Bits(List<Boolean> list){
        if(!list.isEmpty()){
            bitList = list;
        }
    }

    public Bits(int bitCount){
        if(bitCount > 0){
            init(bitCount);
        }
    }

    public Bits(byte[] bytes){
        loadByteArray(bytes);
    }

    public Bits(byte b){
        init(8);
        loadByte(b);
    }

    //### LOAD ###

    /**
     * loads a ByteArray
     * !!! DIRECTION OF BYTES/BITS IS EXTREMELY IMPORTANT !!!
     *
     * @param bytes the ByteArray
     */
    private void loadByteArray(byte[] bytes){
        init(bytes.length * 8);
        byte b;
        for(int i=0; i<bytes.length; i++){
            b = bytes[bytes.length-1-i];
            loadByte(b, i*8);
        }
    }

    /**
     * loads a Byte with Number of Bits as Offset
     *
     * @param b passed Byte
     * @param bitOffset Number of Bits as Offset
     */
    private void loadByte(byte b, int bitOffset){
        String s = Integer.toBinaryString(b);
        //Abschneiden
        if(s.length() > 8){
            s = s.substring(s.length()-8, s.length());
        }
        //Auffüllen
        while(s.length() < 8){
            s = "0" + s;
        }
        //System.out.println(s);
        //Byte von rechts (LSB) durchgehen
        for(int j=0; j<8; j++){
            if(s.charAt(7-j) == '1'){
                //System.out.println("1");
                setBit(j + bitOffset, true);
            }
        }
    }

    /**
     * loads a Byte
     *
     * @param b passed Byte
     */
    private void loadByte(byte b){
        loadByte(b, 0);
    }

    //### INIT ###

    /**
     * initializes the Object with the passed Number of Bits
     *
     * @param bitCount Number of Bits
     */
    private void init(int bitCount){
        bitList = new ArrayList<Boolean>(bitCount);
        initBits(bitCount);
    }

    /**
     * adds a Number of Bits to the Object with the Value = false
     *
     * @param bitCount Number of Bits
     */
    private void initBits(int bitCount){
        for(int i=0; i<bitCount; i++){
            bitList.add(false);
        }
    }

    //### ADD ###

    /**
     * adds a Value (true/false) to the passed Position and shifts the current Rest up in direction of the MSB
     *
     * @param index the Position to insert
     * @param b the Value (true/false)
     */
    public void add(int index, boolean b){
        bitList.add(index, b);
    }

    /**
     * adds a Value (true/false) after the actual MSB as the new MSB
     *
     * @param b the Value (true/false)
     */
    public void add(boolean b){
        bitList.add(b);
    }

    //### DEL ###

    /**
     * removes a specific Bit!
     *
     * @param index Position of the Bit
     */
    public void del(int index){
        bitList.remove(index);
    }

    //### GET ###

    /**
     * gets the Value of a specific Bit
     *
     * @param index Position of the Bit (0-n)
     * @return the Value (true/false)
     */
    public boolean getBit(int index){
        return bitList.get(index);
    }

    /**
     * returns the Count of Bits
     *
     * @return Integer
     */
    public int size(){
        return bitList.size();
    }

    /**
     * get Bits from a specific startIndex(inclusive) to a specific endIndex(exclusive)
     *
     * @param startIndexBit the startIndex
     * @param endIndexBit the endIndex
     * @return the result Bits
     */
    public Bits getBits(int startIndexBit, int endIndexBit){
        if(startIndexBit > endIndexBit || startIndexBit < 0 || endIndexBit < 0){
            return null;
        }
        Bits out = new Bits(bitList.subList(startIndexBit, endIndexBit));
        return out;
    }

    //### SET ###

    /**
     * sets a specific Bit to the passed Value (true/false)
     *
     * @param index startPosition
     * @param b passed Value (true/false)
     */
    public void setBit(int index, boolean b){
        bitList.set(index, b);
    }

    /**
     * set Bits at a specific Position to the passed Bits
     *
     * @param index startPosition
     * @param data passed Bits
     */
    public void setBits(int index, Bits data){
        for(int i=0; i<data.size(); i++){
            bitList.set(index + i, data.getBit(i));
        }
    }

    //### FUNCTIONS ###

    /**
     * sets all Bits to the passed Value (true/False)
     *
     * @param b passed Value (true/false)
     */
    public void clearBits(boolean b){
        for(int i=0; i<bitList.size(); i++){
            setBit(i, b);
        }
    }

    /**
     * sets all Bits to 0/false
     */
    public void clearBits(){
        clearBits(false);
    }

    //### MATH-OPERATIONS ###

    //Addition
    public Bits add_Math(int a){
        int b = toInt();
        int c = a + b;

        byte[] bytes = intToByteArray(c);
        Bits bits = new Bits(bytes);

        bits = bits.getBits(0, size());  //Länge wieder auf das voriege Objekt zurücksetzen

        return bits;
    }

    public byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    //### BIT-OPERATIONS ###
    //and, or, xor, invert, shift unsigned(left/right)

    /**
     * logical AND
     *
     * @param bits Bits for the Operation
     * @return new Bits with result
     */
    public Bits and(Bits bits){
        if(bits.size() != size()){
            log.error("Bits-Size not equal!");
            return null;
        }
        Bits out = new Bits(size());
        for(int i=0; i<size(); i++)
            if (bits.getBit(i) && getBit(i)) {
                out.setBit(i, true);
            }
        return out;
    }

    /**
     * logical OR
     *
     * @param bits Bits for the Operation
     * @return new Bits with result
     */
    public Bits or(Bits bits){
        if(bits.size() != size()){
            return null;
        }
        Bits out = new Bits(size());
        for(int i=0; i<size(); i++)
            if (bits.getBit(i) || getBit(i)) {
                out.setBit(i, true);
            }
        return out;
    }

    /**
     * logical XOR
     *
     * @param bits Bits for the Operation
     * @return new Bits with result
     */
    public Bits xor(Bits bits){
        if(bits.size() != size()){
            return null;
        }
        Bits out = new Bits(size());
        for(int i=0; i<size(); i++)
            if ((bits.getBit(i) && !getBit(i)) || (!bits.getBit(i) && getBit(i))) {
                out.setBit(i, true);
            }
        return out;
    }

    /**
     * inversion of all Bits
     *
     * @return inverted Bits
     */
    public Bits invert(){
        Bits tmp = new Bits(bitList.size());
        tmp.clearBits(true);
        return xor(tmp);
    }

    /**
     * shifts the Bits in Direction of the MSB (left)
     *
     * @param bits Number of Bits shifted
     */
    public void shiftUp(int bits){
        if(bits > 0){
            for(int i=0; i<bits; i++){
                bitList.add(0, false);
                bitList.remove(bitList.size()-1);
            }
        }
        else{
            log.error("Anzahl der Bits <= 0");
        }
    }

    /**
     * shift the Bits in Direction of the LSB (right)
     *
     * @param bits Number of Bits shifted
     */
    public void shiftDown(int bits){
        if(bits > 0){
            for(int i=0; i<bits; i++){
                bitList.add(false);
                bitList.remove(0);
            }
        }
        else{
            log.error("Anzahl der Bits <= 0");
        }
    }

    //### KONVERTIERUNG ###

    //Automatisches Auffüllen nötig?
    //konvertiere Daten in ein Byte wenn es passt. Ansonsten Fehler
    public Byte toByte(){
        if(!bitList.isEmpty() && bitList.size() < 9){
            int out = 0;
            for(int i=0; i<bitList.size(); i++){
                boolean b = bitList.get(i);
                if(b){
                    int tmp = 0x01 << i;
                    out = out | tmp;
                }
            }
            return (byte)out;
        }
        return null;
    }

    /**
     * return the Bits as Java native Short
     *
     * @return
     */
    public Short toShort(){
        if(!bitList.isEmpty() && bitList.size() < 17){
            int out = 0;
            for(int i=0; i<bitList.size(); i++){
                boolean b = bitList.get(i);
                if(b){
                    int tmp = 0x01 << i;
                    out = out | tmp;
                }
            }
            return (short)out;
        }
        return null;
    }

    //Todo: stimmt noch nicht! Integer können 32 Bit haben!!!
    public Integer toInt(){
        if(!bitList.isEmpty() && bitList.size() < 33){
            int out = 0;
            for(int i=0; i<bitList.size(); i++){
                boolean b = bitList.get(i);
                if(b){
                    int tmp = 0x01 << i;
                    out = out | tmp;
                }
            }
            return out;
        }
        return null;
    }

    //Todo: check this method! unlkares Ergebnis!
    public Long toLong(){
        if(!bitList.isEmpty() && bitList.size() < 65){
            long out = 0;
            for(int i=0; i<bitList.size(); i++){
                boolean b = bitList.get(i);
                if(b){
                    long tmp = 0x01 << i;
                    out = out | tmp;
                }
            }
            return out;
        }
        return null;
    }


    //### SONSTIGES ###

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bits bits = (Bits) o;

        return !(bitList != null ? !bitList.equals(bits.bitList) : bits.bitList != null);

    }

    @Override
    public int hashCode() {
        return bitList != null ? bitList.hashCode() : 0;
    }

    @Override
    public synchronized String toString() {
        String s = "bits = { ";
        for(int i=0; i<bitList.size(); i++){
            boolean b = bitList.get(bitList.size()-1-i);
            if(b) { s += 1 + ", "; }
            else { s += 0 + ", "; }
        }
        s += "} MSB <-> LSB";
        return s;
    }

    //##############################
    //### Binäre Mathefunktionen ###
    //##############################

    /**
     * erstelle das Einerkomplement für Rechenoperationen in Binär
     *
     * @return the Bits
     */
    public Bits createOnesComplement(){
        return invert();
    }

    /**
     * erstelle das Zweierkomplement für binäre Rechenoperationen (Subtraktion)
     *
     * @return the Bits
     */
    public Bits createComplementOnTwo(){
        Bits b = createOnesComplement();
        Bits addOne = new Bits(b.size());
        addOne.setBit(0, true);
        return addBinaryMathNoCarry(b, addOne);
    }

    /**
     * Binary Math Subtraction without an Carry
     *
     * @param bitsA the to subtract
     * @return the Result
     */
    public Bits subBinaryMathNoCarry(Bits bitsA){
        return subBinaryMathNoCarry(this, bitsA);
    }

    /**
     * General Subtraction (BitsA - BitsB) without an Carry
     *
     * @param bitsA the Bits
     * @param bitsB the Bits to subtract
     * @return the Bits
     */
    public Bits subBinaryMathNoCarry(Bits bitsA, Bits bitsB){
        return addBinaryMathNoCarry(bitsA, bitsB.createComplementOnTwo());
    }

    public boolean carry = false;  //Übertrag für die Binäre Addition. Zurücksetzen nicht vergessen!

    /**
     * Math Binary Addition Operation without an Carry
     *
     * @param bits Bits to add
     * @return the Result as Bits
     */
    public Bits addBinaryMathNoCarry(Bits bits){
        return addBinaryMathNoCarry(this, bits);
    }

    /**
     * General Math Binary Addition Operation without an Carry
     * Both Bits need the same Size of Bits.
     *
     * @param bitsA Bits
     * @param bitsB Bits to add
     * @return the Result as Bits
     */
    public Bits addBinaryMathNoCarry(Bits bitsA, Bits bitsB){
        if(bitsA.size() != bitsB.size()){
            log.error("Bits Size not equal!");
            System.exit(1);
        }
        Bits result = new Bits(bitsA.size());
        carry = false;
        for(int i=0; i<bitsA.size(); i++){
            if(bitsA.getBit(i) && bitsB.getBit(i)){
                if(carry){
                    result.setBit(i, true);
                }
                else{
                    result.setBit(i, false);
                    carry = true;
                }
            }
            else if(bitsA.getBit(i) && !bitsB.getBit(i) || !bitsA.getBit(i) && bitsB.getBit(i)){
                if(carry){
                    result.setBit(i, false);
                }
                else{
                    result.setBit(i, true);
                }
            }
            else{
                //False && False
                if(carry){
                    result.setBit(i, true);
                    carry = false;
                }
                else{
                    result.setBit(i, false);
                }
            }
        }
        if(carry){
            log.info("Übertrag vorhanden, wird verworfen! wurde beachtet!");
        }
        return result;
    }

    //### Sonstige Funktionen, die gebraucht werden ###

    /**
     * change this Bits to random Bits
     *
     */
    public void random(){
        Random rnd = new Random();
        rnd.nextInt();  //Um gleichen Anfang zu verhindern
        for(int i=0; i<size(); i++){
            setBit(i, rnd.nextBoolean());
        }
    }

    /**
     * create Random Bits with specific Size
     *
     * @param bitsCount Size of Bits
     * @return the random Bits
     */
    public Bits random(int bitsCount){
        Bits b = new Bits(bitsCount);
        Random rnd = new Random();
        rnd.nextInt();  //Um gleichen Anfang zu verhindern
        for(int i=0; i<b.size(); i++){
            b.setBit(i, rnd.nextBoolean());
        }
        return b;
    }

}
