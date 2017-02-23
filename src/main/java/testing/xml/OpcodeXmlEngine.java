package testing.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import basics.bits.Bits;
import general.cpu.opcode.GenericCommand;
import general.cpu.opcode.GenericCommandPart;

/**
 * Created by andreas on 15.12.16.
 *
 * Lädt eine XML-Datei mit Opcodes einer CPU.
 */
public class OpcodeXmlEngine extends XmlEngine {

    public OpcodeXmlEngine(){
        super();
    }

    @Override
    protected void init() {
        this.objectList = null;
        this.objectList = new CopyOnWriteArrayList<>();
    }

    private void printGeneral(Element general){
        Element arc = general.getChild("architecture");
        Element other = general.getChild("other");

        //Architecture
        String arcNameString = arc.getChildText("name");
        String versionString = arc.getChildText("version");

        //Other
        String otherCommentString = other.getChildText("comment");
        String otherAuthorString = other.getChildText("author");
        String otherCreationDateString = other.getChildText("creationDate");

        log.info("--- GENERAL ---");
        log.info(arcNameString);
        log.info(versionString);
        log.info(otherCommentString);
        log.info(otherAuthorString);
        log.info(otherCreationDateString);
        log.info("---------------");
    }

    private void generateOpcodes(Element opcodes){
        List<Element> commands = opcodes.getChildren();

        for(Element cmd : commands){
            GenericCommand gc = createCmd(cmd);
            objectList.add(gc);
        }
    }

    private GenericCommand createCmd(Element cmd){
        GenericCommand out = new GenericCommand();

        String name = cmd.getChildText("name");

        List<GenericCommandPart> commandParts = new ArrayList<>();
        List<Element> parts = cmd.getChildren("part");

        for(Element part : parts){
            GenericCommandPart gcp = createCmdPart(part);
            commandParts.add(gcp);
        }

        out.setName(name);
        out.setParts(commandParts);

        log.info(out.toString() + "\n");

        return out;
    }

    private GenericCommandPart createCmdPart(Element part){
        GenericCommandPart out = new GenericCommandPart();

        if(!part.getChildText("name").isEmpty()){
            out.setName(part.getChildText("name"));
        }

        if(!part.getChildText("startIndex").isEmpty()){
            out.setStartIndexBit(Integer.parseInt(part.getChildText("startIndex")));
        }

        if(!part.getChildText("endIndex").isEmpty()){
            out.setEndIndexBit(Integer.parseInt(part.getChildText("endIndex")));
        }

        out.setSizeBits();

        if(!part.getChildText("value").isEmpty()){
            out.setValue(createBitsFromString(part.getChildText("value")));
        }

        if(part.getChildText("necessary").equals("true")){
            out.setValueNecessary(true);
        }
        else if(part.getChildText("necessary").equals("false")){
            out.setValueNecessary(false);
        }

        //log.info(out.toString() + "\n");

        return out;
    }

    /**
     * erstellt ein Bits aus einem BinärString
     *
     * @param binaryString BinaryString "11001011" MSB <-> LSB
     * @return null
     */
    private Bits createBitsFromString(String binaryString){
        if(!binaryString.isEmpty()){
            List<Boolean> booleanList = new ArrayList<>();
            String s;
            for(int i=0; i<binaryString.length(); i++){  //Bitreihenfolge drehen
                s = binaryString.substring(binaryString.length()-1-i, binaryString.length()-i);
                if(s.equals("0")){
                    booleanList.add(false);
                }
                else if(s.equals("1")){
                    booleanList.add(true);
                }
            }
            Bits out = new Bits(booleanList);
            return out;
        }
        return null;
    }

    @Override
    public void parse() {
        try{
            SAXBuilder builder = new SAXBuilder();

            Document document = (Document)builder.build(xmlFile);

            Element rootElement = document.getRootElement();

            log.info("ROOT: " + rootElement.getName());

            //General
            Element general = rootElement.getChild("general");
            printGeneral(general);

            //Opcodes
            Element opcodes = rootElement.getChild("opcodes");
            generateOpcodes(opcodes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //### ANALYSE ###

    public GenericCommand find(Bits bits){
        List<GenericCommand> results = new ArrayList<>();
        int needed = 0;
        int checked = 0;
        for(Object o : objectList){
            needed = 0;
            checked = 0;

            GenericCommand c = (GenericCommand)o;

//            if(c == Commands.SKIP_NEXT_CMD_IF_VX_EQUALS_NN){
//                log.info("start Debug!");
//            }

            for(GenericCommandPart p : c.getParts()){
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
        else{
            return getCmdWithData(results.get(0), bits);
        }
        return null;
    }

    private boolean checkPart(GenericCommandPart p, Bits bits){
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

    public GenericCommand getCmdWithData(GenericCommand cmd, Bits bits){
        for(GenericCommandPart p : cmd.getParts()){
            if(!p.isNeeded()){
                Bits res = bits.getBits(p.getStartIndex(), p.getEndIndex()+1);
                p.setValue(res);
            }
        }
        return cmd;
    }

}
