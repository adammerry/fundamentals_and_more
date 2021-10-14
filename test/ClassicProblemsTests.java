import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import classicProblems.BellmanFord;
import dataStructures.GraphGeneric;
import classicProblems.Dijkstra;
import classicProblems.Knapsack;
import classicProblems.LevenshteinDistance;
import classicProblems.NQueens;
import classicProblems.Prim;
import classicProblems.TowersOfHanoi;
import classicProblems.TravelingSalesman;

public class ClassicProblemsTests {
  private int[][] g1 = new int[4][4];
  private int[][] g2 = new int[5][5];
  private List<Integer> g1Solution;
  private List<Integer> g2Solution;

  private int[] weights1;
  private int[] values1;
  private int[] weights2;
  private int[] values2;
  private int weightLim1;
  private int weightLim2;
  private int weightLim3;
  private int weightLim4;
  private int weightLim5;
  private int weightLim6;

  public ClassicProblemsTests() {
    g1[0][1] = 10;
    g1[1][0] = 10;
    g1[0][2] = 15;
    g1[2][0] = 15;
    g1[0][3] = 20;
    g1[3][0] = 20;
    g1[1][2] = 35;
    g1[2][1] = 35;
    g1[1][3] = 25;
    g1[3][1] = 25;
    g1[2][3] = 30;
    g1[3][2] = 30;
    g1Solution = new LinkedList<>();
    g1Solution.add(0);
    g1Solution.add(1);
    g1Solution.add(3);
    g1Solution.add(2);
    g1Solution.add(0);
    g2[0][1] = 8;
    g2[1][0] = 8;
    g2[0][2] = 11;
    g2[2][0] = 11;
    g2[0][3] = 7;
    g2[3][0] = 7;
    g2[0][4] = 5;
    g2[4][0] = 5;
    g2[1][2] = 9;
    g2[2][1] = 9;
    g2[1][3] = 6;
    g2[3][1] = 6;
    g2[1][4] = 1;
    g2[4][1] = 1;
    g2[2][3] = 12;
    g2[3][2] = 12;
    g2[2][4] = 14;
    g2[4][2] = 14;
    g2[3][4] = 3;
    g2[4][3] = 3;
    g2Solution = new LinkedList<>();
    g2Solution.add(0);
    g2Solution.add(3);
    g2Solution.add(4);
    g2Solution.add(1);
    g2Solution.add(2);
    g2Solution.add(0);

    weights1 = new int[]{1, 2, 3, 4};
    values1 = new int[]{10, 20, 30, 40};
    weights2 = new int[]{5, 2, 7, 11, 3, 15, 1};
    values2 = new int[]{10, 9, 11, 22, 8, 23, 4};
    weightLim1 = 0;
    weightLim2 = 100;
    weightLim3 = 6;
    weightLim4 = 10;
    weightLim5 = 20;
    weightLim6 = 30;
  }

  @Test
  public void testTravelingSalesmanBruteForceSolution() {
    TravelingSalesman ts1 = new TravelingSalesman(g1);
    List<Integer> bruteForceSolution1 = ts1.bruteForceSolution();
    assertTrue(checkSolutionBothDirections(g1Solution, bruteForceSolution1));
    TravelingSalesman ts2 = new TravelingSalesman(g2);
    List<Integer> bruteForceSolution2 = ts2.bruteForceSolution();
    assertTrue(checkSolutionBothDirections(g2Solution, bruteForceSolution2));
  }

  @Test
  public void testTraveingSalesmanDynamicProgrammingSolution() {
    TravelingSalesman ts1 = new TravelingSalesman(g1);
    Integer dpSolution1 = ts1.dynamicProgrammingSolution();
    assertEquals(new Integer(80), dpSolution1);
    TravelingSalesman ts2 = new TravelingSalesman(g2);
    Integer dpSolution2 = ts2.dynamicProgrammingSolution();
    assertEquals(new Integer(31), dpSolution2);
  }

  @Test
  public void testTravelingSalesmanBadInput() {
    assertThrows(IllegalArgumentException.class, () -> new TravelingSalesman(new int[3][3]));
    assertThrows(IllegalArgumentException.class, () -> new TravelingSalesman(new int[4][5]));
  }

