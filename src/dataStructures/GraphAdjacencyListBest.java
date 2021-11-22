package dataStructures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of an unweighted, undirected graph that improves upon GraphAdjacencyListBetter
 * by using HashSets rather than LinkedLists as the data structure for neighbor lists, which
 * allows the following operations to be done in constant time:
 * - Checking if an edge exists between two nodes
 * - Adding an edge between two nodes
 * @param <E> the type of data contained in the graph
 */
public class GraphAdjacencyListBest<E> {
  private final Map<Node, Set<Node>> adjMap;

  /**
   * Constructor for GraphAdjacencyListBest.
   */
  public GraphAdjacencyListBest() { adjMap = new HashMap<>(); }

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
   * Gets the adjacency list used to represent the graph.
   * @return the adjacency list
   */
  public Map<Node, Set<Node>> getGraph() { return adjMap; }

  /**
   * Adds a node with the given data to the graph.
   * @param data the data to add to the graph
   * @return the node added to the graph (for convenience and testing purposes)
   */
  public Node addNode(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    Node node = new Node(data);
    adjMap.put(node, new HashSet<>());
    return node;
  }

  /**
   * Adds an edge between the given nodes.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   */
  public void addEdge(Node node1, Node node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot add edge between nonexistent nodes");
    adjMap.get(node1).add(node2);
    adjMap.get(node2).add(node1);
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
    adjMap.get(node2).remove(node1);
  }

  /**
   * Removes the given node from the graph, if it exists.
   * @param node the node to remove
   */
  public void removeNode(Node node) {
    if (!adjMap.containsKey(node))
      throw new IllegalArgumentException("Cannot remove nonexistent node");
    for (Node neighbor : adjMap.get(node)) adjMap.get(neighbor).remove(node);
    adjMap.remove(node);
  }

  /**
   * Determines whether the given nodes are connected by an edge.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   * @return true if the given nodes are connected by an edge, otherwise false
   */
  public boolean checkEdge(Node node1, Node node2) {
    return adjMap.containsKey(node1) && adjMap.get(node1).contains(node2);
  }
}
