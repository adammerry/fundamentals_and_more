package dataStructures;

// Implementation of a max-heap that stores Characters and has sifting operations implemented in a
// recursive style.
public class MaxHeapCharacterRecursive {
  private static int MAX_SIZE = 100;
  private Character[] heap;
  private int nextIdx;

  public MaxHeapCharacterRecursive() {
    MAX_SIZE = 100;
    heap = new Character[MAX_SIZE];
    nextIdx = 0;
  }

  public MaxHeapCharacterRecursive(Character[] arr) {
    MAX_SIZE = Integer.max(100, arr.length);
    heap = arr;
    nextIdx = arr.length;
    buildHeap();
  }

  public void insert(Character elem) {
    if (nextIdx > MAX_SIZE) System.out.println("Maximum heap size reached");
    else {
      heap[nextIdx] = elem;
      siftUp(nextIdx);
      nextIdx++;
    }
  }

  public void delete(int idx) {
    if (idx >= 0 && idx < nextIdx) {
      changeKey(idx, Character.MAX_VALUE);
      extractMax();
    }
    else System.out.println("Index out of range");
  }

  public Character getMax() { return nextIdx > 0 ? heap[0] : null; }

  public Character extractMax() {
    if (nextIdx == 0) return null;
    Character minElem = heap[0];
    nextIdx--;
    heap[0] = heap[nextIdx];
    siftDown(0);
    return minElem;
  }

  public void changeKey(int idx, Character newVal) {
    if (idx >= 0 && idx < nextIdx) {
      heap[idx] = newVal;
      if (idx > 0 && heap[getParentIdx(idx)] < newVal) siftUp(idx);
      else {
        int leftChildIdx = getLeftChildIdx(idx);
        int rightChildIdx = leftChildIdx + 1;
        if ((leftChildIdx < nextIdx && heap[leftChildIdx] > newVal) ||
                (rightChildIdx < nextIdx && heap[rightChildIdx] > newVal)) {
          siftDown(idx);
        }
      }
    }
    else System.out.println("Index out of range");
  }

  public Character getValAtIdx(int idx) { return idx >= 0 && idx < nextIdx ? heap[idx] : null; }

  private void buildHeap() { for (int i = (heap.length / 2) - 1; i >= 0; i--) siftDown(i); }

  private void swap(int idx1, int idx2) {
    if (idx1 != idx2) {
      Character temp = heap[idx1];
      heap[idx1] = heap[idx2];
      heap[idx2] = temp;
    }
  }

  private void siftUp(int childIdx) {
    if (childIdx > 0) {
      int parentIdx = getParentIdx(childIdx);
      if (heap[childIdx] > heap[parentIdx]) {
        swap(childIdx, parentIdx);
        siftUp(parentIdx);
      }
    }
  }

  private void siftDown(int parentIdx) {
    int leftChildIdx = getLeftChildIdx(parentIdx);
    int rightChildIdx = leftChildIdx + 1;
    int largest = parentIdx;
    if (leftChildIdx < nextIdx && heap[leftChildIdx] > heap[largest]) largest = leftChildIdx;
    if (rightChildIdx < nextIdx && heap[rightChildIdx] > heap[largest]) largest = rightChildIdx;
    if (largest != parentIdx) {
      swap(parentIdx, largest);
      siftDown(largest);
    }
  }

  private int getParentIdx(int childIdx) { return (childIdx - 1) / 2; }

  private int getLeftChildIdx(int parentIdx) { return (2 * parentIdx) + 1; }
}