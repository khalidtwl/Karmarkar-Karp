import java.util.*;
import java.io.*;

public class kk {

  public kk(){}

  public static long KarmarkarKarp (MaxHeap H) {
    // MaxHeap.printHeap(H);

    // Gets two largest elements
    long largest = H.get(1);
    long large;
    int largeIndex;

    // Determines which child node is the second largest element
    if (H.get(2) < H.get(3))
    {
      large = H.get(3);
      largeIndex = 3;
    }
    else
    {
      large = H.get(2);
      largeIndex = 2;
    }

    // Base case: there is one element remaining
    if (large == 0) {
      return largest;
    }

    // Gets their difference
    long residue = Math.abs(largest - large);
    // System.out.println("Residue: " + residue);

    // Replace with residue and/or zero
    H.change(1, residue);
    H.change(largeIndex, 0);

    // Resorts the heap -- 2log(n) time
    MaxHeap.MaxHeapify(H, largeIndex);
    MaxHeap.MaxHeapify(H, 1);

    return KarmarkarKarp(H);
  }

  // Returns the indices of the two largest elements
  public static int[] twoMaxNums(long[] A) {
    // Indices of largest elements
    int largest = 0;
    int secondLargest = 1;

    // One loop through the array, so this runs in O(n)
    for (int i = 0, end = A.length; i < end; i++)
    {
      // We found a larger number
      if (A[i] > A[largest])
      {
        secondLargest = largest;
        largest = i;
      }
      else if (A[i] > A[secondLargest] && A[i] != A[largest])
      {
        secondLargest = i;
      }
    }
    int[] ans = {largest, secondLargest};
    return ans;
  }

  // Just pretty prints the array
  public static void printArray(long[] A) {
    String result = "";

    // Creates a string from the array
    for(int i = 0, end = A.length; i < end; i++)
    {
      result += A[i] + " ";
    }
    System.out.println(result);
  }

  public static void main(String[] args) {
    // Checks the flags
    if(args.length != 1) {
      System.out.println("Output should be of the form 'java kk <inputfile>'");
      return;
    }

    // Loads flags into memory
    String filename = args[0];

    // Initialization
    long[] A = new long[100];
    BufferedReader in = null;

    // Reading the inputfile
    try {
      long num;

      // Contains the file
      in = new BufferedReader(new FileReader(filename));

      // Populates our array
      for (int i = 0; i < A.length; i++) {
        num = Long.parseLong(in.readLine(), 10);
        A[i] = num;
      }

      in.close();
    }
    // Fileread errors
    catch (Exception e) {
      System.out.println("Exception occurred reading " + filename);
      e.printStackTrace();
      return;
    }
    MaxHeap H = new MaxHeap(A);
    System.out.println(KarmarkarKarp(H));
  }
}
