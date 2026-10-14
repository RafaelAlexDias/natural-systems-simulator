package tools;

/**
 * Counts samples into discrete bins and reports simple statistics over them.
 *
 * <p>The supplied data is expected to contain class indices in the range
 * {@code [0, nbins)}; each index is tallied directly into the matching bin.
 */
public class Histogram {
    int[] hist;
    int nbins;

    public Histogram(int[] data, int nbins) {
        this.nbins = nbins;
        hist = new int[nbins];
        for (int i=0; i<data.length; i++) {
            hist[data[i]]++;
        }
    }

    /** @return the per-bin counts gathered so far. */
    public int[] getDistribution() {
        return hist;
    }

    /**
     * Returns the most frequent bin.
     *
     * @param preference bin index returned when it ties with the most frequent
     *                   bin, letting callers favour a reference class
     * @return the index of the most frequent bin, or {@code preference} on a tie
     */
    public int getMode(int preference) {
        int maxVal = 0;
        int mode = 0;
        for (int i=0; i<nbins; i++) {
            if (hist[i] > maxVal) {
                maxVal = hist[i];
                mode = i;
            }
        }
        if (hist[preference] == hist[mode]) {
            return preference;
        }
        return mode;
    }

    /** Prints each bin count, one per line. */
    public void display() {
        for (int i=0; i<nbins; i++) {
            System.out.println(hist[i]);
        }
        System.out.println();
    }
}
