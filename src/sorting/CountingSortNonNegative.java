package sorting;

// Implementation of counting sort algorithm to sort an array of non-negative ints. For a counting
// sort implementation that works with negative numbers, see CountingSortNegative.
public class CountingSortNonNegative {

  public static void sort(int[] ints) {
    if (ints == null) {
      System.out.println("null array encountered");
      return;
    }
    // Determine the range of numbers to be sorted. In some situations this may be given or
    // assumed, but here we calculate it as a preliminary step. This does not change the O(n + k)
    // asymptotic runtime.
    int largest = 0;
    for (int i : ints) {
      if (i < 0) {
        System.out.println("This implementation of counting sort can't take negative numbers");
        return;
      }
      if (i > largest) largest = i;
    }
    int[] counts = new int[largest + 1];
    // We can safely increase any value in the array immediately, since the default value for
    // elements of an int array in Java is 0.
    for (int i : ints) counts[i]++;
    for (int i = 0, sortedIdx = 0; i < counts.length; i++) {
      while (counts[i] > 0) {
        ints[sortedIdx] = i;
        counts[i]--;
        sortedIdx++;
      }
    }
  }
}