package classicProblems;

/**
 * An implementation of an exponential-time brute-force solution, and a pseudo-polynomial-time
 * dynamic programming solution to the Knapsack problem. In both solutions below, weights[] and
 * values[] are assumed to be equal in length, since the problem specifications would otherwise be
 * invalid.
 */
public class Knapsack {

  /**
   * A brute-force solution to the Knapsack problem.
   * @param weightLimit the weight limit of the knapsack
   * @param weights the weights of the items
   * @param values the values of the items
   * @return the maximum value of items that can fit in the knapsack without exceeding the weight
   * limit
   */
  public static int bruteForceSolution(int weightLimit, int[] weights, int[] values) {
    return bruteForceSolutionHelper(weightLimit, weights, values, 0);
  }

  /**
   * A helper method for bruteForceSolution. Performs the bulk of the work to solve the problem.
   * @param weightLimit the weight limit of the knapsack
   * @param weights the weights of the items
   * @param values the values of the items
   * @param item an index in the given Arrays that corresponds to an item
   * @return the maximum value of items that can fit in the knapsack without exceeding the weight
   * limit
   */
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

  /**
   * A dynamic programming solution to the knapsack problem.
   * @param weightLimit the weight limit of the knapsack
   * @param weights the weights of the items
   * @param values the values of the items
   * @return the maximum value of items that can fit in the knapsack without exceeding the weight
   * limit
   */
  public static int dynamicProgrammingSolution(int weightLimit, int[] weights, int[] values) {
    // Create an Array containing the optimal values for all weight limits and for all items, up to
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
