package visualalgo;

import java.util.Arrays;

// https://www.geeksforgeeks.org/suffix-array-set-2-a-nlognlogn-algorithm/
public class SuffixArrayWithRadixSort {

  // Class to store information of a suffix
  public static class Suffix implements Comparable<Suffix> {
    int index;
    int rank;
    int next;

    public Suffix(int ind, int r, int nr) {
      index = ind;
      rank = r;
      next = nr;
    }

    // A comparison function used by sort()
    // to compare two suffixes.
    // Compares two pairs, returns 1
    // if first pair is smaller
    public int compareTo(Suffix s) {
      if (rank != s.rank) return Integer.compare(rank, s.rank);
      return Integer.compare(next, s.next);
    }
  }

  public static int[] suffixArray(String s) {
    int n = s.length();
    Suffix[] su = new Suffix[n];

    for (int i = 0; i < n; i++) {
      su[i] = new Suffix(i, s.charAt(i) - '$', 0);
    }
    for (int i = 0; i < n; i++) {
      su[i].next = (i + 1 < n ? su[i + 1].rank : -1);
    }

    Arrays.sort(su);

    // currently all suffixes are sorted according to first 2 characters. Let us sort suffixes
    // according to first 4 characters, then first 8 and so on.
    int[] tmp = new int[n];

    for (int length = 4; length < 2 * n; length <<= 1) {
      int rank = 0, prev = su[0].rank;
      su[0].rank = rank;
      tmp[su[0].index] = 0;
      for (int i = 1; i < n; i++) {
        if (su[i].rank == prev && su[i].next == su[i - 1].next) {
          prev = su[i].rank;
          su[i].rank = rank;
        } else {
          prev = su[i].rank;
          su[i].rank = ++rank;
        }
        tmp[su[i].index] = i;
      }
      // assign next rank to every suffix
      for (int i = 0; i < n; i++) {
        int nextP = su[i].index + length / 2;
        su[i].next = nextP < n ? su[tmp[nextP]].rank : -1;
      }
      Arrays.sort(su);
    }
    int[] suf = new int[n];
    for (int i = 0; i < n; i++) {
      suf[i] = su[i].index;
    }
    return suf;
  }

  static void printArr(int arr[], int n) {
    for (int i = 0; i < n; i++) System.out.print(arr[i] + " ");
    System.out.println();
  }

  /**
   * Unit tests the {@code SuffixArray} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    String s = "banana";
    int n = s.length();
    int[] suff_arr = suffixArray(s);
    System.out.println("Following is suffix array for banana:");
    printArr(suff_arr, n);
  }
}
