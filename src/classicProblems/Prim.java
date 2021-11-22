package classicProblems;

import dataStructures.GraphGeneric;
import dataStructures.PriorityQueueHeap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Prim's algorithm to find a minimum spanning tree for a weighted, undirected
 * graph. Uses the custom PriorityQueueHeap class to ensure that algorithm execution runs in
 * O(|E|log(|V|)) time.
 */
public class Prim {

  /**
   * Prim's algorithm will return a set of Edge objects, representing the MST.
   * @param <E> the type of data contained in each node
   */
  public static class Edge<E> {
    private final GraphGeneric<E>.Node node1, node2;
    private final int weight;

    /**
     * Constructor for an Edge.
     * @param node1 one of the nodes connected by this edge
     * @param node2 the other node connected by this edge
     * @param weight the weight of this edge
     */
    Edge(GraphGeneric<E>.Node node1, GraphGeneric<E>.Node node2, int weight) {
      this.node1 = node1;
      this.node2 = node2;
      this.weight = weight;
    }

    /**
     * Gets the node assigned in the constructor as node1.
     * @return node1
     */
    public GraphGeneric<E>.Node getNode1() { return node1; }

    /**
     * Gets the node assigned in the constructor as node2.
     * @return node2
     */
    public GraphGeneric<E>.Node getNode2() { return node2; }

    /**
     * Gets the weight of this edge.
     * @return the weight of this edge
     */
    public int getWeight() { return weight; }
  }

  /**
   * Performs Prim's algorithm to find a minimum spanning tree of the given graph.
   * @param graph a graph
   * @param <E> the type of data contained in each node
   * @return a minimum spanning tree
   */
  public static <E> Set<Edge<E>> runPrim(GraphGeneric<E> graph) {
    PriorityQueueHeap<GraphGeneric<E>.Node> pq = new PriorityQueueHeap<>(graph.nodeCount());
    GraphGeneric<E>.Node start = null;
    // Add all nodes in the graph to the priority queue with a priority of "infinity".
    for (GraphGeneric<E>.Node node : graph.getNodes()) {
      if (start == null) start = node; // Set any node as the start node.
      pq.insert(node, Integer.MAX_VALUE);
    }
    Set<Edge<E>> minSpanningTree = new HashSet<>();
    // Map used to keep track of potential MST edges.
    Map<GraphGeneric<E>.Node, GraphGeneric<E>.Node> edgeMap = new HashMap<>();
    pq.changePriority(start, 0);
    while (!pq.isEmpty()) {
      GraphGeneric<E>.Node node = pq.getHighestPriority();
      int priority = pq.getPriority(node);
      pq.deleteHighestPriority();
      if (node != start) minSpanningTree.add(new Edge<>(node, edgeMap.get(node), priority));
      for (Map.Entry<GraphGeneric<E>.Node, Integer> next : graph.getNeighbors(node).entrySet()) {
        GraphGeneric<E>.Node neighbor = next.getKey();
        int weight = next.getValue();
        if (pq.contains(neighbor) && pq.getPriority(neighbor) > weight) {
          pq.changePriority(neighbor, weight);
          edgeMap.put(neighbor, node);
        }
      }
    }
    return minSpanningTree;
  }
}