  // Since a solution to TSP is a cycle of vertices, it is valid both forward and
  // backward on an undirected graph. Therefore, we must check both cases to ensure that one of
  // them is the path returned.
  private boolean checkSolutionBothDirections(List<Integer> solution1, List<Integer> solution2) {
    boolean solutionCorrect = true;
    for (int i = 0; i < solution1.size(); i++)
      if (!solution1.get(i).equals(solution2.get(i))) solutionCorrect = false;
    if (!solutionCorrect) {
      solutionCorrect = true;
      for (int i = 0; i < solution1.size(); i++) {
        if (!solution1.get(solution1.size() - 1 - i).equals(solution2.get(i))) {
          solutionCorrect = false;
        }
      }
    }
    return solutionCorrect;
  }

  @Test
  public void testKnapsackBruteForceSolution() {
    assertEquals(0, Knapsack.bruteForceSolution(weightLim1, weights1, values1));
    assertEquals(100, Knapsack.bruteForceSolution(weightLim2, weights1, values1));
    assertEquals(60, Knapsack.bruteForceSolution(weightLim3, weights1, values1));
    assertEquals(27, Knapsack.bruteForceSolution(weightLim4, weights2, values2));
    assertEquals(45, Knapsack.bruteForceSolution(weightLim5, weights2, values2));
    assertEquals(64, Knapsack.bruteForceSolution(weightLim6, weights2, values2));
  }

  @Test
  public void testKnapsackDynamicProgrammingSolution() {
    assertEquals(0, Knapsack.dynamicProgrammingSolution(weightLim1, weights1, values1));
    assertEquals(100, Knapsack.dynamicProgrammingSolution(weightLim2, weights1, values1));
    assertEquals(60, Knapsack.dynamicProgrammingSolution(weightLim3, weights1, values1));
    assertEquals(27, Knapsack.dynamicProgrammingSolution(weightLim4, weights2, values2));
    assertEquals(45, Knapsack.dynamicProgrammingSolution(weightLim5, weights2, values2));
    assertEquals(64, Knapsack.dynamicProgrammingSolution(weightLim6, weights2, values2));
  }

  @Test
  public void testLevenshteinDistanceRecursive() {
    assertThrows(IllegalArgumentException.class,
            () -> LevenshteinDistance.findDistanceRecursive(null, null));
    assertEquals(0, LevenshteinDistance.findDistanceRecursive("", ""));
    assertEquals(5, LevenshteinDistance.findDistanceRecursive("", "hello"));
    assertEquals(5, LevenshteinDistance.findDistanceRecursive("hello", ""));
    assertEquals(1, LevenshteinDistance.findDistanceRecursive("the", "tha"));
    assertEquals(3, LevenshteinDistance.findDistanceRecursive("comp", "compute"));
    assertEquals(4, LevenshteinDistance.findDistanceRecursive("distance", "dist"));
    assertEquals(3, LevenshteinDistance.findDistanceRecursive("sunday", "saturday"));
    assertEquals(6, LevenshteinDistance.findDistanceRecursive("yellow", "green"));
    assertEquals(3, LevenshteinDistance.findDistanceRecursive("shirt", "this"));
  }

  @Test
  public void testLevenshteinDistanceIterative() {
    assertThrows(IllegalArgumentException.class,
            () -> LevenshteinDistance.findDistanceIterative(null, null));
    assertEquals(0, LevenshteinDistance.findDistanceIterative("", ""));
    assertEquals(5, LevenshteinDistance.findDistanceIterative("", "hello"));
    assertEquals(5, LevenshteinDistance.findDistanceIterative("hello", ""));
    assertEquals(1, LevenshteinDistance.findDistanceIterative("the", "tha"));
    assertEquals(3, LevenshteinDistance.findDistanceIterative("comp", "compute"));
    assertEquals(4, LevenshteinDistance.findDistanceIterative("distance", "dist"));
    assertEquals(3, LevenshteinDistance.findDistanceIterative("sunday", "saturday"));
    assertEquals(6, LevenshteinDistance.findDistanceIterative("yellow", "green"));
    assertEquals(3, LevenshteinDistance.findDistanceIterative("shirt", "this"));
  }

  @Test
  public void testLevenshteinDistanceSpaceOptimized() {
    assertThrows(IllegalArgumentException.class,
            () -> LevenshteinDistance.findDistanceSpaceOptimized(null, null));
    assertEquals(0, LevenshteinDistance.findDistanceSpaceOptimized("", ""));
    assertEquals(5, LevenshteinDistance.findDistanceSpaceOptimized("", "hello"));
    assertEquals(5, LevenshteinDistance.findDistanceSpaceOptimized("hello", ""));
    assertEquals(1, LevenshteinDistance.findDistanceSpaceOptimized("the", "tha"));
    assertEquals(3, LevenshteinDistance.findDistanceSpaceOptimized("comp", "compute"));
    assertEquals(4, LevenshteinDistance.findDistanceSpaceOptimized("distance", "dist"));
    assertEquals(3, LevenshteinDistance.findDistanceSpaceOptimized("sunday", "saturday"));
    assertEquals(6, LevenshteinDistance.findDistanceSpaceOptimized("yellow", "green"));
    assertEquals(3, LevenshteinDistance.findDistanceSpaceOptimized("shirt", "this"));
  }

