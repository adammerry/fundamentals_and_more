package dataStructures;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Implementation of a generic AVL tree. This implementation does not support duplicate node
 * values. An AVL tree is a self-balancing binary search tree where for every node, the height
 * of its left subtree differs from the height of its right subtree by at most 1.
 * @param <E> the type of data contained in the AVL tree
 */
public class AVLTree<E extends Comparable<? super E>> {
    // "removed" represents the Node object extracted from the tree during deletion, which is not
    // necessarily the same as the Node object that contains the data to delete.
    private Node root, removed;
    private int size;
    private boolean removedLeftChild;

    /**
     * Class representing the nodes in the AVL tree.
     */
    private class Node {
        private E data;
        private Node parent, leftChild, rightChild;
        private int balanceFactor;

        private Node(E data) { this.data = data; }

        private E getData() { return data; }

        private boolean hasLeftChild() { return leftChild != null; }

        private boolean hasRightChild() { return rightChild != null; }

        private Node getLeftChild() { return leftChild; }

        private Node getRightChild() { return rightChild; }

        private Node getParent() { return parent; }

        private int getBalanceFactor() { return balanceFactor; }

        private boolean isBalanced() { return balanceFactor == 0; }

        private void setData(E data) { this.data = data; }

        private void setLeftChild(Node child) { leftChild = child; }

        private void setRightChild(Node child) { rightChild = child; }

        private void setParent(Node parent) { this.parent = parent; }

        private void setBalanceFactor(int balanceFactor) { this.balanceFactor = balanceFactor; }

        // Return the largest value in the subtree rooted at the given node.
        private E getLargestVal() { return hasRightChild() ? rightChild.getLargestVal() : data; }
    }

    /**
     * Inserts the given data into the AVL tree.
     * @param insertData the data to insert
     */
    public void insert(E insertData) {
        if (insertData == null) throw new IllegalArgumentException("Data cannot be null");
        Node insertNode = new Node(insertData);
        if (root == null) {
            root = insertNode;
            size++;
            return;
        }
        Node currNode = root;
        while (true) {
            if (insertData.compareTo(currNode.getData()) < 0) {
                if (currNode.hasLeftChild()) currNode = currNode.getLeftChild();
                else {
                    currNode.setLeftChild(insertNode);
                    insertNode.setParent(currNode);
                    break;
                }
            }
            else if (insertData.compareTo(currNode.getData()) > 0) {
                if (currNode.hasRightChild()) currNode = currNode.getRightChild();
                else {
                    currNode.setRightChild(insertNode);
                    insertNode.setParent(currNode);
                    break;
                }
            }
            else break; // Duplicate value encountered.
        }
        // If node-to-insert has a null parent at this point, then it was a duplicate value.
        if (insertNode.getParent() == null) return;
        size++;
        retraceInsert(insertNode);
    }

    /**
     * Retraces the AVL tree upwards to detect and fix any imbalance caused by inserting a node.
     * @param node the inserted node
     */
    private void retraceInsert(Node node) {
        while (node.getParent() != null) {
            Node parent = node.getParent();
            if (parent.getRightChild() == node) {
                if (parent.getBalanceFactor() > 0) { // parent is already right heavy.
                    // parent would now have a balance factor of +2, so it needs rebalancing.
                    if (node.getBalanceFactor() < 0) rightRotate(node);
                    leftRotate(parent);
                    parent.setBalanceFactor(0);
                    node.setBalanceFactor(0);
                    return;
                }
                else { // parent is either perfectly balanced or left-heavy.
                    parent.setBalanceFactor(parent.getBalanceFactor() + 1);
                    // If parent is now perfectly balanced, it has absorbed the height increase and
                    // no further retracing is needed.
                    if (parent.isBalanced()) return;
                }
            }
            else { // node is left child of parent
                if (parent.getBalanceFactor() < 0) { // parent is already left heavy.
                    // parent would now have a balance factor of -2, so it needs rebalancing.
                    if (node.getBalanceFactor() > 0) leftRotate(node);
                    rightRotate(parent);
                    parent.setBalanceFactor(0);
                    node.setBalanceFactor(0);
                    return;
                }
                else { // parent is either perfectly balanced or right-heavy.
                    parent.setBalanceFactor(parent.getBalanceFactor() - 1);
                    // If parent is now perfectly balanced, it has absorbed the height increase and
                    // no further retracing is needed.
                    if (parent.isBalanced()) return;
                }
            }
            node = parent; // Keep retracing upwards if no imbalance found for this node.
        }
    }

