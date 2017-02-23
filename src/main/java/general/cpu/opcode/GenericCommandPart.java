package general.cpu.opcode;

import basics.bits.Bits;

/**
 * Created by andreas on 24.05.16.
 *
 *
 */
public class GenericCommandPart {

    private String name = "";

    private int startIndexBit = -1;

    private int endIndexBit = -1;  //dieses Bit inklusive

    private int sizeBits = -1;  //Anzahl der Bits

    //private int value = -1;  //Datenwerte
    private Bits value = null;

    private boolean valueNecessary = false;

    public GenericCommandPart(){
        this.name = "";
        this.startIndexBit = -1;
        this.endIndexBit = -1;
        this.sizeBits = -1;
        this.value = null;
        this.valueNecessary = false;
    }

    public GenericCommandPart(String name, int startIndexBit, int endIndexBit){
        this.name = name;
        this.startIndexBit = startIndexBit;
        this.endIndexBit = endIndexBit;
        this.sizeBits = endIndexBit - startIndexBit + 1;
        this.value = null;
        this.valueNecessary = false;
    }

    public GenericCommandPart(String name, int startIndexBit, int endIndexBit, Bits value){
        this.name = name;
        this.startIndexBit = startIndexBit;
        this.endIndexBit = endIndexBit;
        this.sizeBits = endIndexBit - startIndexBit + 1;
        this.value = value;
        this.valueNecessary = true;
    }

    //### GET ###

    public String getName(){
        return this.name;
    }

    public int getStartIndex(){
        return this.startIndexBit;
    }

    public int getEndIndex(){
        return this.endIndexBit;
    }

    public int getSize(){
        return this.sizeBits;
    }

    public Bits getValue(){
        return this.value;
    }

    public boolean isNeeded(){
        return this.valueNecessary;
    }

    //### SET ###

    public void setName(String s){
        this.name = s;
    }

    public void setStartIndexBit(int x){
        this.startIndexBit = x;
    }

    public void setEndIndexBit(int x){
        this.endIndexBit = x;
    }

    public void setSizeBits(){
        if(this.startIndexBit != -1 && this.endIndexBit != -1){
            this.sizeBits = endIndexBit - startIndexBit + 1;
        }
        else{
            System.out.println("GenricCommandPart - Error! setSizeBits mit leeren Values!");
        }
    }

    public void setSizeBits(int x){
        this.sizeBits = x;
    }

    public void setValue(Bits bits){
        this.value = bits;
        this.valueNecessary = true;
    }

    public void setValueNecessary(boolean b){
        this.valueNecessary = b;
    }

    @Override
    public String toString(){
        String s = "PartName: " + this.name;
        s += " Start: " + this.startIndexBit;
        s += " End: " + this.endIndexBit;
        s += " Size: " + this.sizeBits;
//        s += " Data: " + Integer.toBinaryString(this.value);
        s += " Data: " + this.value;
        s += " Needed: " + this.valueNecessary;
        return s;
    }

}
