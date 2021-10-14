package classicProblems;

import dataStructures.GraphGeneric;
import dataStructures.PriorityQueueHeap;

import java.util.HashMap;
import java.util.Map;

// An implementation of Dijkstra's algorithm to find the shortest distances from a given source
// node to all other nodes in a graph. Uses the custom PriorityQueueHeap class to ensure all
// operations are performed in optimal runtime.
public class Dijkstra {

  // Returns a result in the form of [node -> value], where the value for each node represents the
  // minimum distance from the source to that node.
  public static <E> Map<GraphGeneric<E>.Node, Integer> runDijkstra(GraphGeneric<E> graph,
                                                                   GraphGeneric<E>.Node source) {
    Map<GraphGeneric<E>.Node, Integer> distances = new HashMap<>();
    distances.put(source, 0);
    PriorityQueueHeap<GraphGeneric<E>.Node> pq = new PriorityQueueHeap<>(graph.nodeCount());
    for (GraphGeneric<E>.Node node : graph.getNodes()) pq.insert(node, Integer.MAX_VALUE);
    pq.changePriority(source, 0);
    while (!pq.isEmpty()) {
      GraphGeneric<E>.Node nextMinNode = pq.getHighestPriority().getItem();
      distances.put(nextMinNode, pq.deleteHighestPriority().getPriority());
      updateNeighbors(graph, pq, nextMinNode, distances);
    }
    return distances;
  }

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
