package chip8;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import basics.bits.Bits;
import chip8.cpu.opcode.Commands;

/**
 * Created by andreas on 25.05.16.
 *
 * testing Chip8 Architecture
 */
public class Chip8Test {

    private final Logger log = LoggerFactory.getLogger(Chip8Test.class);

    @Before
    public void before(){
        //...
    }

    @Test
    public void opcodeIdentificationTest() throws Exception {
        Bits bits0 = new Bits(16);

        //0000 0000 1110 0000
        bits0.setBit(5, true);
        bits0.setBit(6, true);
        bits0.setBit(7, true);
        //log.info("Bits0: " + bits0);

        List<Commands> results = Commands.find(bits0);

        //log.info("Identified Commands: " + results);

        Assert.assertEquals("Command - Not identified!", Commands.CLEAR_SCREEN, results.get(0));
    }

    @After
    public void after(){
        //...
    }

}
