package dataStructures;

// Implementation of a generic priority queue using an array as the underlying data structure.
public class PriorityQueueArray<E> {
  private int defaultSize = 10;
  private int size;
  private PriorityQueueElement<E>[] elements;

  public PriorityQueueArray(int capacity) {
    elements = (PriorityQueueElement<E>[]) ((capacity > 0) ? new PriorityQueueElement[capacity] :
            new PriorityQueueElement[defaultSize]);
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
    if (size == elements.length) { // Capacity has been reached.
      System.out.println("Priority Queue is at capacity.");
      return;
    }
    elements[size++] = new PriorityQueueElement<>(item, priority);
  }

  public E getHighestPriority() {
    if (size == 0) return null;
    PriorityQueueElement<E> highestPriority = elements[0];
    for (PriorityQueueElement<E> element : elements) {
      // A smaller number indicates a higher priority value.
      if (element.getPriority() < highestPriority.getPriority()) {
        highestPriority = element;
      }
    }
    return highestPriority.getItem();
  }

  public E deleteHighestPriority() {
    if (size == 0) return null;
    int highestPriority = elements[0].getPriority();
    int highestPriorityIdx = 0;
    for (int i = 0; i < size; i++) {
      if (elements[i].getPriority() < highestPriority) {
        highestPriority = elements[i].getPriority();
        highestPriorityIdx = i;
      }
    }
    E item = elements[highestPriorityIdx].getItem();
    for (int i = highestPriorityIdx; i < size - 1; i++) {
      elements[i] = elements[i + 1];
    }
    size--;
    return item;
  }

  public void changePriority(E item, int newPriority) {
    for (PriorityQueueElement<E> element : elements) {
      if (element.getItem().equals(item)) {
        element.setPriority(newPriority);
        break;
      }
    }
  }

  public void printQueue() {
    for (int i = 0; i < size; i++) {
      System.out.println(elements[i].getItem().toString() + " : " + elements[i].getPriority());
    }
  }
}
