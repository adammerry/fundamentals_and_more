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
  private Map<Node<E>, Set<Node<E>>> adjMap;

  public GraphAdjacencyListBest() {
    adjMap = new HashMap<>();
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

  public Map<Node<E>, Set<Node<E>>> getGraph() { return adjMap; }

  public void addNode(Node<E> node) {
    adjMap.put(node, new HashSet<>());
  }

  public void addEdge(Node<E> node1, Node<E> node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2))) {
      System.out.println("Cannot add edge between nonexistent nodes.");
      return;
    }
    adjMap.get(node1).add(node2);
    adjMap.get(node2).add(node1);
  }

  public void removeEdge(Node<E> node1, Node<E> node2) {
    if (!(adjMap.containsKey(node1) && adjMap.containsKey(node2))) {
      System.out.println("Cannot remove edge between nonexistent nodes.");
      return;
    }
    adjMap.get(node1).remove(node2);
    adjMap.get(node2).remove(node1);
  }

  public void removeNode(Node<E> node) {
    if (!adjMap.containsKey(node)) System.out.println("Cannot remove nonexistent node.");
    else {
      for (Node<E> neighbor : adjMap.get(node)) adjMap.get(neighbor).remove(node);
      adjMap.remove(node);
    }
  }

  public boolean checkEdge(Node<E> node1, Node<E> node2) {
    return adjMap.containsKey(node1) && adjMap.get(node1).contains(node2);
  }
}
