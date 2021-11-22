package dataStructures;

/**
 * Implementation of a string builder that offers efficient operations for constructing a string.
 */
public class StringBuilder {
  private static final int SIZE_MULTIPLIER = 2, DEFAULT_CAPACITY = 16;
  private char[] buffer;
  private int charCount;

  /**
   * Constructor for StringBuilder. Initializes the internal buffer with a default capacity (16).
   */
  public StringBuilder() { buffer = new char[DEFAULT_CAPACITY]; }

  /**
   * Constructor for StringBuilder. Creates a StringBuilder from the given character array.
   * @param str the character array to convert into a StringBuilder
   */
  public StringBuilder(char[] str) {
    buffer = str;
    charCount = buffer.length;
  }

  /**
   * Constructor for StringBuilder. Creates a StringBuilder from the given string.
   * @param str the string to convert into a StringBuilder
   */
  public StringBuilder(String str) {
    buffer = str.toCharArray();
    charCount = buffer.length;
  }

  /**
   * Appends a single character to the StringBuilder.
   * @param c the character to append
   */
  public void append(char c) {
    if (charCount == buffer.length) resizeBuffer(charCount + 1);
    buffer[charCount++] = c;
  }

  /**
   * Appends the given array of characters to the end of the StringBuilder.
   * @param str the characters to append
   */
  public void append(char[] str) {
    if (str == null) throw new IllegalArgumentException("Argument cannot be null");
    if (charCount + str.length > buffer.length) resizeBuffer(charCount + str.length);
    System.arraycopy(str, 0, buffer, charCount, str.length);
    charCount += str.length;
  }

  /**
   * Appends the given string to the end of the StringBuilder.
   * @param str the string to append
   */
  public void append(String str) {
    if (str == null) throw new IllegalArgumentException("Argument cannot be null");
    append(str.toCharArray());
  }

  /**
   * Resizes the internal buffer to accommodate more characters.
   * @param newCount the number of characters to accommodate
   */
  private void resizeBuffer(int newCount) {
    int newCapacity = buffer.length;
    while (newCapacity < newCount) newCapacity *= SIZE_MULTIPLIER;
    char[] newBuffer = new char[newCapacity];
    System.arraycopy(buffer, 0, newBuffer, 0, charCount);
    buffer = newBuffer;
  }

  /**
   * Gets the string represented by the StringBuilder.
   * @return the string represented by the StringBuilder
   */
  @Override
  public String toString() {
    char[] currentString = new char[charCount];
    System.arraycopy(buffer, 0, currentString, 0, charCount);
    return new String(currentString);
  }
}
