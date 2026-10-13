package chaos_game;

import processing.core.PVector;

import java.util.List;

/**
 * Chaos game played on a pentagon with the extra rule that the same vertex
 * cannot be picked twice in a row.
 */
public class PentagonChaosGame extends AbstractChaosGame {

    private static final int SIDES = 5;

    private int lastVertexIndex = -1;

    @Override
    protected void buildVertices(List<PVector> target) {
        buildRegularPolygon(target, SIDES, SHAPE_RADIUS);
    }

    @Override
    protected float stepFactor() {
        return 0.5f;
    }

    @Override
    protected int nextVertexIndex() {
        int index;

        do {
            index = super.nextVertexIndex();
        } while (index == lastVertexIndex);

        lastVertexIndex = index;
        return index;
    }
}
