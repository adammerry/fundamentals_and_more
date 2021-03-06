package dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Implementation of a generic ArrayList - an array-backed dynamically-resizing list with
// amortized O(1) insertion complexity.
public class ArrayList<E> implements Iterable<E> {
  private static final int MIN_SIZE = 16;
  private E[] elements;
  private int size = 0;

  public ArrayList() { elements = (E[]) new Object[MIN_SIZE]; }

  public ArrayList(int startSize) { elements = (E[]) new Object[Math.max(startSize, MIN_SIZE)]; }

  public void add(E elem) {
    elements[size++] = elem;
    if (size > (elements.length * 0.75)) resize(2);
  }

  public E get(int idx) {
    if (idx < 0 || idx > size - 1) throw new IndexOutOfBoundsException("Invalid index");
    return elements[idx];
  }

  public E getFirst() {
    if (size == 0) throw new NoSuchElementException("List empty");
    return elements[0];
  }

  public E remove(E data) {
    int elemIdx = 0;
    while (elemIdx < size && !elements[elemIdx].equals(data)) elemIdx++;
    if (elemIdx == size) throw new NoSuchElementException("Element not found in list");
    return remove(elemIdx);
  }

  public E remove(int idx) {
    if (idx < 0 || idx > size - 1) throw new IndexOutOfBoundsException("Invalid index");
    E element = elements[idx];
    for (int j = idx + 1; j < size; j++) elements[j - 1] = elements[j];
    size--;
    if ((elements.length > MIN_SIZE) && (size < (elements.length * 0.25))) resize(0.5);
    return element;
  }

  private void resize(double factor) {
    int newArrayLen = (int) (elements.length * factor);
    if (newArrayLen < MIN_SIZE) newArrayLen = MIN_SIZE;
    if (newArrayLen == elements.length) return; // Avoid unnecessarily copying elements.
    E[] newArray = (E[]) new Object[newArrayLen];
    System.arraycopy(elements, 0, newArray, 0, size);
    elements = newArray;
  }

  public int size() { return size; }

  @Override
  public Iterator<E> iterator() { return new ArrayListIterator<>(elements); }

  private class ArrayListIterator<F> implements Iterator<F> {
    int nextIdx = 0;
    private F[] elements;

    ArrayListIterator(F[] arr) { elements = arr; }

    @Override
    public boolean hasNext() { return nextIdx < size; }

    @Override
    public F next() { return elements[nextIdx++]; }
  }
}