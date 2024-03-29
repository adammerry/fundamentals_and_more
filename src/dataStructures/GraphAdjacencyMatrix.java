package dataStructures;

/**
 * An implementation of an undirected, unweighted graph using an adjacency matrix.
 */
public class GraphAdjacencyMatrix {
  private boolean[][] adjMatrix;

  /**
   * Constructor for GraphAdjacencyMatrix.
   * @param numNodes the number of nodes in the graph
   */
  public GraphAdjacencyMatrix(int numNodes) { adjMatrix = new boolean[numNodes][numNodes]; }

  /**
   * Adds an edge between the given nodes.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   */
  public void addEdge(int node1, int node2) {
    if (node1 < 0 || node2 < 0 || node1 >= adjMatrix.length || node2 >= adjMatrix.length)
      throw new IllegalArgumentException("Invalid node number given");
    adjMatrix[node1][node2] = adjMatrix[node2][node1] = true;
  }

  /**
   * Gets the adjacency matrix used to represent the graph.
   * @return the adjacency matrix
   */
  public boolean[][] getGraph() { return adjMatrix; }

  /**
   * Adds a node to the graph.
   */
  public void addNode() {
    boolean[][] newMatrix = new boolean[adjMatrix.length + 1][adjMatrix.length + 1];
    for (int i = 0; i < adjMatrix.length; i++)
      System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, adjMatrix.length);
    adjMatrix = newMatrix;
  }

  /**
   * Removes the edge between the given nodes, if it exists.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   */
  public void removeEdge(int node1, int node2) {
    if (node1 < 0 || node2 < 0 || node1 >= adjMatrix.length || node2 >= adjMatrix.length)
      throw new IllegalArgumentException("Invalid node number given");
    adjMatrix[node1][node2] = adjMatrix[node2][node1] = false;
  }

  /**
   * Removes the given node from the graph, if it exists.
   * @param node the node to remove
   */
  public void removeNode(int node) {
    if (node < 0 || node >= adjMatrix.length)
      throw new IllegalArgumentException("Invalid node number given");
    // Set all other nodes to not have the given node as a neighbor. Do not remove the given node
    // from the adjacency matrix entirely, since that would cause re-numbering issues among nodes.
    for (int i = 0; i < adjMatrix.length; i++) adjMatrix[i][node] = adjMatrix[node][i] = false;
  }

  /**
   * Determines whether the given nodes are connected by an edge.
   * @param node1 a node in the graph
   * @param node2 a node in the graph
   * @return true if the given nodes are connected by an edge, otherwise false
   */
  public boolean checkEdge(int node1, int node2) {
    return node1 >= 0 && node2 >= 0 && node1 < adjMatrix.length && node2 < adjMatrix.length &&
            adjMatrix[node1][node2];
  }
}
