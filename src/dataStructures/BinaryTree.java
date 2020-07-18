package dataStructures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// Example of a generic Binary Tree implementation. A Binary Tree is simply a data structure
// composed of zero or more nodes, where each node has up to two child nodes. No other restrictions
// are placed on the structure of the tree.
public class BinaryTree<E> {
  private Node<E> root;

  public BinaryTree(Node<E> root) {
    this.root = root;
  }

  public BinaryTree() {}

  public Node<E> getRoot() {
    return root;
  }

  public static class Node<E> {
    private E data;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public Node(E data) {
      this.data = data;
    }

    public void setLeftChild(Node<E> child) {
      leftChild = child;
    }

    public void setRightChild(Node<E> child) {
      rightChild = child;
    }

    public boolean hasLeftChild() {
      return leftChild != null;
    }

    public boolean hasRightChild() {
      return rightChild != null;
    }

    public E getData() {
      return data;
    }

    public Node<E> getLeftChild() {
      return leftChild;
    }

    public Node<E> getRightChild() {
      return rightChild;
    }
  }

  // Find the node in the tree that contains the given data, using a breadth-first search. If no
  // such node exists, return null.
  public Node<E> bfs(E data) {
    if (root == null || data == null) {
      return null;
    }
    Queue<Node<E>> nodes = new LinkedList<>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
      Node<E> next = nodes.poll();
      if (next.getData().equals(data)) {
        return next;
      }
      if (next.hasLeftChild()) {
        nodes.add(next.getLeftChild());
      }
      if (next.hasRightChild()) {
        nodes.add(next.getRightChild());
      }
    }
    return null;
  }

  // Find the node in the tree that contains the given data, using a depth-first search. If no
  // such node exists, return null.
  public Node<E> dfs(E data) {
    if (root == null || data == null) {
      return null;
    }
    Stack<Node<E>> nodes = new Stack<>();
    nodes.push(root);
    while (!nodes.isEmpty()) {
      Node<E> next = nodes.pop();
      if (next.getData().equals(data)) {
        return next;
      }
      if (next.hasLeftChild()) {
        nodes.add(next.getLeftChild());
      }
      if (next.hasRightChild()) {
        nodes.add(next.getRightChild());
      }
    }
    return null;
  }

  // Level order insertion. Insert the given data into the next available spot in the tree,
  // searching from top to bottom, left to right.
  public void insert(E data) {
    if (data == null) {
      return;
    }
    if (root == null) {
      root = new Node<E>(data);
      return;
    }
    Queue<Node<E>> nodes = new LinkedList<>();
    nodes.add(root);
    while (true) {
      Node<E> next = nodes.poll();
      if (!next.hasLeftChild()) {
        next.setLeftChild(new Node<>(data));
        return;
      }
      if (!next.hasRightChild()) {
        next.setRightChild(new Node<>(data));
        return;
      }
      nodes.add(next.getLeftChild());
      nodes.add(next.getRightChild());
    }
  }

  // Delete the node with the given data.
  public void delete(E data) {
    root = deleteHelper(root, data);
  }

  private Node<E> deleteHelper(Node<E> root, E data) {
    if (root == null || data == null) {
      return root;
    }
    if (root.getData().equals(data)) {
      if (root.hasLeftChild()) {
        return root.getLeftChild();
      }
      return root.getRightChild();
    }
    root.setLeftChild((deleteHelper(root.getLeftChild(), data)));
    root.setRightChild((deleteHelper(root.getRightChild(), data)));
    return root;
  }

  // Print the value of each node in the tree in the order that they would be reached using a
  // breadth-first search.
  public void printTree() {
    Queue<Node<E>> nodes = new LinkedList<>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
      Node<E> next = nodes.poll();
      System.out.print(next.getData() + " ");
      if (next.getLeftChild() != null) {
        nodes.add(next.getLeftChild());
      }
      if (next.getRightChild() != null) {
        nodes.add(next.getRightChild());
      }
    }
  }
}