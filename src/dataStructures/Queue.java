package dataStructures;

// Implementation of a generic queue.
public class Queue<E> {
  private QueueNode<E> first;
  private QueueNode<E> last;

  private static class QueueNode<E> {
    private E data;
    private QueueNode<E> next;

    private QueueNode(E data) {
      this.data = data;
    }
  }

  public void add(E data) {
    QueueNode<E> newNode = new QueueNode<E>(data);
    if (last != null) last.next = newNode;
    last = newNode;
    if (first == null) first = newNode;
  }

  public E remove() {
    if (first == null) {
      System.out.println("No element to remove.");
      return null;
    }
    E data = first.data;
    first = first.next;
    if (first == null) last = null;
    return data;
  }

  public E peek() {
    if (first == null) {
      System.out.println("No element to peek.");
      return null;
    }
    return first.data;
  }

  public boolean isEmpty() {
    return first == null;
  }
}
