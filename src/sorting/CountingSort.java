package sorting;

// Implementation of counting sort algorithm to sort an array of ints.
public class CountingSortNegative {

  public static void sort(int[] ints) {
    if (ints == null) {
      System.out.println("null array encountered");
      return;
    }
    if (ints.length < 2) return;
    // Determine the range of numbers to be sorted. In some situations this may be given or
    // assumed, but here we calculate it as a preliminary step. This does not change the O(n + k)
    // asymptotic runtime.
    int largest = ints[0], smallest = ints[0];
    for (int i : ints) {
      if (i > largest) largest = i;
      else if (i < smallest) smallest = i;
    }
    int[] counts = new int[largest - smallest + 1];
    for (int i : ints) counts[i - smallest]++;
    for (int i = 0, sortedIdx = 0; i < counts.length; i++)
      for (; counts[i] > 0; counts[i]--, sortedIdx++) ints[sortedIdx] = i + smallest;
  }
}