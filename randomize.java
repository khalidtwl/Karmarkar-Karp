import java.util.Random;

class randomize {

  private static final int max_iter = 25000;
  private static final double t_iter = 10000000000.0 * Math.pow(0.8, (double) max_iter/ (double) 300.0);
  private long[] numlist;
  private int length;
  private Random rnd = new Random(System.nanoTime());

  public randomize(long[] numlist) {
    this.numlist = numlist;
    this.length = numlist.length;
  }

  public long[] repeatedrandomS() {
    long[] s = randomS();
    long resd_s = residue(s);
    for (int i = 0; i < max_iter; i++) {
      long[] sp = randomS();
      long resd_sp = residue(sp);
      if (resd_sp < resd_s) {
        s = sp;
        resd_s = resd_sp;
      }
    }
    return s;
  }

  public void repeatedrandomN() {
    kk k = new kk();
    System.out.println(k.KarmarkarKarp(numlist));
  }

  public long[] hillclimbingS() {
    long[] s = randomS();
    long resd_s = residue(s);
    for (int i = 0; i < max_iter; i++) {
      long[] sp = neighborS(s);
      long resd_sp = residue(sp);
      if (resd_sp < resd_s) {
        s = sp;
        resd_s = resd_sp;
      }
    }
    return s;
  }

	public long[] simulatedannealingS() {
    long[] s = randomS();
    long[] spp = s;
    long resd_s = residue(s);
    long resd_spp = resd_s;

    for (int i = 0; i < max_iter; i++) {
      long[] sp = neighborS(s);
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
    return spp;
  }


  private long[] randomS() {
    long[] s = new long[length];
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

  private long[] neighborS(long[] s) {

    long[] neighborS = new long[length];
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

  private long residue(long[] s) {
    long resd = 0; 
    for (int i = 0; i < length; i++) {
      resd += numlist[i] * s[i];
    }

    if (resd < 0) {
      resd *= -1;
    }

    return resd;
  }

	public static void main(String[] args) {
		long[] a = {10,8,7,6,5};
		int len = a.length;


		randomize r = new randomize(a);

		long[] s = r.simulatedannealingS();
		for (int i = 0; i < len; i++){
			System.out.print(s[i] + " ");
		}

	}	

}