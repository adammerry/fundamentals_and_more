package sorting;

// Implementation of merge sort algorithm to sort an array of ints.
public class MergeSort {

  public static int[] sort(int[] ints) {
    if (ints == null) {
      System.out.println("null array encountered");
      return null;
    }
    if (ints.length == 0) {
      return ints;
    }
    return sortHelper(ints, 0, ints.length - 1);
  }

  private static int[] sortHelper(int[] ints, int lowIdx, int highIdx) {
    if (lowIdx == highIdx) {
      int[] singleton = { ints[lowIdx] };
      return singleton;
    }
    int newHighIdx = (lowIdx + highIdx) / 2;
    int newLowIdx = newHighIdx + 1;
    int[] sorted1 = sortHelper(ints, lowIdx, newHighIdx);
    int[] sorted2 = sortHelper(ints, newLowIdx, highIdx);
    return merge(sorted1, sorted2);
  }

  private static int[] merge(int[] sorted1, int[] sorted2) {
    int[] merged = new int[sorted1.length + sorted2.length];
    int sorted1Idx = 0;
    int sorted2Idx = 0;
    for (int i = 0; i < sorted1.length + sorted2.length; i++) {
      if (sorted1Idx == sorted1.length) {
        merged[i] = sorted2[sorted2Idx];
        sorted2Idx++;
      }
      else if (sorted2Idx == sorted2.length || sorted1[sorted1Idx] < sorted2[sorted2Idx]) {
        merged[i] = sorted1[sorted1Idx];
        sorted1Idx++;
      }
      else {
        merged[i] = sorted2[sorted2Idx];
        sorted2Idx++;
      }
    }
    return merged;
  }
}