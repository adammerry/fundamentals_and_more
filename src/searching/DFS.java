package searching;

import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBetter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Implementations of depth-first search on a graph and a tree. The graph version is
 * slightly more complex, as it must handle cycles as well as disconnected graphs.
 * @param <E> the type of data contained in the graph and tree being searched
 */
public class DFS<E> {
  private final GraphAdjacencyListBetter<E> graph;
  private final BinaryTree<E> tree;

  /**
   * Constructor that provides the graph and tree to search.
   * @param graph the graph to search
   * @param tree the tree to search
   */
  public DFS(GraphAdjacencyListBetter<E> graph, BinaryTree<E> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  /**
   * Runs a DFS on a graph, using iteration and a set for visited nodes.
   * @param searchVal the value to search for
   * @return true if the value is present in the graph, false otherwise
   */
  public boolean dfsGraphIterative(E searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter<E>.Node,
              List<GraphAdjacencyListBetter<E>.Node>> adjMap = graph.getGraph();
      Set<GraphAdjacencyListBetter<E>.Node> seenSet = new HashSet<>();
      for (GraphAdjacencyListBetter<E>.Node node : adjMap.keySet()) {
        if (!seenSet.contains(node) && dfsGraphIterativeHelper(node, searchVal, adjMap, seenSet))
          return true;
      }
    }
    return false;
  }

  /**
   * Helper method for dfsGraphIterative. Begins a new DFS from the given node in the graph.
   * @param root the node at which the DFS will start
   * @param searchVal the value to search for
   * @param adjMap a map containing the list of neighbors for each node
   * @param seenSet the set of nodes that have been seen during this search
   * @return true if the value was found through the DFS, false otherwise
   */
  private boolean dfsGraphIterativeHelper(GraphAdjacencyListBetter<E>.Node root, E searchVal,
                                          Map<GraphAdjacencyListBetter<E>.Node,
                                                  List<GraphAdjacencyListBetter<E>.Node>> adjMap,
                                          Set<GraphAdjacencyListBetter<E>.Node> seenSet) {
    Stack<GraphAdjacencyListBetter<E>.Node> stack = new Stack<>();
    stack.add(root);
    while (!stack.isEmpty()) {
      GraphAdjacencyListBetter<E>.Node nextNode = stack.pop();
      if (nextNode.getData().equals(searchVal)) return true;
      seenSet.add(nextNode);
      for (GraphAdjacencyListBetter<E>.Node neighbor : adjMap.get(root))
        if (!seenSet.contains(neighbor)) stack.push(neighbor);
    }
    return false;
  }

  /**
   * Runs a DFS on a graph, using recursion and a "seen" field for visited nodes.
   * @param searchVal the value to search for
   * @return true if the value is present in the graph, false otherwise
   */
  public boolean dfsGraphRecursive(E searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter<E>.Node,
              List<GraphAdjacencyListBetter<E>.Node>> adjMap = graph.getGraph();
      // Initialize all nodes as not seen.
      for (GraphAdjacencyListBetter<E>.Node node : adjMap.keySet()) node.setSeen(false);
      for (GraphAdjacencyListBetter<E>.Node node : adjMap.keySet())
        if (!node.seen() && dfsGraphRecursiveHelper(node, searchVal, adjMap)) return true;
    }
    return false;
  }

  /**
   * Helper method for dfsGraphRecursive. Begins a new DFS from the given node in the graph.
   * @param root the node at which the DFS will start
   * @param searchVal the value to search for
   * @param adjMap a map containing the list of neighbors for each node
   * @return true if the value was found through the DFS, false otherwise
   */
  private boolean dfsGraphRecursiveHelper(GraphAdjacencyListBetter<E>.Node root, E searchVal,
                                          Map<GraphAdjacencyListBetter<E>.Node,
                                                  List<GraphAdjacencyListBetter<E>.Node>> adjMap) {
    if (root == null) return false;
    if (root.getData().equals(searchVal)) return true;
    root.setSeen(true);
    for (GraphAdjacencyListBetter<E>.Node neighbor : adjMap.get(root))
      if (!neighbor.seen() && dfsGraphRecursiveHelper(neighbor, searchVal, adjMap)) return true;
    return false;
  }

  /**
   * Runs a preorder DFS on a binary tree using iteration.
   * @param searchVal the value to search for
   * @return true if the value is present in the tree, false otherwise
   */
  public boolean dfsTreeIterative(E searchVal) {
    if (searchVal != null) {
      Stack<BinaryTree<E>.Node> stack = new Stack<>();
      stack.push(tree.getRoot());
      while (!stack.isEmpty()) {
        BinaryTree<E>.Node nextNode = stack.pop();
        if (nextNode != null) {
          if (nextNode.getData().equals(searchVal)) return true;
          stack.push(nextNode.getLeftChild());
          stack.push(nextNode.getRightChild());
        }
      }
    }
    return false;
  }

  /**
   * Runs a preorder DFS on a binary tree using recursion.
   * @param searchVal the value to search for
   * @return true if the value is present in the tree, false otherwise
   */
  public boolean dfsTreeRecursive(E searchVal) {
    if (searchVal == null) return false;
    return dfsTreeRecursiveHelper(searchVal, tree.getRoot());
  }

  /**
   * Helper method for dfsTreeRecursive. Runs a preorder DFS from the given root node.
   * @param searchVal the value to search for
   * @param node the node currently being examined
   * @return true if the value was found through the DFS, false otherwise
   */
  private boolean dfsTreeRecursiveHelper(E searchVal, BinaryTree<E>.Node node) {
    if (node == null) return false;
    if (node.getData().equals(searchVal)) return true;
    if (dfsTreeRecursiveHelper(searchVal, node.getLeftChild())) return true;
    return dfsTreeRecursiveHelper(searchVal, node.getRightChild());
  }
}
