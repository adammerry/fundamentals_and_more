package dataStructures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// An implementation of an unweighted, undirected graph that improves upon GraphAdjacencyListBetter
// by using HashSets rather than LinkedLists as the data structure for neighbor lists, which
// allows the following operations to be done in constant time:
// - Checking if an edge exists between two nodes
// - Adding an edge between two nodes
public class GraphAdjacencyListBest<E> {
  private final Map<Node, Set<Node>> adjMap;

  public GraphAdjacencyListBest() { adjMap = new HashMap<>(); }

  public class Node {
    private final E data;
    private boolean seen; // Useful for searching algorithms such as BFS and DFS.

    public Node(E data) {
      this.data = data;
      seen = false;
    }

    public E getData() { return data; }

    public boolean seen() { return seen; }

    public void setSeen(boolean seen) { this.seen = seen; }
  }

  public Map<Node, Set<Node>> getGraph() { return adjMap; }

  public Node addNode(E data) {
    Node node = new Node(data);
    adjMap.put(node, new HashSet<>());
    return node;
  }

  public void addEdge(Node node1, Node node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot add edge between nonexistent nodes");
    adjMap.get(node1).add(node2);
    adjMap.get(node2).add(node1);
  }

  public void removeEdge(Node node1, Node node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2)))
      throw new IllegalArgumentException("Cannot remove edge between nonexistent nodes");
    adjMap.get(node1).remove(node2);
    adjMap.get(node2).remove(node1);
  }

  public void removeNode(Node node) {
    if (!adjMap.containsKey(node))
      throw new IllegalArgumentException("Cannot remove nonexistent node");
    for (Node neighbor : adjMap.get(node)) adjMap.get(neighbor).remove(node);
    adjMap.remove(node);
  }

  public boolean checkEdge(Node node1, Node node2) {
    return adjMap.containsKey(node1) && adjMap.get(node1).contains(node2);
  }
}
