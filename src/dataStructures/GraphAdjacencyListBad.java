package dataStructures;

import java.util.List;
import java.util.LinkedList;

// A poor implementation of an undirected graph using an adjacency list representation. This
// implementation uses an array as the container for the neighbor lists, which is not the best
// way to structure an adjacency list for the following reasons:
// - It requires copying the entire graph to a new array when a new node is added.
// - It does not allow nodes to be represented by custom classes. Each node must be represented
//   by an integer, since integers must be used to index into the array.
public class GraphAdjacencyListBad {
  private List<Integer>[] adjList;

    public GraphAdjacencyListBad(int numNodes) {
      adjList = (List<Integer>[]) new List[numNodes];
      for (int i = 0; i < adjList.length; i++) {
        adjList[i] = new LinkedList<Integer>();
      }
    }

    public List<Integer>[] getGraph() { return adjList; }

    public void addEdge(int node1, int node2) {
      if (node1 < 0 || node2 < 0 || node1 >= adjList.length || node2 >= adjList.length) {
        System.out.println("Invalid node number given");
        return;
      }
      if (!checkEdge(node1, node2)) {
        adjList[node1].add(node2);
        adjList[node2].add(node1);
      }
    }

    public void addNode() {
      List<Integer>[] newAdjList = (List<Integer>[]) new List[adjList.length + 1];
      System.arraycopy(adjList, 0, newAdjList, 0, adjList.length);
      newAdjList[newAdjList.length - 1] = new LinkedList<>();
      adjList = newAdjList;
    }

    public void removeEdge(int node1, int node2) {
      int idxOf2 = adjList[node1].indexOf(node2);
      int idxOf1 = adjList[node2].indexOf(node1);
      if (idxOf2 != -1) {
        adjList[node1].remove(idxOf2);
        adjList[node2].remove(idxOf1);
      }
    }

    public void removeNode(int node) {
      // Remove the given node from all neighbor lists. Do not remove the given node's list from
      // the adjacency list, since that would cause re-numbering issues among nodes.
      List<Integer> neighbors = adjList[node];
      for (Integer neighbor : neighbors) {
        int deleteNodeIdx = adjList[neighbor].indexOf(node);
        adjList[neighbor].remove(deleteNodeIdx);
      }
      adjList[node].clear();
    }

    public boolean checkEdge(int node1, int node2) {
      return adjList[node1].contains(node2);
    }
 }
