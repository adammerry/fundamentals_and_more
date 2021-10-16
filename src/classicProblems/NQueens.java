package classicProblems;

import java.util.LinkedList;
import java.util.List;

// Implementations of solutions to the generic N-Queens problem.
public class NQueens {

  // A typical recursive algorithm to solve the N-Queens problem. Each solution is represented by
  // a 2D NxN array of chars, where 'Q' represents the presence of a queen, and '-' represents
  // the absence of a queen. This algorithm returns a list of all possible solutions for a given N.
  public static List<char[][]> solve(int n) {
    if (n <= 0 || n == 2 || n == 3) return new LinkedList<>(); // No solutions possible.
    char[][] board = new char[n][n];
    List<char[][]> solutions = new LinkedList<>();
    for (int row = 0; row < n; row++) for (int col = 0; col < n; col++) board[row][col] = '-';
    solveHelper(n, 0, board, solutions);
    return solutions;
  }

  // Helper method to perform recursion and find solutions.
  private static void solveHelper(int n, int row, char[][] board, List<char[][]> solutions) {
    if (row == n) {
      char[][] boardCopy = new char[n][n];
      for (int r = 0; r < n; r++) System.arraycopy(board[r], 0, boardCopy[r], 0, n);
      solutions.add(boardCopy);
    }
    else {
      for (int col = 0; col < n; col++) {
        if (validPlacement(n, row, col, board)) {
          board[row][col] = 'Q';
          solveHelper(n, row + 1, board, solutions);
          board[row][col] = '-';
        }
      }
    }
  }

  // Determines whether a queen can be placed at the given row and column, based on the
  // locations of all existing queens.
  private static boolean validPlacement(int n, int row, int col, char[][] board) {
    // Check if any queens have already been placed in this column.
    for (int r = 0; r < row; r++) if (board[r][col] == 'Q') return false;
    // Check if any queens have already been placed in either diagonal.
    for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--)
      if (board[r][c] == 'Q') return false;
    for (int r = row - 1, c = col + 1; r >= 0 && c < n; r--, c++)
      if (board[r][c] == 'Q') return false;
    return true;
  }

  // A space-optimized algorithm to solve the N-Queens problem. This algorithm leverages the fact
  // that we do not need to store the entire NxN board in order to know the placements of the
  // queens for a given solution. Since each row can only have one queen, we can maintain a 1D int
  // array, where each index in the array corresponds to a row, and the value at that index
  // corresponds to the column in that row in which a queen is placed.
  public static List<int[]> solveSpaceEfficient(int n) {
    if (n <= 0 || n == 2 || n == 3) return new LinkedList<>(); // No solutions possible.
    int[] cols = new int[n];
    List<int[]> solutions = new LinkedList<>();
    solveSpaceEfficientHelper(n, 0, cols, solutions);
    return solutions;
  }

  // Helper method to perform recursion and find solutions.
  private static void solveSpaceEfficientHelper(int n, int row, int[] cols, List<int[]> solutions) {
    if (row == n) solutions.add(cols.clone());
    else {
      for (int col = 0; col < n; col++) {
        if (validPlacementSpaceEfficient(row, col, cols)) {
          cols[row] = col;
          solveSpaceEfficientHelper(n, row + 1, cols, solutions);
        }
      }
    }
  }

  // Determines whether a queen can be placed at the given row and column, based on the
  // locations of all existing queens.
  private static boolean validPlacementSpaceEfficient(int row, int col, int[] cols) {
    // Check if any queens have already been placed in this column, or in either diagonal. If the
    // distance between the two columns is the same as the distance between the two rows, then
    // the two points are in the same diagonal.
    for (int r = 0; r < row; r++)
      if (cols[r] == col || (row - r == Math.abs(cols[r] - col))) return false;
    return true;
  }
}