  @Test
  public void testTowersOfHanoi() {
    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();
    Stack<Integer> s3 = new Stack<>();
    TowersOfHanoi.moveTower(s1, s2, s3);
    assertTrue(s1.isEmpty());
    assertTrue(s2.isEmpty());
    assertTrue(s3.isEmpty());
    s1.push(1);
    TowersOfHanoi.moveTower(s1, s2, s3);
    assertEquals(1, (int) s3.pop());
    assertTrue(s1.isEmpty() && s2.isEmpty());
    s1.push(2);
    s1.push(1);
    TowersOfHanoi.moveTower(s1, s2, s3);
    assertEquals(1, (int) s3.pop());
    assertEquals(2, (int) s3.pop());
    assertTrue(s1.isEmpty() && s2.isEmpty());
    s1.push(3);
    s1.push(2);
    s1.push(1);
    TowersOfHanoi.moveTower(s1, s2, s3);
    assertEquals(1, (int) s3.pop());
    assertEquals(2, (int) s3.pop());
    assertEquals(3, (int) s3.pop());
    assertTrue(s1.isEmpty() && s2.isEmpty());
    for (int i = 14; i > 0; i--) s1.push(i);
    TowersOfHanoi.moveTower(s1, s2, s3);
    for (int i = 1; i < 15; i++) assertEquals(i, (int) s3.pop());
    assertTrue(s1.isEmpty() && s2.isEmpty());
    s2.push(6);
    assertThrows(IllegalStateException.class, () -> TowersOfHanoi.moveTower(s1, s2, s3));
  }

  @Test
  public void testNQueens() {
    List<char[][]> n0Solutions = NQueens.solve(0);
    List<char[][]> n1Solutions = NQueens.solve(1);
    List<char[][]> n2Solutions = NQueens.solve(2);
    List<char[][]> n3Solutions = NQueens.solve(3);
    List<char[][]> n4Solutions = NQueens.solve(4);
    List<char[][]> n5Solutions = NQueens.solve(5);
    assertEquals(new LinkedList<>(), n0Solutions);
    assertEquals('Q', n1Solutions.get(0)[0][0]);
    assertEquals(new LinkedList<>(), n2Solutions);
    assertEquals(new LinkedList<>(), n3Solutions);
    assertEquals(2, n4Solutions.size());
    assertEquals(10, n5Solutions.size());
    List<int[]> n0SolutionsSpaceEfficient = NQueens.solveSpaceEfficient(0);
    List<int[]> n1SolutionsSpaceEfficient = NQueens.solveSpaceEfficient(1);
    List<int[]> n2SolutionsSpaceEfficient = NQueens.solveSpaceEfficient(2);
    List<int[]> n3SolutionsSpaceEfficient = NQueens.solveSpaceEfficient(3);
    List<int[]> n4SolutionsSpaceEfficient = NQueens.solveSpaceEfficient(4);
    List<int[]> n5SolutionsSpaceEfficient = NQueens.solveSpaceEfficient(5);
    assertEquals(new LinkedList<>(), n0SolutionsSpaceEfficient);
    assertEquals(0, n1SolutionsSpaceEfficient.get(0)[0]);
    assertEquals(new LinkedList<>(), n2SolutionsSpaceEfficient);
    assertEquals(new LinkedList<>(), n3SolutionsSpaceEfficient);
    assertEquals(2, n4SolutionsSpaceEfficient.size());
    assertEquals(10, n5SolutionsSpaceEfficient.size());
  }

