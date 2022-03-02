import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dataStructures.*;
import dataStructures.StringBuilder; // Must explicitly import this class to resolve ambiguity.
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DataStructuresTests {

  @Test
  public void testBinaryTree() {
    BinaryTree<Integer> b = new BinaryTree<>();
    b.insert(0);
    b.delete(0);
    assertFalse(b.bfs(0));
    b.insert(1);
    b.insert(2);
    b.insert(3);
    b.insert(4);
    b.insert(5);
    b.insert(6);
    b.insert(7);
    b.insert(8);
    assertTrue(b.bfs(5));
    assertFalse(b.bfs(27));
    assertTrue(b.dfs(2));
    assertFalse(b.dfs(41));
    b.delete(3);
    assertFalse(b.bfs(3));
    assertTrue(b.bfs(1));
    assertTrue(b.bfs(2));
    assertTrue(b.bfs(4));
    assertTrue(b.bfs(5));
    assertTrue(b.bfs(6));
    assertTrue(b.bfs(7));
    assertTrue(b.bfs(8));
    b.delete(7);
    assertFalse(b.bfs(7));
    assertTrue(b.bfs(1));
    assertTrue(b.bfs(2));
    assertTrue(b.bfs(4));
    assertTrue(b.bfs(5));
    assertTrue(b.bfs(6));
    assertTrue(b.bfs(8));
    b.delete(8);
    assertFalse(b.bfs(8));
    assertTrue(b.bfs(1));
    assertTrue(b.bfs(2));
    assertTrue(b.bfs(4));
    assertTrue(b.bfs(5));
    assertTrue(b.bfs(6));
    b.delete(1);
    assertFalse(b.bfs(1));
    assertTrue(b.bfs(2));
    assertTrue(b.bfs(4));
    assertTrue(b.bfs(5));
    assertTrue(b.bfs(6));
    b.delete(2);
    assertFalse(b.bfs(2));
    assertTrue(b.bfs(4));
    assertTrue(b.bfs(5));
    assertTrue(b.bfs(6));
    b.insert(2);
    assertTrue(b.bfs(2));
    assertTrue(b.bfs(4));
    assertTrue(b.bfs(5));
    assertTrue(b.bfs(6));
    assertThrows(IllegalArgumentException.class, () -> b.bfs(null));
    assertThrows(IllegalArgumentException.class, () -> b.dfs(null));
    assertThrows(IllegalArgumentException.class, () -> b.insert(null));
    assertThrows(IllegalArgumentException.class, () -> b.delete(null));
  }

  @Test
  public void testBinarySearchTree() {
    BinarySearchTree<Integer> b = new BinarySearchTree<>();
    assertFalse(b.search(9));
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
    assertTrue(b.search(6));
    b.delete(3);
    assertFalse(b.search(3));
    b.delete(4);
    assertFalse(b.search(4));
    b.delete(0);
    assertFalse(b.search(0));
    b.delete(7);
    assertFalse(b.search(7));
    b.delete(8);
    assertFalse(b.search(8));
    b.delete(6);
    assertFalse(b.search(6));
    b.delete(9);
    assertFalse(b.search(9));
    assertThrows(IllegalArgumentException.class, () -> b.insert(null));
    assertThrows(IllegalArgumentException.class, () -> b.delete(null));
    assertThrows(IllegalArgumentException.class, () -> b.search(null));
  }

  @Test
  public void testMinHeapIntegerIterative() {
    MinHeapIntegerIterative m = new MinHeapIntegerIterative();
    assertThrows(NoSuchElementException.class, m::getMin);
    assertThrows(NoSuchElementException.class, m::extractMin);
    assertThrows(IndexOutOfBoundsException.class, () -> m.changeKey(-1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> m.getValAtIdx(0));

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

    assertThrows(IndexOutOfBoundsException.class, () -> m.delete(-2));
    assertThrows(IndexOutOfBoundsException.class, () -> m.delete(101));

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
    for (int i = 0; i < 7; i++) sortedActual[i] = m.extractMin();
    assertArrayEquals(sortedExpected, sortedActual);

    // Test building a heap from a given array.
    MinHeapIntegerIterative m2 =
            new MinHeapIntegerIterative(new Integer[] {67, 82, 3, 15, 105, 9, 44, 37});
    sortedExpected = new Integer[] {3, 9, 15, 37, 44, 67, 82, 105};
    sortedActual = new Integer[8];
    for (int i = 0; i < 8; i++) sortedActual[i] = m2.extractMin();
    assertArrayEquals(sortedExpected, sortedActual);
  }

  @Test
  public void testMaxHeapCharacterRecursive() {
    MaxHeapCharacterRecursive m = new MaxHeapCharacterRecursive();
    assertThrows(NoSuchElementException.class, m::getMax);
    assertThrows(NoSuchElementException.class, m::extractMax);
    assertThrows(IndexOutOfBoundsException.class, () -> m.changeKey(-1, 'a'));
    assertThrows(IndexOutOfBoundsException.class, () -> m.getValAtIdx(0));

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

    assertThrows(IndexOutOfBoundsException.class, () -> m.delete(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> m.delete(32));

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
    for (int i = 0; i < 7; i++) sortedActual[i] = m.extractMax();
    assertArrayEquals(sortedExpected, sortedActual);

    // Test building a heap from a given array.
    MaxHeapCharacterRecursive m2 =
            new MaxHeapCharacterRecursive(new Character[] {'C', 'k', '+', '9', 'u', '6', 'A', '3'});
    sortedExpected = new Character[] {'u', 'k', 'C', 'A', '9', '6', '3', '+'};
    sortedActual = new Character[8];
    for (int i = 0; i < 8; i++) sortedActual[i] = m2.extractMax();
    assertArrayEquals(sortedExpected, sortedActual);
  }

  @Test
  public void testTrie() {
    Trie t = new Trie();
    assertFalse(t.search("a"));
    t.insert("hello");
    assertTrue(t.search("hello"));
    assertFalse(t.search("he"));
    t.insert("word");
    assertTrue(t.search("word"));
    t.insert("tiger");
    assertTrue(t.search("tiger"));
    t.insert("he");
    assertTrue(t.search("he"));
    assertFalse(t.search("hellop"));
    assertFalse(t.search("lion"));
    t.delete("tiger");
    assertFalse(t.search("tiger"));
    t.delete("hello");
    assertFalse(t.search("hello"));
    assertTrue(t.search("he"));
    t.delete("he");
    assertFalse(t.search("he"));
    t.delete("the");
    t.insert("wo");
    t.delete("wo");
    assertFalse(t.search("wo"));
    assertFalse(t.search(""));
    t.insert("");
    assertTrue(t.search(""));
    t.delete("");
    assertFalse(t.search(""));
    assertThrows(IllegalArgumentException.class, () -> t.insert(null));
    assertThrows(IllegalArgumentException.class, () -> t.search(null));
    assertThrows(IllegalArgumentException.class, () -> t.delete(null));
  }

  @Test
  public void testRedBlackTree() {
    RedBlackTree<Integer> rbt = new RedBlackTree<>();
    assertThrows(NoSuchElementException.class, rbt::getMax);
    assertThrows(NoSuchElementException.class, rbt::getMin);
    rbt.insert(50);
    assertTrue(rbt.search(50));
    assertArrayEquals(new Integer[] {50}, rbt.levelOrderTraversal());
    rbt.insert(40);
    assertTrue(rbt.search(40));
    assertArrayEquals(new Integer[] {50, 40}, rbt.levelOrderTraversal());
    rbt.insert(30);
    assertTrue(rbt.search(30));
    assertArrayEquals(new Integer[] {40, 30, 50}, rbt.levelOrderTraversal());
    rbt.insert(70);
    assertTrue(rbt.search(70));
    assertArrayEquals(new Integer[] {40, 30, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(65);
    assertTrue(rbt.search(65));
    assertArrayEquals(new Integer[] {40, 30, 65, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(35);
    assertTrue(rbt.search(35));
    assertArrayEquals(new Integer[] {40, 30, 65, 35, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(37);
    assertTrue(rbt.search(37));
    assertArrayEquals(new Integer[] {40, 35, 65, 30, 37, 50, 70}, rbt.levelOrderTraversal());
    rbt.insert(20);
    assertTrue(rbt.search(20));
    assertArrayEquals(new Integer[] {40, 35, 65, 30, 37, 50, 70, 20}, rbt.levelOrderTraversal());
    rbt.insert(23);
    assertTrue(rbt.search(23));
    assertArrayEquals(new Integer[] {40, 35, 65, 23, 37, 50, 70, 20, 30},
            rbt.levelOrderTraversal());
    // Test that inserting a duplicate value does not alter the tree.
    rbt.insert(23);
    assertTrue(rbt.search(23));
    assertArrayEquals(new Integer[] {40, 35, 65, 23, 37, 50, 70, 20, 30},
            rbt.levelOrderTraversal());
    assertThrows(IllegalArgumentException.class, () -> rbt.insert(null));
    assertFalse(rbt.search(100));
    assertThrows(IllegalArgumentException.class, () -> rbt.search(null));
    assertThrows(IllegalArgumentException.class, () -> rbt.search(null));
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
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(2, 7));
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(-3, 4));
    assertTrue(g.checkEdge(0, 1));
    assertFalse(g.checkEdge(2, 3));
    g.addNode();
    assertTrue(g.checkEdge(0, 1));
    g.removeEdge(0, 1);
    assertThrows(IllegalArgumentException.class, () -> g.removeEdge(2, 7));
    assertThrows(IllegalArgumentException.class, () -> g.removeEdge(-3, 4));
    assertFalse(g.checkEdge(0, 1));
    g.addEdge(2, 4);
    assertTrue(g.checkEdge(2, 4));
    g.removeNode(2);
    assertThrows(IllegalArgumentException.class, () -> g.removeNode(7));
    assertThrows(IllegalArgumentException.class, () -> g.removeNode(-3));
    assertFalse(g.checkEdge(2, 4));
  }

  @Test
  public void testGraphAdjacencyListBest() {
    GraphAdjacencyListBest<String> g = new GraphAdjacencyListBest<>();
    GraphAdjacencyListBest<String> gg = new GraphAdjacencyListBest<>();
    GraphAdjacencyListBest<String>.Node node1 = g.addNode("hello");
    GraphAdjacencyListBest<String>.Node node2 = g.addNode("hi");
    GraphAdjacencyListBest<String>.Node node3 = g.addNode("ok");
    GraphAdjacencyListBest<String>.Node node4 = g.addNode("bye");
    GraphAdjacencyListBest<String>.Node node5 = gg.addNode("node in different graph");
    assertThrows(IllegalArgumentException.class, () -> g.addNode(null));
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(node1, node5));
    assertThrows(IllegalArgumentException.class, () -> g.removeEdge(node1, node5));
    assertThrows(IllegalArgumentException.class, () -> g.removeNode(node5));
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
  public void testGraphAdjacencyListBetter() {
    GraphAdjacencyListBetter<String> g = new GraphAdjacencyListBetter<>();
    GraphAdjacencyListBetter<String> gg = new GraphAdjacencyListBetter<>();
    GraphAdjacencyListBetter<String>.Node node1 = g.addNode("hello");
    GraphAdjacencyListBetter<String>.Node node2 = g.addNode("hi");
    GraphAdjacencyListBetter<String>.Node node3 = g.addNode("ok");
    GraphAdjacencyListBetter<String>.Node node4 = g.addNode("bye");
    GraphAdjacencyListBetter<String>.Node node5 = gg.addNode("node in different graph");
    assertThrows(IllegalArgumentException.class, () -> g.addNode(null));
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(node1, node5));
    assertThrows(IllegalArgumentException.class, () -> g.removeEdge(node1, node5));
    assertThrows(IllegalArgumentException.class, () -> g.removeNode(node5));
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
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(2, 7));
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(-3, 4));
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
    assertFalse(g.checkEdge(22, 3));
    assertThrows(IllegalArgumentException.class, () -> g.removeEdge(-2, 4));
    assertThrows(IllegalArgumentException.class, () -> g.removeNode(73));
  }

  @Test
  public void testGraphGeneric() {
    // Test an undirected, unweighted graph.
    GraphGeneric<String> g = new GraphGeneric<>(false);
    GraphGeneric<String> gg = new GraphGeneric<>(false);
    GraphGeneric<String>.Node node1g = g.addNode("hello");
    GraphGeneric<String>.Node node2g = g.addNode("hi");
    GraphGeneric<String>.Node node3g = g.addNode("ok");
    GraphGeneric<String>.Node node4g = g.addNode("bye");
    GraphGeneric<String>.Node node1gg = gg.addNode("node in different graph");
    assertThrows(IllegalArgumentException.class, () -> g.addNode(null));
    assertThrows(IllegalArgumentException.class, () -> g.addEdge(node1g, node1gg));
    assertThrows(IllegalArgumentException.class, () -> g.removeEdge(node1g, node1gg));
    assertThrows(IllegalArgumentException.class, () -> g.removeNode(node1gg));
    g.addEdge(node1g, node3g);
    assertEquals(1, g.checkEdgeWeight(node1g, node3g));
    assertEquals(1, g.checkEdgeWeight(node3g, node1g));
    g.removeEdge(node1g, node3g);
    assertEquals(-1, g.checkEdgeWeight(node1g, node3g));
    assertEquals(-1, g.checkEdgeWeight(node2g, node4g));
    g.addEdge(node2g, node4g);
    assertEquals(1, g.checkEdgeWeight(node2g, node4g));
    assertEquals(1, g.checkEdgeWeight(node4g, node2g));
    g.removeNode(node4g);
    assertEquals(-1, g.checkEdgeWeight(node2g, node4g));
    // Test a directed, unweighted graph.
    GraphGeneric<String> g2 = new GraphGeneric<>(true);
    GraphGeneric<String>.Node node1g2 = g2.addNode("hello");
    GraphGeneric<String>.Node node2g2 = g2.addNode("hi");
    GraphGeneric<String>.Node node3g2 = g2.addNode("ok");
    GraphGeneric<String>.Node node4g2 = g2.addNode("bye");
    g2.addEdge(node1g2, node3g2);
    assertEquals(1, g2.checkEdgeWeight(node1g2, node3g2));
    assertEquals(-1, g2.checkEdgeWeight(node3g2, node1g2));
    g2.removeEdge(node1g2, node3g2);
    assertEquals(-1, g2.checkEdgeWeight(node1g2, node3g2));
    assertEquals(-1, g2.checkEdgeWeight(node2g2, node4g2));
    g2.addEdge(node2g2, node4g2);
    assertEquals(1, g2.checkEdgeWeight(node2g2, node4g2));
    assertEquals(-1, g2.checkEdgeWeight(node4g2, node2g2));
    g2.addEdge(node4g2, node2g2);
    assertEquals(1, g2.checkEdgeWeight(node4g2, node2g2));
    g2.removeNode(node4g2);
    assertEquals(-1, g2.checkEdgeWeight(node2g2, node4g2));
    assertEquals(-1, g2.checkEdgeWeight(node4g2, node2g2));
    // Test an undirected, weighted graph.
    GraphGeneric<String> g3 = new GraphGeneric<>(false);
    GraphGeneric<String>.Node node1g3 = g3.addNode("hello");
    GraphGeneric<String>.Node node2g3 = g3.addNode("hi");
    GraphGeneric<String>.Node node3g3 = g3.addNode("ok");
    GraphGeneric<String>.Node node4g3 = g3.addNode("bye");
    g3.addEdge(node1g3, node3g3, 4);
    assertEquals(4, g3.checkEdgeWeight(node1g3, node3g3));
    assertEquals(4, g3.checkEdgeWeight(node3g3, node1g3));
    g3.removeEdge(node1g3, node3g3);
    assertEquals(-1, g3.checkEdgeWeight(node1g3, node3g3));
    assertEquals(-1, g3.checkEdgeWeight(node2g3, node4g3));
    g3.addEdge(node2g3, node4g3, 7);
    assertEquals(7, g3.checkEdgeWeight(node2g3, node4g3));
    assertEquals(7, g3.checkEdgeWeight(node4g3, node2g3));
    g3.removeNode(node4g3);
    assertEquals(-1, g3.checkEdgeWeight(node2g3, node4g3));
    // Test a directed, weighted graph.
    GraphGeneric<String> g4 = new GraphGeneric<>(true);
    GraphGeneric<String>.Node node1g4 = g4.addNode("hello");
    GraphGeneric<String>.Node node2g4 = g4.addNode("hi");
    GraphGeneric<String>.Node node3g4 = g4.addNode("ok");
    GraphGeneric<String>.Node node4g4 = g4.addNode("bye");
    g4.addEdge(node1g4, node3g4, 8);
    assertEquals(8, g4.checkEdgeWeight(node1g4, node3g4));
    assertEquals(-1, g4.checkEdgeWeight(node3g4, node1g4));
    g4.removeEdge(node1g4, node3g4);
    assertEquals(-1, g4.checkEdgeWeight(node1g4, node3g4));
    assertEquals(-1, g4.checkEdgeWeight(node2g4, node4g4));
    g4.addEdge(node2g4, node4g4, 15);
    assertEquals(15, g4.checkEdgeWeight(node2g4, node4g4));
    assertEquals(-1, g4.checkEdgeWeight(node4g4, node2g4));
    g4.addEdge(node4g4, node2g4, 29);
    assertEquals(29, g4.checkEdgeWeight(node4g4, node2g4));
    g4.removeNode(node4g4);
    assertEquals(-1, g4.checkEdgeWeight(node2g4, node4g4));
    assertEquals(-1, g4.checkEdgeWeight(node4g4, node2g4));
  }

  @Test
  public void testPriorityQueueArray() {
    PriorityQueueArray<String> pq = new PriorityQueueArray<>(6);
    pq.insert("hello", 12);
    pq.insert("hi", 4);
    pq.insert("bye", 7);
    pq.insert("goodbye", 0);
    pq.insert("yes", -3);
    pq.insert("no", 19);
    assertThrows(Exception.class, () -> pq.insert("ok", 35));
    pq.changePriority("no", -41);
    pq.changePriority("bye", 83);
    assertEquals("no", pq.getHighestPriority());
    assertEquals("no", pq.deleteHighestPriority());
    assertEquals("yes", pq.deleteHighestPriority());
    assertEquals("goodbye", pq.deleteHighestPriority());
    assertEquals("hi", pq.deleteHighestPriority());
    assertEquals("hello", pq.deleteHighestPriority());
    assertEquals("bye", pq.deleteHighestPriority());
    assertThrows(NoSuchElementException.class, pq::getHighestPriority);
    assertThrows(NoSuchElementException.class, pq::deleteHighestPriority);
    assertThrows(NoSuchElementException.class, () -> pq.changePriority("bye", 10));
  }

  @Test
  public void testPriorityQueueList() {
    PriorityQueueList<String> pq = new PriorityQueueList<>();
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
    assertThrows(NoSuchElementException.class, pq::getHighestPriority);
    assertThrows(NoSuchElementException.class, pq::deleteHighestPriority);
    assertThrows(NoSuchElementException.class, () -> pq.changePriority("bye", 10));
  }

  @Test
  public void testPriorityQueueHeap() {
    PriorityQueueHeap<String> pq = new PriorityQueueHeap<>(6);
    assertTrue(pq.isEmpty());
    assertFalse(pq.contains("hello"));
    pq.insert("hello", 12);
    assertTrue(pq.contains("hello"));
    assertEquals(new Integer(12), pq.getPriority("hello"));
    assertFalse(pq.isEmpty());
    pq.insert("hi", 4);
    pq.insert("bye", 7);
    pq.insert("goodbye", 0);
    pq.insert("yes", -3);
    pq.insert("no", 19);
    assertThrows(IllegalStateException.class, () -> pq.insert("ok", 35));
    pq.changePriority("no", -41);
    pq.changePriority("bye", 83);
    assertEquals("no", pq.getHighestPriority());
    assertEquals("no", pq.deleteHighestPriority());
    assertEquals("yes", pq.deleteHighestPriority());
    assertEquals("goodbye", pq.deleteHighestPriority());
    assertEquals("hi", pq.deleteHighestPriority());
    assertEquals("hello", pq.deleteHighestPriority());
    assertEquals("bye", pq.deleteHighestPriority());
    assertTrue(pq.isEmpty());
    assertThrows(NoSuchElementException.class, pq::getHighestPriority);
    assertThrows(NoSuchElementException.class, pq::deleteHighestPriority);
    assertThrows(NoSuchElementException.class, () -> pq.changePriority("bye", 10));
    assertThrows(NoSuchElementException.class, () -> pq.getPriority("hello"));
  }

  @Test
  public void testHashTableOpen() {
    HashTableOpen h = new HashTableOpen();
    assertThrows(IllegalArgumentException.class, () -> h.put(null, null));
    assertThrows(IllegalArgumentException.class, () -> h.put(12, null));
    assertThrows(IllegalArgumentException.class, () -> h.put(null, 5));
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
    assertThrows(IllegalArgumentException.class, () -> h.get(null));
    assertNull(h.get(84));
    assertEquals(17, h.get(15));
    assertEquals("ok", h.get("hello"));
    assertEquals("goodbye", h.get(42.1));
    assertThrows(IllegalArgumentException.class, () -> h.delete(null));
    assertNull(h.delete("what"));
    assertEquals("ok", h.delete("hello"));
    assertEquals("goodbye", h.delete(42.1));
    assertThrows(IllegalArgumentException.class, () -> h.containsKey(null));
    assertFalse(h.containsKey("yes"));
    assertFalse(h.containsKey("hello"));
    assertTrue(h.containsKey(15));
  }

  @Test
  public void testHashTableClosed() {
    HashTableClosed h = new HashTableClosed();
    assertThrows(IllegalArgumentException.class, () -> h.put(null, null));
    assertThrows(IllegalArgumentException.class, () -> h.put(12, null));
    assertThrows(IllegalArgumentException.class, () -> h.put(null, 5));
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
    assertThrows(IllegalArgumentException.class, () -> h.get(null));
    assertNull(h.get(84));
    assertEquals(17, h.get(15));
    assertEquals("ok", h.get("hello"));
    assertEquals("goodbye", h.get(42.1));
    assertThrows(IllegalArgumentException.class, () -> h.delete(null));
    assertNull(h.delete("what"));
    assertEquals("ok", h.delete("hello"));
    assertEquals("goodbye", h.delete(42.1));
    assertThrows(IllegalArgumentException.class, () -> h.containsKey(null));
    assertFalse(h.containsKey("yes"));
    assertFalse(h.containsKey("hello"));
    assertTrue(h.containsKey(15));
    assertTrue(h.containsKey("     "));
    assertTrue(h.containsKey(99));
    assertNull(h.put(42.1, "why"));
    assertEquals("why", h.delete(42.1));
    assertEquals(17, h.delete(15));
    assertEquals('w', h.delete("no"));
    assertEquals(true, h.delete('C'));
    assertEquals(999, h.delete(99));
    assertEquals(false, h.delete(22));
    assertNull(h.put(642, -612));
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
    assertThrows(NoSuchElementException.class, s::peek);
    assertThrows(NoSuchElementException.class, s::pop);
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
    assertThrows(NoSuchElementException.class, q::peek);
    assertThrows(NoSuchElementException.class, q::remove);
  }

  @Test
  public void testLinkedList() {
    LinkedList<String> l = new LinkedList<>();
    assertThrows(NoSuchElementException.class, l::getFirst);
    assertThrows(IndexOutOfBoundsException.class, () -> l.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> l.get(200));
    assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> l.remove(45));
    assertFalse(l.remove("the"));
    assertTrue(l.isEmpty());
    l.add("hello");
    assertFalse(l.isEmpty());
    l.add("goodbye");
    l.add("hi");
    l.add("bye");
    // Test Iterable functionality.
    for (String s : l) System.out.println(s);
    Iterator<String> it = l.iterator();
    assertTrue(it.hasNext());
    assertEquals("hello", it.next());
    assertEquals("goodbye", it.next());
    assertEquals("hi", it.next());
    assertEquals("bye", it.next());
    assertFalse(it.hasNext());
    assertEquals(4, l.size());
    assertEquals("hello", l.get(0));
    assertEquals("goodbye", l.get(1));
    assertEquals("hi", l.get(2));
    assertEquals("bye", l.get(3));
    assertEquals("hello", l.getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> l.get(4));
    assertThrows(IndexOutOfBoundsException.class, () -> l.remove(4));
    assertFalse(l.remove("ok"));
    l.add("ok");
    assertTrue(l.remove("hello"));
    assertTrue(l.remove("bye"));
    assertEquals("ok", l.remove(2));
    assertEquals("hi", l.remove(1));
    assertEquals("goodbye", l.remove(0));
  }

  @Test
  public void testArrayList() {
    ArrayList<String> a = new ArrayList<>();
    assertThrows(NoSuchElementException.class, a::getFirst);
    assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> a.get(97));
    assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> a.remove(898));
    assertThrows(NoSuchElementException.class, () -> a.remove("ok"));
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
    assertThrows(IndexOutOfBoundsException.class, () -> a.get(4));
    assertThrows(IndexOutOfBoundsException.class, () -> a.remove(4));
    assertThrows(NoSuchElementException.class, () -> a.remove("ok"));
    assertEquals("hi", a.remove("hi"));
    assertEquals("goodbye", a.remove(1));
    assertEquals("hello", a.remove(0));
    assertEquals("bye", a.remove("bye"));
    ArrayList<String> b = new ArrayList<>(3);
    // Add elements so that resize() is called.
    b.add("a");
    b.add("b");
    b.add("c");
    b.add("d");
    b.add("e");
    b.add("f");
    b.add("g");
    b.add("h");
    b.add("i");
    b.add("j");
    b.add("k");
    b.add("l");
    b.add("m");
    b.add("n");
    b.add("o");
    b.add("p");
    b.add("q");
    b.add("r");
    b.add("s");
    b.add("t");
    // Test Iterable functionality.
    for (String s : b) System.out.println(s);
    Iterator<String> it = b.iterator();
    assertTrue(it.hasNext());
    assertEquals("a", it.next());
    assertEquals("b", it.next());
    assertEquals("c", it.next());
    assertEquals("d", it.next());
    assertEquals("e", it.next());
    assertEquals("f", it.next());
    assertEquals("g", it.next());
    assertEquals("h", it.next());
    assertEquals("i", it.next());
    assertEquals("j", it.next());
    assertEquals("k", it.next());
    assertEquals("l", it.next());
    assertEquals("m", it.next());
    assertEquals("n", it.next());
    assertEquals("o", it.next());
    assertEquals("p", it.next());
    assertEquals("q", it.next());
    assertEquals("r", it.next());
    assertEquals("s", it.next());
    assertEquals("t", it.next());
    assertFalse(it.hasNext());
    // Remove elements so that resize() is called.
    b.remove(13);
    b.remove(12);
    b.remove(11);
    b.remove(0);
    b.remove(0);
    b.remove(0);
    b.remove(2);
    b.remove(1);
    b.remove(2);
    b.remove(0);
    b.remove(0);
    b.remove(1);
    b.remove(0);
  }

  @Test
  public void testStringBuilder() {
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder(new char[]{'h', 'e', 'l', 'l', 'o'});
    StringBuilder s3 = new StringBuilder("goodbye");
    s1.append("hi");
    s1.append(new char[]{'o', 'k'});
    assertThrows(IllegalArgumentException.class, () -> s2.append((String) null));
    s2.append("a long string that will cause resizing");
    assertThrows(IllegalArgumentException.class, () -> s3.append((char[]) null));
    String s = "qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm";
    s3.append(s);
    assertEquals("hiok", s1.toString());
    assertEquals("helloa long string that will cause resizing", s2.toString());
    assertEquals("goodbye" + s, s3.toString());
    s1.append("abcdefgh");
    s1.append('i');
    s1.append('j');
    s1.append('k');
    s1.append('l');
    s1.append('m');
    s1.append('n');
    s1.append('o');
    assertEquals("hiokabcdefghijklmno", s1.toString());
  }

  @Test
  public void testSplayTree() {
    SplayTree b = new SplayTree();
    assertFalse(b.search(9));
    assertNull(b.getRootVal());
    b.delete(100);
    b.insert(4);
    assertEquals(4, b.getRootVal());
    b.insert(7);
    assertEquals(7, b.getRootVal());
    b.insert(2);
    assertEquals(2, b.getRootVal());
    b.insert(8);
    b.insert(5);
    b.insert(0);
    b.insert(6);
    b.insert(1);
    b.insert(3);
    assertEquals(3, b.getRootVal());
    assertTrue(b.search(3));
    assertEquals(3, b.getRootVal());
    b.delete(3);
    assertEquals(2, b.getRootVal());
    assertFalse(b.search(3));
    assertEquals(4, b.getRootVal());
    b.delete(4);
    assertEquals(2, b.getRootVal());
    assertFalse(b.search(4));
    assertEquals(5, b.getRootVal());
    b.delete(0);
    assertEquals(1, b.getRootVal());
    assertFalse(b.search(0));
    assertEquals(1, b.getRootVal());
    b.delete(7);
    assertEquals(6, b.getRootVal());
    assertFalse(b.search(7));
    assertEquals(8, b.getRootVal());
    b.search(5);
    assertEquals(5, b.getRootVal());
    b.delete(8);
    assertEquals(5, b.getRootVal());
    assertFalse(b.search(8));
    assertEquals(6, b.getRootVal());
    b.delete(6);
    assertFalse(b.search(6));
    b.delete(9);
    assertEquals(5, b.getRootVal());
    assertThrows(IllegalArgumentException.class, () -> b.insert(null));
    assertThrows(IllegalArgumentException.class, () -> b.delete(null));
    assertThrows(IllegalArgumentException.class, () -> b.search(null));
  }

  @Test
  public void testAVLTree() {
    AVLTree<Integer> avlt = new AVLTree<>();
    assertThrows(NoSuchElementException.class, avlt::getMax);
    assertThrows(NoSuchElementException.class, avlt::getMin);
    // Test left-left imbalance after insertion (and rotation that involves root).
    avlt.insert(40);
    assertTrue(avlt.search(40));
    assertArrayEquals(new Integer[] {40}, avlt.levelOrderTraversal());
    avlt.insert(30);
    assertTrue(avlt.search(30));
    assertArrayEquals(new Integer[] {40, 30}, avlt.levelOrderTraversal());
    avlt.insert(20);
    assertTrue(avlt.search(20));
    assertArrayEquals(new Integer[] {30, 20, 40}, avlt.levelOrderTraversal());
    // Test left-right imbalance after insertion.
    avlt.insert(35);
    assertTrue(avlt.search(35));
    assertArrayEquals(new Integer[] {30, 20, 40, 35}, avlt.levelOrderTraversal());
    avlt.insert(37);
    assertTrue(avlt.search(37));
    assertArrayEquals(new Integer[] {30, 20, 37, 35, 40}, avlt.levelOrderTraversal());
    // Test right-left imbalance after insertion.
    avlt.insert(25);
    assertTrue(avlt.search(25));
    assertArrayEquals(new Integer[] {30, 20, 37, 25, 35, 40}, avlt.levelOrderTraversal());
    avlt.insert(23);
    assertTrue(avlt.search(23));
    assertArrayEquals(new Integer[] {30, 23, 37, 20, 25, 35, 40}, avlt.levelOrderTraversal());
    // Test right-right imbalance after insertion.
    avlt.insert(45);
    assertTrue(avlt.search(45));
    assertArrayEquals(new Integer[] {30, 23, 37, 20, 25, 35, 40, 45}, avlt.levelOrderTraversal());
    avlt.insert(50);
    assertTrue(avlt.search(50));
    assertArrayEquals(new Integer[] {30, 23, 37, 20, 25, 35, 45, 40, 50}, avlt.levelOrderTraversal());
    // Test insertion where great-grandparent of inserted node becomes imbalanced on right side.
    avlt.insert(55);
    assertTrue(avlt.search(55));
    assertArrayEquals(new Integer[] {30, 23, 45, 20, 25, 37, 50, 35, 40, 55}, avlt.levelOrderTraversal());
    // Test insertion where great-grandparent of inserted node becomes imbalanced on left side.
    avlt.insert(17);
    assertTrue(avlt.search(17));
    assertArrayEquals(new Integer[] {30, 23, 45, 20, 25, 37, 50, 17, 35, 40, 55}, avlt.levelOrderTraversal());
    avlt.insert(21);
    assertTrue(avlt.search(21));
    assertArrayEquals(new Integer[] {30, 23, 45, 20, 25, 37, 50, 17, 21, 35, 40, 55}, avlt.levelOrderTraversal());
    avlt.insert(18);
    assertTrue(avlt.search(18));
    assertArrayEquals(new Integer[] {30, 20, 45, 17, 23, 37, 50, 18, 21, 25, 35, 40, 55}, avlt.levelOrderTraversal());
    // Test that inserting a duplicate value does not alter the tree.
    avlt.insert(55);
    assertTrue(avlt.search(55));
    assertArrayEquals(new Integer[] {30, 20, 45, 17, 23, 37, 50, 18, 21, 25, 35, 40, 55}, avlt.levelOrderTraversal());
    // Test search failure.
    assertFalse(avlt.search(41));
    // Test deletion of leaf node that does not cause any imbalance.
    avlt.delete(55);
    assertFalse(avlt.search(55));
    assertArrayEquals(new Integer[] {30, 20, 45, 17, 23, 37, 50, 18, 21, 25, 35, 40}, avlt.levelOrderTraversal());
    // Test deletion of internal node that does not cause any imbalance.
    avlt.delete(23);
    assertFalse(avlt.search(23));
    assertArrayEquals(new Integer[] {30, 20, 45, 17, 21, 37, 50, 18, 25, 35, 40}, avlt.levelOrderTraversal());
    // Test deletion of root.
    avlt.delete(30);
    assertFalse(avlt.search(30));
    assertArrayEquals(new Integer[] {25, 20, 45, 17, 21, 37, 50, 18, 35, 40}, avlt.levelOrderTraversal());
    // Test deletion that causes simultaneous left-left and left-right imbalances.
    avlt.delete(50);
    assertFalse(avlt.search(50));
    assertArrayEquals(new Integer[] {25, 20, 37, 17, 21, 35, 45, 18, 40}, avlt.levelOrderTraversal());
    // Test deletion that causes right-left imbalance.
    avlt.delete(35);
    assertFalse(avlt.search(35));
    assertArrayEquals(new Integer[] {25, 20, 40, 17, 21, 37, 45, 18}, avlt.levelOrderTraversal());
    // Test deletion that causes left-right imbalance.
    avlt.delete(21);
    assertFalse(avlt.search(21));
    assertArrayEquals(new Integer[] {25, 18, 40, 17, 20, 37, 45}, avlt.levelOrderTraversal());
    // Test deletion that causes simultaneous right-right and right-left imbalances.
    avlt.delete(18);
    assertFalse(avlt.search(18));
    assertArrayEquals(new Integer[] {25, 17, 40, 20, 37, 45}, avlt.levelOrderTraversal());
    avlt.delete(17);
    assertFalse(avlt.search(17));
    assertArrayEquals(new Integer[] {25, 20, 40, 37, 45}, avlt.levelOrderTraversal());
    avlt.delete(20);
    assertFalse(avlt.search(20));
    assertArrayEquals(new Integer[] {40, 25, 45, 37}, avlt.levelOrderTraversal());
    // Test deletion that causes left-left imbalance.
    avlt.delete(45);
    assertFalse(avlt.search(45));
    assertArrayEquals(new Integer[] {37, 25, 40}, avlt.levelOrderTraversal());
    // Test deletion that causes right-right imbalance.
    avlt.insert(55);
    avlt.delete(25);
    assertFalse(avlt.search(25));
    assertArrayEquals(new Integer[] {40, 37, 55}, avlt.levelOrderTraversal());
    // Test attempting to delete non-existent node.
    avlt.delete(50);
    assertFalse(avlt.search(50));
    assertArrayEquals(new Integer[] {40, 37, 55}, avlt.levelOrderTraversal());
    // Test deletion that requires multiple rounds of rebalancing.
    avlt.insert(35);
    avlt.insert(39);
    avlt.insert(50);
    avlt.insert(60);
    avlt.insert(30);
    avlt.insert(47);
    avlt.insert(53);
    avlt.insert(57);
    avlt.insert(62);
    avlt.insert(45);
    avlt.insert(49);
    avlt.insert(51);
    avlt.insert(54);
    avlt.insert(56);
    avlt.insert(58);
    avlt.insert(61);
    avlt.insert(65);
    avlt.delete(39);
    assertFalse(avlt.search(39));
    assertArrayEquals(new Integer[] {55, 40, 60, 35, 50, 57, 62, 30, 37, 47, 53, 56, 58, 61, 65, 45, 49, 51, 54}, avlt.levelOrderTraversal());
  }
}