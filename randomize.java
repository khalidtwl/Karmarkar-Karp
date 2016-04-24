import java.util.Random;

class randomize {

  private static final int max_iter = 25000;
  private static final double t_iter = 10000000000.0 * Math.pow(0.8, (double) max_iter/ (double) 300.0);
  private long[] a;
  private int length;
  private Random rnd = new Random(System.nanoTime());
  private kk k = new kk();

  public randomize(long[] a) {
    this.a = a;
    this.length = a.length;
  }

  public long repeatedrandomS() {
    int[] s = randomS();
    long resd_s = residue(s);
    for (int i = 0; i < max_iter; i++) {
      int[] sp = randomS();
      long resd_sp = residue(sp);
      if (resd_sp < resd_s) {
        s = sp;
        resd_s = resd_sp;
      }
    }
    return resd_s;
  }

  public long repeatedrandomP() {
    int[] p = randomP();
    long[] ap = aprime(p);
    long resd_ap = k.KarmarkarKarp(ap);
    for (int i = 0; i < max_iter; i++) {
      int[] pp = randomP();
      long[] app = aprime(pp);
      long resd_app = k.KarmarkarKarp(app);
      if (resd_app < resd_ap) {
        p = pp;
        resd_ap = resd_app;
      }
    }
    return resd_ap;
  }

  public long hillclimbingS() {
    int[] s = randomS();
    long resd_s = residue(s);
    for (int i = 0; i < max_iter; i++) {
      int[] sp = neighborS(s);
      long resd_sp = residue(sp);
      if (resd_sp < resd_s) {
        s = sp;
        resd_s = resd_sp;
      }
    }
    return resd_s;
  }

  public long hillclimbingP() {
    int[] p = randomP();
    long[] ap = aprime(p);
    long resd_ap = k.KarmarkarKarp(ap);
    for (int i = 0; i < max_iter; i++) {
      int[] pp = neighborP(p);
      long[] app = aprime(pp);
      long resd_app = k.KarmarkarKarp(app);
      if (resd_app < resd_ap) {
        p = pp;
        resd_ap = resd_app;
      }
    }
    return resd_ap;
  }

	public long simulatedannealingS() {
    int[] s = randomS();
    int[] spp = s;
    long resd_s = residue(s);
    long resd_spp = resd_s;

    for (int i = 0; i < max_iter; i++) {
      int[] sp = neighborS(s);
      long resd_sp = residue(sp);
      double prob = Math.exp((-(double)resd_s-(double)resd_sp)/t_iter);
      if (resd_sp < resd_s || rnd.nextDouble() < prob) {
        s = sp;
        resd_s = resd_sp;
      }
      if (resd_s < resd_spp) {
        spp = s;
        resd_spp = resd_s;
      }
    }
    return resd_spp;
  }

  public long simulatedannealingP() {
    int[] p = randomP();
    int[] ppp = p;
    long[] ap = aprime(p);
    long resd_ap = k.KarmarkarKarp(ap);
    long resd_appp = resd_ap;
    for (int i = 0; i < max_iter; i++) {
      int[] pp = neighborP(p);
      long[] app = aprime(pp);
      long resd_app = k.KarmarkarKarp(app);
      double prob = Math.exp((-(double)resd_ap-(double)resd_app)/t_iter);
      if (resd_app < resd_ap || rnd.nextDouble() < prob) {
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


  private int[] randomS() {
    int[] s = new int[length];
    for (int i = 0; i < length; i++) {
      if (rnd.nextDouble() < 0.5) {
        s[i] = -1;
      }
      else {
        s[i] = 1;
      }
    }
    return s;
  }

  private int[] randomP() {
    int[] p = new int[length];
    for (int i = 0; i < length; i++) {
      p[i] = rnd.nextInt(length);
    }
    return p;
  }

  private long[] aprime(int[] p) {
    long[] ap = new long[length];
    for (int i = 0; i < length; i++) {
      ap[p[i]] += a[i];
    }
    return ap;
  }

  private int[] neighborS(int[] s) {

    int[] neighborS = new int[length];
    System.arraycopy(s, 0, neighborS, 0, length);

    int i = rnd.nextInt(length);
    int j;
    do {
      j = rnd.nextInt(length);
    } while (i == j);

    neighborS[i] = -neighborS[i];
    if (rnd.nextDouble() < 0.5) {
      neighborS[j] = -neighborS[j];
    }

    return neighborS;
	}

  private int[] neighborP(int[] p) {
    int[] neighborP = new int[length];
    System.arraycopy(p, 0, neighborP, 0, length);
    int i;
    int j;
    do {
      i = rnd.nextInt(length);
      do {
        j = rnd.nextInt(length);
      } while (i == j);
    } while (neighborP[i] == j);
    neighborP[i] = j;
    return neighborP;
  }

  private long residue(int[] s) {
    long resd = 0;
    for (int i = 0; i < length; i++) {
      resd += a[i] * s[i];
    }

    if (resd < 0) {
      resd *= -1;
    }

    return resd;
  }

  public static void main(String[] args) {
    long[] numlist = {10,8,7,6,5};
    randomize r = new randomize(numlist);
    System.out.println("repeated random S: " + r.repeatedrandomS());
    System.out.println("repeated random P: " + r.repeatedrandomP());
    System.out.println("hill climbing S: " + r.hillclimbingS());
    System.out.println("hill climbing P: " + r.hillclimbingP());
    System.out.println("simulated annealing S: " + r.simulatedannealingS());
    System.out.println("simulated annealing P: " + r.simulatedannealingP());
  }

}
