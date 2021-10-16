import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import dataStructures.GraphGeneric;
import sorting.CountingSort;
import sorting.HeapSort;
import sorting.InsertionSort;
import sorting.MergeSort;
import sorting.QuickSort;
import sorting.RadixSort;
import sorting.TopologicalSort;

public class SortingTests {
  private int[] l1, l2, l3, l4, l5, l6, l7, l8, s1, s2, s3, s4, s5, s6, s7, s8;
  private GraphGeneric<Integer> g;
  private GraphGeneric<Integer>.Node node1;
  private GraphGeneric<Integer>.Node node2;
  private GraphGeneric<Integer>.Node node3;
  private GraphGeneric<Integer>.Node node4;
  private GraphGeneric<Integer>.Node node5;
  private GraphGeneric<Integer>.Node node6;
  private GraphGeneric<Integer>.Node node7;
  private List<GraphGeneric<Integer>.Node> sort;

  @BeforeEach
  public void setUpArrays() {
    l1 = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    l2 = new int[] {0, 0, 0, 0, 0};
    l3 = new int[] {9, 8, 7, 6};
    l4 = new int[] {5, 8, 234, 457, 2, -7, 9, 2, 1};
    l5 = new int[] {};
    l6 = new int[] {19};
    l7 = null;
    l8 = new int[] {-2, -5, -11, -1};

    s1 = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    s2 = new int[] {0, 0, 0, 0, 0};
    s3 = new int[] {6, 7, 8, 9};
    s4 = new int[] {-7, 1, 2, 2, 5, 8, 9, 234, 457};
    s5 = new int[] {};
    s6 = new int[] {19};
    s7 = null;
    s8 = new int[] {-11, -5, -2, -1};
  }

  @BeforeEach
  public void setUpTopSort() {
    g = new GraphGeneric<>(true);
    sort = new LinkedList<>();
  }

  @Test
  public void testQuickSort() {
    QuickSort.sort(l1);
    QuickSort.sort(l2);
    QuickSort.sort(l3);
    QuickSort.sort(l4);
    QuickSort.sort(l5);
    QuickSort.sort(l6);
    assertThrows(IllegalArgumentException.class, () -> QuickSort.sort(l7));
    QuickSort.sort(l8);
    assertArrayEquals(s1, l1);
    assertArrayEquals(s2, l2);
    assertArrayEquals(s3, l3);
    assertArrayEquals(s4, l4);
    assertArrayEquals(s5, l5);
    assertArrayEquals(s6, l6);
    assertArrayEquals(s7, l7);
    assertArrayEquals(s8, l8);
  }

  @Test
  public void testMergeSort() {
    MergeSort.sort(l1);
    MergeSort.sort(l2);
    MergeSort.sort(l3);
    MergeSort.sort(l4);
    MergeSort.sort(l5);
    MergeSort.sort(l6);
    assertThrows(IllegalArgumentException.class, () -> MergeSort.sort(l7));
    MergeSort.sort(l8);
    assertArrayEquals(s1, l1);
    assertArrayEquals(s2, l2);
    assertArrayEquals(s3, l3);
    assertArrayEquals(s4, l4);
    assertArrayEquals(s5, l5);
    assertArrayEquals(s6, l6);
    assertArrayEquals(s7, l7);
    assertArrayEquals(s8, l8);
  }

  @Test
  public void testInsertionSort() {
    InsertionSort.sort(l1);
    InsertionSort.sort(l2);
    InsertionSort.sort(l3);
    InsertionSort.sort(l4);
    InsertionSort.sort(l5);
    InsertionSort.sort(l6);
    assertThrows(IllegalArgumentException.class, () -> InsertionSort.sort(l7));
    InsertionSort.sort(l8);
    assertArrayEquals(s1, l1);
    assertArrayEquals(s2, l2);
    assertArrayEquals(s3, l3);
    assertArrayEquals(s4, l4);
    assertArrayEquals(s5, l5);
    assertArrayEquals(s6, l6);
    assertArrayEquals(s7, l7);
    assertArrayEquals(s8, l8);
  }

  @Test
  public void testHeapSort() {
    HeapSort.sort(l1);
    HeapSort.sort(l2);
    HeapSort.sort(l3);
    HeapSort.sort(l4);
    HeapSort.sort(l5);
    HeapSort.sort(l6);
    assertThrows(IllegalArgumentException.class, () -> HeapSort.sort(l7));
    HeapSort.sort(l8);
    assertArrayEquals(s1, l1);
    assertArrayEquals(s2, l2);
    assertArrayEquals(s3, l3);
    assertArrayEquals(s4, l4);
    assertArrayEquals(s5, l5);
    assertArrayEquals(s6, l6);
    assertArrayEquals(s7, l7);
    assertArrayEquals(s8, l8);
  }

