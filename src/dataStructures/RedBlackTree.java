package dataStructures;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Implementation of a generic Red-Black tree. This implementation does not support duplicate node
 * values. A Red-Black tree is a self-balancing binary search tree that obeys the following
 * properties:
 * - Each node is given a color; either red or black.
 * - The root of the tree is always black.
 * - No red node has a red child.
 * - For any node, every path from that node to a leaf touches the same number of black nodes.
 * @param <E> the type of data contained in the red-black tree
 */
public class RedBlackTree<E extends Comparable<? super E>> {
  private TreeNode root = new NullNode();
  private int size;

  private enum Color {
    RED,
    BLACK
  }

  /**
   * Abstract class representing both types of nodes that make up the Red-Black tree.
   */
  private abstract class TreeNode {
    DataNode parent;
    Color color;
    boolean isDoubleBlack;

    abstract TreeNode insertHelper(DataNode insertDataNode, DataNode parent);

    abstract boolean isDataNode();

    void setColor(Color color) { this.color = color; }

    void setParent(DataNode parent) { this.parent = parent; }

    DataNode getParent() { return parent; }

    Color getColor() { return color; }
  }

  /**
   * Class representing nodes in the Red-Black tree that store data.
   */
  private class DataNode extends TreeNode {
    private E data;
    private TreeNode leftChild, rightChild;

    private DataNode(E data) {
      this.data = data;
      this.color = Color.RED;
      this.isDoubleBlack = false;
      this.leftChild = new NullNode();
      this.rightChild = new NullNode();
    }

    TreeNode insertHelper(DataNode insertDataNode, DataNode parent) {
      if (insertDataNode.getData().compareTo(getData()) < 0)
        setLeftChild(leftChild.insertHelper(insertDataNode, this));
      else if (insertDataNode.getData().compareTo(getData()) > 0)
        setRightChild(rightChild.insertHelper(insertDataNode, this));
      return this;
    }

    boolean isDataNode() { return true; }

    private void setData(E data) { this.data = data; }

    private void setLeftChild(TreeNode child) { leftChild = child; }

    private void setRightChild(TreeNode child) { rightChild = child; }

    private E getData() { return data; }

    private TreeNode getLeftChild() { return leftChild; }

    private TreeNode getRightChild() { return rightChild; }
  }

  /**
   * Class representing nodes in the Red-Black tree that do not store data. These nodes are used
   * as children of data-storing nodes to indicate the lack of a data-storing child. All null nodes
   * are black, and are capable of becoming double-black nodes during the deletion process.
   */
  private class NullNode extends TreeNode {

    private NullNode() {
      color = Color.BLACK;
      isDoubleBlack = false;
    }

    DataNode insertHelper(DataNode insertDataNode, DataNode parent) {
      insertDataNode.setParent(parent);
      return insertDataNode;
    }

    boolean isDataNode() { return false; }
  }

  /**
   * Inserts the given data into the red-black tree.
   * @param insertData the data to insert
   */
  public void insert(E insertData) {
    if (insertData == null) throw new IllegalArgumentException("Data cannot be null");
    DataNode insertDataNode = new DataNode(insertData);
    root = root.isDataNode() ? root.insertHelper(insertDataNode, null) : insertDataNode;
    fixInsert(insertDataNode);
    root.setColor(Color.BLACK);
    // If node-to-insert has a value that was already contained in the tree, it will have a null
    // parent, and will not be the root. We only want to increase "size" upon insertion of
    // non-duplicate values.
    if (insertDataNode.getParent() != null || insertDataNode == root) size++;
  }

