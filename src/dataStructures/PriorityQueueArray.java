package dataStructures;

import java.util.NoSuchElementException;

// Implementation of a generic priority queue using an array as the underlying data structure.
public class PriorityQueueArray<E> {
  private static final int DEFAULT_SIZE = 10;
  private int size;
  private PriorityQueueElement<E>[] elements;

  public PriorityQueueArray(int capacity) {
    elements = (PriorityQueueElement<E>[]) ((capacity > 0) ? new PriorityQueueElement[capacity] :
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

    private void setPriority(int priority) { this.priority = priority; }
  }

  public void insert(E item, int priority) {
    if (size == elements.length) throw new IllegalStateException("Priority Queue is at capacity");
    elements[size++] = new PriorityQueueElement<>(item, priority);
  }

  public E getHighestPriority() {
    if (size == 0) throw new NoSuchElementException("Priority Queue empty");
    PriorityQueueElement<E> highestPriority = elements[0];
    for (PriorityQueueElement<E> element : elements)
      // A smaller number indicates a higher priority value.
      if (element.getPriority() < highestPriority.getPriority()) highestPriority = element;
    return highestPriority.getItem();
  }

  public E deleteHighestPriority() {
    if (size == 0) throw new NoSuchElementException("Priority Queue empty");
    int highestPriority = elements[0].getPriority();
    int highestPriorityIdx = 0;
    for (int i = 0; i < size; i++) {
      if (elements[i].getPriority() < highestPriority) {
        highestPriority = elements[i].getPriority();
        highestPriorityIdx = i;
      }
    }
    E item = elements[highestPriorityIdx].getItem();
    elements[highestPriorityIdx] = elements[--size];
    return item;
  }

  public void changePriority(E item, int newPriority) {
    for (int i = 0; i < size; i++) {
      if (elements[i].getItem().equals(item)) {
        elements[i].setPriority(newPriority);
        return;
      }
    }
    throw new NoSuchElementException("Item not found in Priority Queue");
  }
}
