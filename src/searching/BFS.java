package searching;

import dataStructures.BinaryTree;
import dataStructures.GraphAdjacencyListBetter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Implementations of a breadth-first search on a graph, and on a tree. The graph version is
// slightly more complex, as it must handle cycles as well as disconnected graphs.
public class BFS {
  private GraphAdjacencyListBetter<Integer> graph;
  private BinaryTree<Integer> tree;

  public BFS(GraphAdjacencyListBetter<Integer> graph, BinaryTree<Integer> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  // BFS on a graph, optimized such that nodes are checked before they are added to the queue, as
  // opposed to after they are removed from the queue. This optimization will often reduce the
  // number of iterations needed to find the given node, since it avoids keeping the desired node
  // in the queue while the rest of the previous level of the graph is examined.
  public GraphAdjacencyListBetter.Node<Integer> bfsGraph(Integer searchVal) {
    if (searchVal != null) {
      Queue<GraphAdjacencyListBetter.Node<Integer>> q = new LinkedList<>();
      Map<GraphAdjacencyListBetter.Node<Integer>,
              List<GraphAdjacencyListBetter.Node<Integer>>> adjMap = graph.getGraph();
      // Initialize all nodes as not seen.
      for (GraphAdjacencyListBetter.Node<Integer> node : adjMap.keySet()) {
        node.setSeen(false);
      }
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
  startNewSearch(Integer searchVal, GraphAdjacencyListBetter. Node<Integer> root,
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

  // BFS on a tree, optimized such that nodes are checked before they are added to the queue, as
  // opposed to after they are removed from the queue. This optimization will often reduce the
  // number of iterations needed to find the given node, since it avoids keeping the desired node
  // in the queue while the rest of the previous level of the tree is examined.
  public BinaryTree.Node<Integer> bfsTree(Integer searchVal) {
    if (searchVal != null) {
      Queue<BinaryTree.Node<Integer>> q = new LinkedList<>();
      BinaryTree.Node<Integer> root = tree.getRoot();
      if (root == null) return null;
      if (root.getData().equals(searchVal)) return root;
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
