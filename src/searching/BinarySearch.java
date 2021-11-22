package searching;

/**
 * Implementation of binary search on a sorted array of numbers.
 */
public class BinarySearch {

  /**
   * Runs a binary search on a sorted array of numbers.
   * @param ints an array of numbers that is assumed to be sorted
   * @param target the number to search for
   * @return the index that contains the target number, or -1 if the target is not present
   */
  public static int binarySearch(int[] ints, int target) {
    int l = 0, r = ints.length - 1;
    while (l <= r) {
      int mid = l + ((r - l) / 2); // To reduce the possibility of overflow, avoid "l + r".
      if (ints[mid] == target) return mid;
      if (ints[mid] < target) l = mid + 1;
      else r = mid - 1;
    }
    return -1;
  }
}
