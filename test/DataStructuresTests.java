import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dataStructures.ArrayList;
import dataStructures.BinarySearchTree;
import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBad;
import dataStructures.GraphAdjacencyListBetter;
import dataStructures.GraphAdjacencyMatrix;
import dataStructures.HashTableOpen;
import dataStructures.HashTableClosed;
import dataStructures.LinkedList;
import dataStructures.MaxHeapCharacterRecursive;
import dataStructures.MinHeapIntegerIterative;
import dataStructures.PriorityQueueArray;
import dataStructures.PriorityQueueHeap;
import dataStructures.PriorityQueueList;
import dataStructures.Queue;
import dataStructures.RedBlackTree;
import dataStructures.Stack;
import dataStructures.Trie;

public class DataStructuresTests {

  @Test
  public void testBinaryTree() {
    BinaryTree<Integer> b = new BinaryTree<>();
    b.insert(0);
    b.insert(1);
    b.insert(2);
    b.insert(3);
    b.insert(4);
    b.insert(5);
    b.insert(6);
    b.insert(7);
    b.insert(8);
    assertEquals(new Integer(5), b.bfs(5).getData());
    assertEquals(null, b.bfs(27));
    assertEquals(new Integer(2), b.dfs(2).getData());
    assertEquals(null, b.dfs(41));
    b.delete(3);
    assertEquals(null, b.bfs(3));
    b.delete(7);
    assertEquals(null, b.bfs(7));
    b.delete(8);
    assertEquals(null, b.bfs(8));
    assertEquals(null, b.bfs(null));
    assertEquals(null, b.dfs(null));
    b.insert(null);
    b.delete(null);
  }

  @Test
  public void testBinarySearchTree() {
    BinarySearchTree<Integer> b = new BinarySearchTree<>();
    b.delete(100);
    b.insert(4);
    b.insert(7);
    b.insert(2);
    b.insert(8);
    b.insert(5);
    b.insert(0);
    b.insert(6);
    b.insert(1);
    b.insert(3);
    assertEquals(new Integer(6), b.search(6).getData());
    b.delete(3);
    assertEquals(null, b.search(3));
    b.delete(4);
    assertEquals(null, b.search(4));
    b.delete(0);
    assertEquals(null, b.search(0));
    b.delete(7);
    assertEquals(null, b.search(7));
    b.delete(8);
    assertEquals(null, b.search(8));
    b.delete(6);
    assertEquals(null, b.search(6));
    b.delete(9);
    b.insert(null);
    b.delete(null);
    b.search(null);
  }

