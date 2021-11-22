package dataStructures;

import java.util.NoSuchElementException;

/**
 * Implementation of a generic queue.
 * @param <E> the type of data contained in the queue
 */
public class Queue<E> {
  private QueueNode head, tail;

  private class QueueNode {
    private final E data;
    private QueueNode next;

    private QueueNode(E data) { this.data = data; }
  }

  /**
   * Adds an element containing the given data to the tail of the queue.
   * @param data the data to add to the queue
   */
  public void add(E data) {
    QueueNode newNode = new QueueNode(data);
    if (head == null) head = tail = newNode;
    else {
      tail.next = newNode;
      tail = newNode;
    }
  }

  /**
   * Gets and removes the data contained in the element at the head of the queue.
   * @return the data at the head of the queue
   */
  public E remove() {
    if (head == null) throw new NoSuchElementException("No element to remove");
    E data = head.data;
    head = head.next;
    if (head == null) tail = null;
    return data;
  }

  /**
   * Gets, but does not remove, the data contained in the element at the head of the queue.
   * @return the data at the head of the queue
   */
  public E peek() {
    if (head == null) throw new NoSuchElementException("No element to peek");
    return head.data;
  }

  /**
   * Determines whether the queue is empty.
   * @return true if the queue is empty, false otherwise
   */
  public boolean isEmpty() { return head == null; }
}
