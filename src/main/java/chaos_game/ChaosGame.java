package chaos_game;

import processing.core.PVector;

import java.util.List;

/**
 * Classic Sierpinski triangle: a three-vertex chaos game where every vertex may
 * be picked in consecutive iterations.
 */
public class ChaosGame extends AbstractChaosGame {

    @Override
    protected void buildVertices(List<PVector> target) {
        target.add(new PVector(p.width / 2f, 0));
        target.add(new PVector(0, p.height));
        target.add(new PVector(p.width, p.height));
    }

    @Override
    protected float stepFactor() {
        return 0.5f;
    }
}
