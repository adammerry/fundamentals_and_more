package classicProblems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * An implementation of a factorial-time brute-force solution, and an exponential-time dynamic
 * programming solution to the Traveling Salesman problem on an undirected graph. (The dynamic
 * programming solution produces only the minimum cost, not the entire path.)
 */
public class TravelingSalesman {
  private final int[][] graph;

  /**
   * Constructor for an instance of the Traveling Salesman problem.
   * @param adjMatrix an undirected graph in the form of an adjacency matrix
   */
  public TravelingSalesman(int[][] adjMatrix) {
    if (adjMatrix.length < 4) throw new IllegalArgumentException("Graph is not interesting");
    else if (adjMatrix.length != adjMatrix[0].length)
      throw new IllegalArgumentException("Adjacency matrix is invalid");
    else graph = adjMatrix;
  }

  // ------------------------------------------------------------------------------------------- //

  /**
   * Produces a solution by generating all permutations of vertices, and running through them to
   * find the one with the least cost.
   * @return a list of Integers representing the order in which nodes should be visited
   */
  public List<Integer> bruteForceSolution() {
    int[] vertices = new int[graph.length - 1];
    for (int i = 0; i < graph.length - 1; i++) vertices[i] = i + 1;
    List<List<Integer>> permutations = generatePermutations(vertices);
    int minCost = Integer.MAX_VALUE;
    List<Integer> minCostPath = new LinkedList<>();
    for (List<Integer> l : permutations) {
      l.add(0);
      l.add(0, 0);
      int sum = sumPath(l);
      if (sum < minCost) {
        minCost = sum;
        minCostPath = l;
      }
    }
    return minCostPath;
  }

  /**
   * Generates all permutations of a given list of vertices.
   * @param vertices the list of vertices
   * @return a list of all the permutations of the given vertices
   */
  private static List<List<Integer>> generatePermutations(int[] vertices) {
    return permutationsHelper(vertices, 0);
  }

  /**
   * A helper method that performs recursion to generate permutations.
   * @param vertices the vertices to be permuted
   * @param idx the index of the next element to add to the permutations of the previous elements
   * @return a list of all the permutations of the given vertices
   */
  private static List<List<Integer>> permutationsHelper(int[] vertices, int idx) {
    List<List<Integer>> permutations = new LinkedList<>();
    if (idx == vertices.length) permutations.add(new ArrayList<>());
    else {
      List<List<Integer>> prevPermutations = permutationsHelper(vertices, idx + 1);
      for (List<Integer> permutation : prevPermutations) {
        for (int i = 0; i <= permutation.size(); i++) {
          List<Integer> newPermutation = new ArrayList<>(permutation);
          newPermutation.add(i, vertices[idx]);
          permutations.add(newPermutation);
        }
      }
    }
    return permutations;
  }

  /**
   * Computes the cost of the path represented by the given list of vertices.
   * @param l the list of vertices
   * @return the cost of the path
   */
  private int sumPath(List<Integer> l) {
    int sum = 0;
    Integer previous = null;
    for (Integer vertex : l) {
      if (previous != null) sum += graph[previous][vertex];
      previous = vertex;
    }
    return sum;
  }

  // ------------------------------------------------------------------------------------------- //

