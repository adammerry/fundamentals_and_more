package bitManipulation;

/**
 * Implementations of methods that use bitwise operators or are related to examining individual
 * bits within a piece of data.
 */
public class BitMethods {

  /**
   * Converts a given decimal number to its binary representation.
   * @param num a decimal number
   * @return a String representing the binary form of the given decimal number
   */
  public static String convertToBinary(int num) {
    if (num == 0) return "0";
    StringBuilder binary = new StringBuilder();
    while (num > 0) {
      int bit = num % 2;
      binary.append((bit == 0) ? "0" : "1");
      num >>= 1;
    }
    return binary.reverse().toString();
  }

  /**
   * Converts a String representation of a positive number in any base from 2 to 16, to a decimal
   * integer.
   * @param num a String representation of a positive number
   * @param base a number from 2 to 16
   * @return the decimal equivalent of the given binary number
   */
  public static int convertToDecimal(String num, int base) {
    if (base < 2 || base > 16) throw new IllegalArgumentException("Invalid base");
    int total = 0, multiplier = 1;
    for (int i = num.length() - 1; i >= 0; i--) {
      char digit = num.charAt(i);
      if (digit >= '0' && digit <= '9') total += (digit - '0') * multiplier;
      else if (digit >= 'A' && digit <= 'F') total += (digit - 'A' + 10) * multiplier;
      else if (digit >= 'a' && digit <= 'f') total += (digit - 'a' + 10) * multiplier;
      else return -1;
      multiplier *= base;
    }
    return total;
  }

  /**
   * Determines whether the specified bit of the given integer is 1 or 0.
   * @param num an integer
   * @param bit the bit to get
   * @return true if the specified bit of the given integer is 1, false if not
   */
  public static boolean getBit(int num, int bit) { return (num & (1 << bit)) != 0; }

  /**
   * Sets a specific bit of an integer to 1.
   * @param num an integer
   * @param bit the bit to set
   * @return the given integer with the specified bit set to 1
   */
  public static int setBit(int num, int bit) { return num | (1 << bit); }

  /**
   * Clears a specific bit of an integer.
   * @param num an integer
   * @param bit the bit to clear
   * @return the given integer with the specified bit set to 0
   */
  public static int clearBit(int num, int bit) { return num & ~(1 << bit); }

  /**
   * Updates a specific bit of an integer.
   * @param num an integer
   * @param bit the bit to update
   * @param val the new value of the bit
   * @return the given integer with the specified bit updated to the given value
   */
  public static int updateBit(int num, int bit, int val) {
    if (val < 0 || val > 1) throw new IllegalArgumentException("Invalid value");
    return clearBit(num, bit) | (val << bit);
  }

  /**
   * Toggles a specific bit of an integer.
   * @param num an integer
   * @param bit the bit to toggle
   * @return the given integer with the specified bit toggled to the opposite of its initial value
   */
  public static int toggleBit(int num, int bit) { return num ^ (1 << bit); }

  /**
   * Clears the least significant bit of the given integer.
   * @param num an integer
   * @return the given integer with the least significant bit cleared
   */
  public static int clearLSB(int num) { return num & (num - 1); }

  /**
   * Clears all bits of the given integer from the most significant bit through the given bit.
   * @param num an integer
   * @param bit the least significant bit to clear
   * @return the given integer with the specified bits cleared
   */
  public static int clearBitsMSB(int num, int bit) { return num & ((1 << bit) - 1); }

  /**
   * Clears all bits of the given integer from the given bit (inclusive) through 0.
   * @param num an integer
   * @param bit the most significant bit to clear
   * @return the given integer with the specified bits cleared
   */
  public static int clearBitsThoughZero(int num, int bit) { return num & (-1 << (bit + 1)); }

  /**
   * Determines whether exactly one bit is set in the given integer.
   * @param num an integer
   * @return true if exactly one bit is set in the given integer, false if not
   */
  public static boolean oneBitSet(int num) { return (num != 0) && ((num & (num - 1)) == 0); }

  /**
   * Finds the lowest set bit in the given integer. Note that since Java stores negative integers
   * in two's complement notation, "~(num - 1)" can be replaced by "-num".
   * @param num an integer
   * @return an integer with one bit set, corresponding to the lowest set bit in the given integer
   */
  public static int lowestBit(int num) { return num & -num; }
}
