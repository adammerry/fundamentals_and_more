import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

import sorting.CountingSortNegative;
import sorting.CountingSortNonNegative;
import sorting.HeapSort;
import sorting.InsertionSort;
import sorting.MergeSort;
import sorting.QuickSort;
import sorting.RadixSort;

public class SortingTests {
  int[] l1;
  int[] l2;
  int[] l3;
  int[] l4;
  int[] l5;
  int[] l6;
  int[] l7;
  int[] l8;

  int[] s1;
  int[] s2;
  int[] s3;
  int[] s4;
  int[] s5;
  int[] s6;
  int[] s7;
  int[] s8;

  @Before
  public void setUpArrays() {
    l1 = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    l2 = new int[] {0, 0, 0, 0, 0};
    l3 = new int[] {9, 8, 7, 6};
    l4 = new int[] {5, 8, 234, 457, 2, -7, 9, 2, 1};
    l5 = new int[] {};
    l6 = new int[] {19};
    l7 = null;
    l8 = new int[] {-2, -5, -11, -1};

    s1 = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    s2 = new int[] {0, 0, 0, 0, 0};
    s3 = new int[] {6, 7, 8, 9};
    s4 = new int[] {-7, 1, 2, 2, 5, 8, 9, 234, 457};
    s5 = new int[] {};
    s6 = new int[] {19};
    s7 = null;
    s8 = new int[] {-11, -5, -2, -1};
  }

  @Test
  public void testQuickSort() {
    assertArrayEquals(s1, QuickSort.sort(l1));
    assertArrayEquals(s2, QuickSort.sort(l2));
    assertArrayEquals(s3, QuickSort.sort(l3));
    assertArrayEquals(s4, QuickSort.sort(l4));
    assertArrayEquals(s5, QuickSort.sort(l5));
    assertArrayEquals(s6, QuickSort.sort(l6));
    assertArrayEquals(s7, QuickSort.sort(l7));
    assertArrayEquals(s8, QuickSort.sort(l8));
  }

  @Test
  public void testMergeSort() {
    assertArrayEquals(s1, MergeSort.sort(l1));
    assertArrayEquals(s2, MergeSort.sort(l2));
    assertArrayEquals(s3, MergeSort.sort(l3));
    assertArrayEquals(s4, MergeSort.sort(l4));
    assertArrayEquals(s5, MergeSort.sort(l5));
    assertArrayEquals(s6, MergeSort.sort(l6));
    assertArrayEquals(s7, MergeSort.sort(l7));
    assertArrayEquals(s8, MergeSort.sort(l8));
  }

  @Test
  public void testInsertionSort() {
    assertArrayEquals(s1, InsertionSort.sort(l1));
    assertArrayEquals(s2, InsertionSort.sort(l2));
    assertArrayEquals(s3, InsertionSort.sort(l3));
    assertArrayEquals(s4, InsertionSort.sort(l4));
    assertArrayEquals(s5, InsertionSort.sort(l5));
    assertArrayEquals(s6, InsertionSort.sort(l6));
    assertArrayEquals(s7, InsertionSort.sort(l7));
    assertArrayEquals(s8, InsertionSort.sort(l8));
  }

  @Test
  public void testHeapSort() {
    assertArrayEquals(s1, HeapSort.sort(l1));
    assertArrayEquals(s2, HeapSort.sort(l2));
    assertArrayEquals(s3, HeapSort.sort(l3));
    assertArrayEquals(s4, HeapSort.sort(l4));
    assertArrayEquals(s5, HeapSort.sort(l5));
    assertArrayEquals(s6, HeapSort.sort(l6));
    assertArrayEquals(s7, HeapSort.sort(l7));
    assertArrayEquals(s8, HeapSort.sort(l8));
  }

  @Test
  public void testCountingSortNonNegative() {
    assertArrayEquals(s1, CountingSortNonNegative.sort(l1));
    assertArrayEquals(s2, CountingSortNonNegative.sort(l2));
    assertArrayEquals(s3, CountingSortNonNegative.sort(l3));
    assertArrayEquals(s5, CountingSortNonNegative.sort(l5));
    assertArrayEquals(s6, CountingSortNonNegative.sort(l6));
    assertArrayEquals(s7, CountingSortNonNegative.sort(l7));
  }

  @Test
  public void testCountingSortNegative() {
    assertArrayEquals(s1, CountingSortNegative.sort(l1));
    assertArrayEquals(s2, CountingSortNegative.sort(l2));
    assertArrayEquals(s3, CountingSortNegative.sort(l3));
    assertArrayEquals(s4, CountingSortNegative.sort(l4));
    assertArrayEquals(s5, CountingSortNegative.sort(l5));
    assertArrayEquals(s6, CountingSortNegative.sort(l6));
    assertArrayEquals(s7, CountingSortNegative.sort(l7));
    assertArrayEquals(s8, InsertionSort.sort(l8));
  }

  @Test
  public void testRadixSort() {
    assertArrayEquals(s1, RadixSort.sort(l1));
    assertArrayEquals(s2, RadixSort.sort(l2));
    assertArrayEquals(s3, RadixSort.sort(l3));
    assertArrayEquals(s5, RadixSort.sort(l5));
    assertArrayEquals(s6, RadixSort.sort(l6));
    assertArrayEquals(s7, RadixSort.sort(l7));
  }
}