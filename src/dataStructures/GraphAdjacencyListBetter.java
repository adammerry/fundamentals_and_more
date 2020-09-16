package dataStructures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// A better implementation of an undirected, unweighted graph using an adjacency list
// representation. This implementation uses a Map as the container for the neighbor lists, which
// is an advantageous way to structure an adjacency list for the following reasons:
// - Adding a new node to the graph is O(1).
// - Nodes may be represented by any type of object.
public class GraphAdjacencyListBetter<E> {
  private Map<Node<E>, List<Node<E>>> adjMap;

  public GraphAdjacencyListBetter() {
    adjMap = new HashMap<>();
  }

  public static class Node<E> {
    private E data;
    private boolean seen;

    public Node(E data) {
      this.data = data;
      seen = false;
    }

    public E getData() { return data; }

    public boolean hasBeenSeen() { return seen; }

    public void setSeen(boolean seen) { this.seen = seen; }
  }

  public Map<Node<E>, List<Node<E>>> getGraph() { return adjMap; }

  public void addEdge(Node<E> node1, Node<E> node2) {
    if (!adjMap.get(node1).contains(node2)) {
      adjMap.get(node1).add(node2);
      adjMap.get(node2).add(node1);
    }
  }

  public void addNode(Node<E> node) {
    adjMap.put(node, new LinkedList<>());
  }

  public void removeEdge(Node<E> node1, Node<E> node2) {
    if (adjMap.containsKey(node1) && adjMap.get(node1).contains(node2)) {
        adjMap.get(node1).remove(node2);
        adjMap.get(node2).remove(node1);
    }
  }

  public void removeNode(Node<E> node) {
    for (Node<E> neighbor : adjMap.get(node)) adjMap.get(neighbor).remove(node);
    adjMap.remove(node);
  }

  public boolean checkEdge(Node<E> node1, Node<E> node2) {
    return adjMap.get(node1).contains(node2);
  }
}
