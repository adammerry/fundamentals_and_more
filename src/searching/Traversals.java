package searching;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dataStructures.BinaryTree;

/**
 * Implementations of an Inorder, a Preorder, a Postorder, and a Level-order traversal of a binary
 * tree of Integers.
 */
public class Traversals {
  private final BinaryTree<Integer> tree;

  public Traversals() {
    tree = new BinaryTree<>();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);
    tree.insert(9);
    tree.insert(10);

    // Construct a tree of the following shape:
    //         1
    //       /   \
    //      2     3
    //    /  \   / \
    //   4    5 6   7
    //  /\   /
    // 8  9 10
  }

  /**
   * Produces a list of the Integers in the tree by running an inorder traversal.
   * @return an inorder list of the Integers in the tree
   */
  public List<Integer> listInorder() {
    List<Integer> inorderList = new LinkedList<>();
    inorderHelper(inorderList, tree.getRoot());
    return inorderList;
  }

  /**
   * Helper method for listInorder.
   * @param list the list to populate
   * @param node the node of the tree currently being examined
   */
  private void inorderHelper(List<Integer> list, BinaryTree<Integer>.Node node) {
    if (node != null) {
      inorderHelper(list, node.getLeftChild());
      list.add(node.getData());
      inorderHelper(list, node.getRightChild());
    }
  }

  /**
   * Produces a list of the Integers in the tree by running a preorder traversal.
   * @return a preorder list of the Integers in the tree
   */
  public List<Integer> listPreorder() {
    List<Integer> preorderList = new LinkedList<>();
    preorderHelper(preorderList, tree.getRoot());
    return preorderList;
  }

  /**
   * Helper method for listPreorder.
   * @param list the list to populate
   * @param node the node of the tree currently being examined
   */
  private void preorderHelper(List<Integer> list, BinaryTree<Integer>.Node node) {
    if (node != null) {
      list.add(node.getData());
      preorderHelper(list, node.getLeftChild());
      preorderHelper(list, node.getRightChild());
    }
  }

  /**
   * Produces a list of the Integers in the tree by running a postorder traversal.
   * @return a postorder list of the Integers in the tree
   */
  public List<Integer> listPostorder() {
    List<Integer> postorderList = new LinkedList<>();
    postorderHelper(postorderList, tree.getRoot());
    return postorderList;
  }

  /**
   * Helper method for listPostorder.
   * @param list the list to populate
   * @param node the node of the tree currently being examined
   */
  private void postorderHelper(List<Integer> list, BinaryTree<Integer>.Node node) {
    if (node != null) {
      postorderHelper(list, node.getLeftChild());
      postorderHelper(list, node.getRightChild());
      list.add(node.getData());
    }
  }

  /**
   * Produces a list of Integers in the tree be running a level-order traversal.
   * @return a level-order list of the Integers in the tree
   */
  public List<Integer> listLevelOrder() {
    List<Integer> levelOrderList = new LinkedList<>();
    Queue<BinaryTree<Integer>.Node> queue = new LinkedList<>();
    if (tree.getRoot() != null) queue.add(tree.getRoot());
    while (!queue.isEmpty()) {
      BinaryTree<Integer>.Node nextNode = queue.poll();
      levelOrderList.add(nextNode.getData());
      if (nextNode.hasLeftChild()) queue.add(nextNode.getLeftChild());
      if (nextNode.hasRightChild()) queue.add(nextNode.getRightChild());
    }
    return levelOrderList;
  }
}
