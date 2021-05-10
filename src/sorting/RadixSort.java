package sorting;

// Implementation of radix sort algorithm to sort an array of ints. This implementation does not
// support sorting arrays with negative numbers.
public class RadixSort {

  public static void sort(int[] ints) {
    if (ints == null) throw new IllegalArgumentException("Argument cannot be null");
    if (ints.length < 2) return;
    int largest = ints[0];
    for (int i : ints) {
      if (i < 0) throw new IllegalArgumentException("Negative numbers not supported");
      if (i > largest) largest = i;
    }
    // Perform counting sort for every digit in the largest number in the input.
    for (int i = 1; largest / i > 0; i *= 10) countingSortByDigit(ints, i);
  }

  private static void countingSortByDigit(int[] ints, int divisor) {
    int base = 10;
    int[] counts = new int[base], output = new int[ints.length];
    // Populate counts[] with information based on appropriate digit of each number in ints[].
    for (int i : ints) counts[(i / divisor) % base]++;
    // Modify the values of the counts[] array so that each value corresponds to an index in the
    // output[] array. Modifying counts[] here is necessary because the indices of counts[] are
    // not the same as the values in the initial array. Therefore, we cannot produce an answer by
    // simply running through counts[] and ignoring the original array. We must find a creative
    // way to map values in the original array to the proper indices in output[].
    for (int i = 1; i < base; i++) counts[i] += counts[i - 1];
    // Build output array.
    for (int i = ints.length - 1; i >= 0; i--) {
      // Map each element of ints[] to its appropriate index in output[]. Working from back to front
      // while decreasing values stored in count[] is necessary to keep the sort stable. Stability
      // of each counting sort iteration is necessary for radix sort to perform properly.
      int digit = (ints[i] / divisor) % base;
      output[counts[digit] - 1] = ints[i];
      counts[digit]--;
    }
    System.arraycopy(output, 0, ints, 0, output.length);
  }
}
