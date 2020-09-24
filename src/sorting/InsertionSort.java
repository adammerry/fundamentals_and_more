package sorting;

// Implementation of insertion sort algorithm to sort an array of ints.
public class InsertionSort {

  public static void sort(int[] ints) {
    if (ints == null) System.out.println("null array encountered");
    else {
      for (int i = 1; i < ints.length; i++)
        for (int j = i; j > 0 && ints[j] < ints[j - 1]; j--) swap(ints, j - 1, j);
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