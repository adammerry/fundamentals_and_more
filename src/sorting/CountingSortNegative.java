package sorting;

// Implementation of counting sort algorithm to sort an array of ints.
public class CountingSortNegative {

  public static void sort(int[] ints) {
    if (ints == null) {
      System.out.println("null array encountered");
      return;
    }
    if (ints.length == 0) return;
    // Determine the range of numbers to be sorted. In some situations this may be given or
    // assumed, but here we calculate it as a preliminary step. This does not change the O(n + k)
    // asymptotic runtime.
    int largest = ints[0], smallest = ints[0];
    for (int i = 1; i < ints.length; i++) {
      if (ints[i] > largest) largest = ints[i];
      if (ints[i] < smallest) smallest = ints[i];
    }
    int[] counts = new int[Math.abs(largest - smallest + 1)];
    // We can safely increase any value in the array immediately, since the default value for
    // elements of an int array in Java is 0.
    for (int i : ints) counts[i - smallest]++;
    for (int i = 0, sortedIdx = 0; i < counts.length; i++) {
      while (counts[i] > 0) {
        ints[sortedIdx] = i + smallest;
        counts[i]--;
        sortedIdx++;
      }
    }
  }
}