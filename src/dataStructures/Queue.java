package dataStructures;

// Implementation of a generic queue.
public class Queue<E> {
  private QueueNode<E> head;
  private QueueNode<E> tail;

  private static class QueueNode<F> {
    private F data;
    private QueueNode<F> next;
    private QueueNode(F data) {
      this.data = data;
    }
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
    if (head == null) {
      System.out.println("No element to remove.");
      return null;
    }
    E data = head.data;
    head = head.next;
    if (head == null) tail = null;
    return data;
  }

  public E peek() {
    if (head == null) {
      System.out.println("No element to peek.");
      return null;
    }
    return head.data;
  }

  public boolean isEmpty() {
    return head == null;
  }
}
