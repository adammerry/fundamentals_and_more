package dataStructures;

import java.util.ArrayList;  // Use Java builtin ArrayList instead of my custom ArrayList.
import java.util.HashMap;
import java.util.NoSuchElementException;

// Implementation of a generic priority queue using a min-heap as the underlying data structure.
// This is probably the best way to implement a priority queue, since all operations can be
// executed in sub-linear worst-case time.
public class PriorityQueueHeap<E> {
  private static final int DEFAULT_CAPACITY = 10;
  private final ArrayList<PriorityQueueElement> heap;
  // HashMap used for locating indices of queue elements in O(1) time.
  private final HashMap<E, Integer> idxMap;
  private final int capacity;
  private int size;

  public PriorityQueueHeap(int customCapacity) {
    capacity = (customCapacity > 0) ? customCapacity : DEFAULT_CAPACITY;
    heap = new ArrayList<>(capacity);
    for (int i = 0; i < capacity; i++) heap.add(null);
    size = 0;
    idxMap = new HashMap<>();
  }

  public class PriorityQueueElement {
    private E item;
    private int priority;

    private PriorityQueueElement(E item, int priority) {
      this.item = item;
      this.priority = priority;
    }

    public E getItem() { return item; }

    public int getPriority() { return priority; }
  }

  public void insert(E item, int priority) {
    if (size == capacity) throw new IllegalStateException("Priority Queue is at capacity");
    PriorityQueueElement elem = new PriorityQueueElement(item, priority);
    heap.set(size, elem);
    idxMap.put(item, size);
    siftUp(size);
    size++;
  }

  public PriorityQueueElement getHighestPriority() {
    if (size == 0) throw new NoSuchElementException("Priority Queue empty");
    return heap.get(0);
  }

  public PriorityQueueElement deleteHighestPriority() {
    if (size == 0) throw new NoSuchElementException("Priority Queue empty");
    PriorityQueueElement highestPriority = heap.get(0);
    idxMap.remove(highestPriority.getItem());
    size--;
    if (size > 0) {
      heap.set(0, heap.get(size));
      idxMap.put(heap.get(0).getItem(), 0);
      siftDown(0);
    }
    return highestPriority;
  }

  public void changePriority(E item, int newPriority) {
    int idx = idxMap.getOrDefault(item, -1);
    if (idx >= 0) {
      boolean newPriorityGreater = newPriority > heap.get(idx).getPriority();
      heap.get(idx).priority = newPriority;
      if (newPriorityGreater) siftDown(idx);
      else siftUp(idx);
    }
    else throw new NoSuchElementException("Item not found in Priority Queue");
  }

  public boolean isEmpty() { return size == 0; }

  public boolean contains(E item) { return idxMap.containsKey(item); }

  public Integer getPriority(E item) {
    if (idxMap.containsKey(item)) return heap.get(idxMap.get(item)).getPriority();
    throw new NoSuchElementException("Item not found in Priority Queue");
  }

  private void siftUp(int childIdx) {
    if (childIdx > 0) {
      int parentIdx = getParentIdx(childIdx);
      if (heap.get(childIdx).getPriority() < heap.get(parentIdx).getPriority()) {
        swap(childIdx, parentIdx);
        siftUp(parentIdx);
      }
    }
  }

  private void siftDown(int parentIdx) {
    int leftChildIdx = getLeftChildIdx(parentIdx);
    int rightChildIdx = leftChildIdx + 1;
    int smallest = parentIdx;
    if (leftChildIdx < size && heap.get(leftChildIdx).getPriority() < heap.get(smallest).getPriority())
      smallest = leftChildIdx;
    if (rightChildIdx < size && heap.get(rightChildIdx).getPriority() < heap.get(smallest).getPriority())
      smallest = rightChildIdx;
    if (smallest != parentIdx) {
      swap(parentIdx, smallest);
      siftDown(smallest);
    }
  }

  private void swap(int idx1, int idx2) {
    if (idx1 != idx2) {
      PriorityQueueElement temp = heap.get(idx1);
      heap.set(idx1, heap.get(idx2));
      heap.set(idx2, temp);
      idxMap.put(heap.get(idx1).getItem(), idx1);
      idxMap.put(heap.get(idx2).getItem(), idx2);
    }
  }

  private int getParentIdx(int childIdx) { return (childIdx - 1) / 2; }

  private int getLeftChildIdx(int parentIdx) { return (2 * parentIdx) + 1; }
}