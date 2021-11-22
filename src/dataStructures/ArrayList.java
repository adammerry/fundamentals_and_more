package dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of a generic ArrayList - an array-backed dynamically-resizing list with
 * amortized O(1) insertion complexity.
 * @param <E> the type of data contained in the ArrayList
 */
public class ArrayList<E> implements Iterable<E> {
  private static final int MIN_CAPACITY = 16;
  private E[] elements;
  private int size = 0;

  /**
   * Constructor for an ArrayList initialized with MIN_CAPACITY.
   */
  public ArrayList() { elements = (E[]) new Object[MIN_CAPACITY]; }

  /**
   * Constructor for an ArrayList initialized with either the given capacity, or MIN_CAPACITY,
   * whichever is greater.
   * @param capacity the initial capacity of the ArrayList
   */
  public ArrayList(int capacity) { elements = (E[]) new Object[Math.max(capacity, MIN_CAPACITY)]; }

  /**
   * Adds an element to the end of the ArrayList.
   * @param elem the element to add
   */
  public void add(E elem) {
    elements[size++] = elem;
    if (size > (elements.length * 0.75)) resize(2);
  }

  /**
   * Gets the element at the given index, if the index is valid.
   * @param idx the index of the element to get
   * @return the element at the given index
   */
  public E get(int idx) {
    if (idx < 0 || idx > size - 1) throw new IndexOutOfBoundsException("Invalid index");
    return elements[idx];
  }

  /**
   * Gets the first element of the ArrayList, if one exists.
   * @return the first element
   */
  public E getFirst() {
    if (size == 0) throw new NoSuchElementException("List empty");
    return elements[0];
  }

  /**
   * Removes the given element from the ArrayList, if it exists.
   * @param element the element to remove
   * @return the removed element
   */
  public E remove(E element) {
    int elemIdx = 0;
    while (elemIdx < size && !elements[elemIdx].equals(element)) elemIdx++;
    if (elemIdx == size) throw new NoSuchElementException("Element not found in list");
    return remove(elemIdx);
  }

  /**
   * Removes the element at the given index from the ArrayList, if the index is valid.
   * @param idx the index of the element to remove
   * @return the removed element
   */
  public E remove(int idx) {
    if (idx < 0 || idx > size - 1) throw new IndexOutOfBoundsException("Invalid index");
    E element = elements[idx];
    for (int j = idx + 1; j < size; j++) elements[j - 1] = elements[j];
    size--;
    if ((elements.length > MIN_CAPACITY) && (size < (elements.length * 0.25))) resize(0.5);
    return element;
  }

  /**
   * Modifies the capacity of the ArrayList by the given factor.
   * @param factor the factor to which the capacity of the ArrayList should be modified
   */
  private void resize(double factor) {
    int newArrayLen = (int) (elements.length * factor);
    if (newArrayLen < MIN_CAPACITY) newArrayLen = MIN_CAPACITY;
    if (newArrayLen == elements.length) return; // Avoid unnecessarily copying elements.
    E[] newArray = (E[]) new Object[newArrayLen];
    System.arraycopy(elements, 0, newArray, 0, size);
    elements = newArray;
  }

  /**
   * Gets the current size of the ArrayList.
   * @return the number of elements contained in the ArrayList
   */
  public int size() { return size; }

  /**
   * Gets an iterator for the elements of the ArrayList.
   * @return an iterator for the ArrayList
   */
  @Override
  public Iterator<E> iterator() { return new ArrayListIterator<>(elements); }

  /**
   * An iterator for the elements of the ArrayList.
   * @param <F> the type of data contained in the ArrayList
   */
  private class ArrayListIterator<F> implements Iterator<F> {
    private int nextIdx = 0;
    private final F[] elements;

    private ArrayListIterator(F[] arr) { elements = arr; }

    /**
     * Determines whether the iterator has more elements to iterate over.
     * @return true if there are more elements to iterate over, false otherwise
     */
    @Override
    public boolean hasNext() { return nextIdx < size; }

    /**
     * Gets the next element in the iteration
     * @return the next element
     */
    @Override
    public F next() { return elements[nextIdx++]; }
  }
}