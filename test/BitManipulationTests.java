import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bitManipulation.BitMethods;

public class BitManipulationTests {

  @Test
  public void testConvertToBinary() {
    assertEquals("0", BitMethods.convertToBinary(0));
    assertEquals("1", BitMethods.convertToBinary(1));
    assertEquals("10", BitMethods.convertToBinary(2));
    assertEquals("11", BitMethods.convertToBinary(3));
    assertEquals("100", BitMethods.convertToBinary(4));
    assertEquals("101", BitMethods.convertToBinary(5));
    assertEquals("110", BitMethods.convertToBinary(6));
    assertEquals("111", BitMethods.convertToBinary(7));
    assertEquals("1000", BitMethods.convertToBinary(8));
    assertEquals("1001", BitMethods.convertToBinary(9));
    assertEquals("1010", BitMethods.convertToBinary(10));
    assertEquals("111011001", BitMethods.convertToBinary(473));
  }

  @Test
  public void testConvertToDecimal() {
    assertEquals(89, BitMethods.convertToDecimal("1011001", 2));
    assertEquals(2846, BitMethods.convertToDecimal("10220102", 3));
    assertEquals(243, BitMethods.convertToDecimal("3303", 4));
    assertEquals(1071, BitMethods.convertToDecimal("13241", 5));
    assertEquals(7465, BitMethods.convertToDecimal("54321", 6));
    assertEquals(94779, BitMethods.convertToDecimal("543216", 7));
    assertEquals(57, BitMethods.convertToDecimal("71", 8));
    assertEquals(404, BitMethods.convertToDecimal("488", 9));
    assertEquals(1234567890, BitMethods.convertToDecimal("1234567890", 10));
    assertEquals(13461, BitMethods.convertToDecimal("A128", 11));
    assertEquals(1714, BitMethods.convertToDecimal("BAA", 12));
    assertEquals(27910, BitMethods.convertToDecimal("c91C", 13));
    assertEquals(534676, BitMethods.convertToDecimal("DcbD2", 14));
    assertEquals(48989, BitMethods.convertToDecimal("E7aE", 15));
    assertEquals(267387871, BitMethods.convertToDecimal("ff003Df", 16));
    assertThrows(IllegalArgumentException.class,
            () -> BitMethods.convertToDecimal("", 1));
    assertThrows(IllegalArgumentException.class,
            () -> BitMethods.convertToDecimal("", 17));
  }

  @Test
  public void testGetBit() {
    assertFalse(BitMethods.getBit(0, 0));
    assertTrue(BitMethods.getBit(1, 0));
    assertTrue(BitMethods.getBit(6, 1));
    assertFalse(BitMethods.getBit(64, 3));
    assertTrue(BitMethods.getBit(64, 6));
  }

  @Test
  public void testSetBit() {
    assertEquals(1, BitMethods.setBit(0, 0));
    assertEquals(1, BitMethods.setBit(1, 0));
    assertEquals(7, BitMethods.setBit(5, 1));
    assertEquals(68, BitMethods.setBit(64, 2));
    assertEquals(12, BitMethods.setBit(4, 3));
  }

  @Test
  public void testClearBit() {
    assertEquals(0, BitMethods.clearBit(0, 0));
    assertEquals(0, BitMethods.clearBit(1, 0));
    assertEquals(5, BitMethods.clearBit(7, 1));
    assertEquals(64, BitMethods.clearBit(68, 2));
    assertEquals(4, BitMethods.clearBit(12, 3));
  }

  @Test
  public void testUpdateBit() {
    assertEquals(0, BitMethods.updateBit(0, 0, 0));
    assertEquals(0, BitMethods.updateBit(1, 0, 0));
    assertEquals(1, BitMethods.updateBit(0, 0, 1));
    assertEquals(9, BitMethods.updateBit(1, 3, 1));
    assertEquals(32, BitMethods.updateBit(36, 2, 0));
    assertEquals(27, BitMethods.updateBit(19, 3, 1));
    assertThrows(IllegalArgumentException.class,
            () -> BitMethods.updateBit(100, 5, -1));
    assertThrows(IllegalArgumentException.class,
            () -> BitMethods.updateBit(100, 2, 2));
  }

  @Test
  public void testToggleBit() {
    assertEquals(1, BitMethods.toggleBit(0, 0));
    assertEquals(0, BitMethods.toggleBit(1, 0));
    assertEquals(5, BitMethods.toggleBit(7, 1));
    assertEquals(36, BitMethods.toggleBit(32, 2));
    assertEquals(27, BitMethods.toggleBit(19, 3));
  }

  @Test
  public void testClearLSB() {
    assertEquals(0, BitMethods.clearLSB(1));
    assertEquals(0, BitMethods.clearLSB(128));
    assertEquals(6, BitMethods.clearLSB(7));
    assertEquals(16, BitMethods.clearLSB(20));
  }

  @Test
  public void testClearBitsMSB() {
    assertEquals(0, BitMethods.clearBitsMSB(0, 0));
    assertEquals(0, BitMethods.clearBitsMSB(1, 0));
    assertEquals(1, BitMethods.clearBitsMSB(15, 1));
    assertEquals(5, BitMethods.clearBitsMSB(173, 3));
  }

  @Test
  public void testClearBitsThroughZero() {
    assertEquals(0, BitMethods.clearBitsThoughZero(0, 0));
    assertEquals(0, BitMethods.clearBitsThoughZero(1, 4));
    assertEquals(24, BitMethods.clearBitsThoughZero(27, 2));
    assertEquals(64, BitMethods.clearBitsThoughZero(127, 5));
  }

  @Test
  public void testOneBitSet() {
    assertFalse(BitMethods.oneBitSet(0));
    assertTrue(BitMethods.oneBitSet(1));
    assertFalse(BitMethods.oneBitSet(75));
    assertTrue(BitMethods.oneBitSet(128));
  }

  @Test
  public void testLowestBit() {
    assertEquals(1, BitMethods.lowestBit(1));
    assertEquals(2, BitMethods.lowestBit(2));
    assertEquals(4, BitMethods.lowestBit(4));
    assertEquals(1, BitMethods.lowestBit(5));
    assertEquals(4, BitMethods.lowestBit(28));
    assertEquals(1, BitMethods.lowestBit(-1));
    assertEquals(2, BitMethods.lowestBit(-2));
    assertEquals(4, BitMethods.lowestBit(-4));
    assertEquals(1, BitMethods.lowestBit(-5));
    assertEquals(4, BitMethods.lowestBit(-28));
  }
}