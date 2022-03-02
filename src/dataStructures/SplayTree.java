package dataStructures;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of a Splay Tree using nodes that hold generic values. A splay tree is a binary
 * search tree with the additional property that recently accessed elements are placed near the
 * root of the tree. This implementation does not support duplicate node values.
 * @param <E> the type of data contained in the tree
 */
public class SplayTree<E extends Comparable<? super E>> {
  private Node root, splayNode, lastAccessed;

  private class Node {
    private E data;
    private Node leftChild, rightChild, parent;

    private Node(E data) { this.data = data; }

    private void setData(E data) { this.data = data; }

    private void setLeftChild(Node child) { leftChild = child; }

    private void setRightChild(Node child) { rightChild = child; }

    private void setParent(Node parent) { this.parent = parent; }

    private boolean hasLeftChild() { return leftChild != null; }

    private boolean hasRightChild() { return rightChild != null; }

    public E getData() { return data; }

    private Node getLeftChild() { return leftChild; }

    private Node getRightChild() { return rightChild; }

    private Node getParent() { return parent; }

    // Return the largest value in the subtree rooted at the given node.
    private E getLargestVal() { return hasRightChild() ? rightChild.getLargestVal() : data; }
  }

  /**
   * Gets the data in the root node.
   * @return the data held in the root node, or null if no root node exists
   */
  public E getRootVal() { return (root == null) ? null : root.getData(); }

  /**
   * Inserts a node with the given data into the tree, then splays the inserted node.
   * @param insertData the data that will be contained in the inserted node
   */
  public void insert(E insertData) {
    if (insertData == null) throw new IllegalArgumentException("Data cannot be null");
    root = insertHelper(root, insertData);
    splay(splayNode);
  }

  /**
   * A helper method that recursively walks through the tree in order to insert the given data.
   * @param node the node currently being examined
   * @param insertData the data to insert
   * @return the new subtree rooted at the given node
   */
  private Node insertHelper(Node node, E insertData) {
    if (node == null) return (splayNode = new Node(insertData));
    if (insertData.compareTo(node.getData()) < 0) {
      Node leftChild = insertHelper(node.getLeftChild(), insertData);
      leftChild.setParent(node);
      node.setLeftChild(leftChild);
    }
    else {
      Node rightChild = insertHelper(node.getRightChild(), insertData);
      rightChild.setParent(node);
      node.setRightChild(rightChild);
    }
    return node;
  }

  /**
   * Finds the node in the tree with data equal to the given data. Splays the matching node, or if
   * no node was found, the last node accessed during the search procedure.
   * @param searchData the data to search for
   * @return true if a node with the given data was found, otherwise false
   */
  public boolean search(E searchData) {
    if (searchData == null) throw new IllegalArgumentException("Data cannot be null");
    Node currNode = root;
    while (currNode != null) {
      if (currNode.getData().equals(searchData)) {
        splayNode = currNode;
        splay(currNode);
        return true;
      }
      lastAccessed = currNode;
      if (currNode.getData().compareTo(searchData) > 0) currNode = currNode.getLeftChild();
      else currNode = currNode.getRightChild();
    }
    if (root != null) splay(lastAccessed);
    return false;
  }

  /**
   * Deletes the node in the tree that has data equal to the given data, if such node exists.
   * Splays the parent of the deleted node, or if no node was found, the last node accessed during
   * the search procedure. Note that there are multiple algorithms for splay tree deletion that may
   * produce different resulting trees. For more information, see:
   * https://en.wikipedia.org/wiki/Splay_tree#Deletion
   * @param data the data to delete
   */
  public void delete(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    splayNode = null;
    boolean deleteRoot = root != null && root.getData().equals(data); // Determine if root will be deleted.
    root = deleteHelper(root, data);
    if (deleteRoot && root != null) root.setParent(null); // Necessary if root was deleted.
    if (root != null && !deleteRoot) splay(splayNode == null ? lastAccessed : splayNode);
  }

  /**
   * A helper method that recursively walks through the tree in order to delete the node with the
   * given data.
   * @param node the node currently being examined
   * @param deleteData the data to search for
   * @return the new subtree rooted at the given node
   */
  private Node deleteHelper(Node node, E deleteData) {
    if (node == null) return null;
    lastAccessed = node;
    if (node.getData().equals(deleteData)) {
      splayNode = node.getParent();
      return deleteNode(node);
    }
    if (node.getData().compareTo(deleteData) > 0) {
      Node leftChild = deleteHelper(node.getLeftChild(), deleteData);
      node.setLeftChild(leftChild);
      if (leftChild != null && leftChild.getParent() != node) leftChild.setParent(node);
    }
    else {
      Node rightChild = deleteHelper(node.getRightChild(), deleteData);
      node.setRightChild(rightChild);
      if (rightChild != null && rightChild.getParent() != node) rightChild.setParent(node);
    }
    return node;
  }