  @Test
  public void testCountingSort() {
    CountingSort.sort(l1);
    CountingSort.sort(l2);
    CountingSort.sort(l3);
    CountingSort.sort(l4);
    CountingSort.sort(l5);
    CountingSort.sort(l6);
    assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(l7));
    CountingSort.sort(l8);
    assertArrayEquals(s1, l1);
    assertArrayEquals(s2, l2);
    assertArrayEquals(s3, l3);
    assertArrayEquals(s4, l4);
    assertArrayEquals(s5, l5);
    assertArrayEquals(s6, l6);
    assertArrayEquals(s7, l7);
    assertArrayEquals(s8, l8);
  }

  @Test
  public void testRadixSort() {
    RadixSort.sort(l1);
    RadixSort.sort(l2);
    RadixSort.sort(l3);
    RadixSort.sort(l5);
    RadixSort.sort(l6);
    assertThrows(IllegalArgumentException.class, () -> RadixSort.sort(l7));
    assertThrows(IllegalArgumentException.class, () -> RadixSort.sort(l8));
    assertArrayEquals(s1, l1);
    assertArrayEquals(s2, l2);
    assertArrayEquals(s3, l3);
    assertArrayEquals(s5, l5);
    assertArrayEquals(s6, l6);
    assertArrayEquals(s7, l7);
  }

  @Test
  public void testTopologicalSortQueue() {
    assertEquals(sort, TopologicalSort.topSortQueue(g));
    node1 = g.addNode(1);
    sort.add(node1);
    assertEquals(sort, TopologicalSort.topSortQueue(g));
    node2 = g.addNode(2);
    node3 = g.addNode(3);
    node4 = g.addNode(4);
    node5 = g.addNode(5);
    node6 = g.addNode(6);
    node7 = g.addNode(7);
    // To prevent the possibility of failing a test by producing a valid topological ordering
    // that is different than the one the test is checking for, create a graph that has only one
    // valid ordering.
    g.addEdge(node1, node2);
    g.addEdge(node1, node3);
    g.addEdge(node1, node4);
    g.addEdge(node2, node7);
    g.addEdge(node3, node5);
    g.addEdge(node4, node2);
    g.addEdge(node4, node7);
    g.addEdge(node5, node4);
    g.addEdge(node5, node6);
    g.addEdge(node5, node7);
    g.addEdge(node6, node2);
    g.addEdge(node6, node4);
    sort.add(node3);
    sort.add(node5);
    sort.add(node6);
    sort.add(node4);
    sort.add(node2);
    sort.add(node7);
    assertEquals(sort, TopologicalSort.topSortQueue(g));
    // Test a graph that contains a cycle.
    g.addEdge(node2, node5);
    assertNull(TopologicalSort.topSortQueue(g));
  }

  @Test
  public void testTopologicalSortDFS() {
    assertEquals(sort, TopologicalSort.topSortDFS(g));
    node1 = g.addNode(1);
    sort.add(node1);
    assertEquals(sort, TopologicalSort.topSortDFS(g));
    node1.setSeen(false);
    node2 = g.addNode(2);
    node3 = g.addNode(3);
    node4 = g.addNode(4);
    node5 = g.addNode(5);
    node6 = g.addNode(6);
    node7 = g.addNode(7);
    // To prevent the possibility of failing a test by producing a valid topological ordering
    // that is different than the one the test is checking for, create a graph that has only one
    // valid ordering.
    g.addEdge(node1, node2);
    g.addEdge(node1, node3);
    g.addEdge(node1, node4);
    g.addEdge(node2, node7);
    g.addEdge(node3, node5);
    g.addEdge(node4, node2);
    g.addEdge(node4, node7);
    g.addEdge(node5, node4);
    g.addEdge(node5, node6);
    g.addEdge(node5, node7);
    g.addEdge(node6, node2);
    g.addEdge(node6, node4);
    sort.add(node3);
    sort.add(node5);
    sort.add(node6);
    sort.add(node4);
    sort.add(node2);
    sort.add(node7);
    assertEquals(sort, TopologicalSort.topSortDFS(g));
  }
}