  @Test
  public void testMinHeapIntegerIterative() {
    MinHeapIntegerIterative m = new MinHeapIntegerIterative();

    m.insert(10);
    m.insert(12);
    m.insert(27);
    m.insert(14);
    m.insert(55);
    m.insert(2);
    m.insert(11);
    m.insert(34);
    m.insert(88);
    assertEquals(new Integer(2), m.getValAtIdx(0));
    assertEquals(new Integer(12), m.getValAtIdx(1));
    assertEquals(new Integer(10), m.getValAtIdx(2));
    assertEquals(new Integer(14), m.getValAtIdx(3));
    assertEquals(new Integer(55), m.getValAtIdx(4));
    assertEquals(new Integer(27), m.getValAtIdx(5));
    assertEquals(new Integer(11), m.getValAtIdx(6));
    assertEquals(new Integer(34), m.getValAtIdx(7));
    assertEquals(new Integer(88), m.getValAtIdx(8));

    m.delete(1);
    assertEquals(new Integer(2), m.getValAtIdx(0));
    assertEquals(new Integer(14), m.getValAtIdx(1));
    assertEquals(new Integer(10), m.getValAtIdx(2));
    assertEquals(new Integer(34), m.getValAtIdx(3));
    assertEquals(new Integer(55), m.getValAtIdx(4));
    assertEquals(new Integer(27), m.getValAtIdx(5));
    assertEquals(new Integer(11), m.getValAtIdx(6));
    assertEquals(new Integer(88), m.getValAtIdx(7));

    m.delete(5);
    assertEquals(new Integer(2), m.getValAtIdx(0));
    assertEquals(new Integer(14), m.getValAtIdx(1));
    assertEquals(new Integer(10), m.getValAtIdx(2));
    assertEquals(new Integer(34), m.getValAtIdx(3));
    assertEquals(new Integer(55), m.getValAtIdx(4));
    assertEquals(new Integer(88), m.getValAtIdx(5));
    assertEquals(new Integer(11), m.getValAtIdx(6));

    m.delete(-2);
    m.delete(101);

    m.changeKey(0, 78);
    assertEquals(new Integer(10), m.getValAtIdx(0));
    assertEquals(new Integer(14), m.getValAtIdx(1));
    assertEquals(new Integer(11), m.getValAtIdx(2));
    assertEquals(new Integer(34), m.getValAtIdx(3));
    assertEquals(new Integer(55), m.getValAtIdx(4));
    assertEquals(new Integer(88), m.getValAtIdx(5));
    assertEquals(new Integer(78), m.getValAtIdx(6));

    m.changeKey(6, 4);
    assertEquals(new Integer(4), m.getValAtIdx(0));
    assertEquals(new Integer(14), m.getValAtIdx(1));
    assertEquals(new Integer(10), m.getValAtIdx(2));
    assertEquals(new Integer(34), m.getValAtIdx(3));
    assertEquals(new Integer(55), m.getValAtIdx(4));
    assertEquals(new Integer(88), m.getValAtIdx(5));
    assertEquals(new Integer(11), m.getValAtIdx(6));

    m.changeKey(2, 45);
    assertEquals(new Integer(4), m.getValAtIdx(0));
    assertEquals(new Integer(14), m.getValAtIdx(1));
    assertEquals(new Integer(11), m.getValAtIdx(2));
    assertEquals(new Integer(34), m.getValAtIdx(3));
    assertEquals(new Integer(55), m.getValAtIdx(4));
    assertEquals(new Integer(88), m.getValAtIdx(5));
    assertEquals(new Integer(45), m.getValAtIdx(6));

    assertEquals(new Integer(4), m.getMin());

    Integer[] sortedExpected = {4, 11, 14, 34, 45, 55, 88};
    Integer[] sortedActual = new Integer[7];
    for (int i = 0; i < 7; i++) {
      sortedActual[i] = m.extractMin();
    }
    assertArrayEquals(sortedExpected, sortedActual);

    // Test building a heap from a given array.
    m = new MinHeapIntegerIterative(new Integer[] {67, 82, 3, 15, 105, 9, 44, 37});
    sortedExpected = new Integer[] {3, 9, 15, 37, 44, 67, 82, 105};
    sortedActual = new Integer[8];
    for (int i = 0; i < 8; i++) {
      sortedActual[i] = m.extractMin();
    }
    assertArrayEquals(sortedExpected, sortedActual);
  }

