package dataStructures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// Example of a generic Binary Tree implementation. A Binary Tree is simply a data structure
// composed of zero or more nodes, where each node has up to two child nodes. No other restrictions
// are placed on the structure of the tree.
public class BinaryTree<E> {
  private Node root, lastNodeParent, lastNode;

  public BinaryTree() {}

  public Node getRoot() { return root; }

  public class Node {
    private final E data;
    private Node leftChild, rightChild;

    public Node(E data) { this.data = data; }

    public void setLeftChild(Node child) { leftChild = child; }

    public void setRightChild(Node child) { rightChild = child; }

    public boolean hasLeftChild() { return leftChild != null; }

    public boolean hasRightChild() { return rightChild != null; }

    public E getData() { return data; }

    public Node getLeftChild() { return leftChild; }

    public Node getRightChild() { return rightChild; }
  }

  // Find the node in the tree that contains the given data, using a breadth-first search.
  public boolean bfs(E data) {
    if (root == null || data == null) return false;
    Queue<Node> nodes = new LinkedList<>();
    nodes.offer(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.poll();
      if (next.getData().equals(data)) return true;
      if (next.hasLeftChild()) nodes.offer(next.getLeftChild());
      if (next.hasRightChild()) nodes.offer(next.getRightChild());
    }
    return false;
  }

  // Find the node in the tree that contains the given data, using a depth-first search.
  public boolean dfs(E data) {
    if (root == null || data == null) return false;
    Stack<Node> nodes = new Stack<>();
    nodes.push(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.pop();
      if (next.getData().equals(data)) return true;
      if (next.hasRightChild()) nodes.push(next.getRightChild());
      if (next.hasLeftChild()) nodes.push(next.getLeftChild());
    }
    return false;
  }

  // Level order insertion. Insert the given data into the next available spot in the tree,
  // searching from top to bottom, left to right.
  public void insert(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    Node insertNode = lastNode = new Node(data);
    if (root == null) {
      root = insertNode;
      return;
    }
    Queue<Node> nodes = new LinkedList<>();
    nodes.offer(root);
    while (true) {
      Node next = nodes.poll();
      if (!next.hasLeftChild()) {
        next.setLeftChild(insertNode);
        lastNodeParent = next;
        return;
      }
      if (!next.hasRightChild()) {
        next.setRightChild(insertNode);
        lastNodeParent = next;
        return;
      }
      nodes.offer(next.getLeftChild());
      nodes.offer(next.getRightChild());
    }
  }

  // Delete the node with the given data, replacing it with the last node inserted into the tree.
  public void delete(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    if ((root = deleteHelper(root, data)) != null) updateLastNode();
  }

  // Return a tree that does not contain a node with the given data.
  private Node deleteHelper(Node currNode, E data) {
    if (currNode == null || data == null) return currNode;
    if (currNode.getData().equals(data)) { // currNode should be removed from the tree.
      if (currNode == lastNode) return null;
      if (lastNode != root) { // Remove the connection between the last node and its parent.
        if (lastNode == lastNodeParent.getLeftChild()) lastNodeParent.setLeftChild(null);
        else lastNodeParent.setRightChild(null);
      }
      // Transfer the deleted node's children to the last node.
      Node currNodeLeft = currNode.getLeftChild(), currNodeRight = currNode.getRightChild();
      if (lastNode != currNodeLeft) lastNode.setLeftChild(currNodeLeft);
      if (lastNode != currNodeRight) lastNode.setRightChild(currNodeRight);
      return lastNode;
    }
    else { // This node should remain in the tree, so recurse on its children.
      currNode.setLeftChild(deleteHelper(currNode.getLeftChild(), data));
      currNode.setRightChild(deleteHelper(currNode.getRightChild(), data));
      return currNode;
    }
  }

  private void updateLastNode() {
    Node currNodeParent = null, currNode = root;
    while (currNode.hasLeftChild() || currNode.hasRightChild()) {
      currNodeParent = currNode;
      currNode = (currNode.hasRightChild()) ? currNode.getRightChild() : currNode.getLeftChild();
    }
    lastNodeParent = currNodeParent;
    lastNode = currNode;
  }

  // Print the value of each node in the tree in the order that they would be reached using a
  // breadth-first search.
  public void printTree() {
    Queue<Node> nodes = new LinkedList<>();
    if (root != null) nodes.offer(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.poll();
      System.out.print(next.getData() + " ");
      if (next.getLeftChild() != null) nodes.offer(next.getLeftChild());
      if (next.getRightChild() != null) nodes.offer(next.getRightChild());
    }
  }
}