  /**
   * Fixes any red-black tree properties that were violated by inserting the given node.
   * @param insertDataNode the recently inserted node
   */
  private void fixInsert(DataNode insertDataNode) {
    DataNode current = insertDataNode, parent = insertDataNode.getParent();
    while (parent != null && parent.getColor() == Color.RED) {
      // grandparent must not be null, since parent is red and root must be black.
      DataNode grandparent = parent.getParent();
      if (parent == grandparent.getLeftChild()) {
        TreeNode uncle = grandparent.getRightChild();
        if (uncle.getColor() == Color.RED) { // Case: UNCLE RED.
          parent.setColor(Color.BLACK);
          uncle.setColor(Color.BLACK);
          grandparent.setColor(Color.RED);
          current = grandparent;
          parent = current.getParent();
        }
        else if (current == parent.getRightChild()) { // Case: LEFT, RIGHT.
          leftRotate(parent);
          current.setColor(Color.BLACK);
          grandparent.setColor(Color.RED);
          rightRotate(grandparent);
          current = parent;
          parent = current.getParent();
        }
        else { // Case: LEFT, LEFT.
          parent.setColor(Color.BLACK);
          grandparent.setColor(Color.RED);
          rightRotate(grandparent);
        }
      }
      else { // parent is the right child of grandparent.
        TreeNode uncle = grandparent.getLeftChild();
        if (uncle.getColor() == Color.RED) { // Case: UNCLE RED.
          parent.setColor(Color.BLACK);
          uncle.setColor(Color.BLACK);
          grandparent.setColor(Color.RED);
          current = grandparent;
          parent = current.getParent();
        }
        else if (current == parent.getLeftChild()) { // Case: RIGHT, LEFT.
          rightRotate(parent);
          current.setColor(Color.BLACK);
          grandparent.setColor(Color.RED);
          leftRotate(grandparent);
          current = parent;
          parent = current.getParent();
        }
        else { // Case: RIGHT, RIGHT.
          parent.setColor(Color.BLACK);
          grandparent.setColor(Color.RED);
          leftRotate(grandparent);
        }
      }
    }
    root.setColor(Color.BLACK);
  }

  /**
   * Left-rotates the Subtree rooted at the given node.
   * @param n the root node of the Subtree being rotated
   */
  private void leftRotate(DataNode n) {
    TreeNode maybePivot = n.getRightChild();
    if (!maybePivot.isDataNode())
      throw new IllegalStateException("Left rotation can't be performed without right child");
    DataNode pivot = (DataNode) maybePivot, parent = n.getParent();
    TreeNode innerSubtree = pivot.getLeftChild();
    pivot.setLeftChild(n);
    pivot.setParent(parent);
    n.setRightChild(innerSubtree);
    n.setParent(pivot);
    innerSubtree.setParent(n);
    if (parent != null) {
      if (n == parent.getLeftChild()) parent.setLeftChild(pivot);
      else parent.setRightChild(pivot);
    }
    if (root == n) root = pivot;
  }

  /**
   * Right-rotates the Subtree rooted at the given node.
   * @param n the root node of the Subtree being rotated
   */
  private void rightRotate(DataNode n) {
    TreeNode maybePivot = n.getLeftChild();
    if (!maybePivot.isDataNode())
      throw new IllegalStateException("Right rotation can't be performed without left child");
    DataNode pivot = (DataNode) maybePivot, parent = n.getParent();
    TreeNode innerSubtree = pivot.getRightChild();
    pivot.setRightChild(n);
    pivot.setParent(parent);
    n.setLeftChild(innerSubtree);
    n.setParent(pivot);
    innerSubtree.setParent(n);
    if (parent != null) {
      if (parent.getLeftChild() == n) parent.setLeftChild(pivot);
      else parent.setRightChild(pivot);
    }
    if (root == n) root = pivot;
  }

  /**
   * Determines whether the given data exists in the tree.
   * @param searchData the data to search for
   * @return true if the data is present in the tree, false otherwise
   */
  public boolean search(E searchData) {
    if (searchData  == null) throw new IllegalArgumentException("Data cannot be null");
    TreeNode currNode = root;
    while (currNode.isDataNode()) {
      DataNode currDataNode = (DataNode) currNode;
      if (currDataNode.getData().equals(searchData)) return true;
      if (currDataNode.getData().compareTo(searchData) > 0)
          currNode = currDataNode.getLeftChild();
        else currNode = currDataNode.getRightChild();
      }
    return false;
  }

