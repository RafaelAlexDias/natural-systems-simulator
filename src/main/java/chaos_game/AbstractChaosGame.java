package chaos_game;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared skeleton for every chaos-game variant.
 *
 * <p>The chaos game always follows the same recipe: pick a vertex of a polygon,
 * move a fraction of the distance towards it and plot the new point. Subclasses
 * only describe <em>which</em> polygon to use, <em>how much</em> to move and, if
 * needed, <em>how</em> the next vertex is selected.
 */
public abstract class AbstractChaosGame implements IProcessingApp {

    /** Points drawn per frame, used to speed up the rendering. */
    protected static final int ITERATIONS_PER_FRAME = 100;

    /** Radius of the polygon built around the centre of the window. */
    protected static final float SHAPE_RADIUS = 200f;

    protected final List<PVector> vertices = new ArrayList<>();

    protected PApplet p;
    protected float x;
    protected float y;

    @Override
    public final void setup(PApplet p) {
        this.p = p;
        resetScene();
    }

    /**
     * Clears the window and rebuilds the polygon plus the initial point.
     * Used both on setup and whenever a subclass needs to restart the drawing.
     */
    protected final void resetScene() {
        p.background(255);
        vertices.clear();
        buildVertices(vertices);
        drawVertices();
        drawInitialPoint();
    }

    /** Fills {@code target} with the vertices of the polygon to play with. */
    protected abstract void buildVertices(List<PVector> target);

    /** Fraction of the distance the point moves towards the chosen vertex. */
    protected abstract float stepFactor();

    /** @return the index of the next vertex to move towards. */
    protected int nextVertexIndex() {
        return (int) p.random(vertices.size());
    }

    @Override
    public void draw(PApplet p, float dt) {
        for (int i = 0; i < ITERATIONS_PER_FRAME; i++) {
            PVector target = vertices.get(nextVertexIndex());
            x = PApplet.lerp(x, target.x, stepFactor());
            y = PApplet.lerp(y, target.y, stepFactor());
            p.point(x, y);
        }
    }

    protected void drawVertices() {
        for (PVector vertex : vertices) {
            p.point(vertex.x, vertex.y);
        }
    }

    protected void drawInitialPoint() {
        p.point(p.random(p.width), p.random(p.height));
    }

    /**
     * Adds the vertices of a regular polygon centred in the window.
     *
     * @param sides  number of vertices
     * @param radius distance from the centre to each vertex
     */
    protected final void buildRegularPolygon(List<PVector> target, int sides, float radius) {
        float angleOffset = PApplet.TWO_PI / sides;

        for (int i = 0; i < sides; i++) {
            float angle = i * angleOffset;
            target.add(new PVector(
                    p.width / 2f + radius * PApplet.cos(angle),
                    p.height / 2f + radius * PApplet.sin(angle)
            ));
        }
    }
}
