package searching;

import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBetter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Implementations of breadth-first search on a graph and a tree. The graph version is
 * slightly more complex, as it must handle cycles as well as disconnected graphs. All BFS
 * implementations are optimized such that nodes are checked before they are added to the queue, as
 * opposed to after they are removed from the queue. This optimization will often reduce the
 * number of iterations needed to find the given node, since it avoids keeping the desired node
 * in the queue while the rest of the nodes in the previous level are examined.
 * @param <E> the type of data contained in the graph and tree being searched
 */
public class BFS<E> {
  private final GraphAdjacencyListBetter<E> graph;
  private final BinaryTree<E> tree;

  /**
   * Constructor that provides the graph and tree to search.
   * @param graph the graph to search
   * @param tree the tree to search
   */
  public BFS(GraphAdjacencyListBetter<E> graph, BinaryTree<E> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  /**
   * Runs a BFS on a graph, where nodes are assumed to offer a boolean "seen" field that can be set
   * to reflect whether a particular node has been visited in the BFS.
   * @param searchVal the value to search for
   * @return true if the value is present in the graph, false otherwise
   */
  public boolean bfsGraphWithSeenField(E searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter<E>.Node,
              List<GraphAdjacencyListBetter<E>.Node>> adjMap = graph.getGraph();
      // Initialize all nodes as not seen.
      for (GraphAdjacencyListBetter<E>.Node node : adjMap.keySet()) node.setSeen(false);
      for (GraphAdjacencyListBetter<E>.Node node : adjMap.keySet()) {
        if (!node.seen()) {
          if (node.getData().equals(searchVal)) return true;
          node.setSeen(true);
          if (startNewSearch(searchVal, node, adjMap)) return true;
        }
      }
    }
    return false;
  }

  /**
   * Helper method for bfsGraphWithSeenField. Begins a new BFS from the given node in the graph.
   * @param searchVal the value to search for
   * @param root the node at which the BFS will start
   * @param adjMap a map containing the list of neighbors for each node
   * @return true if the value was found through the BFS, false otherwise
   */
  private boolean startNewSearch(E searchVal, GraphAdjacencyListBetter<E>.Node root,
                                 Map<GraphAdjacencyListBetter<E>.Node,
                                         List<GraphAdjacencyListBetter<E>.Node>> adjMap) {
    Queue<GraphAdjacencyListBetter<E>.Node> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      GraphAdjacencyListBetter<E>.Node nextNode = q.poll();
      List<GraphAdjacencyListBetter<E>.Node> neighbors = adjMap.get(nextNode);
      for (GraphAdjacencyListBetter<E>.Node neighbor : neighbors) {
        if (!neighbor.seen()) {
          if (neighbor.getData().equals(searchVal)) return true;
          neighbor.setSeen(true);
          q.add(neighbor);
        }
      }
    }
    return false;
  }

  /**
   * Runs a BFS on a graph, where nodes are *not* assumed to offer a "seen" boolean field that can
   * be set to reflect whether a particular node has been visited in the BFS. Therefore, an
   * explicit set must be used to maintain this information.
   * @param searchVal the value to search for
   * @return true if the value is present in the graph, false otherwise
   */
  public boolean bfsGraphWithSeenSet(E searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter<E>.Node,
              List<GraphAdjacencyListBetter<E>.Node>> adjMap = graph.getGraph();
      Set<GraphAdjacencyListBetter<E>.Node> seenSet = new HashSet<>();
      for (GraphAdjacencyListBetter<E>.Node node : adjMap.keySet()) {
        if (!seenSet.contains(node)) {
          if (node.getData().equals(searchVal)) return true;
          seenSet.add(node);
          if (startNewSearch(searchVal, node, adjMap, seenSet)) return true;
        }
      }
    }
    return false;
  }

  /**
   * Helper method for bfsGraphWithSeenSet. Begins a new BFS from the given node in the graph.
   * @param searchVal the value to search for
   * @param root the node at which the BFS will start
   * @param adjMap a map containing the list of neighbors for each node
   * @param seenSet the set of nodes that have been seen during this search
   * @return true if the value was found through the BFS, false otherwise
   */
  private boolean startNewSearch(E searchVal, GraphAdjacencyListBetter<E>.Node root,
                                 Map<GraphAdjacencyListBetter<E>.Node,
                                         List<GraphAdjacencyListBetter<E>.Node>> adjMap,
                                 Set<GraphAdjacencyListBetter<E>.Node> seenSet) {
    Queue<GraphAdjacencyListBetter<E>.Node> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      GraphAdjacencyListBetter<E>.Node nextNode = q.poll();
      List<GraphAdjacencyListBetter<E>.Node> neighbors = adjMap.get(nextNode);
      for (GraphAdjacencyListBetter<E>.Node neighbor : neighbors) {
        if (!seenSet.contains(neighbor)) {
          if (neighbor.getData().equals(searchVal)) return true;
          seenSet.add(neighbor);
          q.add(neighbor);
        }
      }
    }
    return false;
  }

  /**
   * Runs a BFS on a binary tree.
   * @param searchVal the value to search for
   * @return true if the value is present in the tree, false otherwise
   */
  public boolean bfsTree(E searchVal) {
    if (searchVal != null) {
      Queue<BinaryTree<E>.Node> q = new LinkedList<>();
      BinaryTree<E>.Node root = tree.getRoot();
      if (root == null) return false;
      if (root.getData().equals(searchVal)) return true;
      q.add(root);
      while(!q.isEmpty()) {
        BinaryTree<E>.Node nextNode = q.poll();
        if (nextNode.hasLeftChild()) {
          BinaryTree<E>.Node leftChild = nextNode.getLeftChild();
          if (leftChild.getData().equals(searchVal)) return true;
          q.add(leftChild);
        }
        if (nextNode.hasRightChild()) {
          BinaryTree<E>.Node rightChild = nextNode.getRightChild();
          if (rightChild.getData().equals(searchVal)) return true;
          q.add(rightChild);
        }
      }
    }
    return false;
  }
}
