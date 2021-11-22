package classicProblems;

import dataStructures.GraphGeneric;
import dataStructures.PriorityQueueHeap;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of Dijkstra's algorithm to find the shortest distance from a given source
 * node to every other node in a graph. Uses the custom PriorityQueueHeap class to ensure all
 * operations are performed in optimal runtime.
 */
public class Dijkstra {

  /**
   * Performs Dijkstra's algorithm to find the shortest distance from a given source node to every
   * other node in a graph.
   * @param graph a graph with no negative edge weights
   * @param source the source node
   * @param <E> the type of data contained in each node
   * @return the minimum distance to each node from the source node
   */
  public static <E> Map<GraphGeneric<E>.Node, Integer> runDijkstra(GraphGeneric<E> graph,
                                                                   GraphGeneric<E>.Node source) {
    Map<GraphGeneric<E>.Node, Integer> distances = new HashMap<>();
    distances.put(source, 0);
    PriorityQueueHeap<GraphGeneric<E>.Node> pq = new PriorityQueueHeap<>(graph.nodeCount());
    for (GraphGeneric<E>.Node node : graph.getNodes()) pq.insert(node, Integer.MAX_VALUE);
    pq.changePriority(source, 0);
    while (!pq.isEmpty()) {
      GraphGeneric<E>.Node nextMinNode = pq.getHighestPriority();
      int distance = pq.getPriority(nextMinNode);
      pq.deleteHighestPriority();
      distances.put(nextMinNode, distance);
      updateNeighbors(graph, pq, nextMinNode, distances);
    }
    return distances;
  }

  /**
   * Check all neighbors of the given node to see if finalizing this node's distance allows any
   * updates to the current distances of its neighbor nodes.
   * @param graph the graph given to the runDijkstra method
   * @param pq the priority queue of current node distances
   * @param node the node that has just had its distance finalized
   * @param distances the map of nodes and their final distances
   * @param <E> the type of data contained in each node
   */
  private static <E> void updateNeighbors(GraphGeneric<E> graph,
                                          PriorityQueueHeap<GraphGeneric<E>.Node> pq,
                                          GraphGeneric<E>.Node node,
                                          Map<GraphGeneric<E>.Node, Integer> distances) {
    for (GraphGeneric<E>.Node neighbor : graph.getNeighbors(node).keySet()) {
      int newDistance = distances.get(node) + graph.checkEdgeWeight(node, neighbor);
      if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
        distances.put(neighbor, newDistance);
        pq.changePriority(neighbor, newDistance);
      }
    }
  }
}
