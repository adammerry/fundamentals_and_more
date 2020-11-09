package dataStructures;

// Implementation of a min-heap that stores Integers and has sifting operations implemented in an
// iterative style.
public class MinHeapIntegerIterative {
  private static int MAX_SIZE;
  private Integer[] heap;
  private int nextIdx;

  public MinHeapIntegerIterative() {
    MAX_SIZE = 100;
    heap = new Integer[MAX_SIZE];
    nextIdx = 0;
  }

  public MinHeapIntegerIterative(Integer[] arr) {
    MAX_SIZE = Integer.max(100, arr.length);
    heap = arr;
    nextIdx = arr.length;
    buildHeap();
  }

  public void insert(int elem) {
    if (nextIdx > MAX_SIZE) {
      System.out.println("Maximum heap size reached");
    }
    else {
      heap[nextIdx] = elem;
      siftUp(nextIdx);
      nextIdx++;
    }
  }

  public void delete(int idx) {
    if (idx >= 0 && idx < nextIdx) {
      changeKey(idx, Integer.MIN_VALUE);
      extractMin();
    }
    else {
      System.out.println("Index out of range");
    }
  }

  public Integer getMin() {
    return nextIdx > 0 ? heap[0] : null;
  }

  public Integer extractMin() {
    if (nextIdx == 0) {
      return null;
    }
    Integer minElem = heap[0];
    nextIdx--;
    heap[0] = heap[nextIdx];
    siftDown(0);
    return minElem;
  }

  public void changeKey(int idx, Integer newVal) {
    if (idx >= 0 && idx < nextIdx) {
      heap[idx] = newVal;
      if (idx > 0 && heap[getParentIdx(idx)] > newVal) {
        siftUp(idx);
      }
      else {
        int leftChildIdx = getLeftChildIdx(idx);
        int rightChildIdx = leftChildIdx + 1;
        if ((leftChildIdx < nextIdx && heap[leftChildIdx] < newVal) ||
                (rightChildIdx < nextIdx && heap[rightChildIdx] < newVal)) {
          siftDown(idx);
        }
      }
    }
    else {
      System.out.println("Index out of range");
    }
  }

  public Integer getValAtIdx(int idx) {
    return idx >= 0 && idx < nextIdx ? heap[idx] : null;
  }

  private void buildHeap() {
    for (int i = (heap.length / 2) - 1; i >= 0; i--) siftDown(i);
  }

  private void swap(int idx1, int idx2) {
    if (idx1 != idx2) {
      Integer temp = heap[idx1];
      heap[idx1] = heap[idx2];
      heap[idx2] = temp;
    }
  }

  private void siftUp(int idx) {
    int childIdx = idx;
    int parentIdx = getParentIdx(childIdx);
    while (idx > 0 && heap[childIdx] < heap[parentIdx]) {
      swap(childIdx, parentIdx);
      childIdx = parentIdx;
      parentIdx = getParentIdx(childIdx);
    }
  }

  // Note that this method is much more verbose when implemented iteratively than the concise
  // recursive solution implemented in MaxHeapCharacterRecursive.
  private void siftDown(int idx) {
    int parentIdx = idx;
    int leftChildIdx = getLeftChildIdx(parentIdx);
    int rightChildIdx = leftChildIdx + 1;
    int smallest = parentIdx;

    if (leftChildIdx < nextIdx && heap[leftChildIdx] < heap[smallest]) {
      smallest = leftChildIdx;
    }
    if (rightChildIdx < nextIdx && heap[rightChildIdx] < heap[smallest]) {
      smallest = rightChildIdx;
    }
    while (smallest != parentIdx) {
      swap(parentIdx, smallest);
      parentIdx = smallest;
      leftChildIdx = getLeftChildIdx(parentIdx);
      rightChildIdx = leftChildIdx + 1;
      if (leftChildIdx < nextIdx && heap[leftChildIdx] < heap[smallest]) {
        smallest = leftChildIdx;
      }
      if (rightChildIdx < nextIdx && heap[rightChildIdx] < heap[smallest]) {
        smallest = rightChildIdx;
      }
    }
  }

  private int getParentIdx(int childIdx) {
    return (childIdx - 1) / 2;
  }

  private int getLeftChildIdx(int parentIdx) {
    return (2 * parentIdx) + 1;
  }
}