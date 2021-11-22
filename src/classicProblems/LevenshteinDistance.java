package classicProblems;

/**
 * The Levenshtein distance (or edit distance) of two strings is the minimum number of edits needed
 * to transform one string into the other. An edit can be either an insertion, a deletion, or a
 * substitution of a single character. This class implements recursive and iterative methods for
 * determining the Levenshtein distance between two strings using dynamic programming.
 */
public class LevenshteinDistance {

  /**
   * Determines the Levenshtein distance between two strings using recursion.
   * @param s1 the first string
   * @param s2 the second string
   * @return the Levenshtein distance
   */
  public static int findDistanceRecursive(String s1, String s2) {
    if (s1 == null || s2 == null) throw new IllegalArgumentException("Null argument found");
    // Initialize DP Array.
    int[][] dists = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 0; i <= s1.length(); i++)
      for (int j = 0; j <= s2.length(); j++) dists[i][j] = -1;
    return recursiveHelper(s1.toCharArray(), s2.toCharArray(), 0, 0, dists);
  }

  /**
   * A helper method for findDistanceRecursive that performs the recursion.
   * @param s1 the first string
   * @param s2 the second string
   * @param idx1 the index in s1 corresponding to the current character in s1
   * @param idx2 the index in s2 corresponding to the current character in s2
   * @param dists a 2D Array of intermediate distances used for dynamic programming
   * @return the Levenshtein distance between s1 and s2
   */
  private static int recursiveHelper(char[] s1, char[] s2, int idx1, int idx2, int[][] dists) {
    if (dists[idx1][idx2] >= 0) return dists[idx1][idx2];
    // If there are no characters left in s1, then all remaining characters from s2 must be added.
    if (idx1 == s1.length) dists[idx1][idx2] = s2.length - idx2;
    // If there are no characters left in s2, then all remaining characters from s1 must be added.
    else if (idx2 == s2.length) dists[idx1][idx2] = s1.length - idx1;
    // If the characters of each string at the given indices are equal, no edits are needed.
    else if (s1[idx1] == s2[idx2]) dists[idx1][idx2] =
            recursiveHelper(s1, s2, idx1 + 1, idx2 + 1, dists);
    else {
      // Find the remaining distance as if the correct character was inserted in s1 at idx1.
      int insert = recursiveHelper(s1, s2, idx1, idx2 + 1, dists);
      // Find the remaining distance as if the character at idx1 was removed from s1.
      int remove = recursiveHelper(s1, s2, idx1 + 1, idx2, dists);
      // Find the remaining distance as if s1[idx1] was swapped for the correct character.
      int swap = recursiveHelper(s1, s2, idx1 + 1, idx2 + 1, dists);
      dists[idx1][idx2] = 1 + Integer.min(Integer.min(insert, remove), swap);
    }
    return dists[idx1][idx2];
  }

  /**
   * Determines the Levenshtein distance between two strings using iteration.
   * @param s1 the first string
   * @param s2 the second string
   * @return the Levenshtein distance
   */
  public static int findDistanceIterative(String s1, String s2) {
    if (s1 == null || s2 == null) throw new IllegalArgumentException("Null argument found");
    char[] c1 = s1.toCharArray(), c2 = s2.toCharArray();
    // Initialize DP Array.
    int[][] dists = new int[c1.length + 1][c2.length + 1];
    for (int i = 0; i <= c1.length; i++) dists[i][0] = i;
    for (int j = 0; j <= c2.length; j++) dists[0][j] = j;
    for (int i = 1; i <= c1.length; i++) {
      for (int j = 1; j <= c2.length; j++) {
        if (c1[i - 1] == c2[j - 1]) dists[i][j] = dists[i - 1][j - 1];
        else dists[i][j] =
                1 + Math.min(Math.min(dists[i - 1][j], dists[i][j - 1]), dists[i - 1][j - 1]);
      }
    }
    return dists[c1.length][c2.length];
  }

  /**
   * Determines the Levenshtein distance between two strings using iteration while optimizing
   * space usage. Utilizes the fact that only two rows of the dynamic programming Array ever need
   * to be simultaneously held in memory.
   * @param s1 the first string
   * @param s2 the second string
   * @return the Levenshtein distance
   */
  public static int findDistanceSpaceOptimized(String s1, String s2) {
    if (s1 == null || s2 == null) throw new IllegalArgumentException("Null argument found");
    char[] c1 = s1.toCharArray(), c2 = s2.toCharArray();
    // Initialize DP Array.
    int[] currArray = new int[c2.length + 1], prevArray = new int[c2.length + 1];
    for (int j = 0; j <= c2.length; j++) currArray[j] = j;
    for (int i = 1; i <= c1.length; i++) {
      // Swap which Array is currently being used to store values.
      int[] temp = currArray;
      currArray = prevArray;
      prevArray = temp;
      for (int j = 0; j <= c2.length; j++) {
        if (j == 0) currArray[j] = i;
        else if (c1[i - 1] == c2[j - 1]) currArray[j] = prevArray[j - 1];
        else currArray[j] =
                1 + Math.min(Math.min(prevArray[j], currArray[j - 1]), prevArray[j - 1]);
      }
    }
    return currArray[c2.length];
  }
}