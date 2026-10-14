package tools;

/**
 * Affine mapping between a rectangular world window and a pixel viewport.
 *
 * <p>The world window is given as {@code [xmin, xmax, ymin, ymax]} and the
 * viewport as {@code [x, y, width, height]} in normalised {@code [0, 1]}
 * coordinates. The class converts world points to pixel coordinates (and back)
 * and computes the bounding boxes used for drawing.
 */
public class SubPlot {

    private double[] window;
    private float[] viewport;
    private double mx;
    private double bx;
    private double my;
    private double by;

    /**
     * Creates a mapping for the given world window and viewport over a
     * {@code fullwidth x fullheight} drawing surface.
     */
    public SubPlot(double[] window, float[] viewport, float fullwidth, float fullheight) {
        this.window = window;
        this.viewport = viewport;
        mx = viewport[2] * fullwidth / (window[1] - window[0]);
        bx = viewport[0] * fullwidth;
        my = -viewport[3] * fullheight / (window[3] - window[2]);
        by = (1 - viewport[1]) * fullheight;
    }

    /** Converts a world point to pixel coordinates. */
    public float[] getPixelCoord(double x, double y) {
        float[] coord = new float[2];

        coord[0] = (float) (bx + mx * (x - window[0]));
        coord[1] = (float) (by + my * (y - window[2]));

        return coord;
    }

    /** Converts a world point {@code [x, y]} to pixel coordinates. */
    public float[] getPixelCoord(double[] xy) {
        return getPixelCoord(xy[0], xy[1]);
    }

    /** Converts pixel coordinates back to a world point. */
    public double[] getWorldCoord(float xx, float yy) {
        double[] coord = new double[2];

        coord[0] = window[0] + (xx - bx) / mx;
        coord[1] = window[2] + (yy - by) / my;

        return coord;
    }

    /** Converts a pixel point {@code [x, y]} to world coordinates. */
    public double[] getWorldCoord(float[] xy) {
        return getWorldCoord(xy[0], xy[1]);
    }

    /** Returns whether the given pixel coordinates fall inside the world window. */
    public boolean isInside(float xx, float yy) {
        double[] c = getWorldCoord(xx, yy);

        return (c[0] >= window[0] && c[0] <= window[1] &&
                c[1] >= window[2] && c[1] <= window[3]);
    }

    /** Returns whether the given pixel point falls inside the world window. */
    public boolean isInside(float[] xy) {
        return isInside(xy[0], xy[1]);
    }

    /** Returns the viewport as {@code [x, y, width, height]} in pixel coordinates. */
    public float[] getBoundingBox() {
        float[] c1 = getPixelCoord(window[0], window[2]);
        float[] c2 = getPixelCoord(window[1], window[3]);
        float[] box = {c1[0], c2[1], c2[0] - c1[0], c1[1] - c2[1]};

        return box;
    }

    /**
     * Returns the pixel box of a world rectangle whose lower-left corner is at
     * {@code (cx, cy)}.
     */
    public float[] getBox(double cx, double cy, double dimx, double dimy) {
        float[] c1 = getPixelCoord(cx, cy);
        float[] c2 = getPixelCoord(cx + dimx, cy + dimy);
        float[] box = {c1[0], c2[1], c2[0] - c1[0], c1[1] - c2[1]};

        return box;
    }

    /** Returns the pixel box of a world rectangle given as {@code [x, y, width, height]}. */
    public float[] getBox(double[] b) {
        return getBox(b[0], b[1], b[2], b[3]);
    }

    /** Converts a world vector to pixel units, ignoring translation. */
    public float[] getVectorCoord(double dx, double dy) {
        float[] v = new float[2];
        v[0] = (float)(dx*mx);
        v[1] = (float)(-dy*my);

        return v;
    }

    /** Converts a world vector {@code [dx, dy]} to pixel units. */
    public float[] getVectorCoord(double[] dxdy) {
        return getVectorCoord(dxdy[0], dxdy[1]);
    }

    /** @return the normalised viewport {@code [x, y, width, height]}. */
    public float[] getViewport() {
        return viewport;
    }

    /** @return the world window {@code [xmin, xmax, ymin, ymax]}. */
    public double[] getWindow() {
        return window;
    }

    /** Replaces the viewport; the affine mapping is not recomputed. */
    public void setViewport(float[] viewport) {
        this.viewport = viewport;
    }

    /** Replaces the world window; the affine mapping is not recomputed. */
    public void setWindow(double[] window) {
        this.window = window;
    }
}