  @Test
  public void testMaxHeapCharacterRecursive() {
    MaxHeapCharacterRecursive m = new MaxHeapCharacterRecursive();

    m.insert('a');
    m.insert('b');
    m.insert('z');
    m.insert('W');
    m.insert('R');
    m.insert('+');
    m.insert('9');
    m.insert('b');
    m.insert('}');
    assertEquals(new Character('}'), m.getValAtIdx(0));
    assertEquals(new Character('z'), m.getValAtIdx(1));
    assertEquals(new Character('b'), m.getValAtIdx(2));
    assertEquals(new Character('b'), m.getValAtIdx(3));
    assertEquals(new Character('R'), m.getValAtIdx(4));
    assertEquals(new Character('+'), m.getValAtIdx(5));
    assertEquals(new Character('9'), m.getValAtIdx(6));
    assertEquals(new Character('W'), m.getValAtIdx(7));
    assertEquals(new Character('a'), m.getValAtIdx(8));

    m.delete(1);
    assertEquals(new Character('}'), m.getValAtIdx(0));
    assertEquals(new Character('b'), m.getValAtIdx(1));
    assertEquals(new Character('b'), m.getValAtIdx(2));
    assertEquals(new Character('a'), m.getValAtIdx(3));
    assertEquals(new Character('R'), m.getValAtIdx(4));
    assertEquals(new Character('+'), m.getValAtIdx(5));
    assertEquals(new Character('9'), m.getValAtIdx(6));
    assertEquals(new Character('W'), m.getValAtIdx(7));

    m.delete(5);
    assertEquals(new Character('}'), m.getValAtIdx(0));
    assertEquals(new Character('b'), m.getValAtIdx(1));
    assertEquals(new Character('b'), m.getValAtIdx(2));
    assertEquals(new Character('a'), m.getValAtIdx(3));
    assertEquals(new Character('R'), m.getValAtIdx(4));
    assertEquals(new Character('W'), m.getValAtIdx(5));
    assertEquals(new Character('9'), m.getValAtIdx(6));

    m.delete(-1);
    m.delete(32);

    m.changeKey(0, ' ');
    assertEquals(new Character('b'), m.getValAtIdx(0));
    assertEquals(new Character('a'), m.getValAtIdx(1));
    assertEquals(new Character('b'), m.getValAtIdx(2));
    assertEquals(new Character(' '), m.getValAtIdx(3));
    assertEquals(new Character('R'), m.getValAtIdx(4));
    assertEquals(new Character('W'), m.getValAtIdx(5));
    assertEquals(new Character('9'), m.getValAtIdx(6));

    m.changeKey(6, 't');
    assertEquals(new Character('t'), m.getValAtIdx(0));
    assertEquals(new Character('a'), m.getValAtIdx(1));
    assertEquals(new Character('b'), m.getValAtIdx(2));
    assertEquals(new Character(' '), m.getValAtIdx(3));
    assertEquals(new Character('R'), m.getValAtIdx(4));
    assertEquals(new Character('W'), m.getValAtIdx(5));
    assertEquals(new Character('b'), m.getValAtIdx(6));

    m.changeKey(2, 'Z');
    assertEquals(new Character('t'), m.getValAtIdx(0));
    assertEquals(new Character('a'), m.getValAtIdx(1));
    assertEquals(new Character('b'), m.getValAtIdx(2));
    assertEquals(new Character(' '), m.getValAtIdx(3));
    assertEquals(new Character('R'), m.getValAtIdx(4));
    assertEquals(new Character('W'), m.getValAtIdx(5));
    assertEquals(new Character('Z'), m.getValAtIdx(6));

    assertEquals(new Character('t'), m.getMax());

    Character[] sortedExpected = {'t', 'b', 'a', 'Z', 'W', 'R', ' '};
    Character[] sortedActual = new Character[7];
    for (int i = 0; i < 7; i++) {
      sortedActual[i] = m.extractMax();
    }
    assertArrayEquals(sortedExpected, sortedActual);

    // Test building a heap from a given array.
    m = new MaxHeapCharacterRecursive(new Character[] {'C', 'k', '+', '9', 'u', '6', 'A', '3'});
    sortedExpected = new Character[] {'u', 'k', 'C', 'A', '9', '6', '3', '+'};
    sortedActual = new Character[8];
    for (int i = 0; i < 8; i++) {
      sortedActual[i] = m.extractMax();
    }
    assertArrayEquals(sortedExpected, sortedActual);
  }

  @Test
  public void testTrie() {
    Trie t = new Trie();
    assertEquals(false, t.search("a"));
    t.insert("hello");
    assertEquals(true, t.search("hello"));
    assertEquals(false, t.search("he"));
    t.insert("word");
    assertEquals(true, t.search("word"));
    t.insert("tiger");
    assertEquals(true, t.search("tiger"));
    t.insert("he");
    assertEquals(true, t.search("he"));
    assertEquals(false, t.search("hellop"));
    assertEquals(false, t.search("lion"));
    t.delete("tiger");
    assertEquals(false, t.search("tiger"));
    t.delete("hello");
    assertEquals(false, t.search("hello"));
    assertEquals(true, t.search("he"));
    t.delete("he");
    assertEquals(false, t.search("he"));
    t.delete("the");
    t.insert("wo");
    t.delete("wo");
    assertEquals(false, t.search("wo"));
    assertEquals(false, t.search(""));
    t.insert("");
    assertEquals(true, t.search(""));
    t.delete("");
    assertEquals(false, t.search(""));
    t.insert(null);
    t.search(null);
    t.delete(null);
  }

