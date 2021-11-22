package dataStructures;

import java.util.NoSuchElementException;

/**
 * Implementation of a min-heap that stores Integers and has sifting operations implemented in an
 * iterative style.
 */
public class MinHeapIntegerIterative {
  private static int CAPACITY;
  private final Integer[] heap;
  private int nextIdx;

  /**
   * Constructor for MinHeapIntegerIterative. Creates an empty min-heap with a default capacity of
   * 100.
   */
  public MinHeapIntegerIterative() {
    CAPACITY = 100;
    heap = new Integer[CAPACITY];
    nextIdx = 0;
  }

  /**
   * Constructor for MinHeapIntegerIterative. Creates a min-heap from the given array with a
   * capacity equal to the maximum of: [100, arr.length].
   * @param arr the array from which the heap will be built
   */
  public MinHeapIntegerIterative(Integer[] arr) {
    CAPACITY = Integer.max(100, arr.length);
    heap = arr;
    nextIdx = arr.length;
    buildHeap();
  }

  /**
   * Inserts the given element into the heap as long as the heap is not at capacity.
   * @param elem the element to insert
   */
  public void insert(int elem) {
    if (nextIdx == CAPACITY) throw new IllegalStateException("Maximum heap size reached");
    else {
      heap[nextIdx] = elem;
      siftUp(nextIdx++);
    }
  }

  /**
   * Deletes the element at the given index of the underlying array that is used to implement the
   * heap.
   * @param idx the index of the element to delete
   */
  public void delete(int idx) {
    if (idx >= 0 && idx < nextIdx) {
      changeKey(idx, Integer.MIN_VALUE);
      extractMin();
    }
    else throw new IndexOutOfBoundsException("Index out of range");
  }

  /**
   * Gets, but does not remove, the minimum element contained in the heap.
   * @return the minimum element
   */
  public Integer getMin() {
    if (nextIdx == 0) throw new NoSuchElementException("Heap empty");
    return heap[0];
  }

  /**
   * Gets and removes the minimum element contained in the heap.
   * @return the minimum element
   */
  public Integer extractMin() {
    if (nextIdx == 0) throw new NoSuchElementException("Heap empty");
    Integer minElem = heap[0];
    heap[0] = heap[--nextIdx];
    siftDown(0);
    return minElem;
  }

  /**
   * Changes the value at the given index of the underlying array that is used to implement the
   * heap, and adjusts the heap so that all heap properties are still maintained after the value
   * is changed.
   * @param idx the index of the value to change
   * @param newVal the new value
   */
  public void changeKey(int idx, Integer newVal) {
    if (idx >= 0 && idx < nextIdx) {
      heap[idx] = newVal;
      if (idx > 0 && heap[getParentIdx(idx)] > newVal) siftUp(idx);
      else {
        int leftChildIdx = getLeftChildIdx(idx), rightChildIdx = leftChildIdx + 1;
        if ((leftChildIdx < nextIdx && heap[leftChildIdx] < newVal) ||
                (rightChildIdx < nextIdx && heap[rightChildIdx] < newVal)) siftDown(idx);
      }
    }
    else throw new IndexOutOfBoundsException("Index out of range");
  }

  /**
   * Gets the value at the given index of the underlying array that is used to implement the heap.
   * @param idx the index of the value to get
   * @return the value
   */
  public Integer getValAtIdx(int idx) {
    if (idx < 0 || idx >= nextIdx) throw new IndexOutOfBoundsException("Index out of range");
    return heap[idx];
  }

  /**
   * Ensures that the underlying array implements a valid heap.
   */
  private void buildHeap() { for (int i = (heap.length / 2) - 1; i >= 0; i--) siftDown(i); }

  /**
   * Swaps the elements at the given indices of the underlying array.
   * @param idx1 the index of an element to swap
   * @param idx2 the index of an element to swap
   */
  private void swap(int idx1, int idx2) {
    if (idx1 != idx2) {
      Integer temp = heap[idx1];
      heap[idx1] = heap[idx2];
      heap[idx2] = temp;
    }
  }

  /**
   * Sifts the given element up the heap until it satisfies all heap properties.
   * @param childIdx the index of the element to sift up
   */
  private void siftUp(int childIdx) {
    int parentIdx = getParentIdx(childIdx);
    while (childIdx > 0 && heap[childIdx] < heap[parentIdx]) {
      swap(childIdx, parentIdx);
      childIdx = parentIdx;
      parentIdx = getParentIdx(childIdx);
    }
  }

  /**
   * Sifts the given element down the heap until it satisfies all heap properties. Note that this
   * method is much more verbose when implemented iteratively than the concise recursive solution
   * implemented in MaxHeapCharacterRecursive.
   * @param parentIdx the index of the element to sift down
   */
  private void siftDown(int parentIdx) {
    int leftChildIdx = getLeftChildIdx(parentIdx), rightChildIdx = leftChildIdx + 1;
    int smallest = parentIdx;
    if (leftChildIdx < nextIdx && heap[leftChildIdx] < heap[smallest]) smallest = leftChildIdx;
    if (rightChildIdx < nextIdx && heap[rightChildIdx] < heap[smallest]) smallest = rightChildIdx;
    while (smallest != parentIdx) {
      swap(parentIdx, smallest);
      parentIdx = smallest;
      rightChildIdx = (leftChildIdx = getLeftChildIdx(parentIdx)) + 1;
      if (leftChildIdx < nextIdx && heap[leftChildIdx] < heap[smallest]) smallest = leftChildIdx;
      if (rightChildIdx < nextIdx && heap[rightChildIdx] < heap[smallest]) smallest = rightChildIdx;
    }
  }

  /**
   * Gets the parent index of the given child index in the underlying array.
   * @param childIdx the child index
   * @return the corresponding parent index
   */
  private int getParentIdx(int childIdx) { return (childIdx - 1) / 2; }

  /**
   * Gets the left child index of the given parent index in the underlying array.
   * @param parentIdx the parent index
   * @return the corresponding left child index
   */
  private int getLeftChildIdx(int parentIdx) { return (2 * parentIdx) + 1; }
}