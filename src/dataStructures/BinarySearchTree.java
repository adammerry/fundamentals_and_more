package dataStructures;

import java.util.LinkedList;
import java.util.Queue;

// Implementation of a Binary Search Tree using nodes that hold generic values. This
// implementation does not support duplicate node values.
public class BinarySearchTree<E extends Comparable<? super E>> {
  private Node root;

  private class Node {
    private E data;
    private Node leftChild, rightChild;

    private Node(E data) { this.data = data; }

    private void setData(E data) { this.data = data; }

    private void setLeftChild(Node child) { leftChild = child; }

    private void setRightChild(Node child) { rightChild = child; }

    private boolean hasLeftChild() { return leftChild != null; }

    private boolean hasRightChild() { return rightChild != null; }

    public E getData() { return data; }

    private Node getLeftChild() { return leftChild; }

    private Node getRightChild() { return rightChild; }

    // Return the largest value in the subtree rooted at the given node.
    private E getLargestVal() { return hasRightChild() ? rightChild.getLargestVal() : data; }
  }

  // Insert a node with the given data into the tree, if there does not already exist a node with
  // that data. Insertion will preserve all BST properties.
  public void insert(E insertData) {
    if (insertData == null) throw new IllegalArgumentException("Data cannot be null");
    root = insertHelper(root, insertData);
  }

  private Node insertHelper(Node node, E insertData) {
    if (node == null) return new Node(insertData);
    if (insertData.compareTo(node.getData()) < 0)
      node.setLeftChild(insertHelper(node.getLeftChild(), insertData));
    else if (insertData.compareTo(node.getData()) > 0)
      node.setRightChild(insertHelper(node.getRightChild(), insertData));
    return node;
  }

  // Find the node in the tree with data equal to the given data.
  public boolean search(E searchData) {
    if (searchData == null) throw new IllegalArgumentException("Data cannot be null");
    Node currNode = root;
    while (currNode != null) {
      if (currNode.getData().equals(searchData)) return true;
      if (currNode.getData().compareTo(searchData) > 0) currNode = currNode.getLeftChild();
      else currNode = currNode.getRightChild();
    }
    return false;
  }

  // Delete the node in the tree that has data equal to the given data, if such a node exists.
  // Deletion will preserve all BST properties.
  public void delete(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    root = deleteHelper(root, data);
  }

  private Node deleteHelper(Node node, E deleteData) {
    if (node == null) return null;
    if (node.getData().equals(deleteData)) return deleteNode(node);
    if (node.getData().compareTo(deleteData) > 0)
      node.setLeftChild(deleteHelper(node.getLeftChild(), deleteData));
    else node.setRightChild(deleteHelper(node.getRightChild(), deleteData));
    return node;
  }

  // Handle cases for when the node to delete has zero, one, or two children.
  private Node deleteNode(Node node) {
    if (node.hasLeftChild()) {
      if (node.hasRightChild()) {
        node.setData(node.getLeftChild().getLargestVal());
        node.setLeftChild(deleteLargest(node.getLeftChild()));
        return node;
      }
      return node.getLeftChild();
    }
    return (node.hasRightChild()) ? node.getRightChild() : null;
  }

  // Remove the node with the largest value from the subtree rooted at the given node.
  private Node deleteLargest(Node node) {
    if (node.hasRightChild()) {
      node.setRightChild(deleteLargest(node.getRightChild()));
      return node;
    }
    return node.getLeftChild();
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