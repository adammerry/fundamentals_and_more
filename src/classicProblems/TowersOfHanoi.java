package classicProblems;

import java.util.Stack;

// Implementation of a recursive solution to the Towers of Hanoi problem, where each tower is
// represented by a Stack of Integers, and each Integer represents the width of a disk.
public class TowersOfHanoi {

  public static void moveTower(Stack<Integer> t1, Stack<Integer> t2, Stack<Integer> t3) {
    if (t2.isEmpty() && t3.isEmpty()) moveDisks(t1, t2, t3, t1.size());
    else System.out.println("Invalid start state. All disks must begin on tower 1.");
  }

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
