package dataStructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Implementation of a hash table where keys and values can be any type of object, the hash
// function is a simple modulo operation, and the collision resolution policy employs open
// hashing (storing records externally in a list).
public class HashTableOpen {
  private final double LOAD_FACTOR = 0.75;
  private final int INITIAL_CAPACITY = 10;
  private ArrayList<List<Record>> table;
  private int count;

  public HashTableOpen() {
    table = new ArrayList<>(INITIAL_CAPACITY);
    for (int i = 0; i < INITIAL_CAPACITY; i++) {
      table.add(new LinkedList<>());
    }
    count = 0;
  }

  private class Record {
    private Object key;
    private Object value;

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
  }

  // Return the previous value of the specified key in this hash table, or null if the key did
  // not exist.
  public Object put(Object key, Object value) {
    if (key == null || value == null) {
      System.out.println("Neither key nor value can be null.");
      return null;
    }
    // If table has become too full, expand table size and rehash.
    if (count == (int) (table.size() * LOAD_FACTOR)) {
      rehash();
    }
    int slot = Math.abs(key.hashCode()) % table.size();
    Record oldRecord = null;
    Object oldValue = null;
    for (Record r : table.get(slot)) {
      if (r.getKey().equals(key)) {
        oldRecord = r;
        oldValue = r.getValue();
        break;
      }
    }
    if (oldRecord == null) {
      table.get(slot).add(new Record(key, value));
      count++;
    }
    else {
      oldRecord.setValue(value);
    }
    return oldValue;
  }

  private void rehash() {
    List<Record> tableElements = new ArrayList<>();
    for (List<Record> list : table) {
      tableElements.addAll(list);
    }
    int newSize = table.size() * 2;
    table = new ArrayList<>(newSize);
    for (int i = 0; i < newSize; i++) {
      table.add(new LinkedList<>());
    }
    for (Record r : tableElements) {
      put(r.getKey(), r.getValue());
    }
  }

  public Object get(Object key) {
    if (key == null) {
      System.out.println("Key cannot be null.");
      return null;
    }
    int slot = Math.abs(key.hashCode()) % table.size();
    List<Record> list = table.get(slot);
    for (Record r : list) {
      if (r.getKey().equals(key)) return r.getValue();
    }
    return null;
  }

  // Return the value of the record with the deleted key, or null if no such record exists.
  public Object delete(Object key) {
    if (key == null) {
      System.out.println("Key cannot be null.");
      return null;
    }
    int slot = Math.abs(key.hashCode()) % table.size();
    List<Record> list = table.get(slot);
    Record deleteRecord = null;
    for (Record r : list) {
      if (r.getKey().equals(key)) {
        deleteRecord = r;
        break;
      }
    }
    if (deleteRecord == null) return null;
    list.remove(deleteRecord);
    count--;
    return deleteRecord.getValue();
  }

  public boolean containsKey(Object key) {
    if (key == null) {
      System.out.println("Key cannot be null.");
      return false;
    }
    int slot = Math.abs(key.hashCode()) % table.size();
    List<Record> list = table.get(slot);
    for (Record r : list) {
      if (r.getKey().equals(key)) return true;
    }
    return false;
  }
}