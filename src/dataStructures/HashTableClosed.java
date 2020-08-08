package dataStructures;

import java.util.Arrays;

// Implementation of a hash table where keys and values can be any type of object, the hash
// function is a simple modulo operation, and the collision resolution policy employs closed
// hashing with linear probing by steps.
public class HashTableClosed {
  private final double LOAD_FACTOR = 0.75;
  private final int INITIAL_CAPACITY = 10;
  private Record[] table;
  private int count;
  private int stepSize;

  public HashTableClosed() {
    table = new Record[INITIAL_CAPACITY];
    count = 0;
    stepSize = calculateStepSize();
  }

  private class Record {
    private Object key;
    private Object value;
    boolean isTombstone;

    private Record(Object key, Object value) {
      this.key = key;
      this.value = value;
    }

    private Object getKey() {
      return key;
    }

    private Object getValue() {
      return value;
    }

    private void setValue(Object value) {
      this.value = value;
    }

    private boolean isTombstone() {
      return isTombstone;
    }

    private void setTombstone(boolean isTombstone) {
      this.isTombstone = isTombstone;
    }
  }

  private int calculateStepSize() {
    for (int i = 2; i < table.length; i++) {
      // The step size must be relatively prime with respect to the length of the hash table, so
      // that the probe sequence can reach every slot in the table.
      if (gcd(i, table.length) == 1) {
        return i;
      }
    }
    return 1;
  }

  private int gcd(int a, int b) {
    if (a == 0 || b == 0) return 0;
    if (a == 1 || b == 1) return 1; // Not a required base case, but we can short-circuit here.
    if (a == b) return a;
    return (a > b) ? gcd(a - b, b) : gcd(a, b - a);
  }

  public Object put(Object key, Object value) {
    if (key == null || value == null) {
      System.out.println("Neither key nor value can be null.");
      return null;
    }
    // If table has become too full, expand table size and rehash.
    if (count == (int) (table.length * LOAD_FACTOR)) {
      rehash();
    }
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
      if (table[slot].isTombstone() && firstTombstoneIdx == -1) {
        firstTombstoneIdx = slot;
      }
      slot = (slot + stepSize) % table.length;
    }
    // If the given key did not already exist in the table, insert a new record into the hash
    // table in the appropriate slot.
    int insertSlot = (firstTombstoneIdx == -1) ? slot : firstTombstoneIdx;
    table[insertSlot] = new Record(key, value);
    count++;
    return null;
  }

  private void rehash() {
    Record[] tableCopy = Arrays.copyOf(table, table.length);
    table = new Record[table.length * 2];
    stepSize = calculateStepSize();
    count = 0;
    for (Record r : tableCopy) {
      if (r != null && (!r.isTombstone())) {
        put(r.getKey(), r.getValue());
      }
    }
  }

  public Object get(Object key) {
    if (key == null) {
      System.out.println("Key cannot be null.");
      return null;
    }
    int slot = Math.abs(key.hashCode()) % table.length;
    while (table[slot] != null) {
      if ((!table[slot].isTombstone()) && table[slot].getKey().equals(key)) {
        return table[slot].getValue();
      }
      slot = (slot + stepSize) % table.length;
    }
    return null;
  }

  // Replace the given record with a tombstone and return the record's value if it exists.
  // Otherwise, return null.
  public Object delete(Object key) {
    if (key == null) {
      System.out.println("Key cannot be null.");
      return null;
    }
    int slot, homeSlot;
    slot = homeSlot = Math.abs(key.hashCode()) % table.length;
    while (table[slot] != null) {
      if ((!table[slot].isTombstone()) && table[slot].getKey().equals(key)) {
        Object oldValue = table[slot].getValue();
        table[slot].setTombstone(true);
        count--;
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

  public boolean containsKey(Object key) {
    if (key == null) {
      System.out.println("Key cannot be null.");
      return false;
    }
    int slot, homeSlot;
    slot = homeSlot = Math.abs(key.hashCode()) % table.length;
    while (table[slot] != null) {
      if ((!table[slot].isTombstone()) && table[slot].getKey().equals(key)) {
        return true;
      }
      slot = (slot + stepSize) % table.length;
      // Check to see if we have searched through all slots in the table and returned to the home
      // slot. This corner case may come about if, through a series of insertions and deletions,
      // all slots that do not hold an existing record are still occupied by a tombstone.
      if (slot == homeSlot) break;
    }
    return false;
  }
}
