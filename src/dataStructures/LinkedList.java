package dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a singly-linked list.
 * @param <E> the type of data contained in the list
 */
public class LinkedList<E> implements Iterable<E> {
  private ListNode head, tail;
  private int size;

  /**
   * Constructor for LinkedList.
   */
  public LinkedList() { size = 0; }

  private class ListNode {
    E data;
    ListNode next;

    private ListNode(E data) {
      this.data = data;
      next = null;
    }
  }

  /**
   * Adds an element containing the given data to the end of the list.
   * @param data the data to add to the list
   */
  public void add(E data) {
    ListNode newNode = new ListNode(data);
    if (size == 0) head = tail = newNode;
    else {
      tail.next = newNode;
      tail = newNode;
    }
    size++;
  }

  /**
   * Gets the data at the given index in the list.
   * @param idx the index of the data to get
   * @return the data at the given index
   */
  public E get(int idx) {
    if (idx < 0 || idx > (size - 1)) throw new IndexOutOfBoundsException("Invalid index");
    ListNode currNode = head;
    for (int i = 0; i < idx; i++) currNode = currNode.next;
    return currNode.data;
  }

  /**
   * Gets the data at the head of the list.
   * @return the data at the head of the list
   */
  public E getFirst() {
    if (size == 0) throw new NoSuchElementException("List empty");
    return head.data;
  }

  /**
   * Removes the given data from the list.
   * @param data the data to remove
   * @return true if data was removed from the list, false otherwise
   */
  public boolean remove(E data) {
    if (size == 0) return false;
    if (head.data.equals(data)) {
      head = head.next;
      size--;
      return true;
    }
    ListNode prevNode = head, nextNode = head.next;
    while (nextNode != null) {
      if (nextNode.data.equals(data)) {
        prevNode.next = nextNode.next;
        size--;
        return true;
      }
      nextNode = (prevNode = nextNode).next;
    }
    return false;
  }

  /**
   * Removes the data at the given index.
   * @param idx the index of the data to remove
   * @return the data removed from the list
   */
  public E remove(int idx) {
    if (idx < 0 || idx > (size - 1)) throw new IndexOutOfBoundsException("Invalid index");
    if (idx == 0) {
      E retData = head.data;
      head = head.next;
      size--;
      return retData;
    }
    ListNode prevNode = head, nextNode = head.next;
    for (int i = 0; i < idx - 1; i++) nextNode = (prevNode = nextNode).next;
    E retData = nextNode.data;
    prevNode.next = nextNode.next;
    size--;
    return retData;
  }

  /**
   * Gets the size of the list.
   * @return the number of elements in the list
   */
  public int size() { return size; }

  /**
   * Determines whether the list is empty
   * @return true if the list contains no elements, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Provides an iterator over the elements of the list.
   * @return an iterator over the elements of the list
   */
  public Iterator<E> iterator() { return new LinkedListIterator(this); }

  /**
   * Implements an iterator over the elements of the list.
   */
  public class LinkedListIterator implements Iterator<E> {
    private LinkedList<E> list;
    private ListNode nextNode;

    /**
     * Constructor for LinkedListIterator.
     * @param list the list for which the iterator will be constructed
     */
    LinkedListIterator(LinkedList<E> list) {
      this.list = list;
      nextNode = list.head;
    }

    /**
     * Determines if the iterator has more elements to iterate over.
     * @return true if there are more elements, false otherwise
     */
    @Override
    public boolean hasNext() { return nextNode != null; }

    /**
     * Gets the next element in the iteration.
     * @return the next element
     */
    @Override
    public E next() {
      E data = nextNode.data;
      nextNode = nextNode.next;
      return data;
    }
  }
}