  /**
   * Finds and deletes the given data from the tree, if it exists.
   * @param deleteData the data to delete
   */
  public void delete(E deleteData) {
    if (deleteData == null) throw new IllegalArgumentException("Data cannot be null");
    // Find the node to be deleted.
    DataNode deleteNode = findNode(deleteData);
    if (deleteNode != null) {
      // If node to delete has two data-storing children, replace it with its inorder successor.
      if (deleteNode.getLeftChild().isDataNode() && deleteNode.getRightChild().isDataNode()) {
        DataNode successor = findLeftMostNode((DataNode) deleteNode.getRightChild());
        deleteNode.setData(successor.getData());
        deleteNode = successor;
      }
      // If node to delete has < 2 data-storing children, and is the root, replace it appropriately.
      else if (deleteNode == root) {
        TreeNode leftChild = deleteNode.getLeftChild();
        root = (leftChild.isDataNode()) ? leftChild : deleteNode.getRightChild();
        root.setColor(Color.BLACK);
        size--;
        return;
      }
      // We now know that the node-to-delete is not the root, and has < 2 data-storing children.
      // Replace the node-to-delete with the data-storing child if possible. If node-to-delete is
      // red, we are done. Otherwise, check if the new child is red. If so, set its color to
      // black. If not, we have a double-black situation.
      TreeNode leftChild = deleteNode.getLeftChild();
      TreeNode newChild = leftChild.isDataNode() ? leftChild : deleteNode.getRightChild();
      DataNode parent = deleteNode.getParent();
      boolean isLeftChild = true;
      if (deleteNode == parent.getLeftChild()) parent.setLeftChild(newChild);
      else {
        isLeftChild = false;
        parent.setRightChild(newChild);
      }
      newChild.setParent(parent);
      if (deleteNode.getColor() == Color.BLACK) {
        if (newChild.getColor() == Color.RED) newChild.setColor(Color.BLACK);
        else {
          // The node-to-delete is black, and it does not have any red children. This situation
          // requires additional case evaluation in order to keep the black-height property
          // satisfied.
          handleDoubleBlack(newChild, isLeftChild);
        }
      }
      size--;
    }
  }

  /**
   * Finds the node with the given data in the tree. Similar to search, but returns the actual
   * node.
   * @param searchData the data to search for
   * @return the node with data equal to the given data
   */
  private DataNode findNode(E searchData) {
    TreeNode currNode = root;
    while (currNode.isDataNode()) {
      DataNode currDataNode = (DataNode) currNode;
      if (currDataNode.getData().equals(searchData)) return currDataNode;
      if (currDataNode.getData().compareTo(searchData) > 0)
        currNode = currDataNode.getLeftChild();
      else currNode = currDataNode.getRightChild();
    }
    return null;
  }

  /**
   * Handles the case in the deletion process where the node-to-delete is black, and does not have
   * any red children.
   * @param doubleBlackNode the black child of the black node-to-delete
   * @param isLeftChild true if the child is a left child, false if it is a right child
   */
  private void handleDoubleBlack(TreeNode doubleBlackNode, boolean isLeftChild) {
    // Case 1: Double-black node is root.
    // Case 2: Sibling is red.
    // Case 3: Parent is black, sibling is black, and sibling's children are both black.
    // Case 4: Parent is red, sibling is black, and sibling's children are both black.
    // Case 5: Sibling is black, sibling's outer child is black, and sibling's inner child is red.
    // Case 6: Sibling is black, sibling's outer child is red.
    int caseNum = checkCaseNum(doubleBlackNode, isLeftChild);
    DataNode parent = doubleBlackNode.getParent();
    DataNode sibling = (DataNode) (isLeftChild ? parent.getRightChild() :
            parent.getLeftChild());
    if (caseNum == 1) return;
    if (caseNum == 2) {
      if (isLeftChild) leftRotate(doubleBlackNode.getParent());
      else rightRotate(doubleBlackNode.getParent());
      parent.setColor(Color.RED);
      sibling.setColor(Color.BLACK);
      // Reset parent and sibling after rotation.
      parent = doubleBlackNode.getParent();
      sibling = (DataNode) (isLeftChild ? parent.getRightChild() : parent.getLeftChild());
      // Either case 4, case 5, or case 6 will now be applicable.
      caseNum = checkCaseNum(doubleBlackNode, isLeftChild);
    }
    if (caseNum == 3) {
      sibling.setColor(Color.RED);
      // The double-black node is now parent. If parent is root, we can short-circuit, since this
      // situation corresponds to case 1. Otherwise, we recurse on parent.
      if (parent != root) handleDoubleBlack(parent, parent.getParent().getLeftChild() == parent);
    }
    if (caseNum == 4) {
      parent.setColor(Color.BLACK);
      sibling.setColor(Color.RED);
      return;
    }
    if (caseNum == 5) {
      if (isLeftChild) rightRotate(sibling);
      else leftRotate(sibling);
      sibling.setColor(Color.RED);
      sibling.getParent().setColor(Color.BLACK);
      // Reset parent and sibling after rotation.
      parent = doubleBlackNode.getParent();
      sibling = (DataNode) (isLeftChild ? parent.getRightChild() : parent.getLeftChild());
      caseNum = 6;
    }
    if (caseNum == 6) {
      if (isLeftChild) leftRotate(parent);
      else rightRotate(parent);
      sibling.setColor(parent.getColor());
      parent.setColor(Color.BLACK);
      if (isLeftChild) sibling.getRightChild().setColor(Color.BLACK);
      else sibling.getLeftChild().setColor(Color.BLACK);
    }
  }

