import java.util.Arrays;

public class kk {

  public kk(){}

  public static long KarmarkarKarp (long[] A) {
    // printArray(A);

    // Finds the two largest numbers
    int[] largestIndices = twoMaxNums(A);
    // System.out.println("Two largest indices: " + largestIndices[0] + " " + largestIndices[1]);

    // Base case
    if (A[largestIndices[1]] == 0) {
      return A[largestIndices[0]];
    }

    // Gets their difference
    long residue = Math.abs(A[largestIndices[0]] - A[largestIndices[1]]);
    // System.out.println("Residue: " + residue);

    // Replace with residue and/or zero
    A[largestIndices[0]] = residue;
    A[largestIndices[1]] = 0;

    return KarmarkarKarp(A);
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
    // // Checks the flags
    // if(args.length != 1) {
    //   System.out.println("Output should be of the form 'java strassen <inputfile>'");
    //   return;
    // }

    // // Loads flags into memory
    // String filename = args[0];

    // // Initialization
    // int[] A = new int[100];
    // BufferedReader in = null;

    // // Reading the inputfile
    // try {
    //   int num;

    //   // Contains the file
    //   in = new BufferedReader(new FileReader(filename));

    //   // Populates our array
    //   for (int i = 0; i < A.length; i++) {
    //     num = Integer.parseInt(in.readLine(), 10);
    //     A[i] = num;
    //   }

    //   in.close();
    // }
    // // Fileread errors
    // catch (Exception e) {
    //   System.out.println("Exception occurred reading " + filename);
    //   e.printStackTrace();
    //   return;
    // }
    // System.out.println("Residue: " + KarmarkarKarp(A));
  }
}