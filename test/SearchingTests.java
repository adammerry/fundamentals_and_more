import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import dataStructures.GraphAdjacencyListBetter;
import dataStructures.BinaryTree;
import searching.Traversals;
import searching.BFS;

public class SearchingTests {

  @Test
  public void testTraversals() {
    Traversals t = new Traversals();
    assertArrayEquals(new Integer[]{4, 2, 8, 5, 9, 10, 1, 6, 3, 7}, t.listInorder().toArray());
    assertArrayEquals(new Integer[]{1, 2, 4, 5, 8, 9, 10, 3, 6, 7}, t.listPreorder().toArray());
    assertArrayEquals(new Integer[]{4, 8, 10, 9, 5, 2, 6, 7, 3, 1}, t.listPostorder().toArray());
    assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, t.listLevelOrder().toArray());
  }

  @Test
  public void testBFS() {
    GraphAdjacencyListBetter<Integer> graph = new GraphAdjacencyListBetter<>();
    GraphAdjacencyListBetter.Node<Integer> node1 = new GraphAdjacencyListBetter.Node<>(15);
    GraphAdjacencyListBetter.Node<Integer> node2 = new GraphAdjacencyListBetter.Node<>(75);
    GraphAdjacencyListBetter.Node<Integer> node3 = new GraphAdjacencyListBetter.Node<>(12);
    GraphAdjacencyListBetter.Node<Integer> node4 = new GraphAdjacencyListBetter.Node<>(22);
    GraphAdjacencyListBetter.Node<Integer> node5 = new GraphAdjacencyListBetter.Node<>(19);
    GraphAdjacencyListBetter.Node<Integer> node6 = new GraphAdjacencyListBetter.Node<>(74);
    GraphAdjacencyListBetter.Node<Integer> node7 = new GraphAdjacencyListBetter.Node<>(11);
    GraphAdjacencyListBetter.Node<Integer> node8 = new GraphAdjacencyListBetter.Node<>(29);
    GraphAdjacencyListBetter.Node<Integer> node9 = new GraphAdjacencyListBetter.Node<>(88);
    GraphAdjacencyListBetter.Node<Integer> node10 = new GraphAdjacencyListBetter.Node<>(3);
    GraphAdjacencyListBetter.Node<Integer> node11 = new GraphAdjacencyListBetter.Node<>(4);
    GraphAdjacencyListBetter.Node<Integer> node12 = new GraphAdjacencyListBetter.Node<>(1);
    graph.addNode(node1);
    graph.addNode(node2);
    graph.addNode(node3);
    graph.addNode(node4);
    graph.addNode(node5);
    graph.addNode(node6);
    graph.addNode(node7);
    graph.addNode(node8);
    graph.addNode(node9);
    graph.addNode(node10);
    graph.addNode(node11);
    graph.addNode(node12);
    graph.addEdge(node1, node2);
    graph.addEdge(node2, node3);
    graph.addEdge(node3, node4);
    graph.addEdge(node4, node5);
    graph.addEdge(node1, node5);
    graph.addEdge(node2, node4);
    graph.addEdge(node3, node6);
    graph.addEdge(node6, node7);
    graph.addEdge(node7, node8);
    graph.addEdge(node2, node8);
    graph.addEdge(node4, node7);
    graph.addEdge(node1, node7);
    graph.addEdge(node9, node10);
    graph.addEdge(node10, node11);
    graph.addEdge(node10, node12);

    BinaryTree<Integer> tree = new BinaryTree<>();
    tree.insert(0);
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);

    BFS bfs = new BFS(graph, tree);

    assertEquals(new Integer(15), bfs.bfsGraphWithSeenField(15).getData());
    assertEquals(new Integer(75), bfs.bfsGraphWithSeenField(75).getData());
    assertEquals(new Integer(12), bfs.bfsGraphWithSeenField(12).getData());
    assertEquals(new Integer(22), bfs.bfsGraphWithSeenField(22).getData());
    assertEquals(new Integer(19), bfs.bfsGraphWithSeenField(19).getData());
    assertEquals(new Integer(74), bfs.bfsGraphWithSeenField(74).getData());
    assertEquals(new Integer(11), bfs.bfsGraphWithSeenField(11).getData());
    assertEquals(new Integer(29), bfs.bfsGraphWithSeenField(29).getData());
    assertEquals(new Integer(88), bfs.bfsGraphWithSeenField(88).getData());
    assertEquals(new Integer(3), bfs.bfsGraphWithSeenField(3).getData());
    assertEquals(new Integer(4), bfs.bfsGraphWithSeenField(4).getData());
    assertEquals(new Integer(1), bfs.bfsGraphWithSeenField(1).getData());
    assertNull(bfs.bfsGraphWithSeenField(null));
    assertNull(bfs.bfsGraphWithSeenField(17));

    assertEquals(new Integer(15), bfs.bfsGraphWithSeenSet(15).getData());
    assertEquals(new Integer(75), bfs.bfsGraphWithSeenSet(75).getData());
    assertEquals(new Integer(12), bfs.bfsGraphWithSeenSet(12).getData());
    assertEquals(new Integer(22), bfs.bfsGraphWithSeenSet(22).getData());
    assertEquals(new Integer(19), bfs.bfsGraphWithSeenSet(19).getData());
    assertEquals(new Integer(74), bfs.bfsGraphWithSeenSet(74).getData());
    assertEquals(new Integer(11), bfs.bfsGraphWithSeenSet(11).getData());
    assertEquals(new Integer(29), bfs.bfsGraphWithSeenSet(29).getData());
    assertEquals(new Integer(88), bfs.bfsGraphWithSeenSet(88).getData());
    assertEquals(new Integer(3), bfs.bfsGraphWithSeenSet(3).getData());
    assertEquals(new Integer(4), bfs.bfsGraphWithSeenSet(4).getData());
    assertEquals(new Integer(1), bfs.bfsGraphWithSeenSet(1).getData());
    assertNull(bfs.bfsGraphWithSeenSet(null));
    assertNull(bfs.bfsGraphWithSeenSet(17));

    assertEquals(new Integer(1), bfs.bfsTree(1).getData());
    assertEquals(new Integer(2), bfs.bfsTree(2).getData());
    assertEquals(new Integer(3), bfs.bfsTree(3).getData());
    assertEquals(new Integer(4), bfs.bfsTree(4).getData());
    assertEquals(new Integer(5), bfs.bfsTree(5).getData());
    assertEquals(new Integer(6), bfs.bfsTree(6).getData());
    assertEquals(new Integer(7), bfs.bfsTree(7).getData());
    assertEquals(new Integer(8), bfs.bfsTree(8).getData());
    assertNull(bfs.bfsTree(null));
    assertNull(bfs.bfsTree(17));
  }

  @Test
  public void testDFS() {

  }
}
