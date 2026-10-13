package dla;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

/**
 * A single particle of the Diffusion-Limited Aggregation simulation.
 *
 * <p>A walker is either wandering randomly or stopped (stuck to the cluster).
 * When a wandering walker gets close enough to any stopped walker it freezes.
 */
public class Walker {

    public enum State {
        STOPPED,
        WANDER
    }

    private static final int RADIUS = 5;
    private static final float CENTER_PULL = 0.0002f;

    private final PVector pos;
    private State state;

    /** Creates a stopped walker at a fixed position (a cluster seed). */
    public Walker(int x, int y) {
        this.pos = new PVector(x, y);
        this.state = State.STOPPED;
    }

    /** Creates a wandering walker at a random position. */
    public Walker(PApplet p) {
        this.pos = new PVector(p.random(p.width), p.random(p.height));
        this.state = State.WANDER;
    }

    public State getState() {
        return state;
    }

    /** Stops this walker if it touches any stopped walker in the cluster. */
    public void updateState(List<Walker> walkers) {
        if (state == State.STOPPED) {
            return;
        }

        for (Walker other : walkers) {
            boolean touching = other.state == State.STOPPED
                    && PVector.dist(pos, other.pos) < 2 * RADIUS;

            if (touching) {
                state = State.STOPPED;
                return;
            }
        }
    }

    /** Performs one random step, slightly biased towards the centre. */
    public void wander(PApplet p) {
        if (state != State.WANDER) {
            return;
        }

        pos.add(PVector.random2D());
        pos.lerp(new PVector(p.width / 2f, p.height / 2f), CENTER_PULL);
        pos.x = PApplet.constrain(pos.x, 0, p.width);
        pos.y = PApplet.constrain(pos.y, 0, p.height);
    }

    public void display(PApplet p) {
        p.circle(pos.x, pos.y, RADIUS * 2);
    }
}
