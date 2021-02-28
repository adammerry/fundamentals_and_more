package dataStructures;

import java.util.LinkedList;
import java.util.Queue;

// Implementation of a generic Red-Black tree that offers the following operations:
// insert, search, delete, getMin, and getMax. This implementation does not support duplicate
// node values.
// A Red-Black tree is a self-balancing binary search tree that obeys the following properties:
// - Each node is given a color; either red or black.
// - The root of the tree is always black.
// - No red node has a red child.
// - For any node, every path from that node to a leaf touches the same number of black nodes.
public class RedBlackTree<E extends Comparable<? super E>> {
  private TreeNode<E> root = new NullNode<>();
  private int size;

  private enum Color {
    RED,
    BLACK
  }

  // Abstract class representing both types of nodes that make up the Red-Black tree.
  private static abstract class TreeNode<E extends Comparable<? super E>> {
    DataNode<E> parent;
    Color color;
    boolean isDoubleBlack;

    abstract TreeNode<E> insertHelper(DataNode<E> insertDataNode, DataNode<E> parent);

    abstract boolean isDataNode();

    void setColor(Color color) { this.color = color; }

    void setParent(DataNode<E> parent) { this.parent = parent; }

    DataNode<E> getParent() { return parent; }

    Color getColor() { return color; }
  }

  // Class representing nodes in the Red-Black tree that store data.
  public static class DataNode<E extends Comparable<? super E>> extends TreeNode<E> {
    private E data;
    private TreeNode<E> leftChild, rightChild;

    private DataNode(E data) {
      this.data = data;
      this.color = Color.RED;
      this.isDoubleBlack = false;
      this.leftChild = new NullNode<>();
      this.rightChild = new NullNode<>();
    }

    TreeNode<E> insertHelper(DataNode<E> insertDataNode, DataNode<E> parent) {
      if (insertDataNode.getData().compareTo(getData()) < 0)
        setLeftChild(leftChild.insertHelper(insertDataNode, this));
      else if (insertDataNode.getData().compareTo(getData()) > 0)
        setRightChild(rightChild.insertHelper(insertDataNode, this));
      return this;
    }

    boolean isDataNode() { return true; }

    private void setData(E data) { this.data = data; }

    private void setLeftChild(TreeNode<E> child) { leftChild = child; }

    private void setRightChild(TreeNode<E> child) { rightChild = child; }

    public E getData() { return data; }

    private TreeNode<E> getLeftChild() { return leftChild; }

    private TreeNode<E> getRightChild() { return rightChild; }
  }

  // Class representing nodes in the Red-Black tree that do not store data. These nodes are used
  // as children of data-storing nodes to indicate the lack of a data-storing child. All null nodes
  // are black, and are capable of becoming double-black nodes during the deletion process.
  private static class NullNode<E extends Comparable<? super E>> extends TreeNode<E> {

    private NullNode() {
      color = Color.BLACK;
      isDoubleBlack = false;
    }

    DataNode<E> insertHelper(DataNode<E> insertDataNode, DataNode<E> parent) {
      insertDataNode.setParent(parent);
      return insertDataNode;
    }

    boolean isDataNode() { return false; }
  }

  public void insert(E insertData) {
    if (insertData != null) {
      DataNode<E> insertDataNode = new DataNode<>(insertData);
      root = root.isDataNode() ? root.insertHelper(insertDataNode, null) : insertDataNode;
      fixInsert(insertDataNode);
      root.setColor(Color.BLACK);
      // If node-to-insert has a value that was already contained in the tree, it will have a null
      // parent, and will not be the root. We only want to increase "size" upon insertion of
      // non-duplicate values.
      if (insertDataNode.getParent() != null || insertDataNode == root) size++;
    }
  }

