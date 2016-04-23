import java.util.Arrays;

public class Assignment {

  public static int KarmarkarKarp (int[] A)
  {
    printArray(A);

    // Finds the two largest numbers
    int[] largestIndices = twoMaxNums(A);
    System.out.println("Two largest indices: " + largestIndices[0] + " " + largestIndices[1]);

    // Base case
    if (A[largestIndices[1]] == 0) {
      return A[largestIndices[0]];
    }

    // Gets their difference
    int residue = Math.abs(A[largestIndices[0]] - A[largestIndices[1]]);
    System.out.println("Residue: " + residue);

    // Replace with residue and/or zero
    A[largestIndices[0]] = residue;
    A[largestIndices[1]] = 0;

    return KarmarkarKarp(A);
  }

  // Returns the indices of the two largest elements
  public static int[] twoMaxNums(int[] A) {
    int largest = 0;
    int secondLargest = 1;

    for (int i = 0, end = A.length; i < end; i++)
    {
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
  public static void printArray(int[] A){
    String result = "";
    for(int i = 0, end = A.length; i < end; i++)
    {
      result += A[i] + " ";
    }
    System.out.println(result);
  }

  public static void main(String[] args)
  {
    int[] A = {8, 10, 7, 6, 5};
    System.out.println("Residue: " + KarmarkarKarp(A));
  }
}