  @Test
  public void testRedBlacktree() {
    RedBlackTree<Integer> rbt = new RedBlackTree<>();
    assertEquals(null, rbt.getMax());
    assertEquals(null, rbt.getMin());
    rbt.insert(50);
    assertEquals(new Integer(50), rbt.search(50).getData());
    assertArrayEquals(new Integer[] {50}, rbt.levelOrderTraversal());
    rbt.insert(40);
    assertEquals(new Integer(40), rbt.search(40).getData());
    assertArrayEquals(new Integer[] {50, 40}, rbt.levelOrderTraversal());
    rbt.insert(30);
    assertEquals(new Integer(30), rbt.search(30).getData());
    assertArrayEquals(new Integer[] {40, 30, 50}, rbt.levelOrderTraversal());
    rbt.insert(70);
    assertEquals(new Integer(70), rbt.search(70).getData());
    assertArrayEquals(new Integer[] {40, 30, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(65);
    assertEquals(new Integer(65), rbt.search(65).getData());
    assertArrayEquals(new Integer[] {40, 30, 65, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(35);
    assertEquals(new Integer(35), rbt.search(35).getData());
    assertArrayEquals(new Integer[] {40, 30, 65, 35, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(37);
    assertEquals(new Integer(37), rbt.search(37).getData());
    assertArrayEquals(new Integer[] {40, 35, 65, 30, 37, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(20);
    assertEquals(new Integer(20), rbt.search(20).getData());
    assertArrayEquals(new Integer[] {40, 35, 65, 30, 37, 50, 70, 20}, rbt.levelOrderTraversal());
    rbt.insert(23);
    assertEquals(new Integer(23), rbt.search(23).getData());
    assertArrayEquals(new Integer[] {40, 35, 65, 23, 37, 50, 70, 20, 30},
            rbt.levelOrderTraversal());
    // Test that inserting a duplicate value does not alter the tree.
    rbt.insert(23);
    assertEquals(new Integer(23), rbt.search(23).getData());
    assertArrayEquals(new Integer[] {40, 35, 65, 23, 37, 50, 70, 20, 30},
            rbt.levelOrderTraversal());
    rbt.insert(null);
    assertNull(rbt.search(100));
    assertNull(rbt.search(null));
    rbt.delete(null);
    rbt.delete(100);
    rbt.delete(30);
    assertArrayEquals(new Integer[] {40, 35, 65, 23, 37, 50, 70, 20}, rbt.levelOrderTraversal());
    rbt.delete(23);
    assertArrayEquals(new Integer[] {40, 35, 65, 20, 37, 50, 70}, rbt.levelOrderTraversal());
    rbt.delete(37);
    assertArrayEquals(new Integer[] {40, 35, 65, 20, 50, 70}, rbt.levelOrderTraversal());
    rbt.delete(65);
    assertArrayEquals(new Integer[] {40, 35, 70, 20, 50}, rbt.levelOrderTraversal());
    rbt.delete(70);
    assertArrayEquals(new Integer[] {40, 35, 50, 20}, rbt.levelOrderTraversal());
    rbt.delete(50);
    assertArrayEquals(new Integer[] {35, 20, 40}, rbt.levelOrderTraversal());
    rbt.delete(20);
    assertArrayEquals(new Integer[] {35, 40}, rbt.levelOrderTraversal());
    rbt.delete(35);
    assertArrayEquals(new Integer[] {40}, rbt.levelOrderTraversal());
    rbt.delete(40);
    assertArrayEquals(new Integer[] {}, rbt.levelOrderTraversal());
    rbt.delete(1);
    assertArrayEquals(new Integer[] {}, rbt.levelOrderTraversal());
    rbt.insert(40);
    rbt.insert(10);
    rbt.insert(20);
    rbt.insert(30);
    rbt.insert(70);
    rbt.insert(25);
    assertArrayEquals(new Integer[] {20, 10, 40, 30, 70, 25}, rbt.levelOrderTraversal());
    rbt.delete(10);
    assertArrayEquals(new Integer[] {40, 25, 70, 20, 30}, rbt.levelOrderTraversal());
    assertEquals(new Integer(70), rbt.getMax());
    assertEquals(new Integer(20), rbt.getMin());
  }

  @Test
  public void testGraphAdjacencyListBad() {
    GraphAdjacencyListBad g = new GraphAdjacencyListBad(5);
    g.addEdge(0, 1);
    g.addEdge(2, 7);
    g.addEdge(-3, 4);
    assertTrue(g.checkEdge(0, 1));
    assertFalse(g.checkEdge(2, 3));
    g.addNode();
    assertTrue(g.checkEdge(0, 1));
    g.removeEdge(0, 1);
    assertFalse(g.checkEdge(0, 1));
    g.addEdge(2, 4);
    assertTrue(g.checkEdge(2, 4));
    g.removeNode(2);
    assertFalse(g.checkEdge(2, 4));
  }

  @Test
  public void testGraphAdjacencyListBetter() {
    GraphAdjacencyListBetter<String> g = new GraphAdjacencyListBetter<>();
    GraphAdjacencyListBetter.Node<String> node1 = new GraphAdjacencyListBetter.Node<>("hello");
    GraphAdjacencyListBetter.Node<String> node2 = new GraphAdjacencyListBetter.Node<>("hi");
    GraphAdjacencyListBetter.Node<String> node3 = new GraphAdjacencyListBetter.Node<>("ok");
    GraphAdjacencyListBetter.Node<String> node4 = new GraphAdjacencyListBetter.Node<>("bye");
    g.addNode(node1);
    g.addNode(node2);
    g.addNode(node3);
    g.addNode(node4);
    g.addEdge(node1, node3);
    assertTrue(g.checkEdge(node1, node3));
    g.removeEdge(node1, node3);
    assertFalse(g.checkEdge(node1, node3));
    assertFalse(g.checkEdge(node2, node4));
    g.addEdge(node2, node4);
    assertTrue(g.checkEdge(node2, node4));
    g.removeNode(node4);
    assertFalse(g.checkEdge(node2, node4));
  }

  @Test
  public void testGraphAdjacencyMatrix() {
    GraphAdjacencyMatrix g = new GraphAdjacencyMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(2, 7);
    g.addEdge(-3, 4);
    assertTrue(g.checkEdge(0, 1));
    assertFalse(g.checkEdge(2, 3));
    g.addNode();
    assertTrue(g.checkEdge(0, 1));
    g.removeEdge(0, 1);
    assertFalse(g.checkEdge(0, 1));
    g.addEdge(2, 4);
    assertTrue(g.checkEdge(2, 4));
    g.removeNode(2);
    assertFalse(g.checkEdge(2, 4));
    g.checkEdge(22, 3);
    g.removeEdge(-2, 4);
    g.removeNode(73);
  }

  @Test
  public void testPriorityQueueArray() {
    PriorityQueueArray<String> pq = new PriorityQueueArray<>(6);
    assertNull(pq.getHighestPriority());
    pq.insert("hello", 12);
    pq.insert("hi", 4);
    pq.insert("bye", 7);
    pq.insert("goodbye", 0);
    pq.insert("yes", -3);
    pq.insert("no", 19);
    pq.insert("ok", 35);
    pq.changePriority("no", -41);
    pq.changePriority("bye", 83);
    assertEquals("no", pq.getHighestPriority());
    assertEquals("no", pq.deleteHighestPriority());
    assertEquals("yes", pq.deleteHighestPriority());
    assertEquals("goodbye", pq.deleteHighestPriority());
    assertEquals("hi", pq.deleteHighestPriority());
    assertEquals("hello", pq.deleteHighestPriority());
    assertEquals("bye", pq.deleteHighestPriority());
    assertNull(pq.deleteHighestPriority());
  }

  @Test
  public void testPriortyQueueList() {
    PriorityQueueList<String> pq = new PriorityQueueList<>();
    assertNull(pq.getHighestPriority());
    pq.insert("hello", 12);
    pq.insert("hi", 4);
    pq.insert("bye", 7);
    pq.insert("goodbye", 0);
    pq.insert("yes", -3);
    pq.insert("no", 19);
    pq.changePriority("no", -41);
    pq.changePriority("bye", 83);
    assertEquals("no", pq.getHighestPriority());
    assertEquals("no", pq.deleteHighestPriority());
    assertEquals("yes", pq.deleteHighestPriority());
    assertEquals("goodbye", pq.deleteHighestPriority());
    assertEquals("hi", pq.deleteHighestPriority());
    assertEquals("hello", pq.deleteHighestPriority());
    assertEquals("bye", pq.deleteHighestPriority());
    assertNull(pq.deleteHighestPriority());
  }

  @Test
  public void testPriorityQueueHeap() {
    PriorityQueueHeap<String> pq = new PriorityQueueHeap<>(6);
    assertNull(pq.getHighestPriority());
    pq.insert("hello", 12);
    pq.insert("hi", 4);
    pq.insert("bye", 7);
    pq.insert("goodbye", 0);
    pq.insert("yes", -3);
    pq.insert("no", 19);
    pq.insert("ok", 35);
    pq.changePriority("no", -41);
    pq.changePriority("bye", 83);
    assertEquals("no", pq.getHighestPriority());
    assertEquals("no", pq.deleteHighestPriority());
    assertEquals("yes", pq.deleteHighestPriority());
    assertEquals("goodbye", pq.deleteHighestPriority());
    assertEquals("hi", pq.deleteHighestPriority());
    assertEquals("hello", pq.deleteHighestPriority());
    assertEquals("bye", pq.deleteHighestPriority());
    assertNull(pq.deleteHighestPriority());
  }

  @Test
  public void testHashTableOpen() {
    HashTableOpen h = new HashTableOpen();
    assertNull(h.put(null, null));
    assertNull(h.put(12, null));
    assertNull(h.put(null, 5));
    assertNull(h.put(15, 16));
    assertNull(h.put("hello", "ok"));
    assertNull(h.put(42.1, "goodbye"));
    assertEquals(16, h.put(15, 17));
    assertNull(h.put(22, false));
    assertNull(h.put(99, 999));
    assertNull(h.put('C', true));
    assertNull(h.put("no", 'w'));
    // The following put will cause a rehash.
    assertNull(h.put("     ", 55.555));
    assertNull(h.get(null));
    assertNull(h.get(84));
    assertEquals(17, h.get(15));
    assertEquals("ok", h.get("hello"));
    assertEquals("goodbye", h.get(42.1));
    assertNull(h.delete(null));
    assertNull(h.delete("what"));
    assertEquals("ok", h.delete("hello"));
    assertEquals("goodbye", h.delete(42.1));
    assertFalse(h.containsKey(null));
    assertFalse(h.containsKey("yes"));
    assertFalse(h.containsKey("hello"));
    assertTrue(h.containsKey(15));
  }

  @Test
  public void testHashTableClosed() {
    HashTableClosed h = new HashTableClosed();
    assertNull(h.put(null, null));
    assertNull(h.put(12, null));
    assertNull(h.put(null, 5));
    assertNull(h.put(15, 16));
    assertNull(h.put("hello", "ok"));
    assertNull(h.put(42.1, "goodbye"));
    assertEquals(16, h.put(15, 17));
    assertNull(h.put(22, false));
    assertNull(h.put(99, 999));
    assertNull(h.put('C', true));
    assertNull(h.put("no", 'w'));
    // The following put will cause a rehash.
    assertNull(h.put("     ", 55.555));
    assertNull(h.get(null));
    assertNull(h.get(84));
    assertEquals(17, h.get(15));
    assertEquals("ok", h.get("hello"));
    assertEquals("goodbye", h.get(42.1));
    assertNull(h.delete(null));
    assertNull(h.delete("what"));
    assertEquals("ok", h.delete("hello"));
    assertEquals("goodbye", h.delete(42.1));
    assertFalse(h.containsKey(null));
    assertFalse(h.containsKey("yes"));
    assertFalse(h.containsKey("hello"));
    assertTrue(h.containsKey(15));
    assertTrue(h.containsKey("     "));
    assertTrue(h.containsKey(99));
    assertEquals(null, h.put(42.1, "why"));
    assertEquals("why", h.delete(42.1));
    assertEquals(17, h.delete(15));
    assertEquals('w', h.delete("no"));
    assertEquals(true, h.delete('C'));
    assertEquals(999, h.delete(99));
    assertEquals(false, h.delete(22));
    assertEquals(null, h.put(642, -612));
  }

  @Test
  public void testStack() {
    Stack<String> s = new Stack<>();
    assertTrue(s.isEmpty());
    s.push("hello");
    assertFalse(s.isEmpty());
    s.push("hi");
    assertEquals("hi", s.peek());
    assertEquals("hi", s.pop());
    assertEquals("hello", s.pop());
    assertNull(s.peek());
    assertNull(s.pop());
  }

  @Test
  public void testQueue() {
    Queue<String> q = new Queue<>();
    assertTrue(q.isEmpty());
    q.add("hello");
    assertFalse(q.isEmpty());
    q.add("hi");
    assertEquals("hello", q.peek());
    assertEquals("hello", q.remove());
    assertEquals("hi", q.remove());
    assertNull(q.peek());
    assertNull(q.remove());
  }

  @Test
  public void testLinkedList() {
    LinkedList<String> l = new LinkedList<>();
    assertNull(l.getFirst());
    assertNull(l.get(-1));
    assertNull(l.get(200));
    assertNull(l.remove(-1));
    assertNull(l.remove(45));
    assertNull(l.remove("the"));
    l.add("hello");
    l.add("goodbye");
    l.add("hi");
    l.add("bye");
    assertEquals(4, l.size());
    assertEquals("hello", l.get(0));
    assertEquals("goodbye", l.get(1));
    assertEquals("hi", l.get(2));
    assertEquals("bye", l.get(3));
    assertEquals("hello", l.getFirst());
    assertNull(l.get(4));
    assertNull(l.remove(4));
    assertNull(l.remove("ok"));
    assertEquals("hi", l.remove("hi"));
    assertEquals("goodbye", l.remove(1));
    assertEquals("hello", l.remove(0));
    assertEquals("bye", l.remove("bye"));
  }

  @Test
  public void testArrayList() {
    ArrayList<String> a = new ArrayList<>();
    assertNull(a.getFirst());
    assertNull(a.get(-1));
    assertNull(a.get(97));
    assertNull(a.remove(-1));
    assertNull(a.remove(898));
    assertNull(a.remove("ok"));
    a.add("hello");
    a.add("goodbye");
    a.add("hi");
    a.add("bye");
    assertEquals(4, a.size());
    assertEquals("hello", a.get(0));
    assertEquals("goodbye", a.get(1));
    assertEquals("hi", a.get(2));
    assertEquals("bye", a.get(3));
    assertEquals("hello", a.getFirst());
    assertNull(a.get(4));
    assertNull(a.remove(4));
    assertNull(a.remove("ok"));
    assertEquals("hi", a.remove("hi"));
    assertEquals("goodbye", a.remove(1));
    assertEquals("hello", a.remove(0));
    assertEquals("bye", a.remove("bye"));
    a = new ArrayList<>(412);
    a = new ArrayList<>(3);
    // Add and remove elements so that resize() is called twice.
    a.add("a");
    a.add("b");
    a.add("c");
    a.add("d");
    a.add("e");
    a.add("f");
    a.add("g");
    a.add("h");
    a.add("i");
    a.add("j");
    a.add("k");
    a.add("l");
    a.add("m");
    a.add("n");
    a.remove(13);
    a.remove(12);
    a.remove(11);
    a.remove(0);
    a.remove(0);
    a.remove(0);
    a.remove(2);
    a.remove(1);
    a.remove(2);
    a.remove(0);
    a.remove(0);
    a.remove(1);
    a.remove(0);
  }
}