  /**
   * Checks which double-black situation exists for the given double-black node.
   * @param doubleBlackNode the double-black node
   * @param isLeftChild true if the double-black node is a left child, false if it is a right child
   * @return the number that corresponds to whichever double-black case exists
   */
  private int checkCaseNum(TreeNode doubleBlackNode, boolean isLeftChild) {
    if (doubleBlackNode == root) return 1; // Case 1.
    DataNode parent = doubleBlackNode.getParent();
    // Because insertion always maintains the black-height property, every black data-storing node
    // must have a data-storing sibling. Therefore, it is always safe to perform the following cast.
    DataNode sibling = (DataNode) (isLeftChild ? parent.getRightChild() :
            parent.getLeftChild());
    if (sibling.getColor() == Color.RED) return 2;
    // At this point, we know that the sibling is black.
    boolean leftSibChildBlack = sibling.getLeftChild().getColor() == Color.BLACK;
    boolean rightSibChildBlack = sibling.getRightChild().getColor() == Color.BLACK;
    if (leftSibChildBlack && rightSibChildBlack) return (parent.getColor() == Color.BLACK) ? 3 : 4;
    // At this point, we know that the sibling has at least one red child.
    return ((isLeftChild && rightSibChildBlack) || ((!isLeftChild) && leftSibChildBlack)) ? 5 : 6;
  }

  /**
   * Finds the left-most node of the Subtree rooted at the given node.
   * @param n the root of the current Subtree
   * @return the left-most node of the current Subtree
   */
  private DataNode findLeftMostNode(DataNode n) {
    DataNode successor = n;
    while (n.getLeftChild().isDataNode()) successor = (DataNode) n.getLeftChild();
    return successor;
  }

  /**
   * Gets the minimum data value stored in the tree.
   * @return the minimum data value
   */
  public E getMin() {
    TreeNode currNode = root;
    while (currNode.isDataNode()) {
      DataNode currDataNode = (DataNode) currNode;
      if (currDataNode.getLeftChild().isDataNode()) currNode = currDataNode.getLeftChild();
      else return currDataNode.getData();
    }
    throw new NoSuchElementException("Tree empty");
  }

  /**
   * Gets the maximum data value stored in the tree.
   * @return the maximum data value
   */
  public E getMax() {
    TreeNode currNode = root;
    while (currNode.isDataNode()) {
      DataNode currDataNode = (DataNode) currNode;
      if (currDataNode.getRightChild().isDataNode()) currNode = currDataNode.getRightChild();
      else return currDataNode.getData();
    }
    throw new NoSuchElementException("Tree empty");
  }

  /**
   * Performs a level-order traversal of the tree, and casts the data at each node to an Integer.
   * @return an array of Integers containing a level-order traversal of the tree
   */
  public Integer[] levelOrderTraversal() {
    Integer[] nodeData = new Integer[size];
    int counter = 0;
    Queue<DataNode> nodes = new LinkedList<>();
    if (root.isDataNode()) nodes.add((DataNode) root);
    while (!nodes.isEmpty()) {
      DataNode next = nodes.poll();
      nodeData[counter++] = (Integer) next.getData();
      if (next.getLeftChild().isDataNode()) nodes.add((DataNode) next.getLeftChild());
      if (next.getRightChild().isDataNode()) nodes.add((DataNode) next.getRightChild());
    }
    return nodeData;
  }
}