  private void fixInsert(DataNode<E> insertDataNode) {
    DataNode<E> current = insertDataNode, parent = insertDataNode.getParent();
    while (parent != null && parent.getColor() == Color.RED) {
      // grandparent must not be null, since parent is red and root must be black.
      DataNode<E> grandparent = parent.getParent();
      if (parent == grandparent.getLeftChild()) {
        TreeNode<E> uncle = grandparent.getRightChild();
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
        TreeNode<E> uncle = grandparent.getLeftChild();
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

  private void leftRotate(DataNode<E> n) {
    TreeNode<E> maybePivot = n.getRightChild();
    if (!maybePivot.isDataNode()) {
      System.out.println("Left rotation can't be performed when right child doesn't exist.");
      return;
    }
    DataNode<E> pivot = (DataNode<E>) maybePivot, parent = n.getParent();
    TreeNode<E> innerSubTree = pivot.getLeftChild();
    pivot.setLeftChild(n);
    pivot.setParent(parent);
    n.setRightChild(innerSubTree);
    n.setParent(pivot);
    innerSubTree.setParent(n);
    if (parent != null) {
      if (n == parent.getLeftChild()) parent.setLeftChild(pivot);
      else parent.setRightChild(pivot);
    }
    if (root == n) root = pivot;
  }

  private void rightRotate(DataNode<E> n) {
    TreeNode<E> maybePivot = n.getLeftChild();
    if (!maybePivot.isDataNode()) {
      System.out.println("Right rotation can't be performed when left child doesn't exist.");
      return;
    }
    DataNode<E> pivot = (DataNode<E>) maybePivot, parent = n.getParent();
    TreeNode<E> innerSubTree = pivot.getRightChild();
    pivot.setRightChild(n);
    pivot.setParent(parent);
    n.setLeftChild(innerSubTree);
    n.setParent(pivot);
    innerSubTree.setParent(n);
    if (parent != null) {
      if (parent.getLeftChild() == n) parent.setLeftChild(pivot);
      else parent.setRightChild(pivot);
    }
    if (root == n) root = pivot;
  }

  // Find and return the node in the tree with data equal to the given data. If no such node
  // exists, return null.
  public DataNode<E> search(E searchData) {
    if (searchData != null) {
      TreeNode<E> currNode = root;
      while (currNode.isDataNode()) {
        DataNode<E> currDataNode = (DataNode<E>) currNode;
        if (currDataNode.getData().equals(searchData)) return currDataNode;
        if (currDataNode.getData().compareTo(searchData) > 0)
          currNode = currDataNode.getLeftChild();
        else
          currNode = currDataNode.getRightChild();
      }
    }
    return null;
  }

  public void delete(E deleteData) {
    if (deleteData == null) return;
    // Find the node to be deleted.
    DataNode<E> deleteNode = search(deleteData);
    if (deleteNode != null) {
      // If node to delete has two data-storing children, replace it with it's inorder successor.
      if (deleteNode.getLeftChild().isDataNode() && deleteNode.getRightChild().isDataNode()) {
        DataNode<E> successor = findLeftMostNode((DataNode<E>) deleteNode.getRightChild());
        deleteNode.setData(successor.getData());
        deleteNode = successor;
      }
      // If node to delete has < 2 data-storing children, and is the root, replace it appropriately.
      else if (deleteNode == root) {
        TreeNode<E> leftChild = deleteNode.getLeftChild();
        root = (leftChild.isDataNode()) ? leftChild : deleteNode.getRightChild();
        root.setColor(Color.BLACK);
        size--;
        return;
      }
      // We now know that the node-to-delete is not the root, and has < 2 data-storing children.
      // Replace the node-to-delete with the data-storing child if possible. If node-to-delete is
      // red, we are done. Otherwise, check if the new child is red. If so, set its color to
      // black. If not, we have a double-black situation.
      TreeNode<E> leftChild = deleteNode.getLeftChild();
      TreeNode<E> newChild = leftChild.isDataNode() ? leftChild : deleteNode.getRightChild();
      DataNode<E> parent = deleteNode.getParent();
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

  private void handleDoubleBlack(TreeNode<E> doubleBlackNode, boolean isLeftChild) {
    // Case 1: Double-black node is root.
    // Case 2: Sibling is red.
    // Case 3: Parent is black, sibling is black, and sibling's children are both black.
    // Case 4: Parent is red, sibling is black, and sibling's children are both black.
    // Case 5: Sibling is black, sibling's outer child is black, and sibling's inner child is red.
    // Case 6: Sibling is black, sibling's outer child is red.
    int caseNum = checkCaseNum(doubleBlackNode, isLeftChild);
    DataNode<E> parent = doubleBlackNode.getParent();
    DataNode<E> sibling = (DataNode<E>) (isLeftChild ? parent.getRightChild() :
            parent.getLeftChild());
    if (caseNum == 1) return;
    if (caseNum == 2) {
      if (isLeftChild) leftRotate(doubleBlackNode.getParent());
      else rightRotate(doubleBlackNode.getParent());
      parent.setColor(Color.RED);
      sibling.setColor(Color.BLACK);
      // Reset parent and sibling after rotation.
      parent = doubleBlackNode.getParent();
      sibling = (DataNode<E>) (isLeftChild ? parent.getRightChild() : parent.getLeftChild());
      // Either case 4, case 5, or case 6 will now be applicable.
      caseNum = checkCaseNum(doubleBlackNode, isLeftChild);
    }
    if (caseNum == 3) {
      sibling.setColor(Color.RED);
      // The double-black node is now parent. If parent is root, we can short-circuit, since this
      // situation corresponds to case 1. Otherwise, we recurse on parent.
      if (parent != root) {
        if (parent.getParent().getLeftChild() == parent) handleDoubleBlack(parent, true);
        else handleDoubleBlack(parent, false);
      }
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
      sibling = (DataNode<E>) (isLeftChild ? parent.getRightChild() : parent.getLeftChild());
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

  private int checkCaseNum(TreeNode<E> doubleBlackNode, boolean isLeftChild) {
    if (doubleBlackNode == root) return 1; // Case 1.
    DataNode<E> parent = doubleBlackNode.getParent();
    // Because insertion always maintains the black-height property, every black data-storing node
    // must have a data-storing sibling. Therefore, it is always safe to perform the following cast.
    DataNode<E> sibling = (DataNode<E>) (isLeftChild ? parent.getRightChild() :
            parent.getLeftChild());
    if (sibling.getColor() == Color.RED) return 2;
    // At this point, we know that the sibling is black.
    boolean leftSibChildBlack = sibling.getLeftChild().getColor() == Color.BLACK;
    boolean rightSibChildBlack = sibling.getRightChild().getColor() == Color.BLACK;
    if (leftSibChildBlack && rightSibChildBlack) return (parent.getColor() == Color.BLACK) ? 3 : 4;
    // At this point, we know that the sibling has at least one red child.
    return ((isLeftChild && rightSibChildBlack) || ((!isLeftChild) && leftSibChildBlack)) ? 5 : 6;
  }

  private DataNode<E> findLeftMostNode(DataNode<E> n) {
    DataNode<E> successor = n;
    while (n.getLeftChild().isDataNode()) successor = (DataNode<E>) n.getLeftChild();
    return successor;
  }

  public E getMin() {
    TreeNode<E> currNode = root;
    while (currNode.isDataNode()) {
      DataNode<E> currDataNode = (DataNode<E>) currNode;
      if (currDataNode.getLeftChild().isDataNode()) currNode = currDataNode.getLeftChild();
      else return currDataNode.getData();
    }
    return null;
  }

  public E getMax() {
    TreeNode<E> currNode = root;
    while (currNode.isDataNode()) {
      DataNode<E> currDataNode = (DataNode<E>) currNode;
      if (currDataNode.getRightChild().isDataNode()) currNode = currDataNode.getRightChild();
      else return currDataNode.getData();
    }
    return null;
  }

  // Return an array containing a level-order traversal of all nodes, where the data at each node
  // is cast to an Integer.
  public Integer[] levelOrderTraversal() {
    Integer[] nodeData = new Integer[size];
    int counter = 0;
    Queue<DataNode<E>> nodes = new LinkedList<>();
    if (root.isDataNode()) nodes.add((DataNode<E>) root);
    while (!nodes.isEmpty()) {
      DataNode<E> next = nodes.poll();
      nodeData[counter++] = (Integer) next.getData();
      if (next.getLeftChild().isDataNode()) nodes.add((DataNode<E>) next.getLeftChild());
      if (next.getRightChild().isDataNode()) nodes.add((DataNode<E>) next.getRightChild());
    }
    return nodeData;
  }
}