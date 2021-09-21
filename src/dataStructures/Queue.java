package dataStructures;

import java.util.NoSuchElementException;

// Implementation of a generic queue.
public class Queue<E> {
  private QueueNode<E> head, tail;

  private class QueueNode<F> {
    private F data;
    private QueueNode<F> next;
    private QueueNode(F data) { this.data = data; }
  }

  public void add(E data) {
    QueueNode<E> newNode = new QueueNode<>(data);
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