  /**
   * Uses dynamic programming to produce a solution that finds the length of the shortest
   * Hamiltonian cycle based on the following recurrence (where C(S, i) represents the minimum
   * cost path through each vertex in set S, beginning at vertex 0 and ending at vertex i):
   * C(S, i) = min [C(S - {i}), j) + edgeCost(j, i)] where (j is in S) && (j != 1) && (j != i)
   * - Note that this algorithm produces only the minimum cost cycle, not the ordered list of
   * vertices in said cycle.
   * @return the minimum cost Hamiltonian cycle
   */
  public Integer dynamicProgrammingSolution() {
    // [ (Set of vertices) : [ (End vertex on path beginning at vertex 0) : (min cost) ] ]
    Map<Set<Integer>, Map<Integer, Integer>> minCosts = new HashMap<>();
    int[] setElements = new int[graph.length - 1];
    for (int i = 1; i < graph.length; i++) setElements[i - 1] = i;
    List<Set<Integer>> powerSet = generatePowerSet(setElements);
    // Populate the maps with initial values.
    for (Set<Integer> set : powerSet) {
      for (Integer vertex : set) {
        Map<Integer, Integer> initialCosts = new HashMap<>();
        if (set.size() == 1) initialCosts.put(vertex, graph[0][vertex]);
        else initialCosts.put(vertex, Integer.MAX_VALUE);
        minCosts.put(set, initialCosts);
      }
    }
    // Iterate through all sets to compute the minimum cost paths that start at the start vertex,
    // end at each vertex in each set, and pass through all other vertices in each set.
    for (Set<Integer> set : powerSet) {
      if (set.size() > 1) {
        for (Integer vertex : set) {
          Set<Integer> previousSet = copyAllExcept(set, vertex);
          int minCost = getMinCostPath(minCosts.get(previousSet), vertex);
          minCosts.get(set).put(vertex, minCost);
        }
      }
    }
    // Calculate the final result by adding the cost of returning to the start vertex.
    Map<Integer, Integer> finalPathCosts = minCosts.get(powerSet.get(powerSet.size() - 1));
    int minCost = Integer.MAX_VALUE;
    for (Map.Entry<Integer, Integer> vertexCost : finalPathCosts.entrySet()) {
      int cost = vertexCost.getValue() + graph[vertexCost.getKey()][0];
      if (cost < minCost) minCost = cost;
    }
    return minCost;
  }

  /**
   * Generates the power set of the given set.
   * @param setElements an Array that represents a set
   * @return the power set of the given set
   */
  private static List<Set<Integer>> generatePowerSet(int[] setElements) {
    List<Set<Integer>> powerSet = new LinkedList<>();
    populatePowerSet(0, setElements, new Stack<>(), powerSet);
    return powerSet;
  }

  /**
   * A helper method for generatePowerSet that recursively finds all subsets of the given set.
   * @param idx the index of the next element to add to the current subset
   * @param setElements the elements of the original set
   * @param subset the subset currently being constructed
   * @param powerSet the list to which all subsets of the original set are added
   */
  private static void populatePowerSet(int idx, int[] setElements, Stack<Integer> subset,
                                       List<Set<Integer>> powerSet) {
    if (idx == setElements.length) powerSet.add(new HashSet<>(subset));
    else {
      populatePowerSet(idx + 1, setElements, subset, powerSet);
      subset.push(setElements[idx]);
      populatePowerSet(idx + 1, setElements, subset, powerSet);
      subset.pop();
    }
  }

  /**
   * Copies all elements of the given set to a new set, except the element to be excluded.
   * @param oldSet the original set
   * @param excluded the element from the original set to exclude from the new set
   * @return a set containing all elements of the original set, except the element to be excluded
   */
  private static Set<Integer> copyAllExcept(Set<Integer> oldSet, int excluded) {
    Set<Integer> newSet = new HashSet<>();
    for (int elem : oldSet) if (elem != excluded) newSet.add(elem);
    return newSet;
  }

  /**
   * Takes a map containing costs of paths ending at certain vertices and calculates the minimum
   * cost of those paths when extended to the next vertex.
   * @param vertexCosts a map in the form of [ (vertex v) : (cost of path ending at v) ]
   * @param nextVertex the vertex to which the paths will be extended
   * @return the minimum cost of all extended paths
   */
  private int getMinCostPath(Map<Integer, Integer> vertexCosts, int nextVertex) {
    int minCost = Integer.MAX_VALUE;
    for (Map.Entry<Integer, Integer> vertexCost : vertexCosts.entrySet()) {
      int cost = vertexCost.getValue() + graph[vertexCost.getKey()][nextVertex];
      if (cost < minCost) minCost = cost;
    }
    return minCost;
  }
}
