package sorting;

/**
 * Implementation of insertion sort algorithm to sort an array of ints.
 */
public class InsertionSort {

  /**
   * Sorts the given array of ints using insertion sort.
   * @param ints the array to sort
   */
  public static void sort(int[] ints) {
    if (ints == null) throw new IllegalArgumentException("Argument cannot be null");
    else {
      for (int i = 1; i < ints.length; i++)
        for (int j = i; j > 0 && ints[j] < ints[j - 1]; j--) swap(ints, j - 1, j);
    }
  }

  /**
   * Swaps the elements at the given indices in the given array.
   * @param ints the array containing the elements to swap
   * @param idx1 the index of an element to swap
   * @param idx2 the index of an element to swap
   */
  private static void swap(int[] ints, int idx1, int idx2) {
    if (idx1 != idx2) {
      int temp = ints[idx1];
      ints[idx1] = ints[idx2];
      ints[idx2] = temp;
    }
  }
}