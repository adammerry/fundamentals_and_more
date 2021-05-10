package dataStructures;

// Implementation of a string-builder that offers efficient operations for constructing a string.
public class StringBuilder {
  private static final int SIZE_MULTIPLIER = 2, DEFAULT_SIZE = 16;
  private char[] buffer;
  private int charCount;

  public StringBuilder() { buffer = new char[DEFAULT_SIZE]; }

  public StringBuilder(char[] str) {
    buffer = str;
    charCount = buffer.length;
  }

  public StringBuilder(String str) {
    buffer = str.toCharArray();
    charCount = buffer.length;
  }

  public void append(char[] str) {
    if (str == null) throw new IllegalArgumentException("Argument cannot be null");
    if (charCount + str.length > buffer.length) resizeBuffer(charCount + str.length);
    System.arraycopy(str, 0, buffer, charCount, str.length);
    charCount += str.length;
  }

  public void append(String str) {
    if (str == null) throw new IllegalArgumentException("Argument cannot be null");
    append(str.toCharArray());
  }

  private void resizeBuffer(int newCount) {
    int newSize = buffer.length;
    while (newSize < newCount) newSize *= SIZE_MULTIPLIER;
    char[] newBuffer = new char[newSize];
    System.arraycopy(buffer, 0, newBuffer, 0, charCount);
    buffer = newBuffer;
  }

  @Override
  public String toString() {
    char[] currentString = new char[charCount];
    System.arraycopy(buffer, 0, currentString, 0, charCount);
    return new String(currentString);
  }
}
