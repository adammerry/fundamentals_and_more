package searching;

import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBetter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

// Implementations of depth-first search on a graph and a tree. The graph version is
// slightly more complex, as it must handle cycles as well as disconnected graphs.
public class DFS<E> {
  private GraphAdjacencyListBetter<E> graph;
  private BinaryTree<E> tree;

  public DFS(GraphAdjacencyListBetter<E> graph, BinaryTree<E> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  // Preorder DFS on a graph, using iteration and a Set for visited nodes.
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

  // Preorder DFS on a graph, using recursion and a "seen" field for visited nodes.
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

  // Preorder DFS on a binary tree using iteration.
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

  // Preorder DFS on a binary tree using recursion.
  public boolean dfsTreeRecursive(E searchVal) {
    if (searchVal == null) return false;
    return dfsTreeRecursiveHelper(searchVal, tree.getRoot());
  }

  private boolean dfsTreeRecursiveHelper(E searchVal, BinaryTree<E>.Node root) {
    if (root == null) return false;
    if (root.getData().equals(searchVal)) return true;
    if (dfsTreeRecursiveHelper(searchVal, root.getLeftChild())) return true;
    return dfsTreeRecursiveHelper(searchVal, root.getRightChild());
  }
}
