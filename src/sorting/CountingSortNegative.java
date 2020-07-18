package sorting;

// Implementation of counting sort algorithm to sort an array of ints.
public class CountingSortNegative {

  public static int[] sort(int[] ints) {
    if (ints == null) {
      System.out.println("null array encountered");
      return null;
    }
    // Determine the range of numbers to be sorted. In some situations this may be given or
    // assumed, but here we calculate it as a preliminary step. This does not change the O(n + k)
    // asymptotic runtime.
    int largest = Integer.MIN_VALUE;
    int smallest = Integer.MAX_VALUE;
    for (int i : ints) {
      if (i > largest) {
        largest = i;
      }
      if (i < smallest) {
        smallest = i;
      }
    }
    int range = Math.abs(largest - smallest + 1);
    int[] counts = new int[range];
    // We can safely increase any value in the array immediately, since the default value for
    // elements of an int array in Java is 0.
    for (int i : ints) {
      counts[i - smallest]++;
    }
    int sortedIdx = 0;
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        ints[sortedIdx] = i + smallest;
        sortedIdx++;
      }
    }
    return ints;
  }
}