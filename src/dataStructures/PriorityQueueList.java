package dataStructures;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of a generic priority queue using a LinkedList as the underlying data structure.
 * @param <E> the type of data contained in the priority queue
 */
public class PriorityQueueList<E> {
  private final List<PriorityQueueElement> elements;

  public PriorityQueueList() { elements =  new LinkedList<>(); }

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
    int insertIdx = 0;
    for (PriorityQueueElement element : elements) {
      if (priority < element.getPriority()) break;
      insertIdx++;
    }
    elements.add(insertIdx, new PriorityQueueElement(item, priority));
  }

  /**
   * Gets, but does not remove, the item with the highest priority in the queue.
   * @return the item with the highest priority
   */
  public E getHighestPriority() {
    if (elements.isEmpty()) throw new NoSuchElementException("Priority Queue empty");
    return elements.get(0).getItem();
  }

  /**
   * Gets and removes the item with the highest priority in the queue.
   * @return the item with the highest priority
   */
  public E deleteHighestPriority() {
    if (elements.isEmpty()) throw new NoSuchElementException("Priority Queue empty");
    return elements.remove(0).getItem();
  }

  /**
   * Changes the priority of the given item.
   * @param item an item in the priority queue
   * @param newPriority the new priority of the item
   */
  public void changePriority(E item, int newPriority) {
    int idx = 0;
    boolean newPriorityGreater = false;
    for (PriorityQueueElement element : elements) {
      if (element.getItem().equals(item)) {
        newPriorityGreater = newPriority > element.getPriority();
        element.setPriority(newPriority);
        break;
      }
      idx++;
    }
    if (idx < elements.size()) { // Item found in priority queue.
      if (newPriorityGreater) {
        while (idx < elements.size() - 1 &&
                elements.get(idx).getPriority() > elements.get(idx + 1).getPriority()) {
          swap(idx, idx + 1);
          idx++;
        }
      }
      else {
        while (idx > 0 &&
                elements.get(idx).getPriority() < elements.get(idx - 1).getPriority()) {
          swap(idx, idx - 1);
          idx--;
        }
      }
    }
    else throw new NoSuchElementException("Item not found in Priority Queue");
  }

  /**
   * Swaps the elements at the given indices of the underlying array.
   * @param idx1 the index of an element to swap
   * @param idx2 the index of an element to swap
   */
  private void swap(int idx1, int idx2) {
    PriorityQueueElement temp = elements.get(idx1);
    elements.set(idx1, elements.get(idx2));
    elements.set(idx2, temp);
  }
}
