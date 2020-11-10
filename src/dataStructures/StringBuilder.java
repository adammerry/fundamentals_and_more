package dataStructures;

// Implementation of a string-builder that offers efficient operations for constructing a string.
public class StringBuilder {
  private static final int SIZE_MULTIPLIER = 2;
  private static final int DEFAULT_SIZE = 16;
  private char[] buffer;
  private int charCount;

  public StringBuilder() {
    buffer = new char[DEFAULT_SIZE];
  }

  public StringBuilder(char[] str) {
    buffer = str;
    charCount = buffer.length;
  }

  public StringBuilder(String str) {
    buffer = str.toCharArray();
    charCount = buffer.length;
  }

  public void append(char[] str) {
    if (str == null) return;
    if (charCount + str.length > buffer.length) resizeBuffer(charCount + str.length);
    for (int i = 0; i < str.length; i++) buffer[charCount + i] = str[i];
    charCount += str.length;
  }

  public void append(String str) {
    if (str == null) return;
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