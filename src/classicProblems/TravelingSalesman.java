package classicProblems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

// Implementations of a factorial-time brute-force solution, and exponential-time dynamic
// programming solution to the traveling salesman problem on an undirected graph. (The dynamic
// programming solution produces only the minimum cost, not the entire path).
public class TravelingSalesman {
  private int[][] graph = new int[0][0];

  public TravelingSalesman(int[][] adjMatrix) {
    if (adjMatrix.length < 4) System.out.println("Please provide a more interesting graph");
    else if (adjMatrix.length != adjMatrix[0].length)
      System.out.println("Adjacency matrix is invalid");
    else graph = adjMatrix;
  }

  // ------------------------------------------------------------------------------------------- //

  // The brute-force solution will generate all permutations of vertices, and run through them to
  // find the one with the least cost.
  public List<Integer> bruteForceSolution() {
    List<List<Integer>> permutations = new LinkedList<>();
    int[] vertices = new int[graph.length - 1];
    for (int i = 0; i < graph.length - 1; i++) vertices[i] = i + 1;
    generatePermutations(vertices, permutations, graph.length - 1);
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

  // Use Heap's algorithm to generate permutations.
  private void generatePermutations(int[] vertices, List<List<Integer>> permutations, int counter) {
    if (counter == 1) {
      List<Integer> l = new LinkedList<>();
      for (int v : vertices) l.add(v);
      permutations.add(l);
    }
    else {
      generatePermutations(vertices, permutations, counter - 1);
      for (int i = 0; i < counter - 1; i++) {
        if (counter % 2 == 0) swap(vertices, i, counter - 1);
        else swap(vertices, 0, counter - 1);
        generatePermutations(vertices, permutations, counter - 1);
      }
    }
  }

  // Sum a list of integers.
  private int sumPath(List<Integer> l) {
    // Use a list iterator to compute the path cost in linear time.
    Iterator<Integer> iterator = l.iterator();
    int sum = 0;
    Integer previousVertex = null;
    while (iterator.hasNext()) {
      Integer thisVertex = iterator.next();
      if (previousVertex == null) previousVertex = thisVertex;
      else {
        sum += graph[previousVertex][thisVertex];
        previousVertex = thisVertex;
      }
    }
    return sum;
  }

  // ------------------------------------------------------------------------------------------- //

  // The dynamic programming solution will find the length of the shortest Hamiltonian cycle
  // based on the following recurrence (where C(S, i) represents the minimum cost path through
  // each vertex in set S, beginning at vertex 0 and ending at vertex i):
  // C(S, i) = min [C(S - {i}), j) + edgeCost(j, i)] where (j is in S) && (j != 1) && (j != i)
  // Note that this algorithm produces only the minimum cost cycle, not the ordered list of
  // vertices in said cycle.
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
          int minCost = getMinCost(minCosts.get(previousSet), vertex);
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

  // Generate the power set of a given set.
  private List<Set<Integer>> generatePowerSet(int[] setElements) {
    List<Set<Integer>> powerSet = new LinkedList<>();
    populatePowerSet(0, setElements, new Stack<>(), powerSet);
    return powerSet;
  }

  // Recursively find all subsets of the given set.
  private void populatePowerSet(int idx, int[] setElements, Stack<Integer> subset,
                                List<Set<Integer>> powerSet) {
    if (idx == setElements.length) powerSet.add(new HashSet<>(subset));
    else {
      populatePowerSet(idx + 1, setElements, subset, powerSet);
      subset.push(setElements[idx]);
      populatePowerSet(idx + 1, setElements, subset, powerSet);
      subset.pop();
    }
  }

  // Return a new set containing all elements of the given set, with the exception of the element
  // to be excluded.
  private Set<Integer> copyAllExcept(Set<Integer> oldSet, int excluded) {
    Set<Integer> newSet = new HashSet<>();
    for (int elem : oldSet) if (elem != excluded) newSet.add(elem);
    return newSet;
  }

  // Given a map containing costs of paths ending at certain vertices, return the minimum cost of
  // those paths when extended to the next vertex.
  private int getMinCost(Map<Integer, Integer> vertexCosts, int nextVertex) {
    int minCost = Integer.MAX_VALUE;
    for (Map.Entry<Integer, Integer> vertexCost : vertexCosts.entrySet()) {
      int cost = vertexCost.getValue() + graph[vertexCost.getKey()][nextVertex];
      if (cost < minCost)minCost = cost;
    }
    return minCost;
  }

  private static void swap(int[] vertices, int idx1, int idx2) {
    if (idx1 != idx2) {
      int temp = vertices[idx1];
      vertices[idx1] = vertices[idx2];
      vertices[idx2] = temp;
    }
  }
}
