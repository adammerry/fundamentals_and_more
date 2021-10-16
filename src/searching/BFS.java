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
public class BFS<E> {
  private final GraphAdjacencyListBetter<E> graph;
  private final BinaryTree<E> tree;

  public BFS(GraphAdjacencyListBetter<E> graph, BinaryTree<E> tree) {
    this.graph = graph;
    this.tree = tree;
  }

  // BFS on a graph, where nodes are assumed to offer a "seen" boolean field that can be set to
  // reflect whether a particular node has been visited in the BFS.
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


  // BFS on a graph, where nodes are *not* assumed to offer a "seen" boolean field that can be set
  // to reflect whether a particular node has been visited in the BFS. Therefore, an
  // explicit Set must be used to maintain this information.
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

  // BFS on a binary tree.
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
