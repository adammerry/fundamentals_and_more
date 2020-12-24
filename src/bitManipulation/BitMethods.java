package bitManipulation;

// Implementations of methods that use bitwise operators or are related to examining individual bits
// within a piece of data.
public class BitMethods {

  // Returns a String representing the binary form of the given decimal number.
  public static String convertToBinary(int num) {
    if (num == 0) return "0";
    String binary = "";
    while (num > 0) {
      int bit = num % 2;
      binary = ((bit == 0) ? "0" : "1") + binary;
      num >>= 1;
    }
    return binary;
  }

  // Converts a String representation of a positive number in any base from 2 to 16, to a decimal
  // integer.
  public static int convertToDecimal(String num, int base) {
    if (base < 2 || base > 16) return -1;
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

  // Returns true if the specified bit of the given integer is 1, otherwise false.
  public static boolean getBit(int num, int bit) {
    return (num & (1 << bit)) != 0;
  }

  // Sets a specific bit of an integer to 1 and returns the new integer.
  public static int setBit(int num, int bit) {
    return num | (1 << bit);
  }

  // Clears a specific bit of a given integer and returns the new integer.
  public static int clearBit(int num, int bit) {
    return num & ~(1 << bit);
  }

  // Updates a specific bit of an integer to the given value, and returns the new integer.
  public static int updateBit(int num, int bit, int val) {
    if (val < 0 || val > 1) return num; // If the value is invalid, return the given integer.
    return clearBit(num, bit) | (val << bit);
  }

  // Toggles a specific bit of an integer, and returns the new integer.
  public static int toggleBit(int num, int bit) {
    return num ^ (1 << bit);
  }

  // Clears the least-significant bit of the given integer, and returns the new integer.
  public static int clearLSB(int num) {
    return num & (num - 1);
  }

  // Clears all bits of the given integer from the most-significant bit through the given bit.
  public static int clearBitsMSB(int num, int bit) {
    return num & ((1 << bit) - 1);
  }

  // Clears all bits from the given bit (inclusive) through 0.
  public static int clearBitsThoughZero(int num, int bit) {
    return num & (-1 << (bit + 1));
  }

  // Returns true if exactly one bit is set in the given integer, otherwise false.
  public static boolean oneBitSet(int num) {
    return (num != 0) && ((num & (num - 1)) == 0);
  }
}
