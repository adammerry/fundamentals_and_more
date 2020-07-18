package dataStructures;

import java.util.LinkedList;
import java.util.Queue;

// Implementation of a Trie data structure that allows insertion, search, and deletion.
public class Trie {
  final int ALPHABET_SIZE = 26;
  private TrieNode root;

  private class TrieNode {
    private TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    private boolean wordEnd;
    private int childCount;

    private boolean hasChild(int idx) {
      return idx >= 0 && idx < ALPHABET_SIZE && children[idx] != null;
    }

    private TrieNode getChild(int idx) {
      if (idx >= 0 && idx < ALPHABET_SIZE) {
        return children[idx];
      }
      return null;
    }

    private void setChild(int idx, TrieNode node) {
      if (idx >= 0 && idx < ALPHABET_SIZE) {
        if (children[idx] == null) {
          if (node != null) {
            childCount++;
          }
        }
        else if (node == null) {
            childCount--;
        }
        children[idx] = node;
      }
    }

    private boolean hasChildren() {
      return childCount > 0;
    }

    private boolean isEnd() {
      return wordEnd;
    }

    private void markEnd() {
      wordEnd = true;
    }

    private void unmarkEnd() {
      wordEnd = false;
    }

  }

  public void insert(String word) {
    if (word == null) {
      return;
    }
    if (root == null) {
      root = new TrieNode();
    }
    int childIdx = 0;
    TrieNode currNode = root;
    for (int i = 0; i < word.length(); i++) {
      childIdx = word.charAt(i) - 'a';
      if (!currNode.hasChild(childIdx)) {
        currNode.setChild(childIdx, new TrieNode());
      }
      currNode = currNode.getChild(childIdx);
    }
    currNode.markEnd();
  }

  public boolean search(String word) {
    if (word == null || root == null) {
      return false;
    }
    int childIdx;
    TrieNode currNode = root;
    for (int i = 0; i < word.length(); i++) {
      childIdx = word.charAt(i) - 'a';
      if (!currNode.hasChild(childIdx)) {
        return false;
      }
      currNode = currNode.getChild(childIdx);
    }
    return currNode.isEnd();
  }

  public void delete(String word) {
    if (word == null || root == null) {
      return;
    }
    root = deleteHelper(root, word, 0);
  }

  private TrieNode deleteHelper(TrieNode node, String word, int letterIdx) {
    // Case where we have reached the end of a branch without finding the word in the trie.
    if (node == null) {
      return null;
    }
    // Case where we have reached the end of the given word.
    if (letterIdx == word.length()) {
      if (node.isEnd()) {
        node.unmarkEnd();
        if (!node.hasChildren()) {
          return null;
        }
      }
    }
    // Case where we have found a word in the trie that is a prefix of the given word.
    else if (node.isEnd()) {
      int childIdx = word.charAt(letterIdx) - 'a';
      node.setChild(childIdx, deleteHelper(node.getChild(childIdx), word, letterIdx + 1));
    }
    else {
      int childIdx = word.charAt(letterIdx) - 'a';
      node.setChild(childIdx, deleteHelper(node.getChild(childIdx), word, letterIdx + 1));
      if (!node.hasChildren()) {
        return null;
      }
    }
    return node;
  }

  // Print each character in the trie using a breadth-first search.
  public void printTrieBFS() {
    Queue<TrieNode> nodes = new LinkedList<>();
    if (root != null) {
      nodes.add(root);
    }
    while (!nodes.isEmpty()) {
      TrieNode next = nodes.poll();
      if (next.hasChildren()) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
          if (next.hasChild(i)) {
            char letter = (char) ('a' + i);
            System.out.print(letter + " ");
            nodes.add(next.getChild(i));
          }
        }
      }
    }
  }
}
