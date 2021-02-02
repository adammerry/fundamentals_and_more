package dataStructures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// Example of a generic Binary Tree implementation. A Binary Tree is simply a data structure
// composed of zero or more nodes, where each node has up to two child nodes. No other restrictions
// are placed on the structure of the tree.
public class BinaryTree<E> {
  private Node<E> root, lastNodeParent, lastNode;

  public BinaryTree(Node<E> root) { this.root = lastNode = root; }

  public BinaryTree() {}

  public Node<E> getRoot() {
    return root;
  }

  public static class Node<E> {
    private E data;
    private Node<E> leftChild, rightChild;

    public Node(E data) { this.data = data; }

    public void setLeftChild(Node<E> child) { leftChild = child; }

    public void setRightChild(Node<E> child) { rightChild = child; }

    public boolean hasLeftChild() { return leftChild != null; }

    public boolean hasRightChild() { return rightChild != null; }

    public E getData() { return data; }

    public Node<E> getLeftChild() { return leftChild; }

    public Node<E> getRightChild() { return rightChild; }
  }

  // Find the node in the tree that contains the given data, using a breadth-first search. If no
  // such node exists, return null.
  public Node<E> bfs(E data) {
    if (root == null || data == null) return null;
    Queue<Node<E>> nodes = new LinkedList<>();
    nodes.offer(root);
    while (!nodes.isEmpty()) {
      Node<E> next = nodes.poll();
      if (next.getData().equals(data)) return next;
      if (next.hasLeftChild()) nodes.offer(next.getLeftChild());
      if (next.hasRightChild()) nodes.offer(next.getRightChild());
    }
    return null;
  }

  // Find the node in the tree that contains the given data, using a depth-first search. If no
  // such node exists, return null.
  public Node<E> dfs(E data) {
    if (root == null || data == null) return null;
    Stack<Node<E>> nodes = new Stack<>();
    nodes.push(root);
    while (!nodes.isEmpty()) {
      Node<E> next = nodes.pop();
      if (next.getData().equals(data)) return next;
      if (next.hasRightChild()) nodes.push(next.getRightChild());
      if (next.hasLeftChild()) nodes.push(next.getLeftChild());
    }
    return null;
  }

  // Level order insertion. Insert the given data into the next available spot in the tree,
  // searching from top to bottom, left to right.
  public void insert(E data) {
    if (data == null) return;
    Node<E> insertNode = lastNode = new Node<>(data);
    if (root == null) {
      root = insertNode;
      return;
    }
    Queue<Node<E>> nodes = new LinkedList<>();
    nodes.offer(root);
    while (true) {
      Node<E> next = nodes.poll();
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
    if ((root = deleteHelper(root, data)) != null) updateLastNode();
  }

  // Return a tree that does not contain a node with the given data.
  private Node<E> deleteHelper(Node<E> currNode, E data) {
    if (currNode == null || data == null) return currNode;
    if (currNode.getData().equals(data)) { // currNode should be removed from the tree.
      if (currNode == lastNode) return null;
      if (lastNode != root) { // Remove the connection between the last node and its parent.
        if (lastNode == lastNodeParent.getLeftChild()) lastNodeParent.setLeftChild(null);
        else lastNodeParent.setRightChild(null);
      }
      // Transfer the deleted node's children to the last node.
      Node<E> currNodeLeft = currNode.getLeftChild(), currNodeRight = currNode.getRightChild();
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
    Node<E> currNodeParent = null, currNode = root;
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
    Queue<Node<E>> nodes = new LinkedList<>();
    if (root != null) nodes.offer(root);
    while (!nodes.isEmpty()) {
      Node<E> next = nodes.poll();
      System.out.print(next.getData() + " ");
      if (next.getLeftChild() != null) nodes.offer(next.getLeftChild());
      if (next.getRightChild() != null) nodes.offer(next.getRightChild());
    }
  }
}