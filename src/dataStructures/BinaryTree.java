package dataStructures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * An example of a generic Binary Tree implementation. A Binary Tree is simply a data structure
 * composed of zero or more nodes, where each node has up to two child nodes. No other restrictions
 * are placed on the structure of the tree.
 * @param <E> the type of data contained in the Binary Tree
 */
public class BinaryTree<E> {
  private Node root, lastNodeParent, lastNode;

  /**
   * The nodes that make up the Binary Tree.
   */
  public class Node {
    private final E data;
    private Node leftChild, rightChild;

    /**
     * Constructor for Node.
     * @param data the data contained in the node
     */
    public Node(E data) { this.data = data; }

    /**
     * Sets the left child of the node.
     * @param child the node to set as the left child
     */
    public void setLeftChild(Node child) { leftChild = child; }

    /**
     * Sets the right child of the node.
     * @param child the node to set as the right child
     */
    public void setRightChild(Node child) { rightChild = child; }

    /**
     * Checks whether the node has a left child.
     * @return true if the node has a left child, otherwise false
     */
    public boolean hasLeftChild() { return leftChild != null; }

    /**
     * Checks whether the node has a right child.
     * @return true if the node has a right child, otherwise false
     */
    public boolean hasRightChild() { return rightChild != null; }

    /**
     * Gets the data contained in the node.
     * @return the data contained in the node
     */
    public E getData() { return data; }

    /**
     * Gets the left child of the node.
     * @return the left child of the node
     */
    public Node getLeftChild() { return leftChild; }

    /**
     * Gets the right child of the node.
     * @return the right child of the node
     */
    public Node getRightChild() { return rightChild; }
  }

  /**
   * Gets the node designated as the root of the Binary Tree.
   * @return the node at the root of the Tree
   */
  public Node getRoot() { return root; }

  /**
   * Determines whether any node in the tree contains the given data, using a breadth-first search.
   * @param data the data to search for
   * @return true if the given data exists in the tree, false otherwise
   */
  public boolean bfs(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    Queue<Node> nodes = new LinkedList<>();
    if (root != null) nodes.offer(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.poll();
      if (next.getData().equals(data)) return true;
      if (next.hasLeftChild()) nodes.offer(next.getLeftChild());
      if (next.hasRightChild()) nodes.offer(next.getRightChild());
    }
    return false;
  }

  /**
   * Determines whether any node in the tree contains the given data, using a depth-first search.
   * @param data the data to search for
   * @return true if the given data exists in the tree, false otherwise
   */
  public boolean dfs(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    Stack<Node> nodes = new Stack<>();
    if (root != null) nodes.push(root);
    while (!nodes.isEmpty()) {
      Node next = nodes.pop();
      if (next.getData().equals(data)) return true;
      if (next.hasRightChild()) nodes.push(next.getRightChild());
      if (next.hasLeftChild()) nodes.push(next.getLeftChild());
    }
    return false;
  }

  /**
   * Inserts the data into the next available spot in the tree using level-order insertion.
   * @param data the data to insert
   */
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

  /**
   * Deletes the node with the given data, if it exists, and replaces it with the last node
   * inserted into the tree.
   * @param data the data to delete
   */
  public void delete(E data) {
    if (data == null) throw new IllegalArgumentException("Data cannot be null");
    if ((root = deleteHelper(root, data)) != null) updateLastNode();
  }

  /**
   * A helper method that recursively walks through the tree and deletes the node with the given
   * data, if it is found.
   * @param currNode the node currently being examined
   * @param data the data to delete
   * @return a tree that does not contain a node with the given data
   */
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

  /**
   * Updates the fields that keep track of the node most recently inserted into the tree and its
   * parent.
   */
  private void updateLastNode() {
    Node currNodeParent = null, currNode = root;
    while (currNode.hasLeftChild() || currNode.hasRightChild()) {
      currNodeParent = currNode;
      currNode = (currNode.hasRightChild()) ? currNode.getRightChild() : currNode.getLeftChild();
    }
    lastNodeParent = currNodeParent;
    lastNode = currNode;
  }

  /**
   * Performs a level-order traversal to print the data held in each node.
   */
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