  @Test
  public void testDijkstra() {
    // Test graph with one node.
    GraphGeneric<Integer> g = new GraphGeneric<>(false);
    GraphGeneric<Integer>.Node n1 = g.addNode(1);
    Map<GraphGeneric<Integer>.Node, Integer> dijkstraMap = Dijkstra.runDijkstra(g, n1);
    assertEquals(1,  dijkstraMap.size());
    assertEquals(new Integer(0),  dijkstraMap.get(n1));
    // Test undirected graph.
    GraphGeneric<Integer>.Node n2 = g.addNode(2);
    GraphGeneric<Integer>.Node n3 = g.addNode(3);
    GraphGeneric<Integer>.Node n4 = g.addNode(4);
    GraphGeneric<Integer>.Node n5 = g.addNode(5);
    GraphGeneric<Integer>.Node n6 = g.addNode(6);
    g.addEdge(n1, n2, 10);
    g.addEdge(n1, n6, 3);
    g.addEdge(n2, n3, 5);
    g.addEdge(n2, n4, 1);
    g.addEdge(n2, n5, 4);
    g.addEdge(n3, n4, 2);
    g.addEdge(n4, n6, 7);
    g.addEdge(n5, n6, 2);
    dijkstraMap = Dijkstra.runDijkstra(g, n1);
    assertEquals(6, dijkstraMap.size());
    assertEquals(new Integer(0), dijkstraMap.get(n1));
    assertEquals(new Integer(9), dijkstraMap.get(n2));
    assertEquals(new Integer(12), dijkstraMap.get(n3));
    assertEquals(new Integer(10), dijkstraMap.get(n4));
    assertEquals(new Integer(5), dijkstraMap.get(n5));
    assertEquals(new Integer(3), dijkstraMap.get(n6));
    // Test directed graph.
    g = new GraphGeneric<>(true);
    g.addNode(1);
    g.addNode(2);
    g.addNode(3);
    g.addNode(4);
    g.addNode(5);
    g.addNode(6);
    g.addEdge(n1, n2, 2);
    g.addEdge(n1, n3, 16);
    g.addEdge(n2, n4, 1);
    g.addEdge(n2, n6, 4);
    g.addEdge(n3, n1, 5);
    g.addEdge(n3, n2, 10);
    g.addEdge(n3, n5, 6);
    g.addEdge(n4, n3, 4);
    g.addEdge(n4, n5, 12);
    g.addEdge(n5, n2, 3);
    g.addEdge(n5, n4, 9);
    g.addEdge(n5, n6, 13);
    g.addEdge(n6, n3, 15);
    g.addEdge(n6, n4, 17);
    dijkstraMap = Dijkstra.runDijkstra(g, n1);
    assertEquals(6, dijkstraMap.size());
    assertEquals(new Integer(0), dijkstraMap.get(n1));
    assertEquals(new Integer(2), dijkstraMap.get(n2));
    assertEquals(new Integer(7), dijkstraMap.get(n3));
    assertEquals(new Integer(3), dijkstraMap.get(n4));
    assertEquals(new Integer(13), dijkstraMap.get(n5));
    assertEquals(new Integer(6), dijkstraMap.get(n6));
  }

  @Test
  public void testPrim() {
    // Test graph with one node.
    GraphGeneric<Integer> g = new GraphGeneric<>(false);
    GraphGeneric<Integer>.Node n1 = g.addNode(1);
    Set<Prim.Edge<Integer>> edges = Prim.runPrim(g);
    assertTrue(edges.isEmpty());
    GraphGeneric<Integer>.Node n2 = g.addNode(2);
    GraphGeneric<Integer>.Node n3 = g.addNode(3);
    GraphGeneric<Integer>.Node n4 = g.addNode(4);
    GraphGeneric<Integer>.Node n5 = g.addNode(5);
    GraphGeneric<Integer>.Node n6 = g.addNode(6);
    g.addEdge(n1, n2, 10);
    g.addEdge(n1, n6, 3);
    g.addEdge(n2, n3, 5);
    g.addEdge(n2, n4, 1);
    g.addEdge(n2, n5, 4);
    g.addEdge(n2, n6, 8);
    g.addEdge(n3, n4, 2);
    g.addEdge(n3, n5, 7);
    g.addEdge(n4, n6, 7);
    g.addEdge(n5, n6, 2);
    edges = Prim.runPrim(g);
    assertEquals(5, edges.size());
    int sum = 0;
    for (Prim.Edge<Integer> e : edges) sum += e.getWeight();
    assertEquals(12, sum);
  }

