package sorting;

/**
 * Implementation of heap sort algorithm to sort an array of ints.
 */
public class HeapSort {

  /**
   * Sorts the given array of ints using heap sort.
   * @param ints the array to sort
   */
  public static void sort(int[] ints) {
    if (ints == null) throw new IllegalArgumentException("Argument cannot be null");
    // Convert array to valid max-heap.
    for (int i = (ints.length / 2) - 1; i >= 0; i--) siftDown(ints, ints.length, i);
    // Repeatedly swap elements and call siftDown() until array is sorted.
    for (int i = 0; i < ints.length - 1; i++) {
      swap(ints, 0, ints.length - 1 - i);
      siftDown(ints, ints.length - 1 - i, 0);
    }
  }

  /**
   * Recursively sifts the specified element down the heap.
   * @param ints the array being sorted
   * @param length the length of the unsorted part of the array
   * @param parentIdx the index of the element currently being sifted down
   */
  private static void siftDown(int[] ints, int length, int parentIdx) {
    int leftChildIdx = getLeftChildIdx(parentIdx), rightChildIdx = leftChildIdx + 1;
    int largest = parentIdx;
    if (leftChildIdx < length && ints[leftChildIdx] > ints[largest]) largest = leftChildIdx;
    if (rightChildIdx < length && ints[rightChildIdx] > ints[largest]) largest = rightChildIdx;
    if (largest != parentIdx) {
      swap(ints, parentIdx, largest);
      siftDown(ints, length, largest);
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

  /**
   * Gets the left child index from the given parent index.
   * @param parentIdx the parent index
   * @return the left child index
   */
  private static int getLeftChildIdx(int parentIdx) { return (2 * parentIdx) + 1; }
}