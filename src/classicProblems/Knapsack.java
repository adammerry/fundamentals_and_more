package classicProblems;

// Implementation of an exponential-time brute-force solution, and pseudo-polynomial-time dynamic
// programming solution to the Knapsack problem. In both solutions below, weights[] and values[]
// are assumed to be equal in length, since otherwise the problem specifications would be invalid.
public class Knapsack {

  // ------------------------------------------------------------------------------------------- //

  public static int bruteForceSolution(int weightLimit, int[] weights, int[] values) {
    return bruteForceSolutionHelper(weightLimit, weights, values, 0);
  }

  private static int bruteForceSolutionHelper(int weightLimit, int[] weights, int[] values,
                                              int item) {
    if (weightLimit <= 0 || item >= weights.length) return 0;
    if (weights[item] > weightLimit)
      return bruteForceSolutionHelper(weightLimit, weights, values, item + 1);
    // Return the maximum of including this item or not.
    return Math.max(bruteForceSolutionHelper(weightLimit - weights[item], weights, values,
            item + 1) + values[item],
            bruteForceSolutionHelper(weightLimit, weights, values, item + 1));
  }

  // ------------------------------------------------------------------------------------------- //

  public static int dynamicProgrammingSolution(int weightLimit, int[] weights, int[] values) {
    // Create an array containing the optimal values for all weight limits and for all items, up to
    // the given weight limit and final item. Include a row for a weight limit of 0, and a column
    // for 0 items.
    int[][] optValWeights = new int[weightLimit + 1][values.length + 1];
    for (int i = 1; i <= weightLimit; i++) {
      for (int j = 0; j < values.length; j++) {
        if (weights[j] > i) optValWeights[i][j + 1] = optValWeights[i][j];
        else optValWeights[i][j + 1] = Math.max(optValWeights[i][j],
                                                optValWeights[i - weights[j]][j] + values[j]);
      }
    }
    return optValWeights[weightLimit][values.length];
  }
}
