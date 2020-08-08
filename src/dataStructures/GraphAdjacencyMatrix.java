package dataStructures;

// An implementation of an undirected, unweighted graph using an adjacency matrix.
public class GraphAdjacencyMatrix<E> {
  private boolean[][] adjMatrix;

  public GraphAdjacencyMatrix(int numNodes) {
    adjMatrix = new boolean[numNodes][numNodes];
  }

  public void addEdge(int node1, int node2) {
    if (node1 < 0 || node2 < 0 || node1 >= adjMatrix.length || node2 >= adjMatrix.length) {
      System.out.println("Invalid node number given");
      return;
    }
    adjMatrix[node1][node2] = true;
    adjMatrix[node2][node1] = true;
  }

  public boolean[][] getGraph() { return adjMatrix; }

  public void addNode() {
    boolean[][] newMatrix = new boolean[adjMatrix.length + 1][adjMatrix.length + 1];
    for (int i = 0; i < adjMatrix.length; i++) {
      System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, adjMatrix.length);
    }
    adjMatrix = newMatrix;
  }

  public void removeEdge(int node1, int node2) {
    if (node1 < 0 || node2 < 0 || node1 >= adjMatrix.length || node2 >= adjMatrix.length) {
      System.out.println("Invalid node number given");
      return;
    }
    adjMatrix[node1][node2] = false;
    adjMatrix[node2][node1] = false;
  }

  public void removeNode(int node) {
    if (node < 0 || node >= adjMatrix.length) {
      System.out.println("Invalid node number given");
      return;
    }
    // Set all other nodes to not have the given node as a neighbor. Do not remove the given node
    // from the adjacency matrix entirely, since that would cause re-numbering issues among nodes.
    for (int i = 0; i < adjMatrix.length; i++) {
      adjMatrix[i][node] = false;
      adjMatrix[node][i] = false;
    }
  }

  public boolean checkEdge(int node1, int node2) {
    if (node1 < 0 || node2 < 0 || node1 >= adjMatrix.length || node2 >= adjMatrix.length) {
      System.out.println("Invalid node number given");
      return false;
    }
    return adjMatrix[node1][node2];
  }
}