  @Test
  public void testBellmanFord() {
    // Test graph with one node.
    GraphGeneric<Integer> g = new GraphGeneric<>(false);
    GraphGeneric<Integer>.Node n1 = g.addNode(1);
    Map<GraphGeneric<Integer>.Node, Integer> bellmanFordMap = BellmanFord.runBellmanFord(g, n1);
    assertEquals(1,  bellmanFordMap.size());
    assertEquals(new Integer(0),  bellmanFordMap.get(n1));
    // Test undirected graph.
    GraphGeneric<Integer>.Node n2 = g.addNode(2);
    GraphGeneric<Integer>.Node n3 = g.addNode(3);
    GraphGeneric<Integer>.Node n4 = g.addNode(4);
    GraphGeneric<Integer>.Node n5 = g.addNode(5);
    GraphGeneric<Integer>.Node n6 = g.addNode(6);
    g.addEdge(n1, n2, 10);
    g.addEdge(n1, n6, 3);
    g.addEdge(n2, n3, 5);
    g.addEdge(n2, n4, 1);
    g.addEdge(n2, n5, 4);
    g.addEdge(n3, n4, 2);
    g.addEdge(n4, n6, 7);
    g.addEdge(n5, n6, 2);
    bellmanFordMap = BellmanFord.runBellmanFord(g, n1);
    assertEquals(6, bellmanFordMap.size());
    assertEquals(new Integer(0), bellmanFordMap.get(n1));
    assertEquals(new Integer(9), bellmanFordMap.get(n2));
    assertEquals(new Integer(12), bellmanFordMap.get(n3));
    assertEquals(new Integer(10), bellmanFordMap.get(n4));
    assertEquals(new Integer(5), bellmanFordMap.get(n5));
    assertEquals(new Integer(3), bellmanFordMap.get(n6));
    // Test directed graph.
    g = new GraphGeneric<>(true);
    g.addNode(1);
    g.addNode(2);
    g.addNode(3);
    g.addNode(4);
    g.addNode(5);
    g.addNode(6);
    g.addEdge(n1, n2, 2);
    g.addEdge(n1, n3, 16);
    g.addEdge(n2, n4, 1);
    g.addEdge(n2, n6, 4);
    g.addEdge(n3, n1, 5);
    g.addEdge(n3, n2, 10);
    g.addEdge(n3, n5, 6);
    g.addEdge(n4, n3, 4);
    g.addEdge(n4, n5, 12);
    g.addEdge(n5, n2, 3);
    g.addEdge(n5, n4, 9);
    g.addEdge(n5, n6, 13);
    g.addEdge(n6, n3, 15);
    g.addEdge(n6, n4, 17);
    bellmanFordMap = BellmanFord.runBellmanFord(g, n1);
    assertEquals(6, bellmanFordMap.size());
    assertEquals(new Integer(0), bellmanFordMap.get(n1));
    assertEquals(new Integer(2), bellmanFordMap.get(n2));
    assertEquals(new Integer(7), bellmanFordMap.get(n3));
    assertEquals(new Integer(3), bellmanFordMap.get(n4));
    assertEquals(new Integer(13), bellmanFordMap.get(n5));
    assertEquals(new Integer(6), bellmanFordMap.get(n6));
    // Test graph with negative edge weights.
    g = new GraphGeneric<>(true);
    g.addNode(1);
    g.addNode(2);
    g.addNode(3);
    g.addNode(4);
    g.addNode(5);
    g.addEdge(n1, n2, -1);
    g.addEdge(n1, n3, 4);
    g.addEdge(n2, n3, 3);
    g.addEdge(n2, n4, 2);
    g.addEdge(n2, n5, 2);
    g.addEdge(n4, n2, 1);
    g.addEdge(n5, n4, -3);
    bellmanFordMap = BellmanFord.runBellmanFord(g, n1);
    assertEquals(new Integer(0), bellmanFordMap.get(n1));
    assertEquals(new Integer(-1), bellmanFordMap.get(n2));
    assertEquals(new Integer(2), bellmanFordMap.get(n3));
    assertEquals(new Integer(-2), bellmanFordMap.get(n4));
    assertEquals(new Integer(1), bellmanFordMap.get(n5));
    // Test graph with negative cycle.
    GraphGeneric<Integer> badGraph = new GraphGeneric<>(false);
    badGraph.addNode(1);
    badGraph.addNode(2);
    badGraph.addNode(3);
    badGraph.addNode(4);
    badGraph.addNode(5);
    badGraph.addEdge(n1, n2, -1);
    badGraph.addEdge(n1, n3, 4);
    badGraph.addEdge(n2, n3, 3);
    badGraph.addEdge(n2, n4, 2);
    badGraph.addEdge(n2, n5, 2);
    badGraph.addEdge(n4, n2, 1);
    badGraph.addEdge(n5, n4, -3);
    badGraph.addEdge(n4, n1, 1);
    assertThrows(IllegalArgumentException.class, () -> BellmanFord.runBellmanFord(badGraph, n1));
  }
}