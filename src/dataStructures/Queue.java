package dataStructures;

import java.util.NoSuchElementException;

// Implementation of a generic queue.
public class Queue<E> {
  private QueueNode head, tail;

  private class QueueNode {
    private E data;
    private QueueNode next;
    private QueueNode(E data) { this.data = data; }
  }

  public void add(E data) {
    QueueNode newNode = new QueueNode(data);
    if (head == null) head = tail = newNode;
    else {
      tail.next = newNode;
      tail = newNode;
    }
  }

  public E remove() {
    if (head == null) throw new NoSuchElementException("No element to remove");
    E data = head.data;
    head = head.next;
    if (head == null) tail = null;
    return data;
  }

  public E peek() {
    if (head == null) throw new NoSuchElementException("No element to peek");
    return head.data;
  }

  public boolean isEmpty() { return head == null; }
}
