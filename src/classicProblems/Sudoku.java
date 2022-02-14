package classicProblems;

/**
 * An implementation of an algorithm to solve a Sudoku puzzle. The puzzle is represented by a 9x9
 * 2D char array, where each cell is either a single-digit number or a period (which represents a
 * blank cell to be filled in).
 */
public class Sudoku {
    /**
     * Validates the input board and populates the blank spaces with numbers that satisfy the
     * constraints of Sudoku.
     * @param board the Sudoku board
     */
    public static void solveSudoku(char[][] board) {
        if (board.length != 9 || board[0].length != 9) throw new IllegalArgumentException("Invalid Sudoku");
        solve(board, 0, 0);
    }

    /**
     * Finds the number that can be placed at the given row and column in order to solve the Sudoku
     * puzzle.
     * @param board the Sudoku board
     * @param row the row
     * @param col the column
     * @return true if it is possible to find a solution from the given board, false otherwise
     */
    private static boolean solve(char[][] board, int row, int col) {
        if (row == board.length) return true;
        if (col == board[row].length) return solve(board, row + 1, 0);
        if (board[row][col] == '.') {
            for (char c = '1'; c <= '9'; c++) {
                if (isValid(board, row, col, c)) {
                    board[row][col] = c;
                    if (solve(board, row, col + 1)) return true;
                }
            }
            board[row][col] = '.';
            return false;
        }
        return solve(board, row, col + 1);
    }

    /**
     * Determines if it is possible to place the given digit at the given row and column in the
     * board.
     * @param board the Sudoku board
     * @param row the row in which to place the given digit
     * @param col the column in which to place the given digit
     * @param digit the digit to place
     * @return true if the digit can be placed without violating any constraints, false otherwise
     */
    private static boolean isValid(char[][] board, int row, int col, char digit) {
        // Check the column.
        for (char[] r : board) if (r[col] == digit) return false;
        // Check the row.
        for (int c = 0; c < board[row].length; c++) if (board[row][c] == digit) return false;
        // Check the 3x3 square.
        for (int localRow = 0; localRow < 3; localRow++) {
            for (int localCol = 0; localCol < 3; localCol++) {
                int absoluteRow = ((row / 3) * 3) + localRow, absoluteCol = ((col / 3) * 3) + localCol;
                if (board[absoluteRow][absoluteCol] == digit) return false;
            }
        }
        return true;
    }
}