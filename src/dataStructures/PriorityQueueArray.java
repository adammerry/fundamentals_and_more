package dataStructures;

import java.util.NoSuchElementException;
import java.util.ArrayList; // Use Java builtin ArrayList instead of my custom ArrayList.

/**
 * Implementation of a generic priority queue using an ArrayList as the underlying data structure.
 * An ArrayList is used here instead of an Array because Arrays do not play nicely with generics in
 * Java. However, the logic involved in this implementation is similar to how a priority queue
 * would be implemented using an Array in other languages, or in Java without generics.
 * @param <E> the type of data contained in the priority queue
 */
public class PriorityQueueArray<E> {
  private static final int DEFAULT_CAPACITY = 10;
  private final int capacity;
  private int size;
  private final ArrayList<PriorityQueueElement> elements;

  /**
   * Constructor for PriorityQueueArray. Creates a priority queue with default capacity (10).
   */
  public PriorityQueueArray() { this(DEFAULT_CAPACITY); }

  /**
   * Constructor for PriorityQueueArray. Creates a priority queue with custom capacity.
   * @param customCapacity the capacity of the priority queue
   */
  public PriorityQueueArray(int customCapacity) {
    capacity = (customCapacity > 0) ? customCapacity : DEFAULT_CAPACITY;
    elements = new ArrayList<>(capacity);
    for (int i = 0; i < capacity; i++) elements.add(null);
    size = 0;
  }

  private class PriorityQueueElement {
    private final E item;
    private int priority;

    private PriorityQueueElement(E item, int priority) {
      this.item = item;
      this.priority = priority;
    }

    private E getItem() { return item; }

    private int getPriority() { return priority; }

    private void setPriority(int priority) { this.priority = priority; }
  }

  /**
   * Inserts an item into the priority queue.
   * @param item the item to insert
   * @param priority the priority of the item
   */
  public void insert(E item, int priority) {
    if (size == capacity) throw new IllegalStateException("Priority Queue is at capacity");
    elements.set(size++, new PriorityQueueElement(item, priority));
  }

  /**
   * Gets, but does not remove, the item with the highest priority in the queue.
   * @return the item with the highest priority
   */
  public E getHighestPriority() {
    if (size == 0) throw new NoSuchElementException("Priority Queue empty");
    PriorityQueueElement highestPriority = elements.get(0);
    for (PriorityQueueElement element : elements)
      // A smaller number indicates a higher priority value.
      if (element.getPriority() < highestPriority.getPriority()) highestPriority = element;
    return highestPriority.getItem();
  }

  /**
   * Gets and removes the item with the highest priority in the queue.
   * @return the item with the highest priority
   */
  public E deleteHighestPriority() {
    if (size == 0) throw new NoSuchElementException("Priority Queue empty");
    int highestPriority = elements.get(0).getPriority(), highestPriorityIdx = 0;
    for (int i = 1; i < size; i++) {
      if (elements.get(i).getPriority() < highestPriority) {
        highestPriority = elements.get(i).getPriority();
        highestPriorityIdx = i;
      }
    }
    E item = elements.get(highestPriorityIdx).getItem();
    elements.set(highestPriorityIdx, elements.get(--size));
    return item;
  }

  /**
   * Changes the priority of the given item.
   * @param item an item in the priority queue
   * @param newPriority the new priority of the item
   */
  public void changePriority(E item, int newPriority) {
    // It is necessary to use a counted for loop here, since indices from "size" to "capacity" may
    // contain deleted items.
    for (int i = 0; i < size; i++) {
      if (elements.get(i).getItem().equals(item)) {
        elements.get(i).setPriority(newPriority);
        return;
      }
    }
    throw new NoSuchElementException("Item not found in Priority Queue");
  }
}
