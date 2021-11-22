package dataStructures;

import java.util.Arrays;

/**
 * Implementation of a hash table where keys and values can be any type of object, the hash
 * function is a simple modulo operation, and the collision resolution policy employs closed
 * hashing with linear probing by steps.
 */
public class HashTableClosed {
  private static final double LOAD_FACTOR_MAX = 0.75, LOAD_FACTOR_MIN = 0.25;
  private static final int INITIAL_CAPACITY = 10;
  private Record[] table = new Record[INITIAL_CAPACITY];
  private int count = 0, stepSize = calculateStepSize();

  private class Record {
    private final Object key;
    private Object value;
    boolean isTombstone;

    private Record(Object key, Object value) {
      this.key = key;
      this.value = value;
    }

    private Object getKey() { return key; }

    private Object getValue() { return value; }

    private void setValue(Object value) { this.value = value; }

    private boolean isTombstone() { return isTombstone; }

    private void setTombstone(boolean isTombstone) { this.isTombstone = isTombstone; }
  }

  /**
   * Calculates the step size for linear probing by steps. The step size must be relatively prime
   * with respect to the length of the hash table, so that the probe sequence can reach every slot
   * in the table.
   * @return the step size
   */
  private int calculateStepSize() {
    for (int i = 2; i < table.length; i++) if (gcd(i, table.length) == 1) return i;
    return 1;
  }

  /**
   * Calculates the greatest common denominator of the two given numbers.
   * @param a a number
   * @param b a number
   * @return the greatest common denominator
   */
  private int gcd(int a, int b) {
    if (a == 0 || b == 0) return 0;
    if (a == 1 || b == 1) return 1; // Not a required base case, but we can short-circuit here.
    if (a == b) return a;
    return (a > b) ? gcd(a - b, b) : gcd(a, b - a);
  }

  /**
   * Inserts a new key/value pair into the hash table.
   * @param key the key to insert
   * @param value the value to insert
   * @return the value previously associated with the given key, or null if the key was not
   * present in the hash table
   */
  public Object put(Object key, Object value) {
    if (key == null || value == null)
      throw new IllegalArgumentException("Neither key nor value can be null");
    // If table has become too full, expand table size and rehash.
    if (count == (int) (table.length * LOAD_FACTOR_MAX)) rehash(2);
    int slot = Math.abs(key.hashCode()) % table.length;
    int firstTombstoneIdx = -1;
    // Iterate until an empty slot is found to ensure that no duplicates will be inserted.
    while (table[slot] != null) {
      if (table[slot].getKey().equals(key)) { // Record with given key was in table at some point.
        if (table[slot].isTombstone()) { // Record with given key had previously been deleted.
          table[slot].setValue(value);
          table[slot].setTombstone(false);
          count++;
          return null;
        }
        else { // Record with given key exists in table.
          Object oldValue = table[slot].getValue();
          table[slot].setValue(value);
          return oldValue;
        }
      }
      // Keep track of first tombstone occurrence.
      if (table[slot].isTombstone() && firstTombstoneIdx == -1) firstTombstoneIdx = slot;
      slot = (slot + stepSize) % table.length;
    }
    // If the given key did not already exist in the table, insert a new record into the hash
    // table in the appropriate slot.
    int insertSlot = (firstTombstoneIdx == -1) ? slot : firstTombstoneIdx;
    table[insertSlot] = new Record(key, value);
    count++;
    return null;
  }

  /**
   * Resizes the capacity of the hash table by the given factor.
   * @param resizeFactor the factor by which the capacity of the hash table should be resized
   */
  private void rehash(double resizeFactor) {
    Record[] tableCopy = Arrays.copyOf(table, table.length);
    table = new Record[Math.max(INITIAL_CAPACITY, (int)(table.length * resizeFactor))];
    stepSize = calculateStepSize();
    count = 0;
    for (Record r : tableCopy) if (!(r == null || r.isTombstone())) put(r.getKey(), r.getValue());
  }

  /**
   * Gets the value in the hash table associated with the given key.
   * @param key the key
   * @return the value associated with the given key, or null if the key is not present in the hash
   * table
   */
  public Object get(Object key) {
    if (key == null) throw new IllegalArgumentException("Key cannot be null");
    int slot = Math.abs(key.hashCode()) % table.length;
    while (table[slot] != null) {
      if ((!table[slot].isTombstone()) && table[slot].getKey().equals(key))
        return table[slot].getValue();
      slot = (slot + stepSize) % table.length;
    }
    return null;
  }

  /**
   * Deletes the record (key and value) associated with the given key from the hash table.
   * @param key the key
   * @return the value previously associated with the given key, or null if the key is not present
   * in the hash table
   */
  public Object delete(Object key) {
    if (key == null) throw new IllegalArgumentException("Key cannot be null");
    int slot, homeSlot;
    slot = homeSlot = Math.abs(key.hashCode()) % table.length;
    while (table[slot] != null) {
      if ((!table[slot].isTombstone()) && table[slot].getKey().equals(key)) {
        Object oldValue = table[slot].getValue();
        table[slot].setTombstone(true);
        count--;
        if (count == (int) (table.length * LOAD_FACTOR_MIN)) rehash(0.5);
        return oldValue;
      }
      slot = (slot + stepSize) % table.length;
      // Check to see if we have searched through all slots in the table and returned to the home
      // slot. This corner case may come about if, through a series of insertions and deletions,
      // all slots that do not hold an existing record are still occupied by a tombstone.
      if (slot == homeSlot) break;
    }
    return null;
  }

  /**
   * Determines whether the hash table contains the given key.
   * @param key the key
   * @return true if the key is present in the hash table, false otherwise
   */
  public boolean containsKey(Object key) {
    if (key == null) throw new IllegalArgumentException("Key cannot be null");
    int slot, homeSlot;
    slot = homeSlot = Math.abs(key.hashCode()) % table.length;
    while (table[slot] != null) {
      if ((!table[slot].isTombstone()) && table[slot].getKey().equals(key)) return true;
      slot = (slot + stepSize) % table.length;
      // Check to see if we have searched through all slots in the table and returned to the home
      // slot. This corner case may come about if, through a series of insertions and deletions,
      // all slots that do not hold an existing record are still occupied by a tombstone.
      if (slot == homeSlot) break;
    }
    return false;
  }
}
