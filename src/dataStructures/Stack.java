package dataStructures;

// Implementation of a generic stack.
public class Stack<E> {
  private StackNode<E> top;

  private static class StackNode<F> {
    private F data;
    private StackNode<F> next;

    private StackNode(F data) {
      this.data = data;
    }
  }

  public E pop() {
    if (top == null) {
      System.out.println("No element to pop.");
      return null;
    }
    E topData = top.data;
    top = top.next;
    return topData;
  }

  public void push(E data) {
    StackNode<E> newNode = new StackNode<E>(data);
    newNode.next = top;
    top = newNode;
  }

  public E peek() {
    if (top == null) {
      System.out.println("No element to peek.");
      return null;
    }
    return top.data;
  }

  public boolean isEmpty() {
    return top == null;
  }
}
