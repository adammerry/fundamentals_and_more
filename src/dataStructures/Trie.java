package dataStructures;

import java.util.LinkedList;
import java.util.Queue;

// Implementation of a Trie data structure that allows insertion, search, and deletion.
public class Trie {
  private static final int ALPHABET_SIZE = 26;
  private TrieNode root;

  public Trie() { root = new TrieNode(); }

  private class TrieNode {
    private final TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    private boolean wordEnd;
    private int childCount;

    private boolean hasChild(int idx) {
      return idx >= 0 && idx < ALPHABET_SIZE && children[idx] != null;
    }

    private TrieNode getChild(int idx) {
      if (idx >= 0 && idx < ALPHABET_SIZE) return children[idx];
      return null;
    }

    private void setChild(int idx, TrieNode node) {
      if (idx >= 0 && idx < ALPHABET_SIZE) {
        if (children[idx] == null) childCount++;
        children[idx] = node;
      }
    }

    private boolean hasChildren() { return childCount > 0; }

    private boolean isEnd() { return wordEnd; }

    private void markEnd() { wordEnd = true; }

    private void unmarkEnd() { wordEnd = false; }
  }

  public void insert(String word) {
    if (word == null) throw new IllegalArgumentException("Argument cannot be null");
    int childIdx;
    TrieNode currNode = root;
    for (int i = 0; i < word.length(); i++) {
      childIdx = word.charAt(i) - 'a';
      if (!currNode.hasChild(childIdx)) currNode.setChild(childIdx, new TrieNode());
      currNode = currNode.getChild(childIdx);
    }
    currNode.markEnd();
  }

  public boolean search(String word) {
    if (word == null) throw new IllegalArgumentException("Argument cannot be null");
    int childIdx;
    TrieNode currNode = root;
    for (int i = 0; i < word.length(); i++) {
      childIdx = word.charAt(i) - 'a';
      if (!currNode.hasChild(childIdx)) return false;
      currNode = currNode.getChild(childIdx);
    }
    return currNode.isEnd();
  }

  public void delete(String word) {
    if (word == null) throw new IllegalArgumentException("Argument cannot be null");
    root = deleteHelper(root, word, 0);
  }

  private TrieNode deleteHelper(TrieNode node, String word, int letterIdx) {
    if (node == null) return null; // We have reached the end of a branch without finding the word.
    if (letterIdx == word.length()) { // We have reached the end of the given word.
      if (node.isEnd()) node.unmarkEnd();
      return (node == root || node.hasChildren()) ? node : null;
    }
    int childIdx = word.charAt(letterIdx) - 'a';
    TrieNode child = deleteHelper(node.getChild(childIdx), word, letterIdx + 1);
    if (child == null) node.childCount--;
    return (node == root || node.isEnd() || node.hasChildren()) ? node : null;
  }

  // Print each character in the trie using a breadth-first search.
  public void printTrieBFS() {
    Queue<TrieNode> nodes = new LinkedList<>();
    nodes.add(root);
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
