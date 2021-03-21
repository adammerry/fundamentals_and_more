package sorting;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import dataStructures.GraphGeneric;

// Implementations of algorithms that produce a topological sort of nodes in a directed, acyclic
// graph (DAG). A topological sort is an ordering of all nodes in a DAG such that for each edge
// U->V, node U comes before node V in the ordering.
public class TopologicalSort {

  // A queue-based topological sorting algorithm. This algorithm is perhaps the most common
  // method of implementing a topological sort. The general strategy is to repeatedly pick a node U
  // with 0 incoming edges, add U to the sorted list, and then decrease the number of incoming edges
  // for all nodes V for which there are edges U->V. If at any time before the full output list
  // has been constructed, there are no remaining nodes with 0 incoming edges, then it can be
  // concluded that the given graph contains a cycle, and therefore a topological sort is not
  // possible.
  public static <E> List<GraphGeneric.Node<E>> topSortQueue(GraphGeneric<E> graph) {
    // Step 1: Construct the necessary maps:
    // - [node -> list of nodes to which this node has outgoing edges]
    // - [node -> number of incoming edges]
    Map<GraphGeneric.Node<E>, List<GraphGeneric.Node<E>>> outEdges = new HashMap<>();
    Map<GraphGeneric.Node<E>, Integer> inDegrees = new HashMap<>();
    for (GraphGeneric.Node<E> n : graph.getNodes()) {
      outEdges.put(n, new LinkedList<>());
      inDegrees.put(n, 0);
    }
    for (GraphGeneric.Node<E> n : graph.getNodes()) {
      for (GraphGeneric.Node<E> neighbor : graph.getNeighbors(n).keySet()) {
        outEdges.get(n).add(neighbor);
        inDegrees.put(neighbor, inDegrees.get(neighbor) + 1);
      }
    }
    // Step 2: Perform the queue-based sort.
    Queue<GraphGeneric.Node<E>> q = new LinkedList<>();
    for (Map.Entry<GraphGeneric.Node<E>, Integer> n : inDegrees.entrySet())
      if (n.getValue() == 0) q.offer(n.getKey());
    List<GraphGeneric.Node<E>> sort = new LinkedList<>();
    while (!q.isEmpty()) {
      GraphGeneric.Node<E> next = q.poll();
      sort.add(next);
      for (GraphGeneric.Node<E> neighbor : outEdges.get(next)) {
        inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
        if (inDegrees.get(neighbor) == 0) q.offer(neighbor);
      }
    }
    // If all nodes were placed in the sorted list, return the list. Otherwise, a cycle was
    // present in the given graph, so return null.
    return sort.size() == graph.getNodes().size() ? sort : null;
  }

  // A DFS-based topological sorting algorithm. This algorithm operates by performing a post-order
  // depth-first search from each node. Once all nodes have been added to the output list,
  // reversing the list will produce a topological sort. This algorithm has the advantage that it
  // is shorter and simpler to code, but also the disadvantage that it cannot detect cycles.
  public static <E> List<GraphGeneric.Node<E>> topSortDFS(GraphGeneric<E> graph) {
    List<GraphGeneric.Node<E>> sort = new LinkedList<>();
    for (GraphGeneric.Node<E> n : graph.getNodes()) if (!n.hasBeenSeen()) dfsHelper(graph, n, sort);
    Collections.reverse(sort);
    return sort;
  }

  private static <E> void dfsHelper(GraphGeneric<E> graph, GraphGeneric.Node<E> n,
                                    List<GraphGeneric.Node<E>> sort) {
    n.setSeen(true);
    for (GraphGeneric.Node<E> neighbor : graph.getNeighbors(n).keySet())
      if (!neighbor.hasBeenSeen()) dfsHelper(graph, neighbor, sort);
    sort.add(n);
  }
}
