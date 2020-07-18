package searching;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dataStructures.BinaryTree;

// Implementations of an Inorder, a Preorder, a Postorder, and a Level-order traversal of a binary
// tree.
public class Traversals {
  private BinaryTree<Integer> tree;

  public Traversals() {
    BinaryTree.Node<Integer> n1 = new BinaryTree.Node<>(1);
    BinaryTree.Node<Integer> n2 = new BinaryTree.Node<>(2);
    BinaryTree.Node<Integer> n3 = new BinaryTree.Node<>(3);
    BinaryTree.Node<Integer> n4 = new BinaryTree.Node<>(4);
    BinaryTree.Node<Integer> n5 = new BinaryTree.Node<>(5);
    BinaryTree.Node<Integer> n6 = new BinaryTree.Node<>(6);
    BinaryTree.Node<Integer> n7 = new BinaryTree.Node<>(7);
    BinaryTree.Node<Integer> n8 = new BinaryTree.Node<>(8);
    BinaryTree.Node<Integer> n9 = new BinaryTree.Node<>(9);
    BinaryTree.Node<Integer> n10 = new BinaryTree.Node<>(10);

    // Construct a tree of the following shape:
    //        1
    //      /   \
    //     2    3
    //    / \  / \
    //   4  5 6  7
    //     /\
    //    8 9
    //      \
    //      10

    n9.setRightChild(n10);
    n5.setLeftChild(n8);
    n5.setRightChild(n9);
    n2.setLeftChild(n4);
    n2.setRightChild(n5);
    n3.setLeftChild(n6);
    n3.setRightChild(n7);
    n1.setLeftChild(n2);
    n1.setRightChild(n3);

    tree = new BinaryTree<>(n1);
  }

  public List<Integer> listInorder() {
    List<Integer> inorderList = new LinkedList<>();
    BinaryTree.Node<Integer> root = tree.getRoot();
    inorderHelper(inorderList, root);
    return inorderList;
  }

  private void inorderHelper(List<Integer> list, BinaryTree.Node<Integer> node) {
    if (node != null) {
      inorderHelper(list, node.getLeftChild());
      list.add(node.getData());
      inorderHelper(list, node.getRightChild());
    }
  }

  public List<Integer> listPreorder() {
    List<Integer> preorderList = new LinkedList<>();
    BinaryTree.Node<Integer> root = tree.getRoot();
    preorderHelper(preorderList, root);
    return preorderList;
  }

  private void preorderHelper(List<Integer> list, BinaryTree.Node<Integer> node) {
    if (node != null) {
      list.add(node.getData());
      preorderHelper(list, node.getLeftChild());
      preorderHelper(list, node.getRightChild());
    }
  }

  public List<Integer> listPostorder() {
    List<Integer> postorderList = new LinkedList<>();
    BinaryTree.Node<Integer> root = tree.getRoot();
    postorderHelper(postorderList, root);
    return postorderList;
  }

  private void postorderHelper(List<Integer> list, BinaryTree.Node<Integer> node) {
    if (node != null) {
      postorderHelper(list, node.getLeftChild());
      postorderHelper(list, node.getRightChild());
      list.add(node.getData());
    }
  }

  public List<Integer> listLevelOrder() {
    if (tree.getRoot() == null) {
      return new LinkedList<>();
    }
    List<Integer> levelOrderList = new LinkedList<>();
    Queue<BinaryTree.Node<Integer>> queue = new LinkedList<>();
    queue.add(tree.getRoot());
    while (!queue.isEmpty()) {
      BinaryTree.Node<Integer> nextNode = queue.poll();
      levelOrderList.add(nextNode.getData());
      if (nextNode.hasLeftChild()) {
        queue.add(nextNode.getLeftChild());
      }
      if (nextNode.hasRightChild()) {
        queue.add(nextNode.getRightChild());
      }
    }
    return levelOrderList;
  }
}
