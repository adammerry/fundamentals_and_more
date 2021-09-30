package dataStructures;

import java.util.LinkedList;
import java.util.Queue;

// Implementation of a Splay Tree using nodes that hold generic values. A splay tree is a binary
// search tree with the additional property that recently accessed elements are placed near the
// root of the tree. This implementation does not support duplicate node values.
public class SplayTree<E extends Comparable<? super E>> {
  private Node root;

  public class Node {
    private E data;
    private Node leftChild, rightChild, parent;

    private Node(E data) { this.data = data; }

    private void setData(E data) { this.data = data; }

    private void setLeftChild(Node child) { leftChild = child; }

    private void setRightChild(Node child) { rightChild = child; }

    private boolean hasLeftChild() { return leftChild != null; }

    private boolean hasRightChild() { return rightChild != null; }

    public E getData() { return data; }

    private Node getLeftChild() { return leftChild; }

    private Node getRightChild() { return rightChild; }

    private Node getParent() { return parent; }

    private void setParent(Node parent) { this.parent = parent; }

    // Return the largest value in the subtree rooted at the given node.
    private E getLargestVal() { return hasRightChild() ? rightChild.getLargestVal() : data; }
  }

  // Insert a node with the given data into the tree, if there does not already exist a node with
  // that data. Insertion will preserve all BST properties.
  public void insert(E insertData) {
    if (insertData != null) {
      Node insertNode = new Node(insertData);
      root = (root == null) ? insertNode : insertHelper(root, null, insertNode);
      splay(insertNode);
    }
  }

  private Node insertHelper(Node node, Node parent, Node insertNode) {
    if (node == null) {
      insertNode.parent = parent;
      return insertNode;
    }
    if (insertNode.getData().compareTo(node.getData()) < 0)
      node.setLeftChild(insertHelper(node.getLeftChild(), node, insertNode));
    else if (insertNode.getData().compareTo(node.getData()) > 0)
      node.setRightChild(insertHelper(node.getRightChild(), node, insertNode));
    return node;
  }

  // Find and return the node in the tree with data equal to the given data. If no such node
  // exists, return null. Additionally, perform a splaying operation after searching. If a node is
  // found through searching, splay that node. Otherwise, splay the last node accessed during
  // the search procedure.
  public Node search(E searchData) {
    if (searchData == null) return null;
    Node currNode = root, prevNode = null;
    while (!(currNode == null || currNode.data == searchData)) {
      prevNode = currNode;
      if (currNode.getData().compareTo(searchData) > 0) currNode = currNode.getLeftChild();
      else currNode = currNode.getRightChild();
    }
    if (currNode == null) splay(prevNode);
    else splay(currNode);
    return currNode;
  }

  // Delete the node in the tree that has data equal to the given data, if such a node exists.
  // Deletion will preserve all BST properties.
  public void delete(E data) { if (data != null) root = deleteHelper(root, data); }

  private Node deleteHelper(Node node, E deleteData) {
    if (node == null) return null;
    if (node.getData().equals(deleteData)) return deleteNode(node);
    if (node.getData().compareTo(deleteData) > 0)
      node.setLeftChild(deleteHelper(node.getLeftChild(), deleteData));
    else node.setRightChild(deleteHelper(node.getRightChild(), deleteData));
    // If element not found, splay most recently accessed element.
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

  // Move the given node to the root of the tree while maintaining the BST property.
  private void splay(Node node) {

  }

  // Print the value of each node in the tree in the order that they would be reached using a
  // breadth-first search.
  public void printTree() {
    Queue<Node> nodes = new LinkedList<>();
    if (root != null) nodes.add(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.poll();
      System.out.print(next.getData() + " ");
      if (next.getLeftChild() != null) nodes.add(next.getLeftChild());
      if (next.getRightChild() != null) nodes.add(next.getRightChild());
    }
  }
}
