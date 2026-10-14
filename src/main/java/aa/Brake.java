package aa;

import processing.core.PVector;

/**
 * Brake behavior that attempts to stop the boid by producing zero desired velocity.
 */
public class Brake extends Behavior {
    /**
     * Creates a new brake behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Brake(float weight) {
        super(weight);
    }

    /**
     * Returns zero desired velocity to slow or stop the boid.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        return new PVector();
    }
}
