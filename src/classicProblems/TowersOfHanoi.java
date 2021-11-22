package classicProblems;

import java.util.Stack;

/**
 * An implementation of a recursive solution to the Towers of Hanoi problem, where each tower is
 * represented by a Stack of Integers, and each Integer represents the width of a disk.
 */
public class TowersOfHanoi {

  /**
   * Solves the Towers of Hanoi problem.
   * @param t1 the tower that initially contains all the disks
   * @param t2 an empty tower
   * @param t3 an empty tower that will contain all the disks after the algorithm finishes
   */
  public static void moveTower(Stack<Integer> t1, Stack<Integer> t2, Stack<Integer> t3) {
    if (t2.isEmpty() && t3.isEmpty()) moveDisks(t1, t2, t3, t1.size());
    else throw new IllegalStateException("Invalid start state");
  }

  /**
   * A helper method that performs recursion to solve the Towers of Hanoi problem.
   * @param source the tower with disks that should be moved to the destination
   * @param temp the tower to be used as temporary storage
   * @param dest the tower where disks from the source should be placed
   * @param n the number of disks on the source tower that should be moved
   */
  private static void moveDisks(Stack<Integer> source, Stack<Integer> temp, Stack<Integer> dest,
                                int n) {
    if (n == 1) dest.push(source.pop());
    else if (n > 1) {
      // Move the top n - 1 disks from the source tower to the temporary tower, using the
      // destination tower as temporary storage.
      moveDisks(source, dest, temp, n - 1);
      // Place the nth disk from the top on the destination tower.
      dest.push(source.pop());
      // Move the n - 1 disks on the temporary tower to the destination tower.
      moveDisks(temp, source, dest, n - 1);
    }
  }
}
