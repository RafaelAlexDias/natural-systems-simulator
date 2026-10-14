package tools;

/**
 * Draws random class indices from a discrete probability distribution.
 *
 * <p>The constructor condenses the supplied probability mass function into a
 * cumulative distribution, which {@link #getRandomClass()} then inverts to turn
 * a uniform random draw into a class index.
 */
public class CustomRandomGenerator {

    private double[] cdf;

    /** Builds the cumulative distribution from the given probability mass function. */
    public CustomRandomGenerator(double[] pmf) {
        cdf = new double[pmf.length];
        cdf[0] = pmf[0];
        for(int i=1; i<pmf.length; i++) {
            cdf[i] = cdf[i-1] + pmf[i];
        }
    }

    /** @return a class index drawn according to the distribution. */
    public int getRandomClass() {
        double r = Math.random();
        int val = 0;
        for(int i=0; i<cdf.length; i++) {
            if(r>cdf[i]) {
                val++;
            }
            else {
                break;
            }
        }
        return val;
    }
}
