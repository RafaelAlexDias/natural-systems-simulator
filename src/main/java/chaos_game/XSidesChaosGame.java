package chaos_game;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

/**
 * Chaos game whose polygon can be changed at runtime.
 *
 * <p>Pressing a number between 5 and 9 rebuilds the shape with that many sides
 * and the matching step factor.
 */
public class XSidesChaosGame extends AbstractChaosGame {

    private static final int MIN_SIDES = 5;
    private static final int MAX_SIDES = 9;

    /** Step factor for polygons with 5 to 9 sides. */
    private static final float[] STEP_FACTORS = {
            0.618f, 0.667f, 0.692f, 0.707f, 0.742f
    };

    private int sides = 6;
    private float stepFactor = STEP_FACTORS[6 - MIN_SIDES];

    @Override
    protected void buildVertices(List<PVector> target) {
        buildRegularPolygon(target, sides, SHAPE_RADIUS);
    }

    @Override
    protected float stepFactor() {
        return stepFactor;
    }

    @Override
    public void keyPressed(PApplet p) {
        int requestedSides = p.key - '0';

        if (requestedSides < MIN_SIDES || requestedSides > MAX_SIDES) {
            return;
        }

        sides = requestedSides;
        stepFactor = STEP_FACTORS[requestedSides - MIN_SIDES];
        resetScene();
    }
}
