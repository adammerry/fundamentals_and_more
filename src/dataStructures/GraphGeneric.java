package dataStructures;

import java.util.HashMap;
import java.util.Map;

// An implementation of a generic graph that uses a (modified) adjacency-list representation, and
// can be treated as weighted or unweighted, and directed or undirected.
public class GraphGeneric<E> {
  // [Node -> [Neighbor -> Weight]]
  private Map<Node<E>, Map<Node<E>, Integer>> adjMap;
  private boolean directed;

  public GraphGeneric(boolean directed) {
    adjMap = new HashMap<>();
    this.directed = directed;
  }

  public static class Node<E> {
    private E data;
    private boolean seen; // Useful for searching algorithms such as BFS and DFS.

    public Node(E data) {
      this.data = data;
      seen = false;
    }

    public E getData() { return data; }

    public boolean hasBeenSeen() { return seen; }

    public void setSeen(boolean seen) { this.seen = seen; }
  }

  public Map<Node<E>, Map<Node<E>, Integer>> getGraph() { return adjMap; }

  public void addNode(Node<E> node) { adjMap.put(node, new HashMap<>()); }

  public void addEdge(Node<E> node1, Node<E> node2) {
    addEdge(node1, node2, 1);
  }

  public void addEdge(Node<E> node1, Node<E> node2, int weight) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      System.out.println("Cannot add edge between nonexistent nodes.");
    else {
      adjMap.get(node1).put(node2, weight);
      if (!directed) adjMap.get(node2).put(node1, weight);
    }
  }

  public void removeEdge(Node<E> node1, Node<E> node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      System.out.println("Cannot remove edge between nonexistent nodes.");
    else {
      adjMap.get(node1).remove(node2);
      if (!directed) adjMap.get(node2).remove(node1);
    }
  }

  public void removeNode(Node<E> node) {
    if (!adjMap.containsKey(node))
      System.out.println("Cannot remove nonexistent node.");
    else {
      for (Node<E> neighbor : adjMap.get(node).keySet()) adjMap.get(neighbor).remove(node);
      adjMap.remove(node);
    }
  }

  public int checkEdgeWeight(Node<E> node1, Node<E> node2) {
    return (adjMap.containsKey(node1) && adjMap.get(node1).containsKey(node2)) ?
            adjMap.get(node1).get(node2) : -1;
  }
}
