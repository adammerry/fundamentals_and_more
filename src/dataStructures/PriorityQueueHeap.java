package dataStructures;

// Implementation of a generic priority queue using a min-heap as the underlying data structure.
// This is probably the best way to implement a priority queue, since all operations can be
// executed in sub-linear worst-case time.
public class PriorityQueueHeap<E> {
  private static final int DEFAULT_SIZE = 10;
  private PriorityQueueElement<E>[] heap;
  private int size;

  public PriorityQueueHeap(int capacity) {
    heap = (PriorityQueueElement<E>[]) ((capacity > 0) ? new PriorityQueueElement[capacity] :
            new PriorityQueueElement[DEFAULT_SIZE]);
    size = 0;
  }

  private class PriorityQueueElement<F> {
    private F item;
    private int priority;

    private PriorityQueueElement(F item, int priority) {
      this.item = item;
      this.priority = priority;
    }

    private F getItem() { return item; }

    private int getPriority() { return priority; }

    private void setPriority(int priority) {
      this.priority = priority;
    }
  }

  public void insert(E item, int priority) {
    if (size == heap.length) { // Capacity has been reached.
      System.out.println("Priority Queue is at capacity.");
      return;
    }
    heap[size] = new PriorityQueueElement<>(item, priority);
    siftUp(size);
    size++;
  }

  public E getHighestPriority() {
    return (size == 0) ? null : heap[0].getItem();
  }

  public E deleteHighestPriority() {
    if (size == 0) return null;
    E highestPriority = heap[0].getItem();
    size--;
    heap[0] = heap[size];
    siftDown(0);
    return highestPriority;
  }

  public void changePriority(E item, int newPriority) {
    int idx = 0;
    boolean newPriorityGreater = false;
    for (PriorityQueueElement<E> element : heap) {
      if (element.getItem().equals(item)) {
        newPriorityGreater = newPriority > element.getPriority();
        element.setPriority(newPriority);
        break;
      }
      idx++;
    }
    if (newPriorityGreater) siftDown(idx);
    else siftUp(idx);
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
    }
  }

  private int getParentIdx(int childIdx) {
    return (childIdx - 1) / 2;
  }

  private int getLeftChildIdx(int parentIdx) {
    return (2 * parentIdx) + 1;
  }

  public void printQueue() {
    for (int i = 0; i < size; i++)
      System.out.println(heap[i].getItem().toString() + " : " + heap[i].getPriority());
  }
}
