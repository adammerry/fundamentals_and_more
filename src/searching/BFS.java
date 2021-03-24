package searching;

import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBetter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// Implementations of breadth-first search on a graph and a tree. The graph version is
// slightly more complex, as it must handle cycles as well as disconnected graphs. All BFS
// implementations are optimized such that nodes are checked before they are added to the queue, as
// opposed to after they are removed from the queue. This optimization will often reduce the
// number of iterations needed to find the given node, since it avoids keeping the desired node
// in the queue while the rest of the nodes in the previous level are examined.
public class BFS {
  private GraphAdjacencyListBetter<Integer> graph;
  private BinaryTree<Integer> tree;

  public BFS(GraphAdjacencyListBetter<Integer> graph, BinaryTree<Integer> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  // BFS on a graph, where nodes are assumed to offer a "seen" boolean field that can be set to
  // reflect whether or not a particular node has been visited in the BFS.
  public GraphAdjacencyListBetter.Node<Integer> bfsGraphWithSeenField(Integer searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter.Node<Integer>,
              List<GraphAdjacencyListBetter.Node<Integer>>> adjMap = graph.getGraph();
      // Initialize all nodes as not seen.
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) node.setSeen(false);
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) {
        if (!node.hasBeenSeen()) {
          if (node.getData().equals(searchVal)) return node;
          node.setSeen(true);
          GraphAdjacencyListBetter.Node<Integer> result = startNewSearch(searchVal, node, adjMap);
          if (result != null) return result;
        }
      }
    }
    return null;
  }

  private GraphAdjacencyListBetter.Node<Integer>
  startNewSearch(Integer searchVal, GraphAdjacencyListBetter.Node<Integer> root,
                 Map<GraphAdjacencyListBetter.Node<Integer>,
                         List<GraphAdjacencyListBetter.Node<Integer>>> adjMap) {
    Queue<GraphAdjacencyListBetter.Node<Integer>> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      GraphAdjacencyListBetter.Node<Integer> nextNode = q.poll();
      List<GraphAdjacencyListBetter.Node<Integer>> neighbors = adjMap.get(nextNode);
      for (GraphAdjacencyListBetter.Node<Integer> neighbor : neighbors) {
        if (!neighbor.hasBeenSeen()) {
          if (neighbor.getData().equals(searchVal)) return neighbor;
          neighbor.setSeen(true);
          q.add(neighbor);
        }
      }
    }
    return null;
  }


  // BFS on a graph, where nodes are *not* assumed to offer a "seen" boolean field that can be set
  // to reflect whether or not a particular node has been visited in the BFS. Therefore, an
  // explicit Set must be used to maintain this information.
  public GraphAdjacencyListBetter.Node<Integer> bfsGraphWithSeenSet(Integer searchVal) {
    if (searchVal != null) {
      Map<GraphAdjacencyListBetter.Node<Integer>,
              List<GraphAdjacencyListBetter.Node<Integer>>> adjMap = graph.getGraph();
      Set<GraphAdjacencyListBetter.Node<Integer>> seenSet = new HashSet<>();
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) {
        if (!seenSet.contains(node)) {
          if (node.getData().equals(searchVal)) return node;
          seenSet.add(node);
          GraphAdjacencyListBetter.Node<Integer> result =
                  startNewSearch(searchVal, node, adjMap, seenSet);
          if (result != null) return result;
        }
      }
    }
    return null;
  }

  private GraphAdjacencyListBetter.Node<Integer>
  startNewSearch(Integer searchVal, GraphAdjacencyListBetter.Node<Integer> root,
                 Map<GraphAdjacencyListBetter.Node<Integer>,
                         List<GraphAdjacencyListBetter.Node<Integer>>> adjMap,
                 Set<GraphAdjacencyListBetter.Node<Integer>> seenSet) {
    Queue<GraphAdjacencyListBetter.Node<Integer>> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      GraphAdjacencyListBetter.Node<Integer> nextNode = q.poll();
      List<GraphAdjacencyListBetter.Node<Integer>> neighbors = adjMap.get(nextNode);
      for (GraphAdjacencyListBetter.Node<Integer> neighbor : neighbors) {
        if (!seenSet.contains(neighbor)) {
          if (neighbor.getData().equals(searchVal)) return neighbor;
          seenSet.add(neighbor);
          q.add(neighbor);
        }
      }
    }
    return null;
  }

  // BFS on a binary tree.
  public BinaryTree.Node<Integer> bfsTree(Integer searchVal) {
    if (searchVal != null) {
      Queue<BinaryTree.Node<Integer>> q = new LinkedList<>();
      BinaryTree.Node<Integer> root = tree.getRoot();
      if (root == null || root.getData().equals(searchVal)) return null;
      q.add(root);
      while(!q.isEmpty()) {
        BinaryTree.Node<Integer> nextNode = q.poll();
        if (nextNode.hasLeftChild()) {
          BinaryTree.Node<Integer> leftChild = nextNode.getLeftChild();
          if (leftChild.getData().equals(searchVal)) return leftChild;
          q.add(leftChild);
        }
        if (nextNode.hasRightChild()) {
          BinaryTree.Node<Integer> rightChild = nextNode.getRightChild();
          if (rightChild.getData().equals(searchVal)) return rightChild;
          q.add(rightChild);
        }
      }
    }
    return null;
  }
}
