import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import searching.Traversals;

public class SearchingTests {

  @Test
  public void testTraversals() {
    Traversals t = new Traversals();
    assertArrayEquals(new Integer[]{4, 2, 8, 5, 9, 10, 1, 6, 3, 7}, t.listInorder().toArray());
    assertArrayEquals(new Integer[]{1, 2, 4, 5, 8, 9, 10, 3, 6, 7}, t.listPreorder().toArray());
    assertArrayEquals(new Integer[]{4, 8, 10, 9, 5, 2, 6, 7, 3, 1}, t.listPostorder().toArray());
    assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, t.listLevelOrder().toArray());
  }
}
