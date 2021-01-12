package dataStructures;

import java.util.HashMap;

// Implementation of a generic priority queue using a min-heap as the underlying data structure.
// This is probably the best way to implement a priority queue, since all operations can be
// executed in sub-linear worst-case time.
public class PriorityQueueHeap<E> {
  private static final int DEFAULT_SIZE = 10;
  private PriorityQueueElement<E>[] heap;
  // HashMap used for locating indices of queue elements in O(1) time.
  private HashMap<E, Integer> idxMap;
  private int size;

  public PriorityQueueHeap(int capacity) {
    heap = (PriorityQueueElement<E>[]) ((capacity > 0) ? new PriorityQueueElement[capacity] :
            new PriorityQueueElement[DEFAULT_SIZE]);
    idxMap = new HashMap<>();
    size = 0;
  }

  public class PriorityQueueElement<F> {
    private F item;
    private int priority;

    private PriorityQueueElement(F item, int priority) {
      this.item = item;
      this.priority = priority;
    }

    public F getItem() { return item; }

    public int getPriority() { return priority; }
  }

  public void insert(E item, int priority) {
    if (size == heap.length) { // Capacity has been reached.
      System.out.println("Priority Queue is at capacity.");
      return;
    }
    PriorityQueueElement<E> elem = new PriorityQueueElement<>(item, priority);
    heap[size] = elem;
    idxMap.put(item, size);
    siftUp(size);
    size++;
  }

  public PriorityQueueElement<E> getHighestPriority() {
    return (size == 0) ? null : heap[0];
  }

  public PriorityQueueElement<E> deleteHighestPriority() {
    if (size == 0) return null;
    PriorityQueueElement<E> highestPriority = heap[0];
    size--;
    heap[0] = heap[size];
    idxMap.put(heap[0].getItem(), 0);
    siftDown(0);
    return highestPriority;
  }

  public void changePriority(E item, int newPriority) {
    boolean newPriorityGreater;
    int idx = idxMap.getOrDefault(item, -1);
    if (idx >= 0) {
      newPriorityGreater = newPriority > heap[idx].getPriority();
      heap[idx].priority = newPriority;
      if (newPriorityGreater) siftDown(idx);
      else siftUp(idx);
    }
  }

  private void siftUp(int childIdx) {
    if (childIdx > 0) {
      int parentIdx = getParentIdx(childIdx);
      if (heap[childIdx].getPriority() < heap[parentIdx].getPriority()) {
        swap(childIdx, parentIdx);
        siftUp(parentIdx);
      }
    }
  }

  private void siftDown(int parentIdx) {
    int leftChildIdx = getLeftChildIdx(parentIdx);
    int rightChildIdx = leftChildIdx + 1;
    int smallest = parentIdx;
    if (leftChildIdx < size && heap[leftChildIdx].getPriority() < heap[smallest].getPriority())
      smallest = leftChildIdx;
    if (rightChildIdx < size && heap[rightChildIdx].getPriority() < heap[smallest].getPriority())
      smallest = rightChildIdx;
    if (smallest != parentIdx) {
      swap(parentIdx, smallest);
      siftDown(smallest);
    }
  }

  private void swap(int idx1, int idx2) {
    if (idx1 != idx2) {
      PriorityQueueElement<E> temp = heap[idx1];
      heap[idx1] = heap[idx2];
      heap[idx2] = temp;
      idxMap.put(heap[idx1].getItem(), idx1);
      idxMap.put(heap[idx2].getItem(), idx2);
    }
  }

  private int getParentIdx(int childIdx) {
    return (childIdx - 1) / 2;
  }

  private int getLeftChildIdx(int parentIdx) {
    return (2 * parentIdx) + 1;
  }
}
