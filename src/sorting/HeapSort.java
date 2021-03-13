package sorting;

// Implementation of heap sort algorithm to sort an array of ints.
public class HeapSort {

  public static void sort(int[] ints) {
    if (ints == null) System.out.println("null array encountered");
    else {
      // Convert array to valid max-heap.
      for (int i = (ints.length / 2) - 1; i >= 0; i--) siftDown(ints, ints.length, i);
      // Repeatedly swap elements and call siftDown() until array is sorted.
      for (int i = 0; i < ints.length - 1; i++) {
        swap(ints, 0, ints.length - 1 - i);
        siftDown(ints, ints.length - 1 - i, 0);
      }
    }
  }

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

  private static void swap(int[] ints, int idx1, int idx2) {
    if (idx1 != idx2) {
      int temp = ints[idx1];
      ints[idx1] = ints[idx2];
      ints[idx2] = temp;
    }
  }

  private static int getLeftChildIdx(int parentIdx) { return (2 * parentIdx) + 1; }
}