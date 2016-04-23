public class MaxHeap {
  long[] heap;

  // Given an unordered array, makes it into a max-heap
  public MaxHeap (long[] A) {
    heap = A;
    for (int i = A.length / 2; i >= 1; i--)
    {
      MaxHeapify(this, i);
    }
  }

  // Rearranges the tree rooted at N to be a Max-Heap
  public static void MaxHeapify (MaxHeap H, int N) {
    int largest;
    int l = H.Left(N);
    int r = H.Right(N);
    if (H.isValid(l) && H.get(l) > H.get(N))
      largest = l;
    else
      largest = N;
    if (H.isValid(r) && H.get(r) > H.get(largest))
      largest = r;
    if (largest != N)
    {
      H.swap(N, largest);
      MaxHeapify(H, largest);
    }
  }

  // The following works with indices
  public int Parent(int i) {
    return (i / 2);
  }

  public int Left(int i) {
    return (2 * i);
  }

  public int Right(int i) {
    return (2 * i + 1);
  }

  public long get(int i) {
    return heap[i - 1];
  }

  public void change (int i, long l) {
    heap[i - 1] = l;
  }

  // Makes sure the given index is valid
  public boolean isValid(int i) {
    return (i > 0 && i <= heap.length);
  }

  // Swaps two elements in our heap
  public void swap(int a, int b) {
    long temp = heap[a - 1];
    heap[a - 1] = heap[b - 1];
    heap[b - 1] = temp;
  }

  // Prints out the heap as an array
  public static void printHeap(MaxHeap H) {
    kk.printArray(H.heap);
  }

  public static void main(String[] args) {
    long[] A = {2, 1, 4, 3, 6, 5};
    MaxHeap H = new MaxHeap(A);
  }
}
