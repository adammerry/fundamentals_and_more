package sorting;

// Implementation of quicksort algorithm to sort an array of ints.
public class QuickSort {

  public static void sort(int[] ints) {
    if (ints == null) throw new IllegalArgumentException("Argument cannot be null");
    else sortHelper(ints, 0, ints.length - 1);
  }

  private static void sortHelper(int[] ints, int lowIdx, int highIdx) {
    if (lowIdx < highIdx) {
      int pivot = ints[highIdx], pivIdx = lowIdx;
      for (int i = lowIdx; i < highIdx; i++) if (ints[i] < pivot) swap(ints, pivIdx++, i);
      swap(ints, pivIdx, highIdx);
      sortHelper(ints, lowIdx, pivIdx - 1);
      sortHelper(ints, pivIdx + 1, highIdx);
    }
  }

  private static void swap(int[] ints, int idx1, int idx2) {
    if (idx1 != idx2) {
      int temp = ints[idx1];
      ints[idx1] = ints[idx2];
      ints[idx2] = temp;
    }
  }
}