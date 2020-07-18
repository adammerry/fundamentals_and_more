import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import dataStructures.BinarySearchTree;
import dataStructures.BinaryTree;
import dataStructures.MaxHeapCharacterRecursive;
import dataStructures.MinHeapIntegerIterative;
import dataStructures.RedBlackTree;
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
}