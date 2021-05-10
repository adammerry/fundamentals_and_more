package sorting;

// Implementation of merge sort algorithm to sort an array of ints.
public class MergeSort {

  public static void sort(int[] ints) {
    if (ints == null) throw new IllegalArgumentException("Argument cannot be null");
    else if (ints.length > 0) sortHelper(ints, 0, ints.length - 1);
  }

  private static void sortHelper(int[] ints, int lowIdx, int highIdx) {
    if (lowIdx < highIdx) {
      int midIdx = lowIdx + ((highIdx - lowIdx) / 2);
      sortHelper(ints, lowIdx, midIdx);
      sortHelper(ints, midIdx + 1, highIdx);
      merge(ints, lowIdx, midIdx, highIdx);
    }
  }

  private static void merge(int[] ints, int lowIdx, int midIdx, int highIdx) {
    int[] merged = new int[highIdx - lowIdx + 1];
    int sorted1Idx = lowIdx, sorted2Idx = midIdx + 1;
    for (int i = 0; i < merged.length; i++) {
      if (sorted1Idx > midIdx) merged[i] = ints[sorted2Idx++];
      else if (sorted2Idx > highIdx || ints[sorted1Idx] < ints[sorted2Idx])
        merged[i] = ints[sorted1Idx++];
      else merged[i] = ints[sorted2Idx++];
    }
    System.arraycopy(merged, 0, ints, lowIdx, merged.length);
  }
}