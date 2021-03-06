package dataStructures;

import java.util.NoSuchElementException;

// Implementation of a generic stack.
public class Stack<E> {
  private StackNode<E> top;

  private static class StackNode<F> {
    private F data;
    private StackNode<F> next;

    private StackNode(F data, StackNode<F> next) {
      this.data = data;
      this.next = next;
    }
  }

  public E pop() {
    if (top == null) throw new NoSuchElementException("No element to pop");
    E topData = top.data;
    top = top.next;
    return topData;
  }

  public void push(E data) { top = new StackNode<>(data, top); }

  public E peek() {
    if (top == null) throw new NoSuchElementException("No element to peek");
    return top.data;
  }

  public boolean isEmpty() { return top == null; }
}
