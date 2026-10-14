package tools;

/**
 * Standalone demo of {@link Histogram}.
 *
 * <p>Generates random class indices, tallies them into a histogram and prints
 * the per-bin counts followed by the most frequent class.
 */
public class TestHistogram {

    private static int nclasses = 5;
    private static int nsamples = 10;

    public static void main(String[] args) {
        // Draw random class indices in the range [0, nclasses).
        int[] data = new int[nsamples];
        for (int i=0; i<nsamples; i++) {
            data[i] = (int) (nclasses* Math.random());
        }
        Histogram h = new Histogram(data, nclasses);

        // Print the per-bin counts and then the most frequent class.
        int[] d = h.getDistribution();
        for (int i=0; i<nclasses; i++) {
            System.out.println(d[i]);
        }
        System.out.println("");
        System.out.println(h.getMode(0));
    }
}
