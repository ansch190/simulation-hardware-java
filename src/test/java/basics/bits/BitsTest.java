package basics.bits;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by andreas on 22.05.16.
 *
 * Tests for the basics.bits Class
 */
public class BitsTest {

    private final Logger log = LoggerFactory.getLogger(BitsTest.class);

    @Before
    public void before(){
        //...
    }

    @Test
    public void andTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bits1 = new Bits(8);
        Bits bitsOK = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        //0010 0111
        bits1.setBit(0, true);
        bits1.setBit(1, true);
        bits1.setBit(2, true);
        bits1.setBit(5, true);
        //log.info("Bits1: " + bits1);

        //0000 0101
        bitsOK.setBit(0, true);
        bitsOK.setBit(2, true);
        //log.info("BitsOK: " + bitsOK);

        Bits result = bits0.and(bits1);

        //log.info("AND: " + result);

        Assert.assertEquals("AND - Not Equal!", bitsOK, result);
    }

    @Test
    public void orTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bits1 = new Bits(8);
        Bits bitsOK = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        //0010 0111
        bits1.setBit(0, true);
        bits1.setBit(1, true);
        bits1.setBit(2, true);
        bits1.setBit(5, true);
        //log.info("Bits1: " + bits1);

        //0111 1111
        bitsOK.setBit(0, true);
        bitsOK.setBit(1, true);
        bitsOK.setBit(2, true);
        bitsOK.setBit(3, true);
        bitsOK.setBit(4, true);
        bitsOK.setBit(5, true);
        bitsOK.setBit(6, true);
        //log.info("BitsOK: " + bitsOK);

        Bits result = bits0.or(bits1);

        //log.info("OR: " + result);

        Assert.assertEquals("OR - Not Equal!", bitsOK, result);
    }

    @Test
    public void xorTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bits1 = new Bits(8);
        Bits bitsOK = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        //0010 0111
        bits1.setBit(0, true);
        bits1.setBit(1, true);
        bits1.setBit(2, true);
        bits1.setBit(5, true);
        //log.info("Bits1: " + bits1);

        //0111 1010
        bitsOK.setBit(1, true);
        bitsOK.setBit(3, true);
        bitsOK.setBit(4, true);
        bitsOK.setBit(5, true);
        bitsOK.setBit(6, true);
        //log.info("BitsOK: " + bitsOK);

        Bits result = bits0.xor(bits1);

        //log.info("XOR: " + result);

        Assert.assertEquals("XOR - Not Equal!", bitsOK, result);
    }

    @Test
    public void invertTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bitsOK = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        //1010 0010
        bitsOK.setBit(1, true);
        bitsOK.setBit(5, true);
        bitsOK.setBit(7, true);
        //log.info("BitsOK: " + bitsOK);

        Bits result = bits0.invert();

        //log.info("INVERT: " + result);

        Assert.assertEquals("INVERT - Not Equal!", bitsOK, result);
    }

    @Test
    public void shiftUpTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bitsOK = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        //1011 1010
        bitsOK.setBit(1, true);
        bitsOK.setBit(3, true);
        bitsOK.setBit(4, true);
        bitsOK.setBit(5, true);
        bitsOK.setBit(7, true);
        //log.info("BitsOK: " + bitsOK);

        bits0.shiftUp(1);

        //log.info("SHIFT_UP: " + result);

        Assert.assertEquals("SHIFT_UP - Not Equal!", bitsOK, bits0);
    }

    @Test
    public void shiftDownTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bitsOK = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        //0010 1110
        bitsOK.setBit(1, true);
        bitsOK.setBit(2, true);
        bitsOK.setBit(3, true);
        bitsOK.setBit(5, true);
        //log.info("BitsOK: " + bitsOK);

        bits0.shiftDown(1);

        //log.info("SHIFT_DOWN: " + result);

        Assert.assertEquals("SHIFT_DOWN - Not Equal!", bitsOK, bits0);
    }

    @Test
    public void binaryToByteTest() throws Exception {
        Bits bits0 = new Bits(8);

        //0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        //log.info("Bits0: " + bits0);

        Byte b = bits0.toByte();

        //log.info("BINARY->BYTE: " + b);

        Assert.assertEquals("BINARY->BYTE - Not Equal!", (byte)93, (byte)b);
    }

    @Test
    public void binaryToShortTest() throws Exception {
        Bits bits0 = new Bits(16);

        //0001 0000 0101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        bits0.setBit(12, true);
        //log.info("Bits0: " + bits0);

        Short s = bits0.toShort();

        //log.info("BINARY->SHORT: " + s);

        Assert.assertEquals("BINARY->SHORT - Not Equal!", (short)4189, (short)s);
    }

    @Test
    public void binaryToIntegerTest() throws Exception {
        Bits bits0 = new Bits(32);

        //  Byte 4   Byte 3    Byte 2    Byte 1
        //0000 0010 0000 0000 0000 0000 1101 1101
        bits0.setBit(0, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(4, true);
        bits0.setBit(6, true);
        bits0.setBit(7, true);
        bits0.setBit(25, true);
        //log.info("Bits0: " + bits0);

        int result = bits0.toInt();

        //log.info("BINARY->INTEGER: " + result);

        Assert.assertEquals("BINARY->INTEGER - Not Equal!", 33554653, result);
    }

//    @Test
//    public void binaryToLongTest() throws Exception {
//        Bits bits0 = new Bits(64);
//
//        //  Byte 7   Byte 6    Byte 5    Byte 4    Byte 3    Byte 2    Byte 1    Byte 0
//        //0000 0000 0000 0000 0000 1000 1101 1101 0000 0010 0000 0000 0000 0000 1101 1101
//        bits0.setBit(0, true);
//        bits0.setBit(2, true);
//        bits0.setBit(3, true);
//        bits0.setBit(4, true);
//        bits0.setBit(6, true);
//        bits0.setBit(7, true);
//        bits0.setBit(25, true);
//        bits0.setBit(32, true);
//        bits0.setBit(34, true);
//        bits0.setBit(35, true);
//        bits0.setBit(36, true);
//        bits0.setBit(38, true);
//        bits0.setBit(39, true);
//        bits0.setBit(43, true);
//        //log.info("Bits0: " + bits0);
//
//        long result = bits0.toLong();
//
//        log.info(Long.toBinaryString(result));
//        log.info(Long.toUnsignedString(result));
//        log.info(String.valueOf(Long.bitCount(result)));
//        log.info(String.valueOf(Long.highestOneBit(result)));
//
//        //log.info("BINARY->LONG: " + result);
//
//        Assert.assertEquals("BINARY->LONG - Not Equal!", (long) 1, result);
//    }

    @Test
    public void getBitsTest() throws Exception {
        Bits bits0 = new Bits(8);

        // Byte 1
        //1100 1111
        bits0.setBit(0, true);
        bits0.setBit(1, true);
        bits0.setBit(2, true);
        bits0.setBit(3, true);
        bits0.setBit(6, true);
        bits0.setBit(7, true);
        //log.info("Bits0: " + bits0);

        Bits result = bits0.getBits(2, 7);
        Bits correctResult = new Bits(Arrays.asList(true, true, false, false, true));  //10011

        //log.info("GetBits(2,7): " + result);

        Assert.assertEquals("GetBits() - Not Equal!", correctResult, result);
    }

    @Test
    public void setBitsTest() throws Exception {
        Bits bits0 = new Bits(8);

        // Byte 1
        //1110 0000
        bits0.setBit(5, true);
        bits0.setBit(6, true);
        bits0.setBit(7, true);
        //log.info("Bits0: " + bits0);

        //1 0110
        Bits bits1 = new Bits(5);
        bits1.setBit(1, true);
        bits1.setBit(2, true);
        bits1.setBit(4, true);
        //log.info("Bits1: " + bits1);

        bits0.setBits(0, bits1);
        Bits correctResult = new Bits(Arrays.asList(false, true, true, false, true, true, true, true));  //1111 0110

        //log.info("SetBits(0): " + bits0);

        Assert.assertEquals("SetBits(0) - Not Equal!", correctResult, bits0);
    }

    @Test
    public void loadByteArrayTest() throws Exception {
        byte[] bytes = new byte[2];

        bytes[0] = 0b01011010;
        bytes[1] = 0b01110000;

        Bits result = new Bits(bytes);
        Bits correctResult = new Bits(Arrays.asList(false, false, false, false, true, true, true, false,
                false, true, false, true, true, false, true, false));

        log.info("BITS-ARRAY: " + result);
        Assert.assertEquals("LoadByteArray() - Not Equal!", correctResult, result);
    }

    //### Binary new Math Operations
    @Test
    public void addBinaryMathNoCarryTest() throws Exception {
        //Test A
        //Addition zweier Zahlen
        //   0000 0000
        // + 0000 0000
        //-------------
        //   0000 0000

        Bits bitsA = new Bits(8);
        bitsA.setBit(0, false);
        bitsA.setBit(1, false);
        bitsA.setBit(2, false);
        bitsA.setBit(3, false);

        bitsA.setBit(4, false);
        bitsA.setBit(5, false);
        bitsA.setBit(6, false);
        bitsA.setBit(7, false);

        Bits bitsB = new Bits(8);
        bitsB.setBit(0, false);
        bitsB.setBit(1, false);
        bitsB.setBit(2, false);
        bitsB.setBit(3, false);

        bitsB.setBit(4, false);
        bitsB.setBit(5, false);
        bitsB.setBit(6, false);
        bitsB.setBit(7, false);

        Bits result = bitsA.addBinaryMathNoCarry(bitsB);

        Bits correctResult = new Bits(Arrays.asList(false, false, false, false, false, false, false, false));

        Assert.assertEquals("Addition Test A - Not Equal!", correctResult, result);

        //Test B
        //Addition zweier Zahlen
        //   0000 0000
        // + 1111 1111
        //-------------
        //   1111 1111

        bitsA.setBit(0, false);
        bitsA.setBit(1, false);
        bitsA.setBit(2, false);
        bitsA.setBit(3, false);

        bitsA.setBit(4, false);
        bitsA.setBit(5, false);
        bitsA.setBit(6, false);
        bitsA.setBit(7, false);

        bitsB.setBit(0, true);
        bitsB.setBit(1, true);
        bitsB.setBit(2, true);
        bitsB.setBit(3, true);

        bitsB.setBit(4, true);
        bitsB.setBit(5, true);
        bitsB.setBit(6, true);
        bitsB.setBit(7, true);

        result = bitsA.addBinaryMathNoCarry(bitsB);

        correctResult = new Bits(Arrays.asList(true, true, true, true, true, true, true, true));

        Assert.assertEquals("Addition Test B - Not Equal!", correctResult, result);

        //Test C
        //Addition zweier Zahlen
        //   1111 1111
        // + 0000 0000
        //-------------
        //   1111 1111

        bitsA.setBit(0, true);
        bitsA.setBit(1, true);
        bitsA.setBit(2, true);
        bitsA.setBit(3, true);

        bitsA.setBit(4, true);
        bitsA.setBit(5, true);
        bitsA.setBit(6, true);
        bitsA.setBit(7, true);

        bitsB.setBit(0, false);
        bitsB.setBit(1, false);
        bitsB.setBit(2, false);
        bitsB.setBit(3, false);

        bitsB.setBit(4, false);
        bitsB.setBit(5, false);
        bitsB.setBit(6, false);
        bitsB.setBit(7, false);

        result = bitsA.addBinaryMathNoCarry(bitsB);

        correctResult = new Bits(Arrays.asList(true, true, true, true, true, true, true, true));

        Assert.assertEquals("Addition Test C - Not Equal!", correctResult, result);

        //Test D
        //Addition zweier Zahlen
        //   0000 1111
        // + 0000 0001
        //-------------
        //   0001 0000

        bitsA.setBit(0, true);
        bitsA.setBit(1, true);
        bitsA.setBit(2, true);
        bitsA.setBit(3, true);

        bitsA.setBit(4, false);
        bitsA.setBit(5, false);
        bitsA.setBit(6, false);
        bitsA.setBit(7, false);

        bitsB.setBit(0, true);
        bitsB.setBit(1, false);
        bitsB.setBit(2, false);
        bitsB.setBit(3, false);

        bitsB.setBit(4, false);
        bitsB.setBit(5, false);
        bitsB.setBit(6, false);
        bitsB.setBit(7, false);

        result = bitsA.addBinaryMathNoCarry(bitsB);

        correctResult = new Bits(Arrays.asList(false, false, false, false, true, false, false, false));

        Assert.assertEquals("Addition Test D - Not Equal!", correctResult, result);

        //Test E
        //Addition zweier Zahlen
        //   1111 1111
        // + 1111 1111
        //-------------
        // 1 1111 1110

        bitsA.setBit(0, true);
        bitsA.setBit(1, true);
        bitsA.setBit(2, true);
        bitsA.setBit(3, true);

        bitsA.setBit(4, true);
        bitsA.setBit(5, true);
        bitsA.setBit(6, true);
        bitsA.setBit(7, true);

        bitsB.setBit(0, true);
        bitsB.setBit(1, true);
        bitsB.setBit(2, true);
        bitsB.setBit(3, true);

        bitsB.setBit(4, true);
        bitsB.setBit(5, true);
        bitsB.setBit(6, true);
        bitsB.setBit(7, true);

        result = bitsA.addBinaryMathNoCarry(bitsB);

        correctResult = new Bits(Arrays.asList(false, true, true, true, true, true, true, true));

        Assert.assertEquals("Addition Test E - Not Equal!", correctResult, result);

        //Test F
        //Addition zweier Zahlen
        //   0110 1101
        // + 1100 1001
        //-------------
        // 1 0011 0110

        bitsA.setBit(0, true);
        bitsA.setBit(1, false);
        bitsA.setBit(2, true);
        bitsA.setBit(3, true);

        bitsA.setBit(4, false);
        bitsA.setBit(5, true);
        bitsA.setBit(6, true);
        bitsA.setBit(7, false);

        bitsB.setBit(0, true);
        bitsB.setBit(1, false);
        bitsB.setBit(2, false);
        bitsB.setBit(3, true);

        bitsB.setBit(4, false);
        bitsB.setBit(5, false);
        bitsB.setBit(6, true);
        bitsB.setBit(7, true);

        result = bitsA.addBinaryMathNoCarry(bitsB);

        correctResult = new Bits(Arrays.asList(false, true, true, false, true, true, false, false));

        Assert.assertEquals("Addition Test F - Not Equal!", correctResult, result);

        //Test G
        //Addition zweier Zahlen
        //   1111 1111
        // + 0000 0001
        //-------------
        // 1 0000 0000

        bitsA.setBit(0, true);
        bitsA.setBit(1, true);
        bitsA.setBit(2, true);
        bitsA.setBit(3, true);

        bitsA.setBit(4, true);
        bitsA.setBit(5, true);
        bitsA.setBit(6, true);
        bitsA.setBit(7, true);

        bitsB.setBit(0, true);
        bitsB.setBit(1, false);
        bitsB.setBit(2, false);
        bitsB.setBit(3, false);

        bitsB.setBit(4, false);
        bitsB.setBit(5, false);
        bitsB.setBit(6, false);
        bitsB.setBit(7, false);

        result = bitsA.addBinaryMathNoCarry(bitsB);

        correctResult = new Bits(Arrays.asList(false, false, false, false, false, false, false, false));

        Assert.assertEquals("Addition Test G - Not Equal!", correctResult, result);
    }

    @Test
    public void subBinaryMathNoCarryTest() throws Exception {
        //Test A
        //Subtraktion zweier Zahlen
        //   0001 0100
        // - 0000 0001
        //-------------
        //   0001 0011

        Bits bitsA = new Bits(8);
        bitsA.setBit(0, false);
        bitsA.setBit(1, false);
        bitsA.setBit(2, true);
        bitsA.setBit(3, false);

        bitsA.setBit(4, true);
        bitsA.setBit(5, false);
        bitsA.setBit(6, false);
        bitsA.setBit(7, false);

        Bits bitsB = new Bits(8);
        bitsB.setBit(0, true);
        bitsB.setBit(1, false);
        bitsB.setBit(2, false);
        bitsB.setBit(3, false);

        bitsB.setBit(4, false);
        bitsB.setBit(5, false);
        bitsB.setBit(6, false);
        bitsB.setBit(7, false);

        Bits result = bitsA.subBinaryMathNoCarry(bitsA, bitsB);

        Bits correctResult = new Bits(Arrays.asList(true, true, false, false, true, false, false, false));

        Assert.assertEquals("Subtraction Test A - Not Equal!", correctResult, result);
    }

    @Test
    public void randomTest() throws Exception {
        Bits bits0 = new Bits(8);
        Bits bits1 = new Bits(8);

        bits0.random();
        bits1.random();

        Assert.assertNotSame("Random-Bits Test A - Equal!", bits0, bits1);

        bits0 = bits0.random(16);
        bits1 = bits1.random(16);

        Assert.assertNotSame("Random-Bits Test B - Equal!", bits0, bits1);
    }


    @After
    public void after(){
        //...
    }

}
