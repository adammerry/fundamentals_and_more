package dataStructures;

import java.util.ArrayList;
import java.util.List;

// Implementation of a hash table where keys and values can be any type of object, the hash
// function is a simple modulo operation, and the collision resolution policy employs open
// hashing (storing records in a list).
public class HashTableOpen {
  private static final double LOAD_FACTOR_MAX = 0.75, LOAD_FACTOR_MIN = 0.25;
  private static final int INITIAL_CAPACITY = 10;
  private ArrayList<ArrayList<Record>> table;
  private int count;

  public HashTableOpen() {
    table = new ArrayList<>(INITIAL_CAPACITY);
    for (int i = 0; i < INITIAL_CAPACITY; i++) table.add(new ArrayList<>());
    count = 0;
  }

  private class Record {
    private Object key, value;

    private Record(Object key, Object value) {
      this.key = key;
      this.value = value;
    }

    private Object getKey() { return key; }

    private Object getValue() { return value; }

    private void setValue(Object value) { this.value = value; }
  }

  // Return the previous value of the specified key in this hash table, or null if the key did
  // not exist.
  public Object put(Object key, Object value) {
    if (key == null || value == null)
      throw new IllegalArgumentException("Neither key nor value can be null");
    // If table has become too full, expand table size and rehash.
    if (count == (int)(table.size() * LOAD_FACTOR_MAX)) rehash(2);
    int slot = Math.abs(key.hashCode()) % table.size();
    for (Record r : table.get(slot)) {
      if (r.getKey().equals(key)) {
        Object oldValue = r.getValue();
        r.setValue(value);
        return oldValue;
      }
    }
    table.get(slot).add(new Record(key, value));
    count++;
    return null;
  }

  private void rehash(double resizeFactor) {
    ArrayList<ArrayList<Record>> oldTable = table;
    int newSize = Math.max(INITIAL_CAPACITY, (int)(table.size() * resizeFactor));
    table = new ArrayList<>(newSize);
    for (int i = 0; i < newSize; i++) table.add(new ArrayList<>());
    for (List<Record> l : oldTable) for (Record r : l) put(r.getKey(), r.getValue());
  }

  public Object get(Object key) {
    if (key == null) throw new IllegalArgumentException("Key cannot be null");
    for (Record r : table.get(Math.abs(key.hashCode()) % table.size()))
      if (r.getKey().equals(key)) return r.getValue();
    return null;
  }

  // Return the value of the record with the deleted key, or null if no such record exists.
  public Object delete(Object key) {
    if (key == null) throw new IllegalArgumentException("Key cannot be null");
    ArrayList<Record> l = table.get(Math.abs(key.hashCode()) % table.size());
    int deleteIdx = -1;
    for (int i = 0; i < l.size() && deleteIdx == -1; i++)
      if (l.get(i).getKey().equals(key)) deleteIdx = i;
    if (deleteIdx == -1) return null;
    Object deleteValue = l.get(deleteIdx).getValue();
    l.remove(deleteIdx);
    count--;
    // If table has become too empty, reduce table size and rehash.
    if (count == (int)(table.size() * LOAD_FACTOR_MIN)) rehash(0.5);
    return deleteValue;
  }

  public boolean containsKey(Object key) {
    if (key == null) throw new IllegalArgumentException("Key cannot be null");
    for (Record r : table.get(Math.abs(key.hashCode()) % table.size()))
      if (r.getKey().equals(key)) return true;
    return false;
  }
}