  /**
   * Deletes the given node from the tree, handling cases for when the node has zero, one, or two
   * children.
   * @param node the node to delete
   * @return the new subtree rooted at the given node
   */
  private Node deleteNode(Node node) {
    if (node.hasLeftChild()) {
      if (node.hasRightChild()) {
        node.setData(node.getLeftChild().getLargestVal());
        Node leftChild = deleteLargest(node.getLeftChild());
        node.setLeftChild(leftChild);
        if (leftChild != null) leftChild.setParent(node);
        return node;
      }
      node.getLeftChild().setParent(node.getParent());
      return node.getLeftChild();
    }
    if (node.hasRightChild()) {
      node.getRightChild().setParent(node.getParent());
      return node.getRightChild();
    }
    return null;
  }

  /**
   * Removes the node with the largest value from the subtree rooted at the given node.
   * @param node the root of the subtree being examined
   * @return the new subtree rooted at the given node
   */
  private Node deleteLargest(Node node) {
    if (node.hasRightChild()) {
      node.setRightChild(deleteLargest(node.getRightChild()));
      return node;
    }
    return node.getLeftChild();
  }

  /**
   * Moves the given node to the root of the tree and ensures that the resulting tree maintains all
   * BST properties.
   * @param node the node to move
   */
  private void splay(Node node) {
    while (node.getParent() != null) {
      if (node.getParent() == root) {
        if (node == root.getLeftChild()) rightRotate(root);
        else leftRotate(root);
      }
      else {
        Node parent = node.getParent(), grandParent = parent.getParent();
        if (grandParent.getLeftChild() == parent && parent.getLeftChild() == node) {
          rightRotate(grandParent);
          rightRotate(parent);
        }
        else if (grandParent.getRightChild() == parent && parent.getRightChild() == node) {
          leftRotate(grandParent);
          leftRotate(parent);
        }
        else if (grandParent.getLeftChild() == parent && parent.getRightChild() == node) {
          leftRotate(parent);
          rightRotate(grandParent);
        }
        else {
          rightRotate(parent);
          leftRotate(grandParent);
        }
      }
    }
    root = node;
  }

  /**
   * Left-rotates the subtree rooted at the given node.
   * @param node the root node of the subtree being rotated
   */
  private void leftRotate(Node node) {
    Node pivot = node.getRightChild(), parent = node.getParent(), innerSubtree = pivot.getLeftChild();
    pivot.setParent(parent);
    pivot.setLeftChild(node);
    node.setRightChild(innerSubtree);
    node.setParent(pivot);
    if (innerSubtree != null) innerSubtree.setParent(node);
    if (parent != null) {
      if (node == parent.getLeftChild()) parent.setLeftChild(pivot);
      else parent.setRightChild(pivot);
    }
  }

  /**
   * Right-rotates the subtree rooted at the given node.
   * @param node the root node of the subtree being rotated
   */
  private void rightRotate(Node node) {
    Node pivot = node.getLeftChild(), parent = node.getParent();
    Node innerSubtree = pivot.getRightChild();
    pivot.setParent(parent);
    pivot.setRightChild(node);
    node.setLeftChild(innerSubtree);
    node.setParent(pivot);
    if (innerSubtree != null) innerSubtree.setParent(node);
    if (parent != null) {
      if (node == parent.getLeftChild()) parent.setLeftChild(pivot);
      else parent.setRightChild(pivot);
    }
  }

  /**
   * Performs a level-order traversal to print information about each node in the tree.
   */
  public void printTree() {
    System.out.println("Printing tree" + "\n");
    Queue<Node> nodes = new LinkedList<>();
    if (root != null) nodes.offer(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.poll(), parent = next.getParent(), left = next.getLeftChild(), right = next.getRightChild();
      System.out.println("Node is: " + next.getData());
      System.out.println("Parent is: " + ((parent == null) ? null : parent.getData()));
      System.out.println("Left child is: " + ((left == null) ? null : left.getData()));
      System.out.println("Right child is: " + ((right == null) ? null : right.getData()));
      System.out.println();
      if (left != null) nodes.offer(left);
      if (right != null) nodes.offer(right);
    }
  }
}
