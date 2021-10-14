import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBetter;
import searching.BFS;
import searching.BinarySearch;
import searching.DFS;
import searching.Traversals;

public class SearchingTests {
  private GraphAdjacencyListBetter<Integer> graph;
  private GraphAdjacencyListBetter<Integer>.Node node1;
  private GraphAdjacencyListBetter<Integer>.Node node2;
  private GraphAdjacencyListBetter<Integer>.Node node3;
  private GraphAdjacencyListBetter<Integer>.Node node4;
  private GraphAdjacencyListBetter<Integer>.Node node5;
  private GraphAdjacencyListBetter<Integer>.Node node6;
  private GraphAdjacencyListBetter<Integer>.Node node7;
  private GraphAdjacencyListBetter<Integer>.Node node8;
  private GraphAdjacencyListBetter<Integer>.Node node9;
  private GraphAdjacencyListBetter<Integer>.Node node10;
  private GraphAdjacencyListBetter<Integer>.Node node11;
  private GraphAdjacencyListBetter<Integer>.Node node12;
  private BinaryTree<Integer> tree;

  @BeforeEach
  public void setUpGraphAndTree() {
    graph = new GraphAdjacencyListBetter<>();
    node1 = graph.addNode(15);
    node2 = graph.addNode(75);
    node3 = graph.addNode(12);
    node4 = graph.addNode(22);
    node5 = graph.addNode(19);
    node6 = graph.addNode(74);
    node7 = graph.addNode(11);
    node8 = graph.addNode(29);
    node9 = graph.addNode(88);
    node10 = graph.addNode(3);
    node11 = graph.addNode(4);
    node12 = graph.addNode(1);
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
    tree = new BinaryTree<>();
    tree.insert(0);
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);
  }

  @Test
  public void testTraversals() {
    Traversals t = new Traversals();
    assertArrayEquals(new Integer[]{8, 4, 9, 2, 10, 5, 1, 6, 3, 7}, t.listInorder().toArray());
    assertArrayEquals(new Integer[]{1, 2, 4, 8, 9, 5, 10, 3, 6, 7}, t.listPreorder().toArray());
    assertArrayEquals(new Integer[]{8, 9, 4, 10, 5, 2, 6, 7, 3, 1}, t.listPostorder().toArray());
    assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, t.listLevelOrder().toArray());
  }

  @Test
  public void testBFS() {
    BFS bfs = new BFS(graph, tree);
    // Test bfsGraphWithSeenField().
    assertTrue(bfs.bfsGraphWithSeenField(15));
    assertTrue(bfs.bfsGraphWithSeenField(75));
    assertTrue(bfs.bfsGraphWithSeenField(12));
    assertTrue(bfs.bfsGraphWithSeenField(22));
    assertTrue(bfs.bfsGraphWithSeenField(19));
    assertTrue(bfs.bfsGraphWithSeenField(74));
    assertTrue(bfs.bfsGraphWithSeenField(11));
    assertTrue(bfs.bfsGraphWithSeenField(29));
    assertTrue(bfs.bfsGraphWithSeenField(88));
    assertTrue(bfs.bfsGraphWithSeenField(3));
    assertTrue(bfs.bfsGraphWithSeenField(4));
    assertTrue(bfs.bfsGraphWithSeenField(1));
    assertFalse(bfs.bfsGraphWithSeenField(null));
    assertFalse(bfs.bfsGraphWithSeenField(17));
    // Test bfsGraphWithSeenSet().
    assertTrue(bfs.bfsGraphWithSeenSet(15));
    assertTrue(bfs.bfsGraphWithSeenSet(75));
    assertTrue(bfs.bfsGraphWithSeenSet(12));
    assertTrue(bfs.bfsGraphWithSeenSet(22));
    assertTrue(bfs.bfsGraphWithSeenSet(19));
    assertTrue(bfs.bfsGraphWithSeenSet(74));
    assertTrue(bfs.bfsGraphWithSeenSet(11));
    assertTrue(bfs.bfsGraphWithSeenSet(29));
    assertTrue(bfs.bfsGraphWithSeenSet(88));
    assertTrue(bfs.bfsGraphWithSeenSet(3));
    assertTrue(bfs.bfsGraphWithSeenSet(4));
    assertTrue(bfs.bfsGraphWithSeenSet(1));
    assertFalse(bfs.bfsGraphWithSeenSet(null));
    assertFalse(bfs.bfsGraphWithSeenSet(17));
    // Test bfsTree().
    assertTrue(bfs.bfsTree(1));
    assertTrue(bfs.bfsTree(2));
    assertTrue(bfs.bfsTree(3));
    assertTrue(bfs.bfsTree(4));
    assertTrue(bfs.bfsTree(5));
    assertTrue(bfs.bfsTree(6));
    assertTrue(bfs.bfsTree(7));
    assertTrue(bfs.bfsTree(8));
    assertFalse(bfs.bfsTree(null));
    assertFalse(bfs.bfsTree(17));
  }

  @Test
  public void testDFS() {
    DFS dfs = new DFS(graph, tree);
    // Test dfsGraphIterative().
    assertTrue(dfs.dfsGraphIterative(15));
    assertTrue(dfs.dfsGraphIterative(75));
    assertTrue(dfs.dfsGraphIterative(12));
    assertTrue(dfs.dfsGraphIterative(22));
    assertTrue(dfs.dfsGraphIterative(19));
    assertTrue(dfs.dfsGraphIterative(74));
    assertTrue(dfs.dfsGraphIterative(11));
    assertTrue(dfs.dfsGraphIterative(29));
    assertTrue(dfs.dfsGraphIterative(88));
    assertTrue(dfs.dfsGraphIterative(3));
    assertTrue(dfs.dfsGraphIterative(4));
    assertTrue(dfs.dfsGraphIterative(1));
    assertFalse(dfs.dfsGraphIterative(null));
    assertFalse(dfs.dfsGraphIterative(17));
    // Test dfsGraphRecursive().
    assertTrue(dfs.dfsGraphRecursive(15));
    assertTrue(dfs.dfsGraphRecursive(75));
    assertTrue(dfs.dfsGraphRecursive(12));
    assertTrue(dfs.dfsGraphRecursive(22));
    assertTrue(dfs.dfsGraphRecursive(19));
    assertTrue(dfs.dfsGraphRecursive(74));
    assertTrue(dfs.dfsGraphRecursive(11));
    assertTrue(dfs.dfsGraphRecursive(29));
    assertTrue(dfs.dfsGraphRecursive(88));
    assertTrue(dfs.dfsGraphRecursive(3));
    assertTrue(dfs.dfsGraphRecursive(4));
    assertTrue(dfs.dfsGraphRecursive(1));
    assertFalse(dfs.dfsGraphRecursive(null));
    assertFalse(dfs.dfsGraphRecursive(17));
    // Test dfsTreeIterative().
    assertTrue(dfs.dfsTreeIterative(1));
    assertTrue(dfs.dfsTreeIterative(2));
    assertTrue(dfs.dfsTreeIterative(3));
    assertTrue(dfs.dfsTreeIterative(4));
    assertTrue(dfs.dfsTreeIterative(5));
    assertTrue(dfs.dfsTreeIterative(6));
    assertTrue(dfs.dfsTreeIterative(7));
    assertTrue(dfs.dfsTreeIterative(8));
    assertFalse(dfs.dfsTreeIterative(null));
    assertFalse(dfs.dfsTreeIterative(17));
    // Test dfsTreeRecursive().
    assertTrue(dfs.dfsTreeRecursive(1));
    assertTrue(dfs.dfsTreeRecursive(2));
    assertTrue(dfs.dfsTreeRecursive(3));
    assertTrue(dfs.dfsTreeRecursive(4));
    assertTrue(dfs.dfsTreeRecursive(5));
    assertTrue(dfs.dfsTreeRecursive(6));
    assertTrue(dfs.dfsTreeRecursive(7));
    assertTrue(dfs.dfsTreeRecursive(8));
    assertFalse(dfs.dfsTreeRecursive(null));
    assertFalse(dfs.dfsTreeRecursive(17));
  }

  @Test
  public void testBinarySearch() {
    assertEquals(-1, BinarySearch.binarySearch(new int[]{}, 1));
    assertEquals(0, BinarySearch.binarySearch(new int[]{1}, 1));
    assertEquals(-1, BinarySearch.binarySearch(new int[]{2}, 1));
    assertEquals(5, BinarySearch.binarySearch(new int[]{1,2,3,4,5,6,7,8,9}, 6));
    assertEquals(-1, BinarySearch.binarySearch(new int[]{1,2,3,4,5,6,8,9,10}, 7));
    assertEquals(-1, BinarySearch.binarySearch(new int[]{9,22,35,67,80}, -2));
    assertEquals(-1, BinarySearch.binarySearch(new int[]{9,22,35,67,80}, 86));
    assertEquals(4, BinarySearch.binarySearch(new int[]{9,22,35,67,80}, 80));
    assertEquals(0, BinarySearch.binarySearch(new int[]{9,22,35,67,80}, 9));
  }
}
