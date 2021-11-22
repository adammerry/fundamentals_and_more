package dataStructures;

import java.util.NoSuchElementException;

/**
 * Implementation of a generic stack.
 * @param <E> the type of data contained in the stack
 */
public class Stack<E> {
  private StackNode top;

  private class StackNode {
    private final E data;
    private final StackNode next;

    private StackNode(E data, StackNode next) {
      this.data = data;
      this.next = next;
    }
  }

  /**
   * Gets and removes the data contained in the element at the top of the stack.
   * @return the data at the top of the stack
   */
  public E pop() {
    if (top == null) throw new NoSuchElementException("No element to pop");
    E topData = top.data;
    top = top.next;
    return topData;
  }

  /**
   * Adds an element containing the given data to the top of the stack.
   * @param data the data to add to the stack
   */
  public void push(E data) { top = new StackNode(data, top); }

  /**
   * Gets, but does not remove, the data contained in the element at the top of the stack.
   * @return the data at the top of the stack
   */
  public E peek() {
    if (top == null) throw new NoSuchElementException("No element to peek");
    return top.data;
  }

  /**
   * Determines whether the stack is empty.
   * @return true if the stack is empty, false otherwise
   */
  public boolean isEmpty() { return top == null; }
}