    /**
     * Left-rotates the Subtree rooted at the given node.
     * @param node the root node of the Subtree being rotated
     */
    private void leftRotate(Node node) {
        Node pivot = node.getRightChild(), parent = node.getParent(), innerSubtree = pivot.getLeftChild();
        pivot.setParent(parent);
        pivot.setLeftChild(node);
        node.setRightChild(innerSubtree);
        node.setParent(pivot);
        if (innerSubtree != null) innerSubtree.setParent(node);
        if (parent == null) root = pivot;
        else {
            if (node == parent.getLeftChild()) parent.setLeftChild(pivot);
            else parent.setRightChild(pivot);
        }
    }

    /**
     * Right-rotates the Subtree rooted at the given node.
     * @param node the root node of the Subtree being rotated
     */
    private void rightRotate(Node node) {
        Node pivot = node.getLeftChild(), parent = node.getParent();
        Node innerSubtree = pivot.getRightChild();
        pivot.setParent(parent);
        pivot.setRightChild(node);
        node.setLeftChild(innerSubtree);
        node.setParent(pivot);
        if (innerSubtree != null) innerSubtree.setParent(node);
        if (parent == null) root = pivot;
        else {
            if (node == parent.getLeftChild()) parent.setLeftChild(pivot);
            else parent.setRightChild(pivot);
        }
    }

    /**
     * Determines whether the given data exists in the tree.
     * @param searchData the data to search for
     * @return true if the data is present in the tree, false otherwise
     */
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

    /**
     * Finds and deletes the given data from the tree, if it exists.
     * @param deleteData the data to delete
     */
    public void delete(E deleteData) {
        if (deleteData == null) throw new IllegalArgumentException("Data cannot be null");
        removed = null;
        root = deleteHelper(root, deleteData);
        if (removed != null) retraceDelete(removed);
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
        if (node.getData().equals(deleteData)) return deleteNode(node);
        if (node.getData().compareTo(deleteData) > 0)
            node.setLeftChild(deleteHelper(node.getLeftChild(), deleteData));
        else node.setRightChild(deleteHelper(node.getRightChild(), deleteData));
        return node;
    }

    /**
     * Deletes the given node from the tree, handling cases for when the node has zero, one, or two
     * children.
     * @param node the node to delete
     * @return the new subtree rooted at the given node
     */
    private Node deleteNode(Node node) {
        size--; // Decrease the size once the node-to-delete has been found.
        // Setting "removed" and "removedLeftChild" is necessary here in case the deleted node and
        // the removed node are the same.
        removed = node;
        removedLeftChild = (node.getParent() != null) && (node.getParent().getLeftChild() == node);
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
        removedLeftChild = (node.getParent().getLeftChild() == node);
        removed = node;
        return node.getLeftChild();
    }

