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
public class DFS {
  private GraphAdjacencyListBetter<Integer> graph;
  private BinaryTree<Integer> tree;

  public DFS(GraphAdjacencyListBetter<Integer> graph, BinaryTree<Integer> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  // Preorder DFS on a graph, using iteration and a Set for visited nodes.
  public GraphAdjacencyListBetter.Node<Integer> dfsGraphIterative(Integer searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter.Node<Integer>,
              List<GraphAdjacencyListBetter.Node<Integer>>> adjMap = graph.getGraph();
      Set<GraphAdjacencyListBetter.Node<Integer>> seenSet = new HashSet<>();
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) {
        if (!seenSet.contains(node)) {
          GraphAdjacencyListBetter.Node<Integer> nextSearchResult =
                  dfsGraphIterativeHelper(node, searchVal, adjMap, seenSet);
          if (nextSearchResult != null) return nextSearchResult;
        }
      }
    }
    return null;
  }

  private GraphAdjacencyListBetter.Node<Integer>
  dfsGraphIterativeHelper(GraphAdjacencyListBetter.Node<Integer> root,
                          Integer searchVal,
                          Map<GraphAdjacencyListBetter.Node<Integer>,
                                  List<GraphAdjacencyListBetter.Node<Integer>>> adjMap,
                          Set<GraphAdjacencyListBetter.Node<Integer>> seenSet) {
    Stack<GraphAdjacencyListBetter.Node<Integer>> stack = new Stack<>();
    stack.add(root);
    while (!stack.isEmpty()) {
      GraphAdjacencyListBetter.Node<Integer> nextNode = stack.pop();
      if (nextNode.getData().equals(searchVal)) return nextNode;
      seenSet.add(nextNode);
      for (GraphAdjacencyListBetter.Node<Integer> neighbor : adjMap.get(root))
        if (!seenSet.contains(neighbor)) stack.push(neighbor);
    }
    return null;
  }

  // Preorder DFS on a graph, using recursion and a "seen" field for visited nodes.
  public GraphAdjacencyListBetter.Node<Integer> dfsGraphRecursive(Integer searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter.Node<Integer>,
              List<GraphAdjacencyListBetter.Node<Integer>>> adjMap = graph.getGraph();
      // Initialize all nodes as not seen.
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) node.setSeen(false);
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) {
        if (!node.hasBeenSeen()) {
          GraphAdjacencyListBetter.Node<Integer> nextSearchResult =
                  dfsGraphRecursiveHelper(node, searchVal, adjMap);
          if (nextSearchResult != null) return nextSearchResult;
        }
      }
    }
    return null;
  }

  private GraphAdjacencyListBetter.Node<Integer>
  dfsGraphRecursiveHelper(GraphAdjacencyListBetter.Node<Integer> root,
                          Integer searchVal,
                          Map<GraphAdjacencyListBetter.Node<Integer>,
                                  List<GraphAdjacencyListBetter.Node<Integer>>> adjMap) {
    if (root == null || root.getData().equals(searchVal)) return root;
    root.setSeen(true);
    for (GraphAdjacencyListBetter.Node<Integer> neighbor : adjMap.get(root)) {
      if (!neighbor.hasBeenSeen()) {
        GraphAdjacencyListBetter.Node<Integer> dfsRetVal =
                dfsGraphRecursiveHelper(neighbor, searchVal, adjMap);
        if (dfsRetVal != null) return dfsRetVal;
      }
    }
    return null;
  }

  // Preorder DFS on a binary tree using iteration.
  public BinaryTree.Node<Integer> dfsTreeIterative(Integer searchVal) {
    if (searchVal != null) {
      Stack<BinaryTree.Node<Integer>> stack = new Stack<>();
      stack.push(tree.getRoot());
      while (!stack.isEmpty()) {
        BinaryTree.Node<Integer> nextNode = stack.pop();
        if (nextNode != null) {
          if (nextNode.getData().equals(searchVal)) return nextNode;
          stack.push(nextNode.getLeftChild());
          stack.push(nextNode.getRightChild());
        }
      }
    }
    return null;
  }

  // Preorder DFS on a binary tree using recursion.
  public BinaryTree.Node<Integer> dfsTreeRecursive(Integer searchVal) {
    if (searchVal == null) return null;
    BinaryTree.Node<Integer> root = tree.getRoot();
    return dfsTreeRecursiveHelper(searchVal, root);
  }

  private BinaryTree.Node<Integer> dfsTreeRecursiveHelper(Integer searchVal,
                                                          BinaryTree.Node<Integer> root) {
    if (root == null || root.getData().equals(searchVal)) return root;
    BinaryTree.Node<Integer> leftSubtreeRetVal =
            dfsTreeRecursiveHelper(searchVal, root.getLeftChild());
    return (leftSubtreeRetVal == null) ? dfsTreeRecursiveHelper(searchVal, root.getRightChild()) :
            leftSubtreeRetVal;
  }
}
