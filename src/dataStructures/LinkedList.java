package dataStructures;

// Implementation of a singly-linked list.
public class LinkedList<E> {
  private ListNode<E> dummyHead;
  private ListNode<E> head;
  private ListNode<E> tail;
  private ListNode<E> dummyTail;
  private int length;

  public LinkedList() {
    dummyHead = new ListNode<>(null);
    head = null;
    tail = null;
    dummyTail = new ListNode<>(null);
    length = 0;
  }

  private class ListNode<F> {
    F data;
    ListNode<F> next;

    private ListNode(F data) {
      this.data = data;
      next = null;
    }

    private F getData() { return data; }

    private ListNode<F> getNext() { return next; }

    private void setNext(ListNode<F> next) { this.next = next; }
  }

  public void add(E data) {
    if (length == 0) {
      head = tail = new ListNode<>(data);
      dummyHead.setNext(head);
      tail.setNext(dummyTail);
    }
    else {
      tail.setNext(new ListNode<>(data));
      tail = tail.getNext();
      tail.setNext(dummyTail);
    }
    length++;
  }

  public E get(int idx) {
    ListNode<E> currNode = head;
    if (idx < 0 || idx > (length - 1)) {
      System.out.println("Invalid index.");
      return null;
    }
    for (int i = 0; i < idx; i++) {
      currNode = currNode.getNext();
    }
    return currNode.getData();
  }

  public E getFirst() {
    if (length == 0) {
      System.out.println("List empty.");
      return null;
    }
    return head.getData();
  }

  public E remove(E data) {
    if (length == 0) {
      System.out.println("Element not found in list.");
      return null;
    }
    ListNode<E> prevNode = dummyHead;
    ListNode<E> nextNode = head;
    while (nextNode != dummyTail) {
      if (nextNode.getData().equals(data)) {
        prevNode.setNext(nextNode.getNext());
        length--;
        return nextNode.getData();
      }
      prevNode = nextNode;
      nextNode = nextNode.getNext();
    }
    System.out.println("Element not found in list.");
    return null;
  }

  public E remove(int idx) {
    if (idx < 0 || idx > (length - 1)) {
      System.out.println("Invalid index.");
      return null;
    }
    ListNode<E> currNode = dummyHead;
    for (int i = 0; i < idx; i++) currNode = currNode.getNext();
    E retData = currNode.getNext().getData();
    currNode.setNext(currNode.getNext().getNext());
    length--;
    return retData;
  }

  public int size() { return length; }
}
