package dataStructures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// A better implementation of an undirected graph using an adjacency list representation. This
// implementation uses a Map as the container for the neighbor lists, which is an advantageous
// way to structure an adjacency list for the following reasons:
// - Adding a new node to the graph is O(1).
// - Nodes may be represented by any type of object.
public class GraphAdjacencyListBetter<E> {
  private Map<Node, List<Node>> adjMap;

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

    public void setSeen() {
      seen = true;
    }
  }

  public void addEdge(Node node1, Node node2) {
    if (!checkEdge(node1, node2)) {
      adjMap.get(node1).add(node2);
      adjMap.get(node2).add(node1);
    }
  }

  public void addNode(Node<E> node) {
    adjMap.put(node, new LinkedList<>());
  }

  public void removeEdge(Node node1, Node node2) {
    if (adjMap.containsKey(node1) && adjMap.get(node1).contains(node2)) {
        adjMap.get(node1).remove(node2);
        adjMap.get(node2).remove(node1);
    }
  }

  public void removeNode(Node node) {
    for (Node neighbor : adjMap.get(node)) {
      adjMap.get(neighbor).remove(node);
    }
    adjMap.remove(node);
  }

  public boolean checkEdge(Node node1, Node node2) {
    return adjMap.get(node1).contains(node2);
  }
}
