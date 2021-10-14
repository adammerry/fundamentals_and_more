package dataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// An implementation of a generic graph that uses a (modified) adjacency-list representation, and
// can be treated as weighted or unweighted, and directed or undirected.
public class GraphGeneric<E> {
  // [Node -> [Neighbor -> Weight]]
  private Map<Node, Map<Node, Integer>> adjMap;
  private boolean directed;

  public GraphGeneric(boolean directed) {
    adjMap = new HashMap<>();
    this.directed = directed;
  }

  public class Node {
    private E data;
    private boolean seen; // Useful for searching algorithms such as BFS and DFS.

    public Node(E data) {
      this.data = data;
      seen = false;
    }

    public E getData() { return data; }

    public boolean seen() { return seen; }

    public void setSeen(boolean seen) { this.seen = seen; }
  }

  public Node addNode(E data) {
    Node node = new Node(data);
    adjMap.put(node, new HashMap<>());
    return node;
  }

  public void addEdge(Node node1, Node node2) { addEdge(node1, node2, 1); }

  public void addEdge(Node node1, Node node2, int weight) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot add edge between nonexistent nodes");
    adjMap.get(node1).put(node2, weight);
    if (!directed) adjMap.get(node2).put(node1, weight);
  }

  public void removeEdge(Node node1, Node node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot remove edge between nonexistent nodes");
    adjMap.get(node1).remove(node2);
    if (!directed) adjMap.get(node2).remove(node1);
  }

  public void removeNode(Node node) {
    if (!adjMap.containsKey(node))
      throw new IllegalArgumentException("Cannot remove nonexistent node");
    for (Node neighbor : adjMap.get(node).keySet()) adjMap.get(neighbor).remove(node);
    adjMap.remove(node);
  }

  public int checkEdgeWeight(Node node1, Node node2) {
    return (adjMap.containsKey(node1) && adjMap.get(node1).containsKey(node2)) ?
            adjMap.get(node1).get(node2) : -1;
  }

  public int nodeCount() { return adjMap.size(); }

  public Map<Node, Integer> getNeighbors(Node node) {
    return adjMap.getOrDefault(node, null);
  }

  public Set<Node> getNodes() { return adjMap.keySet(); }
}
