import java.util.Random;

class randomize {

  private static final int max_iter = 25000;
  private static final double t_iter = 10000000000.0 * Math.pow(0.8, 83.0);
  private Random rng = new Random(System.nanoTime());
  private kk k = new kk();

  public randomize(){}

  // return a residue from repeated random algorithm using P representation
  public long repeatedrandomS(long[] a) {
    int length = a.length;
    int[] s = randomS(length);
    long resd_s = residue(s, a);
    for (int i = 0; i < max_iter; i++) {
      int[] sp = randomS(length);
      long resd_sp = residue(sp, a);
      if (resd_sp < resd_s) {
        s = sp;
        resd_s = resd_sp;
      }
    }
    return resd_s;
  }

  // return a residue from hill climbing algorithm using S representation
  public long hillclimbingS(long[] a) {
    int length = a.length;
    int[] s = randomS(length);
    long resd_s = residue(s, a);
    for (int i = 0; i < max_iter; i++) {
      int[] sp = neighborS(s);
      long resd_sp = residue(sp, a);
      if (resd_sp < resd_s) {
        s = sp;
        resd_s = resd_sp;
      }
    }
    return resd_s;
  }

    // return a residue from repeated random algorithm using P representation
  public long repeatedrandomP(long[] a) {
    int length = a.length;
    int[] p = randomP(length);
    long[] ap = aprime(p, a);
    long resd_p = k.KarmarkarKarp(new MaxHeap(ap));
    for (int i = 0; i < max_iter; i++) {
      int[] pp = randomP(length);
      long[] app = aprime(pp, a);
      long resd_pp = k.KarmarkarKarp(new MaxHeap(app));
      if (resd_pp < resd_p) {
        p = pp;
        resd_p = resd_pp;
      }
    }
    return resd_p;
  }

    // return a residue from simulated annealing algorithm using S representation
  public long simulatedannealingS(long[] a) {
    int length = a.length;
    int[] s = randomS(length);
    int[] spp = s;
    long resd_s = residue(s, a);
    long resd_spp = resd_s;

    for (int i = 0; i < max_iter; i++) {
      int[] sp = neighborS(s);
      long resd_sp = residue(sp, a);
      double prob = Math.exp((-((double)resd_sp-(double)resd_s))/t_iter);
      if (resd_sp > resd_s && prob > 0.001)
        System.out.println(resd_s + " " + resd_sp + " " + prob);
      if (resd_sp < resd_s || rng.nextDouble() < prob) {
        s = sp;
        resd_s = resd_sp;
      }
      if (resd_s < resd_spp) {
        spp = s;
        resd_spp = resd_s;
      }
    }
    // printArray(spp);
    return resd_spp;
  }

  // return a residue from hill climbing algorithm using P representation
  public long hillclimbingP(long[] a) {
    int length = a.length;
    int[] p = randomP(length);
    long[] ap = aprime(p, a);
    long resd_ap = k.KarmarkarKarp(new MaxHeap(ap));
    for (int i = 0; i < max_iter; i++) {
      int[] pp = neighborP(p);
      long[] app = aprime(pp, a);
      long resd_app = k.KarmarkarKarp(new MaxHeap(app));
      if (resd_app < resd_ap) {
        p = pp;
        resd_ap = resd_app;
      }
    }
    return resd_ap;
  }


  // return a residue from simulated annealing algorithm using P representation
  public long simulatedannealingP(long[] a) {
    int length = a.length;
    int[] p = randomP(length);
    int[] ppp = p;
    long[] ap = aprime(p, a);
    long resd_ap = k.KarmarkarKarp(new MaxHeap(ap));
    long resd_appp = resd_ap;
    for (int i = 0; i < max_iter; i++) {
      int[] pp = neighborP(p);
      long[] app = aprime(pp, a);
      long resd_app = k.KarmarkarKarp(new MaxHeap(app));
      double prob = Math.exp((-((double)resd_app-(double)resd_ap))/t_iter);
      if (resd_app < resd_ap || rng.nextDouble() < prob) {
        p = pp;
        resd_ap = resd_app;
      }
      if (resd_ap < resd_appp) {
        ppp = p;
        resd_appp = resd_ap;
      }
    }
    return resd_appp;
  }