    /**
     * Retraces the AVL tree upwards to detect and fix any imbalances caused by deleting a node.
     * @param node the deleted node
     */
    private void retraceDelete(Node node) {
        while (node.getParent() != null) {
            Node parent = node.getParent();
            if ((node == removed && removedLeftChild) || parent.getLeftChild() == node) {
                if (parent.getBalanceFactor() > 0) {
                    // parent would now have a balance factor of +2, so it needs rebalancing.
                    Node sibling = parent.getRightChild();
                    if (sibling.getBalanceFactor() < 0) rightRotate(sibling);
                    sibling.setBalanceFactor(sibling.hasRightChild() ? 1 : 0);
                    Node newSubtreeRoot = parent.getRightChild();
                    leftRotate(parent);
                    // Adjust balance factors of affected nodes.
                    parent.setBalanceFactor((parent.hasRightChild() ? 1 : 0));
                    newSubtreeRoot.setBalanceFactor(parent.getBalanceFactor() == 0 ? 0 : -1);
                    // If the height of this subtree did not change, it is safe to exit.
                    if (newSubtreeRoot.getBalanceFactor() == -1) return;
                    // If the new subtree root is perfectly balanced, then the subtree has lost a
                    // level, and retracing must continue.
                    node = newSubtreeRoot;
                }
                else { // parent is either perfectly balanced or left-heavy.
                    parent.setBalanceFactor(parent.getBalanceFactor() + 1);
                    // If parent was perfectly balanced, it has now absorbed the height decrease and
                    // no further retracing is needed.
                    if (!parent.isBalanced()) return;
                    // If parent was left-heavy, then it has now lost a level and retracing must
                    // continue.
                    node = parent;
                }
            }
            else { // node is right child of parent
                if (parent.getBalanceFactor() < 0) { // parent is already left-heavy.
                    // parent would now have a balance factor of -2, so it needs rebalancing.
                    Node sibling = parent.getLeftChild();
                    // Perform rotations to rebalance subtree.
                    if (sibling.getBalanceFactor() > 0) leftRotate(sibling);
                    sibling.setBalanceFactor(sibling.hasLeftChild() ? -1 : 0);
                    Node newSubtreeRoot = parent.getLeftChild();
                    rightRotate(parent);
                    // Adjust balance factors of affected nodes.
                    parent.setBalanceFactor((parent.hasLeftChild() ? -1 : 0));
                    newSubtreeRoot.setBalanceFactor(parent.getBalanceFactor() == 0 ? 0 : 1);
                    // If the height of this subtree did not change, it is safe to exit.
                    if (newSubtreeRoot.getBalanceFactor() == 1) return;
                    // If the new subtree root is perfectly balanced, then the subtree has lost a
                    // level, and retracing must continue.
                    node = newSubtreeRoot;
                }
                else { // parent is either perfectly balanced or right-heavy.
                    parent.setBalanceFactor(parent.getBalanceFactor() - 1);
                    // If parent was perfectly balanced, it has now absorbed the height decrease and
                    // no further retracing is needed.
                    if (!parent.isBalanced()) return;
                    // If parent was right-heavy, then it has now lost a level and retracing must
                    // continue.
                    node = parent;
                }
            }
        }
    }

    /**
     * Gets the minimum data value stored in the tree.
     * @return the minimum data value
     */
    public E getMin() {
        Node currNode = root;
        while (currNode != null) {
            if (currNode.getLeftChild() != null) currNode = currNode.getLeftChild();
            else return currNode.getData();
        }
        throw new NoSuchElementException("Tree empty");
    }

    /**
     * Gets the maximum data value stored in the tree.
     * @return the maximum data value
     */
    public E getMax() {
        Node currNode = root;
        while (currNode != null) {
            if (currNode.getRightChild() != null) currNode = currNode.getRightChild();
            else return currNode.getData();
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
        Queue<Node> nodes = new LinkedList<>();
        if (root != null) nodes.add(root);
        while (!nodes.isEmpty()) {
            Node next = nodes.poll();
            nodeData[counter++] = (Integer) next.getData();
            if (next.getLeftChild() != null) nodes.add(next.getLeftChild());
            if (next.getRightChild() != null) nodes.add(next.getRightChild());
        }
        return nodeData;
    }

    /**
     * Utility method that prints the balance factor of each node in the tree.
     */
    public void checkBalanceFactors() {
        Queue<Node> nodes = new LinkedList<>();
        if (root != null) nodes.add(root);
        while (!nodes.isEmpty()) {
            Node next = nodes.poll();
            System.out.println("Node with data: " + next.getData() + " has balance factor: " +
                    next.getBalanceFactor());
            if (next.getLeftChild() != null) nodes.add(next.getLeftChild());
            if (next.getRightChild() != null) nodes.add(next.getRightChild());
        }
    }
}
