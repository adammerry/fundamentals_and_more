package dataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of a generic graph that uses a (modified) adjacency-list representation, and
 * can be treated as weighted or unweighted, and directed or undirected.
 * @param <E> the type of data contained in the graph
 */
public class GraphGeneric<E> {
  // [Node -> [Neighbor -> Weight]]
  private final Map<Node, Map<Node, Integer>> adjMap;
  private final boolean directed;

  /**
   * Constructor for GraphGeneric.
   * @param directed true if the graph is directed, false otherwise
   */
  public GraphGeneric(boolean directed) {
    adjMap = new HashMap<>();
    this.directed = directed;
  }

  /**
   * The nodes that make up the graph.
   */
  public class Node {
    private final E data;
    private boolean seen; // Useful for searching algorithms such as BFS and DFS.

    /**
     * Constructor for Node.
     * @param data the data contained in the node
     */
    public Node(E data) {
      this.data = data;
      seen = false;
    }

    /**
     * Gets the data contained in the node.
     * @return the data contained in the node
     */
    public E getData() { return data; }

    /**
     * Checks whether the node has been seen. Useful for searching algorithms.
     * @return true if the node has been seen, false otherwise
     */
    public boolean seen() { return seen; }

    /**
     * Sets the "seen" field to the given value. Useful for searching algorithms.
     * @param seen the value to which the "seen" field should be updated
     */
    public void setSeen(boolean seen) { this.seen = seen; }
  }

  /**
   * Adds a node with the given data to the graph.
   * @param data the data to add to the graph
   * @return the node added to the graph (for convenience and testing purposes)
   */
  public Node addNode(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    Node node = new Node(data);
    adjMap.put(node, new HashMap<>());
    return node;
  }

  /**
   * Adds an edge between the given nodes with a weight of 1.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   */
  public void addEdge(Node node1, Node node2) { addEdge(node1, node2, 1); }

  /**
   * Adds an edge between the given nodes with the given weight.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   * @param weight the weight of the new edge
   */
  public void addEdge(Node node1, Node node2, int weight) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot add edge between nonexistent nodes");
    adjMap.get(node1).put(node2, weight);
    if (!directed) adjMap.get(node2).put(node1, weight);
  }

  /**
   * Removes the edge between the given nodes, if it exists.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   */
  public void removeEdge(Node node1, Node node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot remove edge between nonexistent nodes");
    adjMap.get(node1).remove(node2);
    if (!directed) adjMap.get(node2).remove(node1);
  }

  /**
   * Removes the given node from the graph, if it exists.
   * @param node the node to remove
   */
  public void removeNode(Node node) {
    if (!adjMap.containsKey(node))
      throw new IllegalArgumentException("Cannot remove nonexistent node");
    for (Node neighbor : adjMap.get(node).keySet()) adjMap.get(neighbor).remove(node);
    adjMap.remove(node);
  }

  /**
   * Gets the weight of the edge between the given nodes.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   * @return the weight of the edge between the given nodes, or -1 if no edge exists
   */
  public int checkEdgeWeight(Node node1, Node node2) {
    return (adjMap.containsKey(node1) && adjMap.get(node1).containsKey(node2)) ?
            adjMap.get(node1).get(node2) : -1;
  }

  /**
   * Gets the number of nodes in the graph.
   * @return the number of nodes in the graph
   */
  public int nodeCount() { return adjMap.size(); }

  /**
   * Gets the neighbors of the given node.
   * @param node a node in the graph
   * @return a map containing each neighbor of the given node and the weight of the edge connecting
   * them, or null if the given node does not exist in the graph
   */
  public Map<Node, Integer> getNeighbors(Node node) { return adjMap.getOrDefault(node, null); }

  /**
   * Gets the nodes in the graph.
   * @return a set containing the nodes in the graph
   */
  public Set<Node> getNodes() { return adjMap.keySet(); }
}
