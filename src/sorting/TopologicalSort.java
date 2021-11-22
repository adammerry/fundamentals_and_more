package sorting;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import dataStructures.GraphGeneric;

/**
 * Implementations of algorithms that produce a topological sort of nodes in a directed, acyclic
 * graph (DAG). A topological sort is an ordering of all nodes in a DAG such that for each edge
 * U->V, node U comes before node V in the ordering.
 */
public class TopologicalSort {



  /**
   * A queue-based topological sorting algorithm. This algorithm is perhaps the most common
   * method of implementing a topological sort. The general strategy is to repeatedly pick a node U
   * with 0 incoming edges, add U to the sorted list, and then decrease the number of incoming edges
   * for all nodes V for which there are edges U->V. If at any time before the full output list
   * has been constructed, there are no remaining nodes with 0 incoming edges, then it can be
   * concluded that the given graph contains a cycle, and therefore a topological sort is not
   * possible.
   * @param graph the graph on which the topological sort will be performed
   * @param <E> the type of data contained in the graph
   * @return a topological sort of the given graph, or null if no topological sort is possible
   */
  public static <E> List<GraphGeneric<E>.Node> topSortQueue(GraphGeneric<E> graph) {
    // Step 1: Construct the necessary maps:
    // - [node -> list of nodes to which this node has outgoing edges]
    // - [node -> number of incoming edges]
    Map<GraphGeneric<E>.Node, List<GraphGeneric<E>.Node>> outEdges = new HashMap<>();
    Map<GraphGeneric<E>.Node, Integer> inDegrees = new HashMap<>();
    for (GraphGeneric<E>.Node n : graph.getNodes()) {
      outEdges.put(n, new LinkedList<>());
      inDegrees.put(n, 0);
    }
    for (GraphGeneric<E>.Node n : graph.getNodes()) {
      for (GraphGeneric<E>.Node neighbor : graph.getNeighbors(n).keySet()) {
        outEdges.get(n).add(neighbor);
        inDegrees.put(neighbor, inDegrees.get(neighbor) + 1);
      }
    }
    // Step 2: Perform the queue-based sort.
    Queue<GraphGeneric<E>.Node> q = new LinkedList<>();
    for (Map.Entry<GraphGeneric<E>.Node, Integer> n : inDegrees.entrySet())
      if (n.getValue() == 0) q.offer(n.getKey());
    List<GraphGeneric<E>.Node> sort = new LinkedList<>();
    while (!q.isEmpty()) {
      GraphGeneric<E>.Node next = q.poll();
      sort.add(next);
      for (GraphGeneric<E>.Node neighbor : outEdges.get(next)) {
        inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
        if (inDegrees.get(neighbor) == 0) q.offer(neighbor);
      }
    }
    // If all nodes were placed in the sorted list, return the list. Otherwise, a cycle was
    // present in the given graph, so return null.
    return sort.size() == graph.getNodes().size() ? sort : null;
  }

  /**
   * A DFS-based topological sorting algorithm. This algorithm operates by performing a postorder
   * depth-first search from each node. Once all nodes have been added to the output list,
   * reversing the list will produce a topological sort. This algorithm has the advantage that it
   * is shorter and simpler to code, but also the disadvantage that it cannot detect cycles.
   * @param graph the (acyclic) graph on which the topological sort will be performed
   * @param <E> the type of data contained in the graph
   * @return a topological sort of the given graph
   */
  public static <E> List<GraphGeneric<E>.Node> topSortDFS(GraphGeneric<E> graph) {
    List<GraphGeneric<E>.Node> sort = new LinkedList<>();
    for (GraphGeneric<E>.Node n : graph.getNodes()) if (!n.seen()) dfsHelper(graph, n, sort);
    Collections.reverse(sort);
    return sort;
  }

  /**
   * Helper method for topSortDFS. Runs a postorder DFS from the given node.
   * @param graph the graph on which the topological sort is being performed
   * @param n the node currently being examined in the DFS
   * @param sort the current list of topologically sorted nodes
   * @param <E> the type of data contained in the graph
   */
  private static <E> void dfsHelper(GraphGeneric<E> graph, GraphGeneric<E>.Node n,
                                    List<GraphGeneric<E>.Node> sort) {
    n.setSeen(true);
    for (GraphGeneric<E>.Node neighbor : graph.getNeighbors(n).keySet())
      if (!neighbor.seen()) dfsHelper(graph, neighbor, sort);
    sort.add(n);
  }
}
