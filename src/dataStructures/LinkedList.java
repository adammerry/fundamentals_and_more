package dataStructures;

import java.util.Iterator;

// Implementation of a singly-linked list.
public class LinkedList<E> implements Iterable<E> {
  private ListNode<E> head;
  private ListNode<E> tail;
  private int size;

  public LinkedList() {
    size = 0;
  }

  private class ListNode<F> {
    F data;
    ListNode<F> next;

    private ListNode(F data) {
      this.data = data;
      next = null;
    }
  }

  public void add(E data) {
    ListNode<E> newNode = new ListNode<>(data);
    if (size == 0) head = tail = newNode;
    else {
      tail.next = newNode;
      tail = newNode;
    }
    size++;
  }

  public E get(int idx) {
    if (idx < 0 || idx > (size - 1)) {
      System.out.println("Invalid index.");
      return null;
    }
    ListNode<E> currNode = head;
    for (int i = 0; i < idx; i++) currNode = currNode.next;
    return currNode.data;
  }

  public E getFirst() {
    if (size == 0) {
      System.out.println("List empty.");
      return null;
    }
    return head.data;
  }

  public E remove(E data) {
    if (size == 0) {
      System.out.println("Element not found in list.");
      return null;
    }
    if (head.data.equals(data)) {
      E retData = head.data;
      head = head.next;
      size--;
      return retData;
    }
    ListNode<E> prevNode = head;
    ListNode<E> nextNode = head.next;
    while (nextNode != null) {
      if (nextNode.data.equals(data)) {
        prevNode.next = nextNode.next;
        size--;
        return nextNode.data;
      }
      prevNode = nextNode;
      nextNode = nextNode.next;
    }
    System.out.println("Element not found in list.");
    return null;
  }

  public E remove(int idx) {
    if (idx < 0 || idx > (size - 1)) {
      System.out.println("Invalid index.");
      return null;
    }
    if (idx == 0) {
      E retData = head.data;
      head = head.next;
      size--;
      return retData;
    }
    ListNode<E> prevNode = head;
    ListNode<E> nextNode = head.next;
    for (int i = 0; i < idx - 1; i++) {
      prevNode = nextNode;
      nextNode = nextNode.next;
    }
    E retData = nextNode.data;
    prevNode.next = nextNode.next;
    size--;
    return retData;
  }
  
  public int size() { return size; }

  public boolean isEmpty() { return size == 0; }

  public Iterator<E> iterator() { return new LinkedListIterator<>(this); }
  
  public class LinkedListIterator<G> implements Iterator<G> {
    LinkedList<G> list;
    ListNode<G> nextNode;
    
    LinkedListIterator(LinkedList<G> list) {
      this.list = list;
      nextNode = (ListNode<G>) list.head;
    }
    
    @Override
    public boolean hasNext() { return (nextNode != null); }
    
    @Override
    public G next() {
      G data = nextNode.data;
      nextNode = nextNode.next;
      return data;
    }
  }
}
