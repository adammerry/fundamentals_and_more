import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import classicProblems.Knapsack;
import classicProblems.LevenshteinDistance;
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
    assertEquals(-1, LevenshteinDistance.findDistanceRecursive(null, null));
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
    assertEquals(-1, LevenshteinDistance.findDistanceIterative(null, null));
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
    assertEquals(-1, LevenshteinDistance.findDistanceSpaceOptimized(null, null));
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
    s3.push(5);
    TowersOfHanoi.moveTower(s1, s2, s3);
  }
}
