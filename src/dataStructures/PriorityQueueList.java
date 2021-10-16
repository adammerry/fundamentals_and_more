package dataStructures;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

// Implementation of a generic priority queue using a LinkedList as the underlying data structure.
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

  public void insert(E item, int priority) {
    int insertIdx = 0;
    for (PriorityQueueElement element : elements) {
      if (priority < element.getPriority()) break;
      insertIdx++;
    }
    elements.add(insertIdx, new PriorityQueueElement(item, priority));
  }

  public E getHighestPriority() {
    if (elements.isEmpty()) throw new NoSuchElementException("Priority Queue empty");
    return elements.get(0).getItem();
  }

  public E deleteHighestPriority() {
    if (elements.isEmpty()) throw new NoSuchElementException("Priority Queue empty");
    return elements.remove(0).getItem();
  }

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

  private void swap(int i, int j) {
    PriorityQueueElement temp = elements.get(i);
    elements.set(i, elements.get(j));
    elements.set(j, temp);
  }
}
