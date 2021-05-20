package classicProblems;

import dataStructures.GraphGeneric;

import java.util.HashMap;
import java.util.Map;

// An implementation of the Bellman-Ford algorithm to find the shortest distances from a given
// source node to all other nodes in a graph. Bellman-Ford is slower than Dijkstra's, but more
// versatile, as it can handle graphs with negative edge weights. It is also worth noting that
// Bellman-Ford requires the input graph to be directed. This restriction does not
// significantly limit its usefulness though, since undirected graphs can be transformed into
// directed graphs by simply treating each edge as two opposing directed edges. However, this
// approach must be done with caution, since any edge with a negative weight in an undirected
// graph will be transformed into a negative cycle in the corresponding directed graph - thus
// breaking Bellman-Ford.
public class BellmanFord {

  // Returns a result in the form of [node -> value], where the value for each node represents the
  // minimum distance from the source to that node. Returns null if the graph contains a negative
  // cycle.
  public static <E> Map<GraphGeneric.Node<E>, Integer> runBellmanFord(GraphGeneric<E> graph,
                                                                      GraphGeneric.Node<E> source) {
    Map<GraphGeneric.Node<E>, Integer> distances = new HashMap<>();
    int nodeCount = graph.getNodes().size();
    for (GraphGeneric.Node<E> node : graph.getNodes()) distances.put(node, Integer.MAX_VALUE);
    distances.put(source, 0);
    for (int i = 0; i < nodeCount - 1; i++) {
      for (GraphGeneric.Node<E> u : graph.getNodes()) {
        for (GraphGeneric.Node<E> v : graph.getNeighbors(u).keySet()) {
          Integer distU = distances.get(u), weight = graph.checkEdgeWeight(u, v);
          if (distU == Integer.MAX_VALUE) continue;
          if (distU + weight < distances.get(v)) distances.put(v, distU + weight);
        }
      }
    }
    // Check for negative cycles.
    for (GraphGeneric.Node<E> u : graph.getNodes()) {
      for (GraphGeneric.Node<E> v : graph.getNeighbors(u).keySet()) {
        Integer distU = distances.get(u), weight = graph.checkEdgeWeight(u, v);
        if (distU + weight < distances.get(v))
          throw new IllegalArgumentException("Graph cannot contain negative cycles");
      }
    }
    return distances;
  }
}