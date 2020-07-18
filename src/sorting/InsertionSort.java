package sorting;

// Implementation of insertion sort algorithm to sort an array of ints.
public class InsertionSort {

  public static int[] sort(int[] ints) {
    if (ints == null) {
      System.out.println("null array encountered");
      return null;
    }
    int sortedIdx;
    for (int i = 1; i < ints.length; i++) {
      sortedIdx = i;
      while (sortedIdx > 0 && ints[sortedIdx] < ints[sortedIdx - 1]) {
        swap(ints, sortedIdx - 1, sortedIdx);
        sortedIdx--;
      }
    }
    return ints;
  }

  private static void swap(int[] ints, int idx1, int idx2) {
    if (idx1 != idx2) {
      int temp = ints[idx1];
      ints[idx1] = ints[idx2];
      ints[idx2] = temp;
    }
  }
}