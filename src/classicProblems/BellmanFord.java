package classicProblems;

import dataStructures.GraphGeneric;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Bellman-Ford algorithm to find the shortest distance from a given
 * source node to every other node in a graph. Bellman-Ford is slower than Dijkstra's, but more
 * versatile, as it can handle graphs with negative edge weights. It is also worth noting that
 * Bellman-Ford requires the input graph to be directed. This restriction does not significantly
 * limit its usefulness though, since undirected graphs can be transformed into directed graphs
 * by simply treating each edge as two opposing directed edges. However, this approach must be done
 * with caution, since any edge with a negative weight in an undirected graph will be transformed
 * into a negative cycle in the corresponding directed graph - thus breaking Bellman-Ford.
 */
public class BellmanFord {

  /**
   * Performs the Bellman-Ford algorithm to find the shortest distance from a source node to every
   * other node in the graph.
   * @param graph a directed graph with no negative cycles
   * @param source the source node
   * @param <E> the type of data contained in each node
   * @return the minimum distance to each node from the source node
   */
  public static <E> Map<GraphGeneric<E>.Node, Integer> runBellmanFord(GraphGeneric<E> graph,
                                                                      GraphGeneric<E>.Node source) {
    Map<GraphGeneric<E>.Node, Integer> distances = new HashMap<>();
    int nodeCount = graph.getNodes().size();
    for (GraphGeneric<E>.Node node : graph.getNodes()) distances.put(node, Integer.MAX_VALUE);
    distances.put(source, 0);
    for (int i = 0; i < nodeCount - 1; i++) {
      for (GraphGeneric<E>.Node u : graph.getNodes()) {
        for (GraphGeneric<E>.Node v : graph.getNeighbors(u).keySet()) {
          Integer distU = distances.get(u), weight = graph.checkEdgeWeight(u, v);
          if (distU == Integer.MAX_VALUE) continue;
          if (distU + weight < distances.get(v)) distances.put(v, distU + weight);
        }
      }
    }
    // Check for negative cycles by performing one additional iteration of the algorithm, and
    // seeing if any distances decrease.
    for (GraphGeneric<E>.Node u : graph.getNodes()) {
      for (GraphGeneric<E>.Node v : graph.getNeighbors(u).keySet()) {
        Integer distU = distances.get(u), weight = graph.checkEdgeWeight(u, v);
        if (distU + weight < distances.get(v))
          throw new IllegalArgumentException("Graph cannot contain negative cycles");
      }
    }
    return distances;
  }
}