  // return a random S
  private int[] randomS(int length) {
    int[] s = new int[length];
    for (int i = 0; i < length; i++) {
      if (rng.nextDouble() < 0.5) {
        s[i] = -1;
      }
      else {
        s[i] = 1;
      }
    }
    return s;
  }

  // return a random prepartitioning P
  private int[] randomP(int length) {
    int[] p = new int[length];
    for (int i = 0; i < length; i++) {
      p[i] = rng.nextInt(length);
    }
    return p;
  }

  // return a new sequence A' from A which enforces the prepartitioning from P
  private long[] aprime(int[] p, long[] a) {
    int length = p.length;
    long[] ap = new long[length];
    for (int i = 0; i < length; i++) {
      ap[p[i]] += a[i];
    }
    return ap;
  }

  // return a random neighbor of S as defined in the assignment
  private int[] neighborS(int[] s) {
    int length = s.length;
    int[] neighborS = new int[length];
    System.arraycopy(s, 0, neighborS, 0, length);

    int i;
    int j;
    do {
      i = rng.nextInt(length);
      j = rng.nextInt(length);
    } while (i == j);

    neighborS[i] = -neighborS[i];
    if (rng.nextDouble() < 0.5) {
      neighborS[j] = -neighborS[j];
    }

    return neighborS;
	}

  // return a random neighbor of P as defined in the assignment
  private int[] neighborP(int[] p) {
    int length = p.length;
    int[] neighborP = new int[length];
    System.arraycopy(p, 0, neighborP, 0, length);
    int i;
    int j;
    do {
      i = rng.nextInt(length);
      j = rng.nextInt(length);
    } while (i == j || neighborP[i] == j);
    neighborP[i] = j;
    return neighborP;
  }

  // given S and A, return the residue
  private long residue(int[] s, long[] a) {
    long resd = 0;
    for (int i = 0, length = s.length; i < length; i++) {
      resd += a[i] * s[i];
    }

    if (resd < 0) {
      resd *= -1;
    }
    return resd;
  }

  // return an array of 100 random longs
  private long[] rand_100longs() {
    long[] result = new long[100];
    for (int i = 0; i < 100; i++) {
      result[i] = nextLong(1000000000000L) + 1L;
    }
    return result;
  }

  // http://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
  // return the random long from [0,n] 
  private long nextLong(long n) {
    long bits, val;
    do {
      bits = (rng.nextLong() << 1) >>> 1;
      val = bits % n;
    } while (bits-val+(n-1) < 0L);
    return val;
  }

  // run tests to find residues for all algorithms
  public void run_tests() {
    long startTime = System.nanoTime();
    for (int i = 0; i < 50; i++) {
      long[] a = rand_100longs();
      int length = a.length;
      long[] a_clone = new long[length];

      System.arraycopy(a, 0, a_clone, 0, length);
      long kk_resd = k.KarmarkarKarp(new MaxHeap(a_clone));

      System.arraycopy(a, 0, a_clone, 0, length);
      long repeatedrandomS_resd = repeatedrandomS(a_clone);

      System.arraycopy(a, 0, a_clone, 0, length);
      long repeatedrandomP_resd = repeatedrandomP(a_clone);

      System.arraycopy(a, 0, a_clone, 0, length);
      long hillclimbingS_resd = hillclimbingS(a_clone);

      System.arraycopy(a, 0, a_clone, 0, length);
      long hillclimbingP_resd = hillclimbingP(a_clone);

      System.arraycopy(a, 0, a_clone, 0, length);
      long simulatedannealingS_resd = simulatedannealingS(a_clone);

      System.arraycopy(a, 0, a_clone, 0, length);
      long simulatedannealingP_resd = simulatedannealingP(a_clone);

      System.out.println(kk_resd + ", " + repeatedrandomS_resd + ", " + repeatedrandomP_resd + ", " + 
        hillclimbingS_resd + ", " + hillclimbingP_resd + ", " + simulatedannealingS_resd 
        + ", " + simulatedannealingP_resd);
    }
    long endTime = System.nanoTime();
    System.out.println("Total Time: " + (endTime-startTime)/1000000000.0 + " seconds.");
  }

  // public static void main(String[] args) {
  //   randomize r = new randomize();
  //   r.run_